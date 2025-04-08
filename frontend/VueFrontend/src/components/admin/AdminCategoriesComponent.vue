<script setup>
//Get categories from backend
import {ref, defineProps} from 'vue'
import {createCategory, deleteCategory, getAllCategories} from "@/services/CategoryService.js";
import SuccessFailModal from "@/components/modal/SuccessFailModal.vue";
import {userStore} from "@/stores/user.js";
import {isTokenExpired} from "@/services/TokenService.js";

const categories = ref([]);
const newCategoryName = ref('')

const userStorage = userStore();
const token = userStorage.token;
if (isTokenExpired(token)) {
  userStorage.logout();
}
const response = await getAllCategories(token);
console.log("response: ", response.categories);
categories.value.push(...response.categories)


console.log(categories);

const showResultModal = ref(false);
const resultModalMessage = ref('');

const props = defineProps({
  isEditMode: Boolean
})

const localDeleteCategory = (index) => {
  const category = categories.value[index]
  deleteCategory(category.id, token)
      .then(() => {
        categories.value.splice(index, 1)
      })
      .catch((error) => {
        console.error('Failed to delete category:', error)
        resultModalMessage.value = "Failed to delete category";
        showResultModal.value = true;
      })
}

const newCategory = () => {
  console.log(newCategoryName.value);
  const response = createCategory(newCategoryName.value, token)
  categories.value.push({
    id: response.id,
    name: newCategoryName.value,
  })
}

</script>

<template>
  <div class="category-container">
    <div class="edit-mode" v-if="isEditMode">
      <div class="category-item">
        <button class="basic-blue-btn" id="new-category-btn" @click="newCategory">+</button>
        <input id="category-input" type="text" v-model="newCategoryName" placeholder="Name"/>
      </div>
      <div class="category-item" v-for="(category, index) in categories" :key="category.id">
        <button class="basic-blue-btn" id="delete-btn" @click="() => localDeleteCategory(index)">Delete</button>
        <input type="text" v-model="category.name"/>
      </div>
    </div>
    <div class="not-edit-mode" v-if="!isEditMode">
      <div class="category-item" v-for="category in categories" :key="category.id">
        <p class="category-text">{{ category.name }}</p>
      </div>
    </div>
  </div>
  <SuccessFailModal
      v-if="showResultModal"
      :message="resultModalMessage"
      @close="showResultModal = false"
  />
</template>

<style scoped>
.category-item {
  display: flex;
  align-items: center;
  gap: 12px;
  width: 100%;
}

#category-input {
  margin-top: 22px;
}

.not-edit-mode {
  display: flex;
  flex-direction: column;
  gap: 50px;
  margin: 98px 0 30px 83px;
}

#new-category-btn {
  background-color: white;
  color: #333333;
  border: #1C64FF 2px solid;

  width: 60px;
}

#new-category-btn:hover {
  background-color: #D9D9D9;
}

#delete-btn {
  background-color: crimson;

  margin-bottom: 23px;
  font-size: 14px;
  width: auto;
  white-space: nowrap;
}

#delete-btn:hover {
  background-color: darkred;
}

/* Responsive design for smaller screens */
@media (max-width: 768px) {
  .category-item {
    flex-direction: column; /* Stack items vertically */
    align-items: flex-start; /* Align items to the left */
    gap: 10px; /* Reduce gap for smaller screens */
  }

  #category-input {
    width: 100%; /* Take full width on smaller screens */
    max-width: none; /* Remove max-width restriction */
  }

  #new-category-btn {
    width: 100%; /* Take full width for better usability */
    height: 50px; /* Increase height for touch devices */
    font-size: 16px; /* Adjust font size */
  }

  #delete-btn {
    width: 100%; /* Take full width for better usability */
    font-size: 16px; /* Adjust font size */
  }

  .not-edit-mode {
    gap: 15px; /* Reduce gap for smaller screens */
    margin: 10px 0; /* Adjust margins */
  }

  /* Additional styles for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .category-item {
    flex-direction: column; /* Stack items vertically */
    align-items: stretch; /* Stretch items to fill the container */
    gap: 8px; /* Reduce gap further for very small screens */
  }

  #category-input {
    width: 100%; /* Ensure input takes full width */
    font-size: 14px; /* Adjust font size for smaller screens */
    padding: 8px; /* Reduce padding */
  }

  #new-category-btn {
    width: 100%; /* Take full width */
    height: 45px; /* Slightly reduce height */
    font-size: 14px; /* Adjust font size */
  }

  #delete-btn {
    width: 100%; /* Take full width */
    font-size: 14px; /* Adjust font size */
    padding: 8px; /* Add padding for better touch usability */
  }

  .not-edit-mode {
    gap: 10px; /* Reduce gap further */
    margin: 5px 0; /* Adjust margins */
  }

  .category-text {
    font-size: 14px; /* Adjust font size for smaller screens */
    word-wrap: break-word; /* Ensure long text wraps properly */
  }
}
}
</style>
