<script setup>
import { ref, onMounted } from 'vue';
import { Icon } from '@iconify/vue'
import { userStore } from '@/stores/user.js';
import { useI18n } from 'vue-i18n'

const { locale } = useI18n()

const user = userStore();

// Mobile menu state
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

// Set language based on user preference
const setLanguage = (lang) => {
  locale.value = lang
  localStorage.setItem('lang', lang) 
}

// Detect screen resize
onMounted(() => {
  window.addEventListener('resize', checkScreenSize);

  const savedLang = localStorage.getItem('lang')
  if (savedLang) locale.value = savedLang
});
</script>

<template>
<div class="navbar">
  <router-link to="/" id="header">{{ $t('logo') }}</router-link>

  <!-- Desktop Menu -->
  <template v-if="!isMobile">
    <div class="desktop-navbar">
      <div class="options">

        <!-- Logged in menu -->
        <template v-if="user.isLoggedIn">
          <router-link to="/newListing" id="router-link">{{ $t('header.new-listing') }}</router-link>
          <router-link to="/profile/favorites" id="router-link">{{ $t('header.favorites') }}</router-link>
          <router-link to="/inbox" id="router-link">{{ $t('header.inbox') }}</router-link>
          <router-link to="/profile" id="router-link">{{ $t('header.profile') }}</router-link>

          <!-- Admin settings -->
          <router-link to="/admin" id="admin-settings" v-if="isAdmin">
            <Icon :icon="'material-symbols:settings'" width="30" height="30" />
          </router-link>
        </template>

        <!-- Logged out menu -->
        <template v-else>
          <router-link to="/login" id="router-link">{{ $t('header.login') }}</router-link>
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
          <template v-if="user.isLoggedIn" v-show="isOpen">
            <li><router-link to="/newListing" @click="toggleMenu" id="router-link">{{ $t('button.new-listing') }}</router-link></li>
            <li><router-link to="/profile/favorites" @click="toggleMenu" id="router-link">{{ $t('button.favorites') }}</router-link></li>
            <li><router-link to="/inbox" @click="toggleMenu" id="router-link">{{ $t('button.inbox') }}</router-link></li>
            <li><router-link to="/profile" @click="toggleMenu" id="router-link">{{ $t('button.profile') }}</router-link></li>

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

  <!-- Language Dropdown -->
  <div class="language-dropdown">
    <button class="dropbtn">{{ $t('languages.language') }}
      <Icon :icon="'material-symbols:globe'" width="20" height="20" />
    </button>
    <div class="language-content">
      <a href="#" @click="setLanguage('en')">{{ $t('languages.english') }}</a>
      <a href="#" @click="setLanguage('no')">{{ $t('languages.norwegian') }}</a>
      <a href="#" @click="setLanguage('fr')">{{ $t('languages.french') }}</a>
      <a href="#" @click="setLanguage('es')">{{ $t('languages.spanish') }}</a>
      <a href="#" @click="setLanguage('cmn')">{{ $t('languages.mandarin') }}</a>
      <a href="#" @click="setLanguage('ar')">{{ $t('languages.arabic') }}</a>
    </div>
  </div>
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
  color: #333;

  cursor: pointer;
  padding: 20px;
  transition: color 0.3s;
  position: absolute;
  right: 120px;
}

.menu-btn:hover {
  color: #0056b3;
}

/* Dropdown language menu */
.dropdown {
  display: none;
  flex-direction: column;
  background-color: #ffffff;
  position: absolute;
  top: 60px;
  right: 220px;
  width: 200px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
  border-radius: 8px;
  
  padding: 10px 0;
}

.dropdown[style*="display: block"] {
  display: flex !important;
}

.dropdown li {
  list-style: none;
  padding: 0;
  margin: 0;
}

.dropdown li a {
  text-decoration: none;
  color: #333;
  display: block;
  padding: 10px 15px;
  transition: background-color 0.3s;
}

.dropdown li a:hover {
  background-color: #f1f1f1;
}

/* Language Dropdown */
.language-dropdown {
  position: relative;
  display: inline-block;
  margin-left: 30px;
  z-index: 999;
  
}

.dropbtn {
  color: #333;
  padding: 7px 10px;
  font-size: 18px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
  margin-right: 10px;
  margin-top: 2px;
  transition: background-color 0.3s;
  border: 1px solid #333;
}

.dropbtn:hover {
  background-color: #f0f0f0;
}

.language-content {
  display: none;
  position: absolute;
  top: 100%;
  right: 0;
  background-color: #ffffff;
  min-width: 160px;
  border-radius: 5px;
  overflow: hidden;
  padding: 5px 0;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.1);
}

.language-dropdown:hover .language-content {
  display: block;
}

.language-content a {
  color: #333;
  padding: 10px 15px;
  text-decoration: none;
  display: block;
  font-size: 14px;
}

.language-content a:hover {
  background-color: #f0f0f0;
}

/* Media for Mobile */
@media (max-width: 768px) {
  .desktop-navbar {
    display: none;
  }

  .mobile-navbar {
    display: flex;
  }
}
</style>
