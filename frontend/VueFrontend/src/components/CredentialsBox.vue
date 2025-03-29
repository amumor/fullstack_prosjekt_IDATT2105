<script setup>
import { ref, defineProps, onMounted } from 'vue';

const props = defineProps({
  hasUser: {
    type: Boolean,
    default: false
  },
});

const isRegistered = () => {
  props.hasUser = !props.hasUser;
};

const login = () => {
  // Logic to handle login
};


</script>

<template>
<div class="credentials-box">
  <template v-if="!props.hasUser">
    <div class="login">
      <h2>Log in</h2>
      <div class="fields">
        <input type="text" placeholder="E-mail" required />
        <input type="password" placeholder="Password" required />
        <router-link to="/" @click="login">Log in</router-link>
      </div>
      <div class="to-sign-up">
        <p>Don't have an account yet?</p>
        <button @click="isRegistered">Sign up</button>
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
        <button type="submit" @click="isRegistered">Register</button>
      </div>
      <div class="to-login">
        <p>Already have an account?</p>
        <button @click="isRegistered">Log in</button>
      </div>
    </div>
  </template>
</div>
</template>

<style scoped>

</style>
