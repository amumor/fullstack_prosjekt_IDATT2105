<script setup>
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router'

const props = defineProps ({
  firstName: String,
  lastName: String,
  email: String,
  phoneNumber: String
})

const user = ref({
  firstName: props.firstName,
  lastName: props.lastName,
  email: props.email,
  phoneNumber: props.phoneNumber
})
const newFirstName = ref(props.firstName);
const newLastName = ref(props.lastName);
const newEmail = ref(props.email);
const newPhoneNumber = ref(props.phoneNumber);

const router = useRouter()

const saveChanges = () => {
  user.value.firstName = newFirstName.value;
  user.value.lastName = newLastName.value;
  user.value.email = newEmail.value;
  user.value.phoneNumber = newPhoneNumber.value;
  router.push('/profile');
}
</script>

<template>
<div class="display-credentials-container">
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
.display-credentials-container {
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  font-family: 'Inter', sans-serif;

  width: 40%;
  max-width: 600px;
  padding: 40px;
  border-radius: 10px;
  margin: 70px auto;
  position: relative;
}

/* Header */
h2 {
  font-size: 24px;
  color: #333333;
  font-weight: 500;

  text-align: left;
  margin-bottom: 20px;
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

input {
  font-size: 16px;
  transition: border-color 0.3s ease;

  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input:focus {
  border-color: #1C64FF;
  outline: none;
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
  .display-credentials-container {
    width: 80%;
    padding: 20px;
  }
}
</style>
