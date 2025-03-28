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

const isOpen = ref(false);
const toggleMenu = () => {
  isOpen.value = !isOpen.value;
};
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

  <!-- Mobile Dropdown Menu -->
  <template v-else>
    <div class="mobile-navbar">
      <h4 class="logo-header">FIND.no</h4>
      <button class="menu-btn" @click="toggleMenu" v-if="isMobile">☰</button>
      <div class="options">

        <!-- Logged In Menu -->
        <template v-if="isLoggedIn">
          <li><router-link to="/newListing" @click="toggleMenu">New listing</router-link></li>
          <li><router-link to="/favourites" @click="toggleMenu">Favourites</router-link></li>
          <li><router-link to="/inbox" @click="toggleMenu">Inbox</router-link></li>
          <li><router-link to="/profile" @click="toggleMenu">Profile</router-link></li>
        </template>

        <!-- Logged Out Menu -->
        <template v-else>
          <li><router-link to="/login" @click="toggleMenu">Login</router-link></li>
        </template>

      </div>
    </div>
  </template>
</div>
</template>

<style scoped>

</style>
