<script setup>
import {onMounted, ref} from 'vue'
import {getAllCategories} from "@/services/CategoryService.js";
import {userStore} from "@/stores/user.js";
import {isTokenExpired} from "@/services/TokenService.js";
import {createListing2, createListingWithoutImage} from "@/services/ListingService.js";
import router from "@/router/index.js";
import SuccessFailModal from "@/components/modal/SuccessFailModal.vue";
import {sendImagesToListing, setImageOnListing} from "@/services/ImageService.js";

const categories = ref([]);
const selectedCategories = ref([]);

const showResultModal = ref(false);
const resultModalMessage = ref('');

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

  /* TODO: dette må inn som en requestPart
    private String title;
    private String description;
    private String categoryName;
    private ListingStatus listingStatus;
    private double price;
    private double latitude;
    private double longitude;
    private List<String> imagesToDelete;
   */
  const listing = {
    title: title.value,
    description: description.value,
    categoryName: selectedCategory.value,
    listingStatus: 'ACTIVE',
    price: price.value,
    latitude: coordinates.value[0],
    longitude: coordinates.value[1],
  };
  // const formData = new FormData();
  // formData.append('listing', JSON.stringify(listing));

  // images.value.forEach((image) => { // TODO: implement when images is fixed
  //   formData.append('images', image);
  // });

  // Check token before sending the request
  const token = user.token;
  if (isTokenExpired(token)) {
    user.logout();
    await router.push('/login');
    return;
  }

  let createListingResponse = "";
  let createListingSuccess = false;
  try {
    createListingResponse = await createListingWithoutImage(listing, token);
    console.log('Listing created successfully:', createListingResponse);
    createListingSuccess = true;
  } catch (error) {
    console.error('Error creating listing:', error);
    resultModalMessage.value = 'Error while creating listing. Please try again.';
    showResultModal.value = true;
  }
  // If images are selected, send them to the server
  if (images.value.length > 0 && createListingSuccess) {
    try {
      const listingId = createListingResponse.listingId; // Assuming the response contains the listing ID
      console.log('Listing ID:', listingId);
      const imageResponse = await sendImagesToListing(images.value, listingId, token);
      console.log('Images sent successfully:', imageResponse);
      resultModalMessage.value = 'Listing created successfully with images.';
      showResultModal.value = true;
    } catch (error) {
      console.error('Error sending images:', error);
      resultModalMessage.value = 'Error while sending images. Please try again.';
      showResultModal.value = true;
    }
  } else {
    resultModalMessage.value = 'Listing created successfully.';
    showResultModal.value = true;
  }
};

const handleFileChange = (event) => {
  const selectedFiles = event.target.files;

  if (selectedFiles && selectedFiles.length > 0) {
    images.value = Array.from(selectedFiles); // Store files in the `images` array
    console.log('Selected files:', images.value);
  } else {
    console.log('No files selected.');
  }
};

onMounted(() => {
  localGetCategories();
  // Check if the user is logged in and if the token is expired, if not logout
  const token = user.token;
  if (isTokenExpired(token)) {
    user.logout();
    router.push("/login")
  }
});
</script>

<template>
  <div class="display-page-container">
    <form @submit.prevent="handleCreateListing" class="fields">
      <h2>{{ $t('header.create-new-listing') }}</h2>
      <div class="text-fields">
        <input v-model="title" type="text" :placeholder="$t('listing.header')" required/>
        <textarea v-model="description" id="description" :placeholder="$t('listing.description')" required></textarea>
        <input v-model="price" type="text" :placeholder="$t('listing.price')" required/>
        <input v-model="location" type="text" :placeholder="$t('listing.location')" required/>
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
        <label for="file">{{ $t('listing.upload-image') }}:</label>
        <input type="file" id="file" :name="$t('listing.file')" accept="image/*" @change="handleFileChange" disabled />
      </div>
      <div class="submit-button">
        <button type="submit" class="basic-blue-btn">{{ $t('button.create') }}</button>
      </div>
    </form>
  </div>
  <SuccessFailModal
      v-if="showResultModal"
      :message="resultModalMessage"
      @close="showResultModal = false"
  />
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
