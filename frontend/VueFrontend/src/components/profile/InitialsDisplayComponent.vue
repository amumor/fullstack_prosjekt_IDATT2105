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
</style>
