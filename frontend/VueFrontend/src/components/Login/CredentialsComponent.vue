<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';

/**
 * @property {Boolean} hasUser - Indicates if the user is registered.
 */
const props = defineProps({
  hasUser: {
    type: Boolean,
    default: false
  },
});

const router = useRouter();
const isUserRegistered = ref(props.hasUser);

const toggleForm = () => {
  isUserRegistered.value = !isUserRegistered.value;
};

const login = () => {
  // Logic to handle login
  router.push('/')
};


</script>

<template>
<div class="display-page-container">
  <h2 class="logo-header">FIND.no</h2>
  <template v-if="isUserRegistered">
    <div class="login">
      <h2>Log in</h2>
      <div class="fields">
        <input type="text" placeholder="E-mail" required />
        <input type="password" placeholder="Password" required />
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
        <input type="text" placeholder="First name" required />
        <input type="text" placeholder="Last name" required />
        <input type="text" placeholder="E-mail" required />
        <input type="text" placeholder="Phone number" required />
        <input type="password" placeholder="Password" required />
        <input type="password" placeholder="Confirm password" required />
        <button class="basic-blue-btn" @click="toggleForm">Register</button>
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
