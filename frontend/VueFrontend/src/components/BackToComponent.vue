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
  padding: 10px 15px;
  border-radius: 5px;
  max-height: 40px;
  line-height: 20px;
}
</style>
