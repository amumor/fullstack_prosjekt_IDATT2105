<script setup>
import { ref, defineProps, onMounted } from 'vue';
import { Icon } from '@iconify/vue'

/**
 * @property {Boolean} isLoggedIn - Indicates if the user is logged in.
 */
const props = defineProps({
  isLoggedIn: {
    type: Boolean,
    default: false
  },
});

// Mobile menu state
const isUserLoggedIn = ref(props.isLoggedIn);
const isOpen = ref(false);
const isMobile = ref(window.innerWidth <= 768);
const isAdmin = ref(true);

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
  <router-link to ="/" id="header">FIND.no</router-link>

  <!-- Desktop Menu -->
  <template v-if="!isMobile">
    <div class="desktop-navbar">
      <div class="options">

        <!-- Logged in menu -->
        <template v-if="isUserLoggedIn">
          <router-link to ="/newListing" id="router-link">New listing</router-link>
          <router-link to ="/profile/favorites" id="router-link">Favorites</router-link>
          <router-link to ="/inbox" id="router-link">Inbox</router-link>
          <router-link to ="/profile" id="router-link">Profile</router-link>

          <!-- Admin settings -->
          <router-link to="/admin" id="admin-settings" v-if="isAdmin">
            <Icon :icon="'material-symbols:settings'" width="30" height="30" />
          </router-link>
        </template>

        <!-- Logged out menu -->
        <template v-else>
          <router-link to ="/login" id="router-link">Log in</router-link>
        </template>
      </div>
    </div>
  </template>

  <!-- Mobile Dropdown Menu -->
  <template v-else>
    <div class="mobile-navbar">
      <button class="menu-btn" @click="toggleMenu">☰</button>
      <div class="options">

        <ul v-show="isOpen" class="dropdown">
          <!-- Logged In Menu -->
          <template v-if="isUserLoggedIn" v-show="isOpen">
            <li><router-link to="/newListing" @click="toggleMenu" id="router-link">New listing</router-link></li>
            <li><router-link to="/profile/favorites" @click="toggleMenu" id="router-link">Favorites</router-link></li>
            <li><router-link to="/inbox" @click="toggleMenu" id="router-link">Inbox</router-link></li>
            <li><router-link to="/profile" @click="toggleMenu" id="router-link">Profile</router-link></li>

            <!-- Admin settings -->
            <li><router-link to="/admin" @click="toggleMenu" id="admin-settings" v-if="isAdmin">
              <Icon :icon="'material-symbols:settings'" width="30" height="30" />
            </router-link></li>
          </template>

          <!-- Logged Out Menu -->
          <template v-else>
            <li><router-link to="/login" id="router-link">Log in</router-link></li>
          </template>
        </ul>

      </div>
    </div>
  </template>
</div>
</template>

<style scoped>
.navbar {
  font-family: 'Inter', sans-serif;
  background-color: #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);

  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
}

#header {
  text-decoration: none;
  color: #333;
  font-size: 24px;
  font-weight: bold;
}

#router-link {
  text-decoration: none;
  color: #333;
  margin-right: 20px;
}

#admin-settings {
  text-decoration: none;
  color: #333;
  margin-right: 20px;
}

.desktop-navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.desktop-navbar .options {
  display: flex;
  gap: 20px;
  margin-left: auto;
}

.options a {
  color: white;
  text-decoration: none;
  background-color: #D9D9D9;

  padding: 10px 15px;
  border-radius: 5px;
  transition: background-color 0.3s, transform 0.3s;
}

.options #admin-settings {
  text-decoration: none;
  background-color: transparent;
  padding: 7px 0 0 0;
}

.options a:hover {
  color: #f1f1f1;
  transform: scale(1.005);
}

/* Mobile Navbar */
.mobile-navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.mobile-navbar .options {
  display: flex;
  gap: 20px;
  margin-left: auto;
}

.menu-btn {
  font-size: 28px;
  background: none;
  border: none;
  color: black;

  cursor: pointer;
  padding: 20px;
  transition: color 0.3s;
  position: absolute;
  right: 0;
}

.menu-btn:hover {
  color: #0056b3;
}

/* Dropdown menu */
.dropdown {
  background-color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);

  position: absolute;
  top: 60px;
  right: 20px;
  padding: 10px;
  border-radius: 4px;
  list-style-type: none;
  width: 200px;
  z-index: 1000;
}

.dropdown li {
  padding: 10px;
}

.dropdown li a {
  text-decoration: none;
  color: black;
  display: block;
}

.dropdown li a:hover {
  background-color: #f1f1f1;
}

/* When isOpen is true, show dropdown */
.dropdown[style*="display: block"] {
  display: block;
}

/* Media Query for Mobile */
@media (max-width: 768px) {
  .desktop-navbar {
    display: none;
  }

  .mobile-navbar {
    display: flex;
  }
}
</style>
