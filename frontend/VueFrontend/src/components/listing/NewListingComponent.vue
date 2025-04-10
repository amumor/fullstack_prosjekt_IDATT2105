<script setup>
import { ref } from 'vue'

const categories = [
  { id: 1, name: 'Boats' },
  { id: 2, name: 'Cars' },
  { id: 3, name: 'Motorcycles' },
  { id: 4, name: 'Real Estate' },
];

const coordinates = ref([59.9139, 10.7522]); // Default coordinates (Oslo)

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

const createListing = () => {
  // Logic to save the listing (v-model?)
};

</script>

<template>
<div class="display-page-container">
  <div class="fields">
    <h2>{{ $t('header.create-new-listing') }}</h2>
    <div class="text-fields">
      <input type="text" :placeholder="$t('listing.header')" required />
      <textarea type="text" id="description" :placeholder="$t('listing.description')" required />
      <input type="text" :placeholder="$t('listing.price')" required />
      <input type="text" :placeholder="$t('listing.location')" required />
      <div v-for="category in categories" :key="category.id">
        <label>
          <input type="checkbox" :name=category.name :value=category.name>{{ category.name }}
        </label>
      </div>
    </div>
    <div class="image-field">
      <form action="/upload" method="post" enctype="multipart/form-data">
        <label for="file">{{ $t('listing.upload-image') }}:</label>
        <input type="file" id="file" :name="$t('listing.file')" accept="image/*">
      </form>
    </div>
    <div class="submit-button">
      <button type="submit" class="basic-blue-btn" @click="createListing">{{ $t('button.create') }}</button>
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
