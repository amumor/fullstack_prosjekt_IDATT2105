<script setup>
//Get categories from backend
import { ref, defineProps } from 'vue'

const categories = [
  { id: 1, name: 'Boats' },
  { id: 2, name: 'Cars' },
  { id: 3, name: 'Motorcycles' },
  { id: 4, name: 'Real Estate' },
];

const props = defineProps({
  isEditMode: Boolean
});

const isEditMode1 = ref(props.isEditMode);

const deleteCategory = () => {
  // Logic to delete the category
}

const updateCategory = (category) => {
  // Logic to update the category

}

</script>

<template>
<div class="category-container">
    <div class="edit-mode" :v-if=isEditMode1>
      <div v-for="category in categories" :key="category.id">
        <button id="delete-btn" class="basic-blue-btn" @click="deleteCategory">Delete</button>
        <input type="text" id="category" v-model=category.name />
      </div>
    </div>
    <div class="not-edit-mode" :v-if=!isEditMode1>
      <div v-for="category in categories" :key="category.id">
        <input type="text" id="category" />
      </div>
    </div>
</div>
</template>

<style scoped>
.basic-blue-btn {
  background-color: crimson;
  width: 20%;
}
</style>
