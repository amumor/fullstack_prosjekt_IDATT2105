<script setup>
import {ref} from 'vue'
import Navbar from '@/components/Navbar.vue'
import AdminCategoriesComponent from '@/components/admin/AdminCategoriesComponent.vue'
import CredentialsComponent from "@/components/login/CredentialsComponent.vue";
import {registerAdminUser, registerUser} from "@/services/AuthenticationService.js";
import {userStore} from "@/stores/user.js";
import SuccessFailModal from "@/components/modal/SuccessFailModal.vue";
import {isTokenExpired} from "@/services/TokenService.js";

const isEditeMode = ref(false);
const userStorage = userStore();
const toggleEditMode = () => {
  isEditeMode.value = !isEditeMode.value;
}

/**
 * Username for registration form
 * @type {import('vue').Ref<string>}
 */
const firstName = ref('');

/**
 * Last name for registration form
 * @type {import('vue').Ref<string>}
 */
const lastName = ref('');

/**
 * Email for registration form
 * @type {import('vue').Ref<string>}
 */
const email = ref('');

/**
 * Phone number for registration form
 * @type {import('vue').Ref<string>}
 */
const phoneNumber = ref('');

/**
 * Password for registration form
 * @type {import('vue').Ref<string>}
 */
const password = ref('');

/**
 * Confirm password for registration form
 * @type {import('vue').Ref<string>}
 */
const confirmPassword = ref('');

const nameRegex = /^[A-Za-zÆØÅæøå]+$/;
const emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
const phoneRegex = /^\d{8}$/;
const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&()_+\-={}:;"'|<>,.?]).{10,}$/;

const showResultModal = ref(false);
const modalMessage = ref('');

const registerAdmin = async () => {
  if (!firstName.value) {
    modalMessage.value = 'First name is required';
    showResultModal.value = true;
    return;
  }
  if (!lastName.value) {
    modalMessage.value = 'Last name is required';
    showResultModal.value = true;
    return;
  }
  if (!email.value) {
    modalMessage.value = 'Email is required';
    showResultModal.value = true;
    return;
  }
  if (!phoneNumber.value) {
    modalMessage.value = 'Phone number is required';
    showResultModal.value = true;
    return;
  }
  if (!password.value) {
    modalMessage.value = 'Password is required';
    showResultModal.value = true;
    return;
  }
  if (!confirmPassword.value) {
    modalMessage.value = 'Confirm password is required';
    showResultModal.value = true;
    return;
  }
  if (!nameRegex.test(firstName.value)) {
    modalMessage.value = 'Invalid first name';
    showResultModal.value = true;
    return;
  }
  if (!nameRegex.test(lastName.value)) {
    modalMessage.value = 'Invalid last name';
    showResultModal.value = true;
    return;
  }
  if (!emailRegex.test(email.value)) {
    modalMessage.value = 'Invalid email';
    showResultModal.value = true;
    return;
  }
  if (!phoneRegex.test(phoneNumber.value)) {
    modalMessage.value = 'Invalid phone number';
    showResultModal.value = true;
    return;
  }
  if (!passwordRegex.test(password.value)) {
    modalMessage.value = 'Invalid password';
    showResultModal.value = true;
    return;
  }
  if (password.value !== confirmPassword.value) {
    modalMessage.value = 'Passwords do not match';
    showResultModal.value = true;
    return;
  }

  try {
    const token = userStorage.token;
    if (isTokenExpired(token)) {
      userStorage.logout();
    }
    const authResponse = await registerAdminUser({
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      password: password.value,
      phoneNumber: phoneNumber.value,
    }, token);

    modalMessage.value = 'Admin registered successfully.';
    showResultModal.value = true;
  } catch (error) {
    modalMessage.value = 'Admin register failed' + error;
    showResultModal.value = true;
  }
};

</script>

