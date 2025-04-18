<script setup>
import { useRouter } from 'vue-router'
import Navbar from '@/components/Navbar.vue'
import ProfileOption from '@/components/profile/ProfileOption.vue'
import InitialsDisplayComponent from '@/components/profile/InitialsDisplayComponent.vue'
import { userStore } from '@/stores/user.js'

const userStorage = userStore();

document.body.style.backgroundColor = "#ffffff";

const router = useRouter()

const routeTo = (route) => {
  router.push('/profile'+ route);
}

/**
 * Handles logout button click.
 * @returns {Promise<void>}
 */
const logout = async () => {
  try {
  await userStorage.logout()
  await router.push('/')
  } catch (error) {
    console.log(error)
  }
}
</script>

<template>
  <Navbar />
  <div class="display-page-container">
    <div class="profile-info-container">
      <!-- Profile info -->
      <div class="profile-icon">
        <InitialsDisplayComponent
          :name="userStorage.firstName + ' ' + userStorage.lastName"
          :width=120
          :height=120 />
      </div>
      <div class="profile-info">
        <h2>{{ userStorage.firstName + ' ' + userStorage.lastName }}</h2>
        <h1>{{ userStorage.email }}</h1>
      </div>
      <button class="basic-blue-btn" @click="logout">{{ $t('button.logout') }}</button>
    </div>
    <div class="options-containers">
      <ProfileOption
        class="profile-container"
        :title="$t('profile.my-profile')"
        :shortDescription="$t('profile.edit-profile-information')"
        :iconString="'material-symbols:person'"
        @click="routeTo('/edit')" />
      <ProfileOption
        class="profile-container"
        :title="$t('listing.listings')"
        :shortDescription="$t('listing.see-all-your-listings')"
        :iconString="'material-symbols:format-list-bulleted'"
        @click="routeTo('/myListings')" />
      <ProfileOption
        class="profile-container"
        :title="$t('header.favorites')"
        :shortDescription="$t('listing.see-all-your-favorites')"
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

/* Name */
h2 {
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
  max-height: 40px;
  line-height: 20px;
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

.basic-blue-btn {
  max-width: 100px;
  max-height: 40px;
  padding: 5px;
  margin-left: auto;
  margin-right: 70px; 
  margin-top: 40px;
}
/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .profile-info-container {
    flex-direction: column; /* Stack profile info vertically */
    align-items: center; /* Center align items */
    margin: 20px 0; /* Adjust margin */
  }

  h2 {
    margin: 20px 0 0 0; /* Adjust margin */
    text-align: center; /* Center align text */
  }

  h1 {
    font-size: 14px; /* Adjust font size */
    text-align: center; /* Center align text */
    margin: 10px 0 0 0; /* Adjust margin */
  }

  .options-containers {
    flex-direction: column; /* Stack options vertically */
    gap: 20px; /* Add spacing between options */
  }

  .profile-container {
    width: 80%; /* Adjust width */
    margin: 10px auto; /* Center align options */
    max-width: 250px; /* Adjust max width */
  }

  .basic-blue-btn {
    font-size: 14px; /* Adjust font size */
    padding: 10px; /* Adjust padding */
    margin-left: 60px;
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .profile-info-container {
    flex-direction: column; /* Stack profile info vertically */
    align-items: center; /* Center align items */
    margin: 10px 0; /* Adjust margin */
  }

  h2 {
    font-size: 16px; /* Adjust font size */
    margin: 10px 0 0 0; /* Adjust margin */
    text-align: center; /* Center align text */
  }

  h1 {
    font-size: 12px; /* Adjust font size */
    text-align: center; /* Center align text */
    margin: 5px 0 0 0; /* Adjust margin */
  }

  .options-containers {
    flex-direction: column; /* Stack options vertically */
    gap: 15px; /* Add spacing between options */
  }

  .profile-container {
    width: 90%; /* Adjust width */
    margin: 5px auto; /* Center align options */
    max-width: 200px; /* Adjust max width */
  }

  .basic-blue-btn {
    font-size: 12px; /* Adjust font size */
    padding: 8px; /* Adjust padding */
  }
}
</style>
