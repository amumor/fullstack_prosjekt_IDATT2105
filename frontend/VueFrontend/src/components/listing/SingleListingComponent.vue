<script setup>
import {ref, onMounted, watch, computed} from 'vue';
import {useRouter} from 'vue-router';
import {Icon} from '@iconify/vue'
import {storeToRefs} from 'pinia';

import ListingMapComponent from '@/components/listing/ListingMapComponent.vue'

import { userStore } from '@/stores/user.js'
import { getListingById, deleteListing, updateListing } from '@/services/ListingService.js'
import { createBookmark, deleteBookmark, getUserBookmarks } from '../../services/BookmarkService';
import { useListingStore } from '@/stores/listing.js'
import { getAddressFromCoordinates } from '@/utils/Location.js'
import { format } from '@/utils/DateTimeFormat.js'
import { fetchImage } from '@/services/ImageService.js'
import { createChatFromBuyer } from '@/services/ChatService.js'

const listing = ref(null); 
const image = 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg';


const user = userStore()
const router = useRouter();
const token = user.token;
const favorites = ref([]);
const isFavorite = ref();
const isArchived = ref(false);
const address = ref('Loading...');

// Verify token
const checkToken = () => {
  if (!token && user.isLoggedIn) {
    try {
      user.logout();
    } catch (err) {
      console.error('Error logging out:', err);
    }
    console.error('Token is expired, user logged out');
  }
}
checkToken();


// Fetch listing id
const isOwner = ref(false);
onMounted(async () => {
  const listingStore = useListingStore();
  const {id} = storeToRefs(listingStore);

  if (!id.value) {
    console.error('Listing ID is null');
    return;
  }
  try {
    // Fetch listing
    listing.value = await getListingById(id.value);
  } catch (err) {
    console.error('Listing not found:', err);
  }

  // Check if the user is the owner of the listing
  if (user.isLoggedIn && listing.value) {
    if (user.firstName === listing.value.sellerFirstName && user.lastName === listing.value.sellerLastName) {
      isOwner.value = true;
    } else {
      isOwner.value = false;
    }
  } else {
    isOwner.value = false;
  }

  // Fetch address if coordinates are available
  if (listing.value.latitude && listing.value.longitude) {
    const fetchedAddress = await getAddressFromCoordinates(
        listing.value.latitude,
        listing.value.longitude
    );
    address.value = fetchedAddress || 'No address found';
  }

  console.log("Current Listing status: ", listing.value.listingStatus)
  if (listing.value.listingStatus == "ACTIVE") {
    isArchived.value = false;
  } if (listing.value.listingStatus == "INACTIVE") {
    isArchived.value = true;
  }
});

const getImageUrl = computed(() => {
    if (listing.value && listing.value.imageUrls && listing.value.imageUrls.length > 0) {
      return fetchImage(listing.value.imageUrls);
    }
    return 'https://placehold.co/600x400?text=No+Image';
});

const messageContent = ref('Is this still available?')
const initiateChat = async () => {
  if (!user.isLoggedIn) {
    router.push('/login')
    return
  }
  
  if (isOwner.value) {
    console.warn('You cannot chat with yourself as the listing owner')
    return
  }

  try {
    const token = user.token
    const newChat = await createChatFromBuyer(listing.value.id, messageContent.value, token)
    
    const chatStore = useChatStore()
    chatStore.addChat(newChat)
    
    router.push('/inbox')
  } catch (error) {
    console.error('Error initiating chat:', error)
  }
}


// Fetch user bookmarks only if the user is logged in
if (user.isLoggedIn) {
  checkToken();
  getUserBookmarks(token)
      .then((data) => {
        favorites.value = data;
        checkIfFavorite();
      })
      .catch((err) => {
        console.error('Error fetching bookmarks:', err);
      });
} else {
  console.warn('User is not logged in. Skipping bookmark fetch.');
}

// Favorite button
const checkIfFavorite = () => {
  if (user.isLoggedIn && listing.value) {
    isFavorite.value = favorites.value.some(favorite => favorite.listingId === listing.value.id);
  } else {
    isFavorite.value = false;
  }
}

const toggleFavorite = async () => {
  checkToken();
  // Check the current state
  checkIfFavorite();
  // Delete as favorite
  if (isFavorite.value) {
    const favoriteId = favorites.value.find(f => f.listingId === listing.value.id).id;
    await deleteBookmark(token, favoriteId);
    favorites.value = favorites.value.filter(f => f.listingId !== listing.value.id);
  } else {
    // Add as favorite
    await createBookmark(token, {
      listingId: listing.value.id,
      userId: user.userId
    });
    // Get the new favoriteId
    getUserBookmarks(token)
        .then((data) => {
          favorites.value = data;
        })
        .catch((err) => {
          console.error('Error fetching bookmarks:', err);
        });
  }
  isFavorite.value = !isFavorite.value;
}

