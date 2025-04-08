<script setup>
//Get categories from backend
import { ref, defineProps } from 'vue'

const categories = ref([
  { id: 1, name: 'Boats' },
  { id: 2, name: 'Cars' },
  { id: 3, name: 'Motorcycles' },
  { id: 4, name: 'Real Estate' },
]);

const props = defineProps ({
  isEditMode: Boolean
})

const deleteCategory = () => {
  // Logic to delete the category
}

const newCategory = () => {
  categories.value.push({
    id: categories.value.length + 1,
    name: ''
  })
}

</script>

<template>
<div class="category-container">
    <div class="edit-mode" v-if="isEditMode">
      <button class="basic-blue-btn" id="new-category-btn" @click="newCategory">+</button>
      <div class="category-item" v-for="(category, index) in categories" :key="category.id">
        <button class="basic-blue-btn" id="delete-btn" @click="() => deleteCategory(index)">Delete</button>
        <input type="text" v-model=category.name />
      </div>
    </div>
    <div class="not-edit-mode" v-if="!isEditMode">
      <div class="category-item" v-for="category in categories" :key="category.id">
        <p class="category-text">{{ category.name }}</p>
      </div>
    </div>
</div>
</template>

<style scoped>
.category-item {
  display: flex;
  align-items: center;
  gap: 12px;
  width: 100%;
}

.not-edit-mode {
  display: flex;
  flex-direction: column;
  gap: 50px;
  margin: 78px 0 0 83px;
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

</style>
