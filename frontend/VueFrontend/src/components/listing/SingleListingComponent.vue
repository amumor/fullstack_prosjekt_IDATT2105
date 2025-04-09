<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
import { Icon } from '@iconify/vue'

import ListingMapComponent from '@/components/listing/ListingMapComponent.vue'
import { userStore } from '@/stores/user.js'
import { getListingById } from '@/services/ListingService.js'

const props = defineProps({
  listingId: String,
})

// User store
const user = userStore()
const token = user.token;

// Fetch the listing
const listing = ref(null); 
getListingById(props.listingId, token)
  .then((data) => {
    listing.value = data;
    console.log('Listing found:', data);
  })
  .catch((err) => {
    console.error('Listing not found:', err);
  });

const router = useRouter();
const isFavorite = ref(false);

const toggleFavorite = () => {
  isFavorite.value = !isFavorite.value;
}

const isOwner = () => {
  if (user.isLoggedIn) {
    user.userId === listing.value.userId  
  }
  return false;
}

const delListing = () => {
  // Logic to delete the listing
  router.back()
}

const toEditListing = () => {
  router.push('/listing/update/' + listing.id + '/edit');
}

// Format LocalDateTime to a readable format
const formatDateTime = (dateTimeString) => {
  const date = new Date(dateTimeString); 
  return date.toLocaleString(); 
};
</script>

<template>
<div class="display-page-container">
  <p>herher</p>
  <!-- Image container -->
  <div class="image-container">
    <!--<img class="image-item" :src="listing.images" alt="Front image">-->
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
    <div class="btn" v-if="user.isLoggedIn && !isOwner">
      <button class="message-btn">Message seller</button>
      <button class="buy-btn">Buy</button>
    </div>

    <!-- Owner options -->
    <div class="owner-options">
      <template v-if="isOwner">
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
