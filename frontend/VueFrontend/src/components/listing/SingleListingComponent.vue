<script setup>
import { ref, defineProps, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Icon } from '@iconify/vue'
import { storeToRefs } from 'pinia';

import ListingMapComponent from '@/components/listing/ListingMapComponent.vue'
import { userStore } from '@/stores/user.js'
import { getListingById } from '@/services/ListingService.js'
import { createBookmark, deleteBookmark, getUserBookmarks } from '../../services/BookmarkService';
import { useListingStore } from '@/stores/listing.js'

const listing = ref(null); 
const image = 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg';

// User store
const user = userStore()

const token = user.token;
if(!token && user.isLoggedIn) {
  user.logout();
  console.error('Token is expired, user logged out');
}

const favorites = ref([]);

// Fetch listing id
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
});

// Fetch user bookmarks only if the user is logged in
if (user.isLoggedIn) {
  getUserBookmarks(token)
    .then((data) => {
      favorites.value = data;
    })
    .catch((err) => {
      console.error('Error fetching bookmarks:', err);
    });
} else {
  console.warn('User is not logged in. Skipping bookmark fetch.');
}

// Favorite button
const isFavorite = ref();
const checkIfFavorite = () => {
  if (user.isLoggedIn) {
    isFavorite.value = favorites.value.some(favorite => favorite.listingId === listing.value.id);
  } else {
    isFavorite.value = false;
  }
}

const toggleFavorite = async () => {
  checkIfFavorite();
  if (isFavorite.value) {
    await deleteBookmark(token, listing.value.id);
    favorites.value = favorites.value.filter(f => f.listingId !== listing.value.id);
  } else {
    await createBookmark(token, listing.value.id);
    favorites.value.push({ listingId: listing.value.id });
  }
  isFavorite.value = !isFavorite.value;
}

// Check if the user is the owner of the listing
const isOwner = () => {
  if (user.isLoggedIn && listing.value) {
    return user.userId === listing.value.userId;
  }
  return false;
}

// Delete listing
const delListing = () => {
  // Logic to delete the listing
  router.back()
}

// Route to edit listing
const router = useRouter();
const toEditListing = () => {
  router.push('/listing/update/' + listing.value.id + '/edit');
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
<div class="display-page-container" v-if="listing">
  <!-- Image container -->
  <div class="image-container">
    <img class="image-item" :src="image" alt="Front image">
    <button class="favorite" :class="{ 'isFavorite': isFavorite }" @click="toggleFavorite">
      <Icon icon="material-symbols:favorite" width="40" height="40" />
    </button>
    <p id="lastEdited">Last edited: {{ formatDateTime(listing.lastEdited) }}</p>
  </div>

  <div class="sidebar">

    <!-- Description -->
    <div class="description">
      <h2>{{ listing.title }}</h2>
      <p id="price">{{ listing.price }}</p>
      <p id="description">{{ listing.description }}</p>
      <p id="categories">{{ listing.category }}</p>
    </div>

    <!-- Buy item or message seller -->
    <div class="btn" v-if="user.isLoggedIn && !isOwner()">
      <button class="message-btn">Message seller</button>
      <button class="buy-btn">Buy</button>
    </div>

    <!-- Owner options -->
    <div class="owner-options">
      <template v-if="isOwner()">
        <button class="owner-btn" @click="toEditListing">Edit</button>
        <button class="owner-btn">Archive</button>
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
  width: 100%;
  max-height: 40px;
  max-width: 400px;
  margin-bottom: 10px;
}

.buy-btn,
.message-btn,
.owner-btn {
  font-size: 16px;
  transition: all 0.3s ease;

  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  line-height: 10px;
}

.buy-btn,
.owner-btn {
  background-color: white;
  color: #333333;
  border: #1C64FF 2px solid;
}

.buy-btn:hover,
.owner-btn:hover {
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
