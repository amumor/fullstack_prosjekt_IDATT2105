<script setup>
import CredentialsComponent from '@/components/login/CredentialsComponent.vue'
document.body.style.backgroundColor = "#D9D9D9";
</script>

<template>
<div class="display-page-container">
  <div class="credentials-container">
    <CredentialsComponent
      :hasUser=true />
  </div>
</div>
</template>

<style scoped>

.credentials-container {
  display: flex;
  justify-content: flex-start;
  max-width: 1000px;
  width: 100%;
  margin-left: -10%;
}

/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .display-page-container {
    padding: 20px; /* Add padding for medium screens */
    justify-content: center; /* Center align content */
  }

  .credentials-container {
    max-width: 90%; /* Reduce width for medium screens */
    margin-left: 0; /* Remove negative margin */
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .display-page-container {
    padding: 15px; /* Reduce padding for small screens */
    justify-content: center; /* Center align content */
  }

  .credentials-container {
    max-width: 100%; /* Take full width */
    margin-left: 0; /* Remove negative margin */
  }
}
</style>
