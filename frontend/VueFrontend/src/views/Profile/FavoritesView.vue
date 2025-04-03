<script setup>

import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/Listing/ListingPreviewComponent.vue'

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
  <div class="display-page-container">
    <Navbar :isLoggedIn="true" />
    <h2>Favorites</h2>
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
.display-page-container {
  font-family: 'Inter', sans-serif;
}

/* Header */
h2 {
  font-size: 24px;
  color: #333333;
  font-weight: 500;

  text-align: left;
  margin-bottom: 20px;
}

/* Listings */
.listings {
  list-style: none;
  padding: 0;
  display: flex;
  gap: 20px;
}
</style>
