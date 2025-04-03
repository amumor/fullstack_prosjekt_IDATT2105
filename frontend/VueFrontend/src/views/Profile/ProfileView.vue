<script setup>
import { useRouter } from 'vue-router'
import Navbar from '@/components/Navbar.vue'
import ProfileOption from '@/components/Profile/ProfileOption.vue'
import InitialsDisplayComponent from '@/components/Profile/InitialsDisplayComponent.vue'

document.body.style.backgroundColor = "#ffffff";

const fullName = 'Fyfasan Ben Reddik';
const email = 'fasan@reddik.ben'
const router = useRouter()

const getInitials = (name) => {
  const nameArray = name.split(' ');
  return nameArray
    .map((word) => word.charAt(0).toUpperCase())
    .join('');
};

const routeTo = (route) => {
  router.push('/profile'+ route);
}
</script>

<template>
  <Navbar :isLoggedIn=true />
  <div class="display-page-container">
    <div class="profile-info-container">
      <!-- Profile info -->
      <div class="profile-icon">
        <InitialsDisplayComponent
          :name=fullName
          :width=120
          :height=120 />
      </div>
      <div class="profile-info">
        <h2>{{ fullName }}</h2>
        <h1>{{email}}</h1>
      </div>
      <!-- Log out logic -->
      <router-link to="/login" id="router-link">Log out</router-link>
    </div>
    <div class="options-containers">
      <ProfileOption
        class="profile-container"
        title="My Profile"
        shortDescription="Edit profile information"
        :iconString="'material-symbols:person'"  />
      <ProfileOption
        class="profile-container"
        title="Listings"
        shortDescription="See all your listings"
        :iconString="'material-symbols:format-list-bulleted'"
        @click="routeTo('/myListings')" />
      <ProfileOption
        class="profile-container"
        title="Favorites"
        shortDescription="See all your saved favorites"
        :iconString="'material-symbols:favorite'"
        @click="routeTo('/favorites')"/>
    </div>
  </div>
</template>

<style scoped>
/* Page container */
.profile-info-container {
  display: flex;
  margin: 30px 0 0 70px;
}

/* Title */
h2 {
  font-size: 24px;
  font-family: 'Inter', sans-serif;
  font-weight: 500;

  margin: 30px 0 0 30px;
}

/* Description */
h1 {
  font-size: 16px;
  font-family: 'Inter', sans-serif;
  font-weight: 400;

  margin: 10px 0 0 30px;
}

/* Log out button */
#router-link {
  text-decoration: none;
  font-family: 'Inter', sans-serif;
  color: #ffffff;
  margin: 30px 70px 0 auto;
  font-size: 16px;
  background-color: #007bff;
  padding: 10px 15px;
  border-radius: 5px;
  max-height: 20px;
}

.options-containers {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.profile-container {
  width: 40%;
  height: 5%;
  margin: 5%;
  padding: 1%;
  max-width: 300px;
  max-height: 200px;
}
</style>
