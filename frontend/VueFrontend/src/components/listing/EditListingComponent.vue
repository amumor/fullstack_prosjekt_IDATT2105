<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router'
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useListingStore } from '@/stores/listing.js';
import { userStore } from '@/stores/user.js'
import { getListingById, updateListing } from '@/services/ListingService.js'
import { getAllCategories } from '../../services/CategoryService';


// Router instance
const router = useRouter();

// User store
const user = userStore();
const token = user.token;

// Listing data
const listing = ref(null);
const categories = ref([]);
const newTitle = ref('');
const newDescription = ref('');
const newStatus = ref('');
const newPrice = ref('');
const newLocation = ref('Loading address...');
const newCategory = ref('');

// Fetch categories
try {
  getAllCategories()
    .then((data) => {
      categories.value = data;
    })
    .catch((err) => {
      console.error('Error fetching categories:', err);
    });
} catch (err) {
  console.error('Error fetching categories:', err);
}

// Status options
const statusOptions = [
  { value: 'ACTIVE', label: 'Active' },
  { value: 'SOLD', label: 'Sold' },
  { value: 'INACTIVE', label: 'Inactive' },
];

// Fetch listing by ID
onMounted(async () => {
  const listingStore = useListingStore();
  const { id } = storeToRefs(listingStore);
  
  if (!id.value) {
    console.error('Listing ID is null');
    return;
  }
  
  try {
    listing.value = await getListingById(id.value);
    
    newTitle.value = listing.value.title;
    newDescription.value = listing.value.description;
    newStatus.value = listing.value.listingStatus;
    newPrice.value = listing.value.price;
    newLocation.value = 'Loading address...'; // Add logic to fetch the location address from coordinates.
    newCategory.value = listing.value.categoryName;
    console.log('Listing:', listing.value);
  } catch (err) {
    console.error('Listing not found:', err);
  }
});

// Create address from coordinates
const getAddressFromCoordinates = async (latitude, longitude) => {
  const url = `https://nominatim.openstreetmap.org/reverse?lat=${latitude}&lon=${longitude}&format=json`;
  try {
    const response = await fetch(url, {
      headers: { 'User-Agent': 'YourAppName/1.0 (your@email.com)' }
    });

    if (!response.ok) {
      throw new Error('Failed to fetch address');
    }

    const data = await response.json();
    return data.display_name;
  } catch (error) {
    console.error('Error:', error);
    return null;
  }
};

// Save changes
const saveChanges = () => {
  //getCoordinates(newLocation);
  console.log('newTitle:', newTitle.value);
  console.log('newDescription:', newDescription.value);
  console.log('newCategory:', newCategory.value);
  console.log('newStatus:', newStatus.value);
  console.log('newPrice:', newPrice.value);
  console.log('latitude:', listing.value.latitude);
  console.log('longitude:', listing.value.longitude);
  try {
    updateListing(listing.value.id, {
      title: newTitle.value,
      description: newDescription.value,
      categoryName: 'Car',
      listingStatus: 'ACTIVE',
      latitude: '59.9139', 
      longitude: '10.7522',
      price: '100',
      // images: imagesArrayHere
    }, token);
    router.go(-1);
  } catch (err) {
    console.error('Error updating listing:', err);
  }
};
</script>

<template>
  <div class="display-page-container">
    <h2 class="display-values-title">{{ $t('header.edit-listing') }}</h2>
    <div class="display-values-content">

      <!-- Title -->
      <div class="display-values-item">
        <label for="title">{{ $t('listing.header') }}:</label>
        <input type="text" id="title" v-model="newTitle"/>
      </div>

      <!-- Description -->
      <div class="display-values-item">
        <label for="description">{{ $t('header.description') }}:</label>
        <input type="text" id="description" v-model="newDescription" />
      </div>

      <!-- Category Selection -->
      <div v-for="category in categories" :key="category.id" class="display-values-item">
        <label>
          <input 
            v-model="newCategory" 
            type="radio" 
            :name="'category'" 
            :value="category.name" />
          {{ category.name }}
        </label>
      </div>

      <!-- Status Selection -->
      <div v-for="status in statusOptions" :key="status.value" class="display-values-item">
        <label>
          <input 
            v-model="newStatus" 
            type="radio" 
            :name="'status'" 
            :value="status.value" />
          {{ status.label }}
        </label>
      </div>

      <!-- Price -->
      <div class="display-values-item">
        <label for="price">{{ $t('listing.price') }}:</label>
        <input type="text" id="price" v-model="newPrice" />
      </div>

      <!-- Location -->
      <div class="display-values-item">
        <label for="location">{{ $t('listing.location') }}:</label>
        <input type="text" id="location" v-model="newLocation" />
      </div>

      <!-- Save changes -->
      <button class="basic-blue-btn" @click="saveChanges">{{ $t('button.save-changes') }}</button>
    </div>
  </div>
</template>

<style scoped>
.display-page-container {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);

  width: 40%;
  max-width: 600px;
  padding: 40px;
  border-radius: 10px;
  margin: 20px auto;
  position: relative;
}

/* Header */
h2 {
  text-align: center;
}

/* Input fields */
.display-values-content {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.display-values-item {
  display: flex;
  flex-direction: column;
}

label {
  font-size: 14px;
  font-weight: 500;
  color: #333333;
  margin-bottom: 5px;
}

/* Save changes button */
.basic-blue-btn {
  width: 100%;
}

/* Responsive Design */
@media (max-width: 768px) {
  .display-page-container {
    width: 80%;
    padding: 20px;
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .display-page-container {
    width: 90%; /* Take almost full width */
    padding: 15px; /* Reduce padding */
    box-shadow: none; /* Simplify shadow for smaller screens */
    border-radius: 8px; /* Slightly reduce border radius */
  }

  h2 {
    font-size: 18px; /* Adjust font size for smaller screens */
    text-align: center; /* Keep heading centered */
  }

  .display-values-content {
    gap: 10px; /* Reduce gap between input fields */
  }

  .display-values-item {
    flex-direction: column; /* Ensure inputs stack vertically */
  }

  label {
    font-size: 12px; /* Adjust label font size */
    margin-bottom: 3px; /* Reduce spacing below labels */
  }

  input {
    font-size: 14px; /* Adjust input font size */
    padding: 8px; /* Reduce padding for inputs */
  }

  .basic-blue-btn {
    font-size: 14px; /* Adjust button font size */
    padding: 10px; /* Add padding for better usability */
    width: 100%; /* Ensure button takes full width */
  }
}
</style>
