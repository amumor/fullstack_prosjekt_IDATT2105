<script setup>
import {onMounted, ref} from 'vue'
import {getAllCategories} from "@/services/CategoryService.js";
import {userStore} from "@/stores/user.js";
import {isTokenExpired} from "@/services/TokenService.js";

const categories = ref([]);
const selectedCategories = ref([]);

const title = ref('');
const description = ref('');
const selectedCategory = ref('');
const price = ref('');
const location = ref('');
const images = ref([]);

const coordinates = ref([59.9139, 10.7522]); // Default coordinates (Oslo)

const user = userStore();

const localGetCategories = async () => {
  try {
    const categoryResponse = await getAllCategories();
    categories.value = categoryResponse.categories
  } catch (error) {
    console.error('Error fetching categories:', error);
  }
};

const handleCreateListing = async () => {
  const formData = new FormData();
  formData.append('title', title.value);
  formData.append('description', description.value);
  formData.append('price', price.value);
  formData.append('location', location.value);
  formData.append('coordinates', JSON.stringify(coordinates.value));
  formData.append('categories', JSON.stringify(selectedCategories.value));

  images.value.forEach((image) => {
    formData.append('images', image);
  });

  try {
    // Call the API to create the listing
    // await createListingAPI(formData);
    console.log('Listing created successfully');
  } catch (error) {
    console.error('Error creating listing:', error);
  }
};

onMounted(() => {
  localGetCategories();
  const token = user.token;
  if (isTokenExpired(token)) {
    user.logout();
  }
});
</script>

<template>
  <div class="display-page-container">
    <form @submit.prevent="createListing" class="fields">
      <h2>Create a new listing</h2>
      <div class="text-fields">
        <input v-model="title" type="text" placeholder="Header" required />
        <textarea v-model="description" id="description" placeholder="Description" required></textarea>
        <input v-model="price" type="text" placeholder="Price" required />
        <input v-model="location" type="text" placeholder="Location" required />
        <div v-for="category in categories" :key="category.id">
          <label>
            <input
                v-model="selectedCategory"
                type="radio"
                name="category"
                :value="category.name"
            />
            {{ category.name }}
          </label>
        </div>
      </div>
      <div class="image-field">
        <label for="file">Upload image:</label>
        <input type="file" id="file" name="file" accept="image/*" @change="handleFileChange" />
      </div>
      <div class="submit-button">
        <button type="submit" class="basic-blue-btn">Create</button>
      </div>
    </form>
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

  .text-fields {
    gap: 10px; /* Reduce gap between input fields */
  }

  #description {
    font-size: 14px; /* Adjust font size */
    height: 80px; /* Reduce height */
    padding: 8px; /* Reduce padding */
  }

  .text-fields input[type="text"],
  .text-fields textarea {
    font-size: 14px; /* Adjust font size */
    padding: 8px; /* Reduce padding */
  }

  .text-fields label {
    font-size: 12px; /* Adjust label font size */
    gap: 3px; /* Reduce gap between checkbox and label text */
  }

  .image-field {
    margin-top: 15px; /* Reduce margin */
  }

  .image-field input {
    font-size: 12px; /* Adjust font size */
    padding: 5px; /* Add padding for better usability */
  }

  .submit-button button {
    font-size: 14px; /* Adjust button font size */
    padding: 10px; /* Add padding for better usability */
    width: 100%; /* Ensure button takes full width */
  }
}
</style>
