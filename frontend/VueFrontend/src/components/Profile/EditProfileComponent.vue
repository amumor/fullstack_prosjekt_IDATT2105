<script setup>

</script>

<template>
<div class="display-credentials-container">
  <h1 class="display-credentials-title">Edit Profile</h1>
  <div class="display-credentials-content">
    <div class="display-credentials-item">
      <label for="username">Username:</label>
      <input type="text" id="username" v-model="username" />
    </div>
    <div class="display-credentials-item">
      <label for="email">Email:</label>
      <input type="email" id="email" v-model="email" />
    </div>
    <div class="display-credentials-item">
      <label for="password">Password:</label>
      <input type="password" id="password" v-model="password" />
    </div>
    <button @click="saveChanges">Save changes</button>
  </div>
</div>
</template>

<style scoped>

</style>
