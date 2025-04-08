<script setup>
import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/listing/ListingPreviewComponent.vue'
import BackToComponent from '@/components/BackToComponent.vue'

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
        :image="listing.image"
        :price="listing.price"
        :town="listing.town"
        :title="listing.title" />
    </div>
  </div>
</div>
</template>

<style scoped>
</style>
