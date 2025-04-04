<script setup>
import CredentialsComponent from '@/components/Login/CredentialsComponent.vue'
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
</style>
