<script setup>
import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/listing/ListingPreviewComponent.vue'
import BackToComponent from '@/components/BackToComponent.vue'
import { getUserBookmarks } from '../../services/BookmarkService';
import { userStore } from '@/stores/user.js'
import { ref } from 'vue';

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
const listings = ref([]); 
getUserBookmarks(token)
  .then((data) => {
    if (data.length === 0) {
      console.log('No bookmarks found');
      return;
    }
    listings.value = data;
    console.log('Bookmarks found:', data);
  })
  .catch((err) => {
    console.error('Error fetching bookmarks:', err);
  });

</script>

<template>
<Navbar />
<div class="display-page-container">
  <BackToComponent />
  <h2>Favorites</h2>
  <div class="listings">
    <div v-for="listing in listings.value" :key="listing.id">
      <!-- No image -->
      <ListingPreviewComponent
        :id="listing.id"
        
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
