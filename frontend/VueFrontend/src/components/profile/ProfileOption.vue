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
  <span class="option-icon">
    <Icon :icon="props.iconString" width="40" height="40" />
  </span>
  <span class="option-text">
    <h2>{{ props.title }}</h2>
    <p>{{ props.shortDescription }}</p>
  </span>
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

}

/* Header */
h2 {
  font-size: 20px;
}

/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .option-btn {
    padding: 10px; /* Add padding for medium screens */
    flex-direction: column; /* Stack content vertically */
    align-items: flex-start;
  }

  .option-icon {
    width: 30px; /* Adjust icon size */
    height: 30px;
    margin-left: 0; /* Remove left margin */
  }

  .option-text h2 {
    font-size: 18px; /* Adjust heading font size */
  }

  .option-text p {
    font-size: 14px; /* Adjust paragraph font size */
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .option-btn {
    padding: 8px; /* Reduce padding for small screens */
    flex-direction: column; /* Stack content vertically */
    align-items: flex-start;
  }

  .option-icon {
    width: 25px; /* Further adjust icon size */
    height: 25px;
  }

  .option-text h2 {
    font-size: 16px; /* Further adjust heading font size */
  }

  .option-text p {
    font-size: 12px; /* Further adjust paragraph font size */
  }
}
</style>
