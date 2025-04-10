<script setup>
import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/listing/ListingPreviewComponent.vue'
import BackToComponent from '@/components/BackToComponent.vue'
import { getUserBookmarks } from '../../services/BookmarkService';
import { userStore } from '@/stores/user.js'
import { ref } from 'vue';
import { getListingById } from '../../services/ListingService';
import { fetchImage } from '@/services/ImageService.js';

/**
const listings = [
  {
    id: 1,
    price: '100 mill',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    town: 'Elverum',
    title: 'Big boat 1',
  },
  {
    id: 2,
    price: '60 mill',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    town: 'Baerum',
    title: 'Big Boat 2',
  },
];
*/

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
</script>

<template>
<Navbar />
<div class="display-page-container">
  <BackToComponent />
  <h2>Favorites</h2>
  <div class="listings">
    <div v-for="listing in listings" :key="listing.id">
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
</div>
</template>

<style scoped>
</style>
