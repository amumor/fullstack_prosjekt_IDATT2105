<script setup>
import {ref, defineProps} from 'vue';
import {useRouter} from 'vue-router';
import {userStore} from '@/stores/user.js';
import {authenticateUser, registerUser} from "@/services/AuthenticationService.js";
import {getMyProfile, getUserByEmail} from "@/services/UserService.js";
import {TokenResponseDTO} from "@/api/index.js";

const userStorage = userStore();

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
  if (!email.value) {
    errorMsg.value = 'Email is required';
    return;
  }
  if (!password.value) {
    errorMsg.value = 'Password is required';
    return;
  }
  if (!emailRegex.test(email.value)) {
    errorMsg.value = 'Invalid email';
    return;
  }
  if (!passwordRegex.test(password.value)) {
    errorMsg.value = 'Invalid password';
    return;
  }

  try {

    await authenticateUser(email.value, password.value)
        .then(async authResponse => {
          console.debug('User authenticated successfully:', authResponse);
          const authToken = authResponse.token;

          await getMyProfile(authToken)
              .then(async userResponse => {
                console.log('user response:', userResponse);
                const user = {
                  token: authToken,
                  email: userResponse.email,
                  firstName: userResponse.firstName,
                  lastName: userResponse.lastName,
                  phoneNumber: userResponse.phoneNumber,
                };
                userStorage.login(user);
                await router.push('/')
              })
              .catch(error => {
                console.error('Fetching user by email failed: ', error);
                errorMsg.value = "Try again.";
              })
        })
        .catch(error => {
          console.error('Authentication failed:', error);
          errorMsg.value = "Try again.";
        });
  } catch (error) {
    console.error("Login failed:", error)
    errorMsg.value = "Try again.";
  }
};

/**
 * Function to handle registration
 * @returns {void}
 */
const register = async () => {
  if (!firstName.value) {
    errorMsg.value = 'First name is required';
    console.log(errorMsg.value);
    return;
  }
  if (!lastName.value) {
    errorMsg.value = 'Last name is required';
    console.log(errorMsg.value);
    return;
  }
  if (!email.value) {
    errorMsg.value = 'Email is required';
    console.log(errorMsg.value);
    return;
  }
  if (!phoneNumber.value) {
    errorMsg.value = 'Phone number is required';
    console.log(errorMsg.value);
    return;
  }
  if (!password.value) {
    errorMsg.value = 'Password is required';
    console.log(errorMsg.value);
    return;
  }
  if (!confirmPassword.value) {
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

  try {

    await registerUser({
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      password: password.value,
      phoneNumber: phoneNumber.value,
    })
        .then(async authResponse => {
          console.debug('User authenticated successfully:', authResponse);
          const authToken = authResponse.token;
          const user = {
            token: authToken,
            email: email.value,
            firstName: firstName.value,
            lastName: lastName.value,
            phoneNumber: phoneNumber.value,
          };
          userStorage.login(user);
          await router.push('/')
              .catch(error => {
                console.error('Fetching user by email failed: ', error);
                errorMsg.value = "Try again.";
              })

        })
        .catch(error => {
          console.error('Registration failed:', error);
        })


    email.value = '';
    password.value = '';
    toggleForm();
  } catch (error) {
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
    <router-link to="/" id="title-header">FIND.no</router-link>

    <template v-if="isUserRegistered">
      <div class="login">
        <h2>Log in</h2>
        <div class="fields">
          <input v-model="email" type="text" placeholder="E-mail"/>
          <input v-model="password" type="password" placeholder="Password"/>
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
          <input v-model="firstName" type="text" placeholder="First name"/>
          <input v-model="lastName" type="text" placeholder="Last name"/>
          <input v-model="email" type="text" placeholder="E-mail"/>
          <input v-model="phoneNumber" type="text" placeholder="Phone number"/>
          <input v-model="password" type="password" placeholder="Password"/>
          <input v-model="confirmPassword" type="password" placeholder="Confirm password"/>
          <button class="basic-blue-btn" @click="register">Register</button>
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

#title-header {
  text-decoration: none;
  color: #333;
  font-size: 24px;
  font-weight: bold;
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

/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .display-page-container {
    width: 80%; /* Adjust width for medium screens */
    padding: 30px; /* Adjust padding */
  }

  input {
    width: 90%; /* Make inputs take more width */
  }

  .logo-header {
    font-size: 22px; /* Adjust font size */
    text-align: center; /* Center align logo */
  }

  h2 {
    font-size: 20px; /* Adjust heading font size */
    text-align: center; /* Center align heading */
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .display-page-container {
    width: 90%; /* Take almost full width */
    padding: 20px; /* Reduce padding */
  }

  input {
    width: 100%; /* Make inputs take full width */
    font-size: 14px; /* Adjust font size */
    padding: 10px; /* Add padding for better usability */
  }

  .logo-header {
    font-size: 18px; /* Adjust font size */
    text-align: center; /* Center align logo */
  }

  h2 {
    font-size: 18px; /* Adjust heading font size */
    text-align: center; /* Center align heading */
  }

  .to-sign-up, .to-login {
    font-size: 12px; /* Adjust font size */
    justify-content: center; /* Center align links */
  }

  .to-sign-up button, .to-login button {
    font-size: 12px; /* Adjust button font size */
  }
}
</style>
