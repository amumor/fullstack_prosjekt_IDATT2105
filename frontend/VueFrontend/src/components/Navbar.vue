<script>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  isLoggedIn: {
    type: Boolean,
    default: false
  },
  isMobile: {
    type: Boolean,
    default: false
  }
});
</script>

<template>
<div class="navbar">

  <!-- Desktop Menu -->
  <template v-if="!isMobile">
    <div class="desktop-navbar">
      <h4 class="logo-header">FIND.no</h4>
      <div class="options">

        <!-- Logged in menu -->
        <template v-if="isLoggedIn">
          <router-link to ="/newListing">New listing</router-link>
          <router-link to ="/favourites">Favourites</router-link>
          <router-link to ="/inbox">Inbox</router-link>
          <router-link to ="/profile">Profile</router-link>
        </template>

        <!-- Logged out menu -->
        <template v-else>
          <router-link to ="/login">Login</router-link>
          <router-link to ="/register">Register</router-link>
        </template>
      </div>
    </div>
  </template>

  <!-- Mobile Menu -->
  <template v-else>
    <div class="mobile-navbar">
      <h4 class="logo-header">FIND.no</h4>
      <div class="options">
        <template v-if="isLoggedIn">

        </template>
        <template v-else>

        </template>
      </div>
    </div>
  </template>
</div>
</template>

<style scoped>

</style>
