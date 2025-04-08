<script setup>
import {ref} from 'vue'
import Navbar from '@/components/Navbar.vue'
import AdminCategoriesComponent from '@/components/admin/AdminCategoriesComponent.vue'
import CredentialsComponent from "@/components/login/CredentialsComponent.vue";
import {registerAdminUser, registerUser} from "@/services/AuthenticationService.js";
import {userStore} from "@/stores/user.js";
import SuccessFailModal from "@/components/modal/SuccessFailModal.vue";

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
    <div class="header-container">
      <h2>Admin settings</h2>
      <div class="edit-mode" v-if="isEditeMode">
        <button class="basic-blue-btn" @click="toggleEditMode">Save</button>
      </div>
      <div class="edit-mode" v-else>
        <button class="basic-blue-btn" id="edit-btn" @click="toggleEditMode">Edit</button>
      </div>
    </div>
    <AdminCategoriesComponent :isEditMode="isEditeMode" />
    <div>
      <div class="sign-up">
        <h2>Register Admin</h2>
        <div class="fields">
          <input v-model="firstName" type="text" placeholder="First name"/>
          <input v-model="lastName" type="text" placeholder="Last name"/>
          <input v-model="email" type="text" placeholder="E-mail"/>
          <input v-model="phoneNumber" type="text" placeholder="Phone number"/>
          <input v-model="password" type="password" placeholder="Password"/>
          <input v-model="confirmPassword" type="password" placeholder="Confirm password"/>
          <button class="basic-blue-btn" @click="registerAdmin">Register</button>
        </div>
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
.header-container {
  display: flex;
  justify-content: space-between;
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
  height: 50px;
  padding: 10px;
}
</style>
