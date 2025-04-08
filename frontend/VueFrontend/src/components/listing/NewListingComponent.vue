<script setup>
import { ref } from 'vue'
import { useListingStore } from '@/stores/listing.js'
import { createListing } from '@/services/ListingService.js'
import { userStore } from '@/stores/user.js'
import { storeToRefs } from 'pinia'

const categories = [
  { id: 1, name: 'Boats' },
  { id: 2, name: 'Cars' },
  { id: 3, name: 'Motorcycles' },
  { id: 4, name: 'Real Estate' },
];

const coordinates = ref([59.9139, 10.7522]); // Default coordinates (Oslo)

const title = ref('');
const description = ref('');
const selectedCategory = ref('');
const price = ref('');
const location = ref('');
const images = ref([]);
const errorMsg = ref('');

const getCoordinates = async (address) => {
  const encodedAddress = encodeURIComponent(address);
  const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodedAddress}`;

  try {
    const response = await fetch(url, {
      headers: {
        'Accept': 'application/json'
      }
    });
    const data = await response.json();
    if (data.length > 0) {
      coordinates.value = [parseFloat(data[0].lat), parseFloat(data[0].lon)];
    }
  } catch (error) {
    console.error('Error fetching coordinates:', error);
  }
  return null;
};

// Get image URL
const handleFileChange = (event) => {
  const files = event.target.files; // Get the list of selected files

  // Check if files are selected
  if (files.length > 0) {
    const file = files[0]; // Get the first file (if multiple files can be selected, handle accordingly)

    // Generate a temporary URL for the image
    const imageUrl = URL.createObjectURL(file);

    // Add the image URL to the images array
    images.value.push(imageUrl);
  }
}

// User store
const user = userStore();

// Listing store
const listingStore = useListingStore();
const { selectedListing } = storeToRefs(listingStore);

const createListings = () => {
  // Logic to save the listing (v-model?)
  if(!title.value){
    errorMsg.value = 'Title is required';
    console.log('Title is required');
    return;
  }
  if(!description.value){
    errorMsg.value = 'Description is required';
    console.log('Description is required');
    return;
  }
  if(!selectedCategory.value){
    errorMsg.value = 'Category is required';
    console.log('Category is required');
    return;
  }
  if(!price.value){
    errorMsg.value = 'Price is required';
    console.log('Price is required');
    return;
  }
  if(!location.value){
    errorMsg.value = 'Location is required';
    console.log('Location is required');
    return;
  }
  if(images.value.length === 0){
    errorMsg.value = 'Image is required';
    console.log('Image is required');
    return;
  }
  listingStore.selectListing({
    seller: '1',
    title: title.value,
    description: description.value,
    category: selectedCategory.value.id,
    price: price.value,
    location: getCoordinates(location.value),
    image: images.value[0],
  });
  listingStore.addListing( selectedListing.value )
  try{
    const listResponse = createListing({
      listing: selectedListing.value,
      images: images.value,
      token: user.token,
    });
    errorMsg.value = 'Listing created successfully';
    console.log('Listing created successfully:', listResponse);
  } catch (error) {
    errorMsg.value = 'Error creating listing';
    console.error('Error creating listing:', error);
  }
};

</script>

<template>
<div class="display-page-container">
  <div class="fields">
    <h2>Create a new listing</h2>
    <div class="text-fields">
      <input v-model="title" type="text" placeholder="Header" required />
      <textarea v-model="description" type="text" id="description" placeholder="Description" required />
      <input v-model="price" type="text" placeholder="Price" required />
      <input v-model="location" type="text" placeholder="Location" required />

      <!-- Categories-->
      <div v-for="category in categories" :key="category.id">
        <label>
          <input 
            v-model="selectedCategory"
            type="radio" 
            :name="'category'" 
            :value="category.name" 
          />
          {{ category.name }}
        </label>
      </div>
    </div>
    <div class="image-field">
      <form action="/upload" method="post" enctype="multipart/form-data">
        <label for="file">Upload image:</label>
        <input type="file" id="file" name="file" accept="image/*" @change="handleFileChange">
      </form>
      <div v-for="(image, index) in images" :key="index">
        <p>{{ image }}</p>
      </div>
    </div>

    <div v-if="errorMsg" class="error-msg">{{ errorMsg }}</div>

    <div class="submit-button">
      <button type="submit" class="basic-blue-btn" @click="createListings">Create</button>
    </div>
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
  margin: auto;
  position: relative;
}

/* Header */
h2 {
  text-align: center;
}

/* Input fields */
.text-fields {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

#description {
  font-size: 16px;
  border: 1px solid #ccc;

  width: 100%;
  height: 100px;
  padding: 10px;
  border-radius: 5px;
  resize: vertical;
  text-align: left;
  vertical-align: top;
}

#description:focus {
  border-color: #1C64FF;
  outline: none;
}

/* Category selection */
.text-fields div {
  color: #333333;

  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
}

.text-fields label {
  font-size: 14px;
  color: #333333;

  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
}

.text-fields input[type="checkbox"] {
  accent-color: #1C64FF;

  width: 16px;
  height: 16px;
}

/* Image upload */
.image-field {
  color: #333333;
  margin-top: 20px;
}

.image-field label {
  font-weight: 500;
}

.image-field input {
  margin-top: 5px;
  border: none;
}

/* Submit button */
.submit-button {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.submit-button button {
  width: 100%;
}

/* Responsive Design */
@media (max-width: 768px) {
  .display-page-container {
    width: 80%;
    padding: 20px;
  }
}
</style>
