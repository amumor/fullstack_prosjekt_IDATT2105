<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router'

const props = defineProps ({
  id: String,
  image: String,
  title: String,
  description: String,
  price: String,
  location: Array
})

const listing = ref({
  image: props.image,
  title: props.title,
  description: props.description,
  price: props.price,
  location: props.location
})

// Create address from coordinates
const getAddress = async (lat, lon) => {
  const url = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lon}`;

  try {
    const response = await fetch(url, {
      headers: {
        'Accept': 'application/json'
      }
    });
    const data = await response.json();
    if (data?.display_name) {
      address.value = data.display_name
    } else {
      address.value = 'No address found'
    }
  } catch (error) {
    console.error('Error fetching address:', error)
    address.value = 'Error fetching address'
  }
};

const address = ref('No address found')
const coordinates = ref([60.472024, 8.524123]); // Default coordinates (Oslo)
const newImage = ref(props.image);
const newTitle = ref(props.title);
const newDescription = ref(props.description);
const newPrice = ref(props.price);
getAddress(coordinates.value[0], coordinates.value[1])
const newLocation = ref(address);


// Create coordinates from location
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


const router = useRouter()

const saveChanges = () => {
  listing.value.image = newImage.value;
  listing.value.title = newTitle.value;
  listing.value.description = newDescription.value;
  listing.value.price = newPrice.value;
  getCoordinates(newLocation)
  listing.value.location = coordinates.value;
  router.back();
}
</script>

<template>
  <div class="display-page-container">
    <h2 class="display-values-title">Edit listing</h2>
    <div class="display-values-content">
      <div class="display-values-item">
        <label for="title">Header:</label>
        <input type="text" id="title" v-model="newTitle"/>
      </div>
      <div class="display-values-item">
        <label for="description">Description:</label>
        <input type="text" id="description" v-model="newDescription" />
      </div>
      <div class="display-values-item">
        <label for="price">Price:</label>
        <input type="text" id="price" v-model="newPrice" />
      </div>
      <div class="display-values-item">
        <label for="location">Location:</label>
        <input type="text" id="location" v-model="newLocation" />
      </div>
      <button class="basic-blue-btn" @click="saveChanges">Save changes</button>
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
</style>
