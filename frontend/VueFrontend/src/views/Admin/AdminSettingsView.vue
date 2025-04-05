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
<Navbar :isLoggedIn=true />
<div class="display-page-container">
  <h2>Admin settings</h2>
  <div class="edit-mode" v-if="isEditeMode">
    <button class="basic-blue-btn" @click="toggleEditMode">Save</button>
  </div>
  <div class="not-edit-mode" v-else>
    <button class="basic-blue-btn" @click="toggleEditMode">Edit</button>
  </div>
  <AdminCategoriesComponent :isEditeMode=isEditeMode />
</div>

</template>

<style scoped>

</style>