watch(favorites, () => {
  checkIfFavorite();
});

// Delete listing
const delListing = () => {
  checkToken();
  if (!isOwner()) {
    console.error("User is not the owner of the listing.");
    return;
  }
  deleteListing(token, listing.value.id)
      .then(() => {
        console.log('Listing deleted successfully!');
        router.back()
      })
      .catch(err => {
        console.error('Error deleting listing:', err);
      });
}

// Route to edit listing
const toEditListing = () => {
  router.push('/listing/update/' + listing.value.id + '/edit');
}

const setStatus = async (status) => {
  const token = user.token;
  if (isTokenExpired(token)) {
    user.logout();
    await router.push("/login")
  }

  const currentValues = await localGetCurrentValues();
  console.log("Current values", currentValues);
  try {
    await updateListing(listing.value.id, {
      title: currentValues.title,
      description: currentValues.description,
      categoryName: currentValues.categoryName,
      listingStatus: status,
      latitude: currentValues.latitude, // TODO: Replace with actual latitude
      longitude: currentValues.longitude, // TODO: Replace with actual longitude
      price: currentValues.price,
    }, token);
  } catch (err) {
    console.error('Error updating listing:', err);
  }
}

const localGetCurrentValues = async () => {
  const listingStore = useListingStore();
  const {id} = storeToRefs(listingStore);

  if (!id.value) {
    console.error('Listing ID is null');
    return;
  }

  try {
    listing.value = await getListingById(id.value);
    if (!listing.value) {
      console.error('Listing not found');
      return null;
    } else {
      return listing.value;
    }

  } catch (err) {
    console.error('Listing not found:', err);
  }
}

// Archive listing
const toggleArchive = async () => {
  checkToken();
  const curValues = await localGetCurrentValues();

  if (curValues.listingStatus === "ACTIVE") {
    try {
      await setStatus('INACTIVE');
    } catch (error) {
      console.error('Error:', error);
    }
  } else if (curValues.listingStatus === "INACTIVE") {
    try {
      await setStatus('ACTIVE');
    } catch (error) {
      console.error('Error:', error);
    }
  }
  isArchived.value = !isArchived.value;
}

const routeToLogin = () => {
  router.push('/login')
}

</script>

<template>
  <div class="toggle-container" id="archive" v-if="isOwner">
    <label class="switch">
      <input type="checkbox" @change="toggleArchive()">
      <span class="slider">
      <template v-if="isArchived">
        <span class="switch-label">{{ $t('listing.activeCL') }}</span>
      </template>
      <template v-else>
        <span class="switch-label">{{ $t('listing.inactiveCL') }}</span>
      </template>
    </span>
   </label>
  </div>
  <div class="display-page-container" v-if="listing">
    <!-- Image container -->
    <div class="image-container">
      <img class="image-item" :src="getImageUrl" alt="Front image">
      <button v-if="user.isLoggedIn" class="favorite" :class="{ 'isFavorite': isFavorite }" @click="toggleFavorite">
        <Icon icon="material-symbols:favorite" width="40" height="40"/>
      </button>
      <p id="lastEdited">{{ $t('listing.last-edited') }}: {{ format(listing.lastEdited, listing.createdAt) }}</p>
    </div>

    <div class="sidebar">

      <!-- Description -->
      <div class="description">
        <h2>{{ listing.title }}</h2>
        <p id="price">{{ listing.price + ' kr' }}</p>
        <p id="description">{{ listing.description }}</p>
        <p id="categories">{{ listing.category }}</p>
        <p id="location">{{ address }}</p>
      </div>

      <!-- Buy item or message seller -->
      <div class="btn" v-if="!isOwner">
        <button v-if="user.isLoggedIn" class="message-btn" @click="initiateChat">
          {{ $t('button.message') }}
        </button>
        <button v-else class="login-btn" @click="routeToLogin">
          {{ $t('button.login-to-message') }}
        </button>
      </div>

      <!-- Owner options -->
      <div class="owner-options">
        <template v-if="isOwner">
          <button class="owner-btn" id="edit" @click="toEditListing">{{ $t('button.edit') }}</button>
          <button class="owner-btn" id="delete" @click=delListing>{{ $t('button.delete') }}</button>
        </template>
      </div>

      <!-- Map -->
      <div class="map">
        <ListingMapComponent
            :location="[listing.latitude, listing.longitude]"/>
      </div>
    </div>
  </div>
</template>

<style scoped>
.display-page-container {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);

  width: 100%;
  display: flex;
}

/* Image container */
.image-container {
  color: #777;
  font-size: 15px;

  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 10px;
  overflow: hidden;
  max-width: 350px;
  margin: 15px;
}

.image-item {
  width: 100%;
  height: 295px;
  object-fit: cover;
  border-radius: 10px;
}

