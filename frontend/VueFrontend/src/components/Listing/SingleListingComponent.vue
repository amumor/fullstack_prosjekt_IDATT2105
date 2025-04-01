<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
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

const router = useRouter();
const isOwner = ref(true);
const isFavorite = ref(false);

const toggleFavorite = () => {
  isFavorite.value = !isFavorite.value;
}

const delListing = () => {
  // Logic to delete the listing
  router.push('/myListings');
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
    <div class="btn" v-if="props.isLoggedIn && !isOwner">
      <button class="message-btn">Message seller</button>
      <button class="buy-btn">Buy</button>
    </div>

    <!-- Owner options -->
    <div class="owner-options">
      <template v-if="isOwner">
        <button class="owner-btn">Edit</button>
        <button class="owner-btn">Archive</button>
        <button class="owner-btn" id="delete" @click=delListing>Delete</button>
      </template>
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
  height: 100%;
  border-radius: 10px;
  overflow: hidden;
  max-width: 350px;
  margin: 15px;
}

.image-item {
  width: 100%;
  height: 295px;
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
.btn,
.owner-options {
  display: flex;
  gap: 10px;
  width: 100%;
  max-height: 40px;
  max-width: 400px;
  margin-bottom: 10px;
}

.buy-btn,
.message-btn,
.owner-btn {
  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.buy-btn,
.owner-btn {
  background-color: white;
  color: #333333;
  border: #1C64FF 2px solid;
}

.buy-btn:hover,
.owner-btn:hover {
  background-color: #D9D9D9;
}

.message-btn {
  background-color: #1C64FF;
  color: white;
}

.message-btn:hover {
  background-color: #0066cc;
}

#delete:hover {
  background-color: crimson;
  border: crimson 2px solid;
}

/* Map placeholder */
.map {
  width: 100%;
  height: 150px;
  background-color: #eee;
  border-radius: 10px;
}
</style>