<template>
  <Navbar/>
  <div class="display-page-container">
    <div class="admin-settings">
      <div class="header-container">
        <h2>{{ $t('header.admin-settings') }}</h2>
        <div class="edit-mode" v-if="isEditeMode">
          <button class="basic-blue-btn" @click="toggleEditMode">{{ $t('button.save') }}</button>
        </div>
        <div class="edit-mode" v-else>
          <button class="basic-blue-btn" id="edit-btn" @click="toggleEditMode">{{ $t('button.edit') }}</button>
        </div>
      </div>
      <suspense>
      <AdminCategoriesComponent :isEditMode="isEditeMode" />
      </suspense>
    </div>
    <div class="sign-up">
      <h2>{{ $t('header.register-admin') }}</h2>
      <div class="fields">
        <input v-model="firstName" type="text" :placeholder="$t('profile.first-name')" />
        <input v-model="lastName" type="text" :placeholder="$t('profile.last-name')"/>
        <input v-model="email" type="text" :placeholder="$t('profile.email')"/>
        <input v-model="phoneNumber" type="text" :placeholder="$t('profile.phone-number')"/>
        <input v-model="password" type="password" :placeholder="$t('profile.password')"/>
        <input v-model="confirmPassword" type="password" :placeholder="$t('profile.confirm-password')"/>
        <button class="basic-blue-btn" @click="registerAdmin">{{ $t('header.register') }}</button>
      </div>
    </div>
  </div>
  <SuccessFailModal
      v-if="showResultModal"
      :message="modalMessage"
      @close="showResultModal = false"
  />
</template>

<style scoped>
.display-page-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex-wrap: wrap;
  padding: 50px;
  gap: 20px; 
  align-items: center;
}

.admin-settings {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);

  border-radius: 10px;
  display: flex;
  flex-direction: column;
  width: 70%;
  padding: 2rem;
}

.header-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.sign-up {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  
  border-radius: 10px;
  padding: 2rem;
  width: 70%;
  align-self: center;
}

#edit-btn {
  background-color: white;
  color: #333333;
  border: #1C64FF 2px solid;
}

#edit-btn:hover {
  background-color: #D9D9D9;
}

.basic-blue-btn {
  width: 100px;
}

.fields input {
  margin-bottom: 15px;
}

.fields button {
  margin-top: 20px;
  width: 100%;
}

/* Responsive design for smaller screens */
@media (max-width: 768px) {
  .display-page-container {
    flex-direction: column; /* Stack sections vertically */
    align-items: center;
  }

  .admin-settings,
  .sign-up {
    max-width: 100%; /* Allow full width on smaller screens */
  }
}

/* Responsive design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .display-page-container {
    flex-direction: column; /* Stack sections vertically */
    align-items: center; /* Center align items */
    padding: 20px; /* Reduce padding */
    gap: 15px; /* Reduce gap between sections */
  }

  .admin-settings,
  .sign-up {
    width: 100%; /* Take full width */
    padding: 1rem; /* Reduce padding */
    box-shadow: none; /* Simplify shadow for smaller screens */
  }

  .header-container {
    flex-direction: column; /* Stack header elements vertically */
    align-items: flex-start; /* Align items to the left */
    gap: 10px; /* Add spacing between elements */
  }

  #edit-btn {
    width: 100%; /* Make the button take full width */
    font-size: 14px; /* Adjust font size */
    padding: 10px; /* Add padding for better usability */
  }

  .basic-blue-btn {
    width: 100%; /* Make buttons take full width */
    font-size: 14px; /* Adjust font size */
    padding: 10px; /* Add padding for better usability */
  }

  .fields input {
    width: 100%; /* Make input fields take full width */
    font-size: 14px; /* Adjust font size */
    padding: 10px; /* Add padding for better usability */
  }

  .fields button {
    width: 100%; /* Make the button take full width */
    font-size: 14px; /* Adjust font size */
    padding: 10px; /* Add padding for better usability */
  }

  h2 {
    font-size: 18px; /* Adjust heading font size */
    text-align: center; /* Center align headings */
  }
}
</style>
