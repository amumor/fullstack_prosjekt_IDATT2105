<script setup>
import CredentialsBox from '@/components/CredentialsBox.vue'
document.body.style.backgroundColor = "#D9D9D9";
</script>

<template>
<div class="display-page-container">
  <div class="credentials-container">
    <CredentialsBox
      :hasUser=true />
  </div>
</div>
</template>

<style scoped>

.display-page-container {
  background: #D9D9D9;
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  overflow-x: hidden;
}

.credentials-container {
  display: flex;
  justify-content: flex-start;
  max-width: 1000px;
  width: 100%;
  margin-left: -10%;
}
</style>
