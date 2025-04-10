<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goBack = () => {
  router.back();
}
</script>

<template>
<button @click="goBack">Go back</button>
</template>

<style scoped>
button {
  text-decoration: none;
  font-family: 'Inter', sans-serif;
  color: #ffffff;
  font-size: 16px;
  background-color: #007bff;

  margin-bottom: 10px;
  margin-top: 10px;
  padding: 10px 15px;
  border-radius: 5px;
  max-height: 40px;
  line-height: 20px;
}

/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  button {
    font-size: 14px; /* Adjust font size */
    padding: 8px 12px; /* Adjust padding */
    max-height: 35px; /* Adjust height */
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  button {
    font-size: 12px; /* Further adjust font size */
    padding: 6px 10px; /* Further adjust padding */
    max-height: 30px; /* Further adjust height */
  }
}
</style>
