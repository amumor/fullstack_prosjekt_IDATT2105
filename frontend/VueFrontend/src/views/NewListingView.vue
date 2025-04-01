<script setup>
import NewListingComponent from '@/components/Listing/NewListingComponent.vue'
import Navbar from '@/components/Navbar.vue'

</script>

<template>
  <Navbar :isLoggedIn="true"/>
  <NewListingComponent />
</template>

<style scoped>

</style>
