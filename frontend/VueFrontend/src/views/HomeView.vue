<script setup>
import Navbar from '@/components/Navbar.vue'


const searchFunction = () => {};

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
    <Navbar />
  </div>

  <div class="search-container">
    <input type="text" class="search-input" placeholder="Search for listings..." id="searchInput">
    <button class="search-btn" @click="searchFunction">Search</button>
  </div>

  <div class="category-container">
    <!-- Get categories from backend -->
    <button>Boats</button>
    <button>Cars</button>
    <button>Motorcycles</button>
    <button>Real Estate</button>
  </div>

  <div class="image-container">
    <!-- Get images from backend -->
    <li v-for="listing in listings" :key="listing.id">
      <img class="image-item" :src="listing.image" alt="Boat">
      <div class="price">{{ listing.price }}</div>
      <div class="town">{{ listing.town }}</div>
      <div class="title">{{ listing.title }}</div>
    </li>
  </div>
</template>

<style scoped>

</style>
