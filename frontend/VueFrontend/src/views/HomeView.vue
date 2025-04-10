<script setup>
import Navbar from '@/components/Navbar.vue'
import ListingPreviewComponent from '@/components/listing/ListingPreviewComponent.vue'
import {getListingsByCategory, getListingSuggestions} from "@/services/ListingService.js";
import {userStore} from "@/stores/user.js";
import {isTokenExpired} from "@/services/TokenService.js";
import {getAllCategories} from "@/services/CategoryService.js";
import {onMounted, ref, computed} from "vue";
import router from "@/router/index.js";
import {getListingsByTitle} from "@/services/ListingService.js";

document.body.style.backgroundColor = "#ffffff";

//Get images from backend
// const listings = [
//   {
//     id: 1,
//     price: '100 mill',
//     image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
//     town: 'Elverum',
//     title: 'Big boat 1',
//   },
//   {
//     id: 2,
//     price: '60 mill',
//     image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
//     town: 'Baerum',
//     title: 'Big Boat 2',
//   },
// ];

// //Get categories from backend
// const categories = [
//   {id: 1, name: 'Boats'},
//   {id: 2, name: 'Cars'},
//   {id: 3, name: 'Motorcycles'},
//   {id: 4, name: 'Real Estate'},
// ];

const user = userStore()
const categories = ref([]);
const listings = ref([]);
const searchInput = ref("");
const noResults = ref(false);
onMounted(async () => {
  await localGetSuggestions();
})

const currentFilter = ref("None");
const handleCategoryFilterClick = async (categoryName) => {
  try {
    listings.value = await getListingsByCategory(categoryName, {page: 1, size: 10});
    console.log("Listings value after filtering", listings.value)
    currentFilter.value = categoryName + " (category)";

  } catch (error) {
    console.log(error);
  }
}

const handleFilterReset = async () => {
  await localGetSuggestions();
  currentFilter.value = "None";
  noResults.value = false;  
  searchInput.value = "";
}

const localGetSuggestions = async () => {
  try {
    const token = user.token
    if (!token) {
      // If user is not logged in, get listings without token
      const listingResponse = await getListingSuggestions({page: 1, size: 10})
      const categoryResponse = await getAllCategories()
      console.log("listing response:", listingResponse)
      console.log("category response:", categoryResponse)
      listings.value = listingResponse.listings
      categories.value = categoryResponse.categories
      console.log("listings: ", listings.value)
      console.log("categories: ", categories.value)
    } else {
      if (isTokenExpired(token)) {
        user.logout()
        await router.push("/")
      } else {
        // If user is logged in, get listings with token
        const listingResponse = await getListingSuggestions({page: 1, size: 10})
        const categoryResponse = await getAllCategories()
        listings.value = listingResponse.listings
        categories.value = categoryResponse.categories
      }
    }
  } catch (error) {
    console.error(error)
  }
}

const searchFunction = async() => {
  try {
    const data = await getListingsByTitle(searchInput.value);
    if (data.listings.length === 0) {
      noResults.value = true;
      console.log('No results found for title:', searchInput.value);
      console.log('noresult:', noResults.value);
    }
    listings.value = data.listings;
    currentFilter.value = searchInput.value;
  } catch (error) {
    noResults.value = true;
    listings.value = [];
    console.error('Error fetching listings:', error);
  }
};

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
  <Navbar/>
  <div class="display-page-container">

    <!-- Search bar -->
    <div class="search-container">
      <input v-model="searchInput" type="text" class="search-input" placeholder="Search for listings..." id="searchInput">
      <button class="search-btn" @click="searchFunction">{{ $t('home.search') }}</button>
      <router-link class="map-btn" to="/map" id="router-link">{{ $t('map.map') }}</router-link>
  
    </div>

    <!-- Categories -->
    <div class="category-container">
      <div v-for="category in categories" :key="category.id">
        <button id="category-btn" @click="() => handleCategoryFilterClick(category.name)">{{ category.name }}</button>
      </div>
      <button id="category-btn" @click="handleFilterReset">{{ $t('button.reset') }}</button>
      <h3>Current filter: {{currentFilter}}</h3>
    </div>

    <!-- Listings -->
    <div class="listings" v-if="!noResults">
      <div class="listing-item" v-for="listing in paginatedListings" :key="listing.id">
        <ListingPreviewComponent
            :id="listing.id"
            :image="listing.imageUrls && listing.imageUrls.length > 0 ? listing.imageUrls[0] : null"
            :price="listing.price"
            :latitude="listing.latitude"
            :longitude="listing.longitude"
            :title="listing.title"/>
      </div>
    </div>
    <div v-else>
      <h1>{{ $('home.no-listings-found') }}</h1>
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

h1 {
  text-align: center;
  margin-right: 40px;
  margin-top: 150px;
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
