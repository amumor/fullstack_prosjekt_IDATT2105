<script setup>
import { ref } from 'vue'
import Navbar from '@/components/Navbar.vue'
import AdminCategoriesComponent from '@/components/admin/AdminCategoriesComponent.vue'

const isEditeMode = ref(false);

const toggleEditMode = () => {
  isEditeMode.value = !isEditeMode.value;
}

</script>

<template>
<Navbar />
<div class="display-page-container">
  <div class="header-container">
    <h2>Admin settings</h2>
    <div class="edit-mode" v-if="isEditeMode">
      <button class="basic-blue-btn" @click="toggleEditMode">Save</button>
    </div>
    <div class="edit-mode" v-else>
      <button class="basic-blue-btn" id="edit-btn" @click="toggleEditMode">Edit</button>
    </div>
  </div>
  <AdminCategoriesComponent :isEditMode=isEditeMode />
</div>

</template>

<style scoped>
.header-container {
  display: flex;
  justify-content: space-between;
}

#edit-btn {
  background-color: white;
  color: #333333;
  border: #1C64FF 2px solid;  
}

#edit-btn:hover {
  background-color: #D9D9D9;
}

.basic-blue-btn {
  width: 100px;
  height: 50px;
  padding: 10px;
}
</style>
