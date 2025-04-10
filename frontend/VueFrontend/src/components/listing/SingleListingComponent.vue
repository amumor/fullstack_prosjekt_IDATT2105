<script setup>
import { ref, onMounted, watch, computed } from 'vue';
import { useRouter } from 'vue-router';
import { Icon } from '@iconify/vue'
import { storeToRefs } from 'pinia';
import { jwtDecode } from 'jwt-decode';

import ListingMapComponent from '@/components/listing/ListingMapComponent.vue'
import { userStore } from '@/stores/user.js'
import { getListingById, deleteListing } from '@/services/ListingService.js'
import { createBookmark, deleteBookmark, getUserBookmarks } from '../../services/BookmarkService';
import { useListingStore } from '@/stores/listing.js'
import { getUserById } from '../../services/UserService';

const listing = ref(null); 
const image = 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg';

// Verify token
const checkToken = () => {
  if (!token && user.isLoggedIn) {
    user.logout();
    console.error('Token is expired, user logged out');
  }
}

// User store
const user = userStore()
const token = user.token;
checkToken();
const favorites = ref([]);
const isFavorite = ref();
const isArchived = ref(false);

// Fetch listing id
const isOwner = ref(false);
onMounted(async () => {
  const listingStore = useListingStore();
  const { id } = storeToRefs(listingStore);
  
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
    if(user.firstName === listing.value.sellerFirstName && user.lastName === listing.value.sellerLastName){
      isOwner.value = true;
    } else {
      isOwner.value = false;
    }
  } else {
    isOwner.value = false;
  }
});

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
const router = useRouter();
const toEditListing = () => {
  router.push('/listing/update/' + listing.value.id + '/edit');
}

// Archive listing
const toggleArchive = () => {
  checkToken();
  // use updateListing function to archive the listing
  isArchived.value = !isArchived.value;
}

// Format LocalDateTime to a readable format
const formatDateTime = (dateTimeString) => {
  if (!dateTimeString) {
    dateTimeString = listing.value.createdAt;
  } 
  if(!dateTimeString) return 'N/A';
  const date = new Date(dateTimeString); 
  if (isNaN(date)) {
    console.error('Invalid date format:', dateTimeString);
    return 'Invalid Date';
  }
  return date.toLocaleString();
};
</script>

<template>
<div class="toggle-container" id="archive" v-if="isOwner">
  <label class="switch">
    <input type="checkbox" @change="toggleArchive()">
    <span class="slider">
      <template v-if="isArchived">
        <span class="switch-label">INACTIVE</span>
      </template>
      <template v-else>
        <span class="switch-label">ACTIVE</span>
      </template>
    </span>
  </label>
</div>
<div class="display-page-container" v-if="listing">
  <!-- Image container -->
  <div class="image-container">
    <img class="image-item" :src="image" alt="Front image">
    <button v-if="user.isLoggedIn" class="favorite" :class="{ 'isFavorite': isFavorite }" @click="toggleFavorite">
      <Icon icon="material-symbols:favorite" width="40" height="40" />
    </button>
    <p id="lastEdited">Last edited: {{ formatDateTime(listing.lastEdited) }}</p>
  </div>

  <div class="sidebar">

    <!-- Description -->
    <div class="description">
      <h2>{{ listing.title }}</h2>
      <p id="price">{{ listing.price + ' kr' }}</p>
      <p id="description">{{ listing.description }}</p>
      <p id="categories">{{ listing.category }}</p>
    </div>

    <!-- Buy item or message seller -->
    <div class="btn" v-if="user.isLoggedIn && !isOwner">
      <button class="message-btn">Message</button>
      <button class="buy-btn">Buy</button>
    </div>

    <!-- Owner options -->
    <div class="owner-options">
      <template v-if="isOwner">
        <button class="owner-btn" id="edit" @click="toEditListing">Edit</button>
        <button class="owner-btn" id="delete" @click=delListing>Delete</button>
      </template>
    </div>

    <!-- Map -->
    <div class="map">
      <ListingMapComponent
        :location="[listing.latitude, listing.longitude]" />
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
  width: 140px;
  height: 40px;
  margin: 0 50px 50px 630px;
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

input:checked + .slider {
  background-color: #1C64FF;
}

input:checked + .slider:before {
  background-color: white;
  margin-right: 20px;
  transform: translateX(100px);
}

input:checked + .slider .switch-label {
  color: white;
}

.switch-label {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
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
