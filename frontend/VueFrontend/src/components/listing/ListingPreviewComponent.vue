<script setup>
import { defineProps } from 'vue';
import { useRouter } from 'vue-router';

const props = defineProps({
  id: Number,
  image: String,
  price: String,
  town: String,
  title: String,
})
const router = useRouter();

const toListingView = () => {
  router.push('/listing/' + props.id);
}
</script>

<template>
  <button class="listings" @click="toListingView">
    <span class="image-container">
      <img class="image-item" :src="props.image" alt="Boat">
      <span class="price">{{ props.price }}</span>
    </span>
    <span class="description">
      <span class="town">{{ props.town }}</span>
      <span class="title">{{ props.title }}</span>
    </span>
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
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

/* Responsive design for smaller screens */
@media (max-width: 768px) {
  .listings {
    padding: 8px;
    border-radius: 8px;
    box-shadow: 1px 1px 8px rgba(0, 0, 0, 0.1);
  }

  .image-item {
    width: 100%; /* Make the image take full width */
    max-width: 300px; /* Limit the maximum width */
    height: auto;
  }

  .price {
    font-size: 12px; /* Adjust font size for smaller screens */
    padding: 4px;
  }

  .town {
    font-size: 14px; /* Adjust font size */
  }

  .title {
    font-size: 20px; /* Adjust font size */
  }

  .description {
    margin: 8px;
    align-items: flex-start;
  }
}

@media (max-width: 480px) {
  .listings {
    padding: 6px;
    border-radius: 6px;
  }

  .image-item {
    max-width: 100%; /* Ensure the image fits within the container */
  }

  .price {
    font-size: 10px; /* Further reduce font size for very small screens */
    padding: 3px;
  }

  .town {
    font-size: 12px;
  }

  .title {
    font-size: 18px;
  }

  .description {
    margin: 6px;
  }
}
</style>
