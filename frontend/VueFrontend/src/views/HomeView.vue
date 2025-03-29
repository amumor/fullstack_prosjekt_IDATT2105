<script setup>
import Navbar from '@/components/Navbar.vue'


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
  <div class="display-page-container">
    <!-- fix login logic!! -->
    <Navbar isLoggedIn="false"/>
  </div>

  <!-- Search bar -->
  <div class="search-container">
    <input type="text" class="search-input" placeholder="Search for listings..." id="searchInput">
    <button class="search-btn" @click="searchFunction">Search</button>
    <button class="map-btn">Map</button>
  </div>

  <!-- Categories -->
  <div class="category-container">
    <li v-for="category in categories" :key="category.id">
      <button id="category-btn">{{ category.name }}</button>
    </li>
  </div>

  <!-- Listings -->
  <div class="listings">
    <li v-for="listing in listings" :key="listing.id">
      <div class="image-container">
        <img class="image-item" :src="listing.image" alt="Boat">
        <div class="price">{{ listing.price }}</div>
      </div>
      <div class="town">{{ listing.town }}</div>
      <div class="title">{{ listing.title }}</div>
    </li>
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
  box-shadow: 0 4px 6px #333333;
  border-radius: 5px;


  flex-grow: 1;
  border: none;
  outline: none;
  padding: 10px;
  outline: none;
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

.listings li {
  position: relative;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* Image container */
.image-container {
  position: relative;
}

.image-item {
  width: 200px;
  height: auto;
  display: block;
}

.price {
  color: white;
  position: absolute;
  bottom: 10px;
  left: 10px;
  padding: 5px;
  border-radius: 5px;
}

/* Listing info */
.town {
  font-size: 15px;
  color: darkgray;
}

.title {
  font-size: 25px;
  color: #333333;
}
</style>
