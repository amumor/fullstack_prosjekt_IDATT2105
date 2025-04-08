<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router'
import { userStore } from '@/stores/user.js';

const user = userStore()

const props = defineProps ({
  firstName: String,
  lastName: String,
  email: String,
  phoneNumber: String
})

const newFirstName = ref(user.firstName);
const newLastName = ref(user.lastName);
const newEmail = ref(user.email);
const newPhoneNumber = ref(user.phoneNumber);

const router = useRouter()

const saveChanges = () => {

  // Validate the input fields
  
  user.setProfile({
    firstName: newFirstName.value,
    lastName: newLastName.value,
    email: newEmail.value,
    phoneNumber: newPhoneNumber.value
  })
  router.push('/profile');
}
</script>

<template>
<div class="display-page-container">
  <h2 class="display-credentials-title">Edit Profile</h2>
  <div class="display-credentials-content">
    <div class="display-credentials-item">
      <label for="firstName">First name:</label>
      <input type="text" id="first-name" v-model="newFirstName"/>
    </div>
    <div class="display-credentials-item">
      <label for="lastName">Last name:</label>
      <input type="text" id="first-name" v-model="newLastName" />
    </div>
    <div class="display-credentials-item">
      <label for="email">Email:</label>
      <input type="email" id="email" v-model="newEmail" />
    </div>
    <div class="display-credentials-item">
      <label for="phoneNumber">Password:</label>
      <input type="text" id="phone-number" v-model="newPhoneNumber" />
    </div>
    <button @click="saveChanges">Save changes</button>
  </div>
</div>
</template>

<style scoped>
.display-page-container {
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);

  width: 40%;
  max-width: 600px;
  padding: 40px;
  border-radius: 10px;
  margin: 20px auto;
  position: relative;
}

/* Input fields */
.display-credentials-content {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.display-credentials-item {
  display: flex;
  flex-direction: column;
}

label {
  font-size: 14px;
  font-weight: 500;
  color: #333333;
  margin-bottom: 5px;
}

/* Save changes button */
button {
  background: #1C64FF;
  color: white;
  font-size: 16px;

  padding: 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.2s ease;
  width: 100%;
  margin-top: 20px;
}

button:hover {
  background: #0056b3;
  transform: scale(1.05);
}

/* Responsive Design */
@media (max-width: 768px) {
  .display-page-container {
    width: 80%;
    padding: 20px;
  }
}
</style>
