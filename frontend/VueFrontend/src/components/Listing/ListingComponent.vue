<script setup>
import { ref, defineProps } from 'vue';
import { Icon } from '@iconify/vue'

const props = defineProps({
  title: String,
  description: String,
  price: String,
  location: String,
  category: String,
  lastEdited: String,
  image: String,
  isLoggedIn: Boolean,
})

const isFavorite = ref(false);
const toggleFavorite = () => {
  isFavorite.value = !isFavorite.value;
}
</script>

<template>
<div class="listing">

  <!-- Image container -->
  <div class="image-container">
    <img class="image-item" :src="props.image" alt="Front image">
    <button class="favorite" :class="{ 'isFavorite': isFavorite }" @click="toggleFavorite">
      <Icon icon="material-symbols:favorite" width="40" height="40" />
    </button>
    <p id="lastEdited">Last edited: {{ props.lastEdited }}</p>
  </div>

  <div class="sidebar">

    <!-- Description -->
    <div class="description">
      <h2>{{ props.title }}</h2>
      <p id="description">{{ props.description }}</p>
    </div>

    <!-- Buy item or message seller -->
    <div class="btn" v-if="props.isLoggedIn">
      <button class="message-button">Message seller</button>
      <button class="buy-button">Buy</button>
    </div>

    <!-- Map -->
    <div class="map">

    </div>

  </div>


</div>
</template>

<style scoped>
.listing {
  background-color: #fff;
  font-family: 'Inter', sans-serif;
  border-radius: 10px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);

  padding: 15px;
  max-width: 1000px;
  width: 100%;
  display: flex;
  gap: 15px;
}

/* Image container */
.image-container {
  color: #777;
  font-size: 15px;

  position: relative;
  width: 100%;
  border-radius: 10px;
  overflow: hidden;
  max-width: 350px;
  margin: 15px;
}

.image-item {
  width: 100%;
  height: 250px;
  object-fit: cover;
  border-radius: 10px;
}

/* Favorite button */
.favorite {
  background: transparent;
  color: rgba(255, 255, 255, 0.8);
  border-radius: 50%;
  border: none;

  position: absolute;
  top: 10px;
  right: 10px;
  padding: 8px;
  cursor: pointer;
}

.isFavorite {
  color: crimson; /* Red when favorite */
}

.favorite:hover {
  color: crimson;
}

/* Description */
.description {
  width: 100%;
  padding: 5px 0;
}

.description h2 {
  font-size: 24px;
  font-weight: 500;
  color: #333;
}

.description p {
  font-size: 15px;
  color: #777;
}

/* Buttons */
.btn {
  display: flex;
  gap: 10px;
  width: 100%;
  max-height: 40px;
  max-width: 400px;
  margin-bottom: 10px;
}

.buy-button,
.message-button {
  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.buy-button {
  background-color: white;
  color: #333333;
  border: #1C64FF 2px solid;
}

.buy-button:hover {
  background-color: #D9D9D9;
}

.message-button {
  background-color: #1C64FF;
  color: white;
}

.message-button:hover {
  background-color: #0066cc;
}

/* Map placeholder */
.map {
  width: 100%;
  height: 150px;
  background-color: #eee;
  border-radius: 10px;
}

</style>