/* Favorite button */
.favorite {
  background: transparent;
  color: rgba(255, 255, 255, 0.8);
  border-radius: 50%;
  border: none;

  position: absolute;
  top: 10px;
  right: 10px;
  padding: 8px;
  cursor: pointer;
}

.isFavorite {
  color: crimson; /* Red when favorite */
}

.favorite:hover {
  color: crimson;
}

/* Description */
.sidebar {
  margin: 10px 15px 15px 15px;
}
.description {
  width: 100%;
  padding: 5px 0;
}

#price {
  font-size: 20px;
  color: #333333;
}

#description, #categories {
  font-size: 15px;
  color: #777;
}

/* Buttons */
.btn,
.owner-options {
  display: flex;
  gap: 10px;
  height: 40px;
  width: 300px;
  margin-bottom: 10px;
}

.buy-btn,
.message-btn,
.owner-btn {
  font-size: 16px;
  transition: all 0.3s ease;

  flex: 1;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  line-height: 10px;
}

.buy-btn,
#edit,
#delete,
.slider {
  background-color: white;
  color: #333333;
  border: #1C64FF 2px solid;
}

.buy-btn:hover,
#edit:hover,
#delete:hover{
  background-color: #D9D9D9;
}

.message-btn {
  background-color: #1C64FF;
  color: white;
}

.message-btn:hover {
  background-color: #0056b3;
}

#delete:hover {
  background-color: crimson;
  border: crimson 2px solid;
}

.login-btn {
  background-color: #D9D9D9;
  color: #333333;
  border: #1C64FF 2px solid;
  font-size: 16px;
  transition: all 0.3s ease;
  flex: 1;
  border-radius: 5px;
  cursor: pointer;
  line-height: 10px;
}

.login-btn:hover {
  background-color: #f1f1f1;
  border-color: #0056b3;
}

.switch {
  position: relative;
  display: inline-block;
  width: 100px;
  height: 40px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0; left: 0; right: 0; bottom: 0;
  transition: 0.4s;
  border-radius: 5px;
  width: 105px;
  height: 40px;
  margin: 0 50px 50px 655px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 30px;
  width: 30px;
  left: 3px;
  bottom: 3px;
  background-color: #1C64FF;
  transition: 0.4s;
  border-radius: 5px;
}

input:checked + .slider:before {
  background-color: #1C64FF;
  transform: translateX(65px);
}

input:checked + .slider .switch-label {
  color: #333333;
  transform: translate(-75%, -50%);
}

.switch-label {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-25%, -50%);
  font-size: 14px;
  color: #333333;
  pointer-events: none;
}

/* Map placeholder */
.map {
  width: 100%;
  height: 150px;
  background-color: #eee;
  border-radius: 10px;
}

/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .display-page-container {
    flex-direction: column; /* Stack the image and sidebar vertically */
    align-items: center; /* Center align items */
    padding: 15px; /* Add padding */
  }

  .image-container {
    max-width: 100%; /* Allow the image to take full width */
    margin: 0 0 15px 0; /* Adjust margin */
  }

  .image-item {
    height: 250px; /* Reduce image height */
  }

  .sidebar {
    width: 100%; /* Take full width */
    margin: 0; /* Remove extra margins */
  }

  .btn,
  .owner-options {
    gap: 10px; /* Add spacing between buttons */
    max-width: 100%; /* Allow buttons to take full width */
  }

  .buy-btn,
  .message-btn,
  .owner-btn {
    font-size: 14px; /* Adjust font size */
    padding: 8px; /* Reduce padding */
  }

  .map {
    height: 120px; /* Reduce map height */
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .display-page-container {
    flex-direction: column; /* Stack the image and sidebar vertically */
    align-items: center; /* Center align items */
    padding: 10px; /* Add padding */
  }

  .image-container {
    max-width: 100%; /* Allow the image to take full width */
    margin: 0 0 10px 0; /* Adjust margin */
  }

  .image-item {
    height: 200px; /* Further reduce image height */
  }

  .sidebar {
    width: 100%; /* Take full width */
    margin: 0; /* Remove extra margins */
  }

  .description {
    padding: 5px; /* Reduce padding */
  }

  #price {
    font-size: 18px; /* Adjust font size */
  }

  #description,
  #categories {
    font-size: 14px; /* Adjust font size */
  }

  .btn,
  .owner-options {
    flex-direction: column; /* Stack buttons vertically */
    gap: 8px; /* Add spacing between buttons */
    max-width: 100%; /* Allow buttons to take full width */
  }

  .buy-btn,
  .message-btn,
  .owner-btn {
    font-size: 12px; /* Adjust font size */
    padding: 6px; /* Reduce padding */
  }

  .map {
    height: 100px; /* Further reduce map height */
  }

  h2 {
    font-size: 16px; /* Adjust heading font size */
    text-align: center; /* Center align heading */
  }
}
</style>
