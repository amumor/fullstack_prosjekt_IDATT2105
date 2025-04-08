<script setup>
import SingleListingComponent from '@/components/listing/SingleListingComponent.vue'
import Navbar from '@/components/Navbar.vue'

document.body.style.backgroundColor = "#ffffff";

// Dummy data for listing
const id= "1";
const title="BÅT MEGA";
const description="Massemammssemamaasad oifdjvl uifhiiiififif jdjdjdjdj";
const price="100kr";
const location=[63.417152858467574, 10.404550601471463];
const category="Guitar";
const lastEdited="1. april 2023";
const image="https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg";

</script>

<template>

  <Navbar />

  <!-- Listing component -->
  <div class="display-page-container">
    <SingleListingComponent :id=id />
  </div>
</template>

<style scoped>
.display-page-container {
  justify-self: center;
  max-width: 800px;
  padding: 20px;
}
</style>
