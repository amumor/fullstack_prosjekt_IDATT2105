<script setup>
import SingleListingComponent from '@/components/listing/SingleListingComponent.vue'
import Navbar from '@/components/Navbar.vue'
import { userStore } from '@/stores/user.js'
import { useListingStore } from '../../stores/listing';
import { on } from 'superagent';
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';

document.body.style.backgroundColor = "#ffffff";

// Dummy data for listing
/**
const id= "b21edfdc-9d97-4df5-8e26-53dba65311ce";
const title="BÅT MEGA";
const description="Massemammssemamaasad oifdjvl uifhiiiififif jdjdjdjdj";
const price="100kr";
const location=[63.417152858467574, 10.404550601471463];
const category="Guitar";
const lastEdited="1. april 2023";
const image="https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg";
*/

</script>

<template>

  <Navbar />

  <!-- Listing component -->
  <div class="display-page-container">
    <SingleListingComponent />
  </div>
</template>

<style scoped>
.display-page-container {
  justify-self: center;
  max-width: 800px;
  padding: 20px;
}

/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .display-page-container {
    max-width: 90%; /* Reduce width for medium screens */
    padding: 15px; /* Adjust padding */
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .display-page-container {
    max-width: 100%; /* Take full width */
    padding: 10px; /* Reduce padding */
  }
}
</style>
