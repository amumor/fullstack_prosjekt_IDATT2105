<script setup>
import Navbar from '@/components/Navbar.vue'
import EditListingComponent from '@/components/Listing/EditListingComponent.vue'

const id = '44'
const image = 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg'
const title = 'Big boat 1'
const description = 'This is a big boat'
const price = '100 mill'
const location = [60.472024, 8.524123] // Oslo coordinates
</script>

<template>
<Navbar :isLoggedIn="true" />
<EditListingComponent
  :id="id"
  :image="image"
  :title="title"
  :description="description"
  :price="price"
  :location="location" />
</template>

<style scoped>

</style>
