<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
import { userStore } from '@/stores/user.js';

const user = userStore();

/**
 * @property {Boolean} hasUser - Indicates if the user is registered.
 */
const props = defineProps({
  hasUser: {
    type: Boolean,
    default: false
  },
});

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

const errorMsg = ref('');

const nameRegex = /^[A-Za-zÆØÅæøå]+$/;

const emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;

const phoneRegex = /^\d{8}$/;

const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&()_+\-={}:;"'|<>,.?]).{10,}$/;

/**
 * Function to handle login
 * @returns {void}
 */
const login = async () => {
  if(!email.value) {
    errorMsg.value = 'Email is required';
    return;
  }
  if(!password.value) {
    errorMsg.value = 'Password is required';
    return;
  }
  if(!emailRegex.test(email.value)) {
    errorMsg.value = 'Invalid email';
    return;
  }
  if(!passwordRegex.test(password.value)) {
    errorMsg.value = 'Invalid password';
    return;
  }

  try{

    // Logic to handle login here
    user.logout();
    const newUser = ({
      //TODO: generate id??
      id: 1,
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      phoneNumber: phoneNumber.value,
    });
    user.login(newUser);
    await router.push('/')
  }catch(error){
    console.error("Login failed:", error)
    errorMsg.value = "Try again.";
  }

};

/**
 * Function to handle registration
 * @returns {void}
 */
const register = async () => {
  if(!firstName.value) {
    errorMsg.value = 'First name is required';
    console.log(errorMsg.value);
    return;
  }
  if(!lastName.value) {
    errorMsg.value = 'Last name is required';
    console.log(errorMsg.value);
    return;
  }
  if(!email.value) {
    errorMsg.value = 'Email is required';
    console.log(errorMsg.value);
    return;
  }
  if(!phoneNumber.value) {
    errorMsg.value = 'Phone number is required';
    console.log(errorMsg.value);
    return;
  }
  if(!password.value) {
    errorMsg.value = 'Password is required';
    console.log(errorMsg.value);
    return;
  }
  if(!confirmPassword.value) {
    errorMsg.value = 'Confirm password is required';
    console.log(errorMsg.value);
    return;
  }
  if (!nameRegex.test(firstName.value)) {
    errorMsg.value = 'Invalid first name';
    console.log(errorMsg.value);
    return;
  }
  if (!nameRegex.test(lastName.value)) {
    errorMsg.value = 'Invalid last name';
    console.log(errorMsg.value);
    return;
  }
  if (!emailRegex.test(email.value)) {
    errorMsg.value = 'Invalid email';
    console.log(errorMsg.value);
    return;
  }
  if (!phoneRegex.test(phoneNumber.value)) {
    errorMsg.value = 'Invalid phone number';
    console.log(errorMsg.value);
    return;
  }
  if (!passwordRegex.test(password.value)) {
    errorMsg.value = 'Invalid password';
    console.log(errorMsg.value);
    return;
  }
  if (password.value !== confirmPassword.value) {
    errorMsg.value = 'Passwords do not match';
    console.log(errorMsg.value);
    return;
  }

  try{

    // Logic to handle registration here
    email.value = '';
    password.value = '';
    toggleForm();
  }catch(error){
    console.error("Registration failed:", error)
    errorMsg.value = "Try again.";
  }
};

/**
 * Vue Router instance
 * @type {import('vue-router').Router}
 */
const router = useRouter();

const isUserRegistered = ref(props.hasUser);

const toggleForm = () => {
  isUserRegistered.value = !isUserRegistered.value;
};
</script>

<template>
<div class="display-page-container">
  <h2 class="logo-header">FIND.no</h2>
  <template v-if="isUserRegistered">
    <div class="login">
      <h2>Log in</h2>
      <div class="fields">
        <input v-model="email" type="text" placeholder="E-mail" />
        <input v-model="password" type="password" placeholder="Password" />
        <button class="basic-blue-btn" @click="login">Log in</button>
      </div>
      <div class="to-sign-up">
        <p>Don't have an account yet?</p>
        <button @click="toggleForm">Sign up</button>
      </div>
    </div>
  </template>
  <template v-else>
    <div class="sign-up">
      <h2>Register</h2>
      <div class="fields">
        <input v-model="firstName" type="text" placeholder="First name" />
        <input v-model="lastName" type="text" placeholder="Last name" />
        <input v-model="email" type="text" placeholder="E-mail" />
        <input v-model="phoneNumber" type="text" placeholder="Phone number" />
        <input v-model="password" type="password" placeholder="Password" />
        <input v-model="confirmPassword" type="password" placeholder="Confirm password" />
        <button class="basic-blue-btn" @click="register" >Register</button>
      </div>
      <div class="to-login">
        <p>Already have an account?</p>
        <button @click="toggleForm">Log in</button>
      </div>
    </div>
  </template>
</div>
</template>

<style scoped>
.display-page-container {
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);

  width: 40%;
  padding: 40px;
  border-radius: 10px;
  margin: 20px auto;
  position: relative;
}

/* Logo */
.logo-header {
  font-size: 26px;
  font-weight: 600;
  color: #333333;
  text-align: left;

  margin: 0;
}

/* Title */
h2 {
  margin-top: 40px;
}

input {
  width: 70%;
}

/* Router links */
.to-sign-up, .to-login {
  font-size: 14px;
  color: #333333;

  display: flex;
  justify-content: left;
  align-items: center;
  margin-top: 10px;
}

.to-sign-up button, .to-login button {
  background: none;
  border: none;
  color: #d97706;
  font-size: 14px;
  cursor: pointer;
}

.to-sign-up button:hover, .to-login button:hover {
  text-decoration: underline;
}

</style>
