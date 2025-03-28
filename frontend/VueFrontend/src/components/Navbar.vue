<script setup>
import { ref, defineProps, onMounted } from 'vue';

const props = defineProps({
  isLoggedIn: {
    type: Boolean,
    default: false
  },
});

// Mobile menu state
const isOpen = ref(false);
const isMobile = ref(window.innerWidth <= 768);

const toggleMenu = () => {
  isOpen.value = !isOpen.value;
};

// Check screen size
const checkScreenSize = () => {
  isMobile.value = window.innerWidth <= 768;
  if (!isMobile.value) isOpen.value = false;
};

// Detect screen resize
onMounted(() => {
  window.addEventListener('resize', checkScreenSize);
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
          <template v-if="props.isLoggedIn">
            <router-link to ="/newListing">New listing</router-link>
            <router-link to ="/favourites">Favourites</router-link>
            <router-link to ="/inbox">Inbox</router-link>
            <router-link to ="/profile">Profile</router-link>
          </template>

          <!-- Logged out menu -->
          <template v-else>
            <router-link to ="/login">Log in</router-link>
          </template>
        </div>
      </div>
    </template>

    <!-- Mobile Dropdown Menu -->
    <template v-else>
      <div class="mobile-navbar">
        <h4 class="logo-header">FIND.no</h4>
        <button class="menu-btn" @click="toggleMenu">☰</button>
        <div class="options">

          <ul v-show="isOpen" class="dropdown">
            <!-- Logged In Menu -->
            <template v-if="props.isLoggedIn" v-show="isOpen">
              <li><router-link to="/newListing" @click="toggleMenu">New listing</router-link></li>
              <li><router-link to="/favourites" @click="toggleMenu">Favourites</router-link></li>
              <li><router-link to="/inbox" @click="toggleMenu">Inbox</router-link></li>
              <li><router-link to="/profile" @click="toggleMenu">Profile</router-link></li>
            </template>

            <!-- Logged Out Menu -->
            <template v-else>
              <li><router-link to="/login">Log in</router-link></li>
            </template>
          </ul>

        </div>
      </div>
    </template>
  </div>
</template>

<style scoped>

</style>
