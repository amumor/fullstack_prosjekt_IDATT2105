<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  name: String,
  height: Number,
  width: Number,
});
const getInitials = (name) => {
  if (!props.name) return '';
  const nameArray = name.split(' ');
  return nameArray
    .map((word) => word.charAt(0).toUpperCase())
    .join('');
};
</script>

<template>
<div class="profile-icon" :style="{ height: props.height + 'px', width: props.width + 'px', fontSize: props.width/3 + 'px' }">
  <span>{{ getInitials(props.name) }}</span>
</div>
</template>

<style scoped>
/* Profile icon */
.profile-icon {
  background-color: #D9D9D9;
  border-radius: 50%;
  color: #333333;
  text-transform: uppercase;
  font-family: 'Inter', sans-serif;

  display: flex;
  justify-content: center;
  align-items: center;
}

/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .profile-icon {
    height: 60px; /* Adjust height */
    width: 60px; /* Adjust width */
    font-size: 20px; /* Adjust font size */
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .profile-icon {
    height: 50px; /* Further adjust height */
    width: 50px; /* Further adjust width */
    font-size: 16px; /* Further adjust font size */
  }
}
</style>
