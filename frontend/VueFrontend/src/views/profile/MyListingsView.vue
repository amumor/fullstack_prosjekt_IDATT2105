<script setup>
import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/listing/ListingPreviewComponent.vue'
import BackToComponent from '@/components/BackToComponent.vue'

const location=[63.417152858467574, 10.404550601471463];
const listings = [
  {
    id: 1,
    price: "100",
    //image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    latitude: 59.92698585271062,
    longitude:  10.582136712612575,
    title: 'Big boat 1',
  },
  {
    id: 2,
    price: "60",
    //image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    latitude: 63.417152858467574,
    longitude: 10.404550601471463,
    title: 'Big Boat 2',
  },
];
</script>

<template>

<Navbar />

<div class="display-page-container">
  <BackToComponent />
  <h2>My listings</h2>

  <!-- Listings -->
  <div class="listings">
    <div v-for="listing in listings" :key="listing.id">
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
