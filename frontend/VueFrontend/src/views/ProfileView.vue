<script setup>
import Navbar from '@/components/Navbar.vue'
import ProfileContainer from '@/components/ProfileContainer.vue'

const fullName = 'Fyfasan Ben Reddik';
const email = 'fasan@reddik.ben'

const getInitials = (name) => {
  const nameArray = name.split(' ');
  const initials = nameArray
    .map((word) => word.charAt(0).toUpperCase())
    .join('');
  return initials;
};
</script>

<template>
  <Navbar :isLoggedIn=true />
  <div class="display-page-container">
    <div class="profile-info-container">
      <!-- Profile info -->
      <div class="profile-icon">
        <span>{{ getInitials(fullName) }}</span>
      </div>
      <div class="profile-info">
        <h2>{{ fullName }}</h2>
        <h1>{{email}}</h1>
      </div>
    </div>
    <div class="options-containers">
      <ProfileContainer
        class="profile-container"
        title="My Profile"
        description="Edit profile information"
        :iconString="'material-symbols:person'"  />
      <ProfileContainer
        class="profile-container"
        title="Listings"
        description="See all your listings"
        :iconString="'material-symbols:format-list-bulleted'" />
      <ProfileContainer
        class="profile-container"
        title="Favourites"
        description="See all your saved favourites"
        :iconString="'material-symbols:favorite'" />
    </div>
  </div>
</template>

<style scoped>
.profile-icon {
  background-color: #D9D9D9;
  border-radius: 50%;
  color: #333333;
  font-size: 30px;
  text-transform: uppercase;

  display: flex;
  justify-content: center;
  align-items: center;
  width: 120px;
  height: 120px;
}

.profile-info-container {
  display: flex;
  margin: 30px 0 0 70px;
}

h2 {
  font-size: 24px;
  font-family: 'Inter', sans-serif;
  font-weight: 500;

  margin: 30px 0 0 30px;
}

h1 {
  font-size: 16px;
  font-family: 'Inter', sans-serif;
  font-weight: 400;

  margin: 10px 0 0 30px;
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
