<script setup>
import { defineProps } from 'vue';
import { Icon } from '@iconify/vue';

const props = defineProps({
  title: String,
  shortDescription: String,
  iconString: String
});

</script>

<template>
<button class="option-btn">
  <div class="option-icon">
    <Icon :icon="props.iconString" width="40" height="40" />
  </div>
  <div class="option-text">
    <h2>{{ props.title }}</h2>
    <p>{{ props.shortDescription }}</p>
  </div>
</button>
</template>

<style scoped>
.option-btn {
  background-color: #D9D9D9;
  border-radius: 5px;
  border: none;

  position: relative;
  text-align: left;
  cursor: pointer;
}

.option-btn:hover {
  background-color: #7BA5FF;
  transform: scale(1.02);
}

.option-icon {
  color: #333333;
  margin-left: 5px;
}

.option-text {
  font-family: 'Inter', sans-serif;
  color: #333333;
  padding: 10px;
}

</style>
