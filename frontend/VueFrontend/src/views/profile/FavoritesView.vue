<script setup>
import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/listing/ListingPreviewComponent.vue'
import BackToComponent from '@/components/BackToComponent.vue'
import { getUserBookmarks } from '../../services/BookmarkService';
import { userStore } from '@/stores/user.js'
import { ref, computed } from 'vue';
import { getListingById } from '../../services/ListingService';

// User store
const user = userStore()
const token = user.token;

// Fetch bookmarked listings
const bookmarks = ref([]);
const listings = ref([]);

// Fetch bookmarked listings
const fetchBookmarks = async () => {
  try {
    const data = await getUserBookmarks(token);
    if (data.length === 0) {
      console.log('No bookmarks found');
      return;
    }
    bookmarks.value = data;

    // Fetch full listing details for each bookmark
    const fetchedListings = await Promise.all(
      bookmarks.value.map(async (bookmark) => {
        try {
          const listing = await getListingById(bookmark.listingId);
          return listing;
        } catch (error) {
          console.error(`Error fetching listing with ID ${bookmark.listingId}:`, error);
          return null; // Handle errors gracefully
        }
      })
    );

    // Filter out any null values (failed fetches)
    listings.value = fetchedListings.filter((listing) => listing !== null);
    console.log('Fetched listings:', listings.value);
  } catch (err) {
    console.error('Error fetching bookmarks:', err);
  }
};

// Fetch bookmarks and listings on component mount
fetchBookmarks();

// Pagination state
const currentPage = ref(1)
const itemsPerPage = 20

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
  <h2>{{ $t('header.favorites') }}</h2>
  <div class="listings">
    <div v-for="listing in paginatedListings" :key="listing.id">
      <!-- No image -->
      <ListingPreviewComponent
        :id="listing.id"
        :image="listing.imageUrls && listing.imageUrls.length > 0 ? listing.imageUrls[0] : null"
        :price="listing.price"
        :latitude="listing.latitude"
        :longitude="listing.longitude"
        :title="listing.title" />
    </div>
  </div>

   <!-- Pagination controls -->
   <div class="pagination-controls">
    <button @click="prevPage" :disabled="currentPage === 1">{{ $t('pageination.previous') }}</button>
    <span>{{ $t('pageination.page') }} {{ currentPage }} {{ $t('pageination.of') }} {{ totalPages }}</span>
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
  margin-left: 30px;
  margin-bottom: 10px;
}

.listing-item {
  flex: 1 0 26%; /* 4 items per row */
  max-width: 300px;
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
/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .search-container {
    width: 90%; /* Adjust width for medium screens */
    flex-direction: column; /* Stack search bar and buttons vertically */
    gap: 15px; /* Adjust gap */
  }

  .search-input {
    font-size: 14px; /* Adjust font size */
    padding: 8px; /* Adjust padding */
  }

  .search-btn, .map-btn {
    font-size: 14px; /* Adjust font size */
    padding: 8px 12px; /* Adjust padding */
    width: 70px; /* Full width for buttons */
  }

  .category-container {
    flex-wrap: wrap; /* Allow categories to wrap */
    gap: 15px; /* Adjust gap */
  }

  #category-btn {
    font-size: 14px; /* Adjust font size */
    padding: 8px 12px; /* Adjust padding */
  }

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

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .search-container {
    width: 100%; /* Take full width */
    flex-direction: column; /* Stack search bar and buttons vertically */
    gap: 10px; /* Adjust gap */
  }

  .search-input {
    font-size: 12px; /* Adjust font size */
    padding: 6px; /* Adjust padding */
  }

  .search-btn, .map-btn {
    font-size: 12px; /* Adjust font size */
    padding: 6px 10px; /* Adjust padding */
  }

  .category-container {
    flex-wrap: wrap; /* Allow categories to wrap */
    gap: 10px; /* Adjust gap */
  }

  #category-btn {
    font-size: 12px; /* Adjust font size */
    padding: 6px 10px; /* Adjust padding */
  }

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
