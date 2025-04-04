<script setup>
import NewListingComponent from '@/components/Listing/NewListingComponent.vue'
import Navbar from '@/components/Navbar.vue'

</script>

<template>
  <Navbar :isLoggedIn="true"/>
  <div class="display-page-container">
    <NewListingComponent />
  </div>
</template>

<style scoped>
</style>
