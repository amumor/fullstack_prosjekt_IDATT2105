<script setup>
import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/Listing/ListingPreviewComponent.vue'
document.body.style.backgroundColor = "#ffffff";


const searchFunction = () => {};

//Get images from backend
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

//Get categories from backend
const categories = [
  { id: 1, name: 'Boats' },
  { id: 2, name: 'Cars' },
  { id: 3, name: 'Motorcycles' },
  { id: 4, name: 'Real Estate' },
];
</script>

<template>
<Navbar :isLoggedIn=true />
<div class="display-page-container">

  <!-- Search bar -->
  <div class="search-container">
    <input type="text" class="search-input" placeholder="Search for listings..." id="searchInput">
    <button class="search-btn" @click="searchFunction">Search</button>
    <button class="map-btn">
      <router-link to="/map" id="router-link">Map</router-link>
    </button>
  </div>

  <!-- Categories -->
  <div class="category-container">
    <div v-for="category in categories" :key="category.id">
      <button id="category-btn">{{ category.name }}</button>
    </div>
  </div>

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
/* Search bar */
.search-container {
  justify-content: center;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  border-radius: 5px;
  width: 500px;
  margin: 20px auto;
}

.search-input {
  font-family: 'Inter', sans-serif;
  font-size: 16px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  border-radius: 5px;


  flex-grow: 1;
  border: none;
  outline: none;
  padding: 10px;
}

.search-btn, .map-btn {
  background-color: #D9D9D9;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  font-family: 'Inter', sans-serif;
  transition: background-color 0.3s, transform 0.3s;
}

.search-btn:hover, .map-btn:hover {
  background-color: #f1f1f1;
  transform: scale(1.05);
}

/* Categories */
.category-container {
  list-style: none;

  justify-content: center;
  display: flex;
  align-items: center;
  gap: 20px;
  margin: 20px auto;
}

#category-btn {
  color: white;
  font-family: 'Inter', sans-serif;
  text-decoration: none;
  background-color: #1C64FF;
  padding: 10px 15px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

#category-btn:hover {
  background-color: #7BA5FF;
  transform: scale(1.05);
}

/* Listings */
.listings {
  font-family: 'Inter', sans-serif;
  list-style: none;
  padding: 0;
  display: flex;
  gap: 20px;
}

</style>
