<script setup>
import { ref, defineProps, onMounted } from 'vue';

const props = defineProps({
  hasUser: {
    type: Boolean,
    default: false
  },
});

const isUserRegistered = ref(props.hasUser);

const toggleForm = () => {
  isUserRegistered.value = !isUserRegistered.value;
};

const login = () => {
  // Logic to handle login
};


</script>

<template>
<div class="credentials-box">
  <h2 class="logo-header">FIND.no</h2>
  <template v-if="isUserRegistered">
    <div class="login">
      <h2>Log in</h2>
      <div class="fields">
        <input type="text" placeholder="E-mail" required />
        <input type="password" placeholder="Password" required />
        <router-link to="/" @click="login">Log in</router-link>
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
        <button type="submit" @click="toggleForm">Register</button>
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
.credentials-box {
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  font-family: 'Inter', sans-serif;

  width: 40%;
  height: 70%;
  padding: 40px;
  border-radius: 10px;
  margin: 70px auto;
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
  font-size: 24px;
  color: #333333;
  font-weight: 500;

  text-align: left;
  margin-bottom: 20px;
  margin-top: 60px;
}

/* Input fields */
.fields input {
  font-size: 16px;

  width: 60%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

/* Log in and register button */
.fields a, .fields button {
  background: #1C64FF;
  color: white;
  font-size: 16px;
  transition: background 0.3s ease;

  text-align: center;
  width: 66%;
  display: block;
  padding: 10px;
  border-radius: 5px;
  margin-top: 10px;
  border: none;
}

.fields a {
  display: block;
  text-decoration: none;
  width: 60%;
}


.fields a:hover, .fields button:hover {
  background: #0056b3;
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
