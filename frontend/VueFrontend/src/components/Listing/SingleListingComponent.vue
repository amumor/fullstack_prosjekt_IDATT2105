<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
import { Icon } from '@iconify/vue'
import ListingMapComponent from '@/components/Listing/ListingMapComponent.vue'

const props = defineProps({
  id: String,
  title: String,
  description: String,
  price: String,
  location: Array,
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
  router.back()
}

const toEditListing = () => {
  router.push('/listing/' + props.id + '/edit');
}

</script>

<template>
<div class="display-page-container">

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
      <p id="price">{{ props.price }}</p>
      <p id="description">{{ props.description }}</p>
      <p id="categories">{{ props.category }}</p>
    </div>

    <!-- Buy item or message seller -->
    <div class="btn" v-if="props.isLoggedIn && !isOwner">
      <button class="message-btn">Message seller</button>
      <button class="buy-btn">Buy</button>
    </div>

    <!-- Owner options -->
    <div class="owner-options">
      <template v-if="isOwner">
        <button class="owner-btn" @click="toEditListing">Edit</button>
        <button class="owner-btn">Archive</button>
        <button class="owner-btn" id="delete" @click=delListing>Delete</button>
      </template>
    </div>

    <!-- Map -->
    <div class="map">
      <ListingMapComponent
        :location=props.location />
    </div>



  </div>


</div>
</template>

<style scoped>
.display-page-container {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);

  width: 100%;
  display: flex;
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
.sidebar {
  margin: 10px 15px 15px 15px;
}
.description {
  width: 100%;
  padding: 5px 0;
}

#price {
  font-size: 20px;
  color: #333333;
}

#description, #categories {
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
  font-size: 16px;
  transition: all 0.3s ease;

  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  line-height: 10px;
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
  background-color: #0056b3;
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
