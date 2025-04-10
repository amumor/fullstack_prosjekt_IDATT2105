<script setup>
import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/listing/ListingPreviewComponent.vue'
import BackToComponent from '@/components/BackToComponent.vue'
import { userStore } from '@/stores/user.js'
import { getListingsBySeller } from '@/services/ListingService.js'
import { ref, computed } from 'vue';  

/**
const location=[63.417152858467574, 10.404550601471463];
const listings = [
  {
    id: 1,
    price: "100",
    //image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    latitude: "59.92698585271062",
    longitude:  "10.582136712612575",
    title: 'Big boat 1',
  },
  {
    id: 2,
    price: "60",
    //image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    latitude: "63.417152858467574",
    longitude: "10.404550601471463",
    title: 'Big Boat 2',
  },
];
*/

// User store
const user = userStore()
const token = user.token;
const listings = ref([]);

getListingsBySeller(token, 0, 10)
  .then((data) => {
    if (data.length === 0) {
      console.log('No listings found');
      return;
    }
    listings.value = data;
    console.log('Listings found:', data);
  })
  .catch((err) => {
    console.error('Error fetching listings:', err);
  });

// Pagination state
const currentPage = ref(1)
const itemsPerPage = 50

// Total number of pages
const totalPages = computed(() => Math.ceil(listings.value.length / itemsPerPage))

// Get the listings for the current page
const paginatedListings = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return listings.value.slice(start, end)
})

// Pagination functions
const nextPage = () => {
  if (currentPage.value < totalPages.value) currentPage.value++
}

const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--
}
</script>

<template>

<Navbar />

<div class="display-page-container">
  <BackToComponent />
  <h2>{{ $t('header.my-listings') }}</h2>

  <!-- Listings -->
  <div class="listings">
    <div v-for="listing in paginatedListings" :key="listing.id">
      <ListingPreviewComponent
        :id="listing.id"
       
        :price="listing.price"
        :latitude="listing.latitude"
        :longitude="listing.longitude"
        :title="listing.title" />
    </div>
  </div>

  <!-- Pagination controls -->
  <div class="pagination-controls">
    <button @click="prevPage" :disabled="currentPage === 1">{{ $t('pageination.previous') }}</button>
    <span>{{ $('pageination.page') }} {{ currentPage }} {{ $t('pageination.of') }} {{ totalPages }}</span>
    <button @click="nextPage" :disabled="currentPage === totalPages">{{ $t('pageination.next') }}</button>
  </div>
</div>
</template>

<style scoped>
/* Listings */
.listings {
  font-family: 'Inter', sans-serif;
  list-style: none;
  padding: 0;
  gap: 20px;
  display: flex;
  flex-wrap: wrap;
  margin-left: 15px;
  margin-bottom: 10px;
}

.listing-item {
  flex: 1 0 26%; /* 4 items per row */
  max-width: 250px;
}

.pagination-controls {
  display: flex;
  gap: 10px;
  align-items: center;
  justify-content: center;
}

.pagination-controls button {
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #1C64FF;
  background-color: white;
  cursor: pointer;
}

.pagination-controls button:disabled {
  cursor: not-allowed;
  background-color: #e0e0e0;
}

.pagination-controls span {
  font-size: 16px;
}

@media(max-width: 768px) {
  .listings {
    flex-wrap: wrap; /* Allow listings to wrap */
    gap: 15px; /* Adjust gap */
    margin-left: 5px;;
  }

  .listing-item {
    flex: 1 0 48%; /* Adjust width for medium screens */
    max-width: 150px; /* Full width for items */
  }
}

@media(max-width: 480px) {
  .listings {
    flex-wrap: wrap; /* Allow listings to wrap */
    gap: 15px; /* Adjust gap */
    margin-left: 3px;;
  }

  .listing-item {
    flex: 1 0 48%; /* Adjust width for medium screens */
    max-width: 100px; /* Full width for items */
  }
}
</style>
