<script setup>
import SingleListingComponent from '@/components/Listing/SingleListingComponent.vue'
import Navbar from '@/components/Navbar.vue'

document.body.style.backgroundColor = "#ffffff";

// Dummy data for listing
const title="BÅT MEGA";
const description="Massemammssemamaasad oifdjvl uifhiiiififif jdjdjdjdj";
const price="100kr";
const location="Home";
const category="Guitar";
const lastEdited="1. april 2023";
const image="https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg";

// Fix login logic
const isLoggedIn = true;
</script>

<template>

  <!-- Navbar -->
  <Navbar :isLoggedIn=isLoggedIn />

  <!-- Listing component -->
  <div class="listing-container">
    <SingleListingComponent
      :title=title
      :description=description
      :price=price
      :location=location
      :category=category
      :lastEdited=lastEdited
      :image=image
      :isLoggedIn=isLoggedIn />
  </div>
</template>

<style scoped>
.listing-container {
  display: flex;
  flex-direction: column;
  justify-self: center;
  align-items: center;
  width: 100%;
  max-width: 800px;
  padding: 20px;
}
</style>
