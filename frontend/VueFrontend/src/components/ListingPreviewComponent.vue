<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  image: String,
  price: String,
  town: String,
  title: String,
})

</script>

<template>
  <button class="listings">
    <div class="image-container">
      <img class="image-item" :src="props.image" alt="Boat">
      <div class="price">{{ props.price }}</div>
    </div>
    <div class="description">
      <div class="town">{{ props.town }}</div>
      <div class="title">{{ props.title }}</div>
    </div>

  </button>
</template>

<style scoped>
.listings {
  position: relative;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 100%;
  transition: all 0.3s ease;
  cursor: pointer;
  display: flex;
  flex-direction: column;
}

.listings:hover {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
  transform: translateY(-2px);
}

/* Image container */
.image-container {
  position: relative;
  overflow: hidden;
  border-radius: 10px;
  margin: 10px  10px 0 10px ;
}

.image-item {
  width: 200px;
  height: auto;
  display: block;
}

.price {
  color: white;
  position: absolute;
  bottom: 10px;
  left: 10px;
  padding: 5px;
  border-radius: 5px;
}

/* Listing info */
.town {
  font-size: 15px;
  color: darkgray;
}

.title {
  font-size: 25px;
  color: #333333;
}

.description {
  margin: 10px;
  text-align: left;
}
</style>
