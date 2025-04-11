<script setup>
import {ref, defineProps} from 'vue';
import {useRouter} from 'vue-router'
import {userStore} from '@/stores/user.js';
import {updateUser} from "@/services/UserService.js";
import {
  validateEmail,
  validateFirstName,
  validateLastName,
  validatePassword,
  validatePhoneNumber
} from "@/utils/Validation.js";
import SuccessFailModal from "@/components/modal/SuccessFailModal.vue";
import {isTokenExpired} from "@/services/TokenService.js";

const user = userStore()

// const props = defineProps ({
//   firstName: String,
//   lastName: String,
//   email: String,
//   phoneNumber: String
// })

// const newFirstName = ref(user.firstName);
// const newLastName = ref(user.lastName);
// const newEmail = ref(user.email);
// const newPhoneNumber = ref(user.phoneNumber);
const newFirstName = ref('');
const newLastName = ref('');
const newEmail = ref('');
const newPhoneNumber = ref('');
const newPassword = ref('');


newFirstName.value = user.firstName
newLastName.value = user.lastName
newEmail.value = user.email
newPhoneNumber.value = user.phoneNumber

const showResultModal = ref(false);
const resultModalMessage = ref('');

const router = useRouter()

const saveChanges = () => {
  try {

    if (!validateFirstName(newFirstName.value)) {
      resultModalMessage.value = "First name is required";
      showResultModal.value = true;
      return;
    }
    if (!validateLastName(newLastName.value)) {
      resultModalMessage.value = "Last name is required";
      showResultModal.value = true;
      return;
    }
    if (!validateEmail(newEmail.value)) {
      resultModalMessage.value = "Email is required";
      showResultModal.value = true;
      return;
    }
    if (!validatePhoneNumber(newPhoneNumber.value)) {
      resultModalMessage.value = "Phone number is required";
      showResultModal.value = true;
      return;
    }
    if (!validatePassword(newPassword.value) && newPassword.value.length > 1) {
      resultModalMessage.value = "Password does not meet the required criteria";
      showResultModal.value = true;
      return;
    }

    const newUser = {
      firstName: newFirstName.value,
      lastName: newLastName.value,
      email: newEmail.value,
      phoneNumber: newPhoneNumber.value,
      password: newPassword.value
    }
    const token = user.token;
    if (isTokenExpired(token)) {
      user.logout();
    }

    updateUser(newUser, token);
    user.logout();

    router.push('/login');
  } catch (error) {
    console.log(error);
    resultModalMessage.value = "Something went wrong";
    showResultModal.value = true;
  }
}
</script>

<template>
  <div class="display-page-container">
    <h2 class="display-credentials-title">{{ $t('profile.edit-profile') }}</h2>
    <div class="display-credentials-content">
      <div class="display-credentials-item">
        <label for="firstName">{{ $t('profile.first-name') }}:</label>
        <input type="text" id="first-name" v-model="newFirstName"/>
      </div>
      <div class="display-credentials-item">
        <label for="lastName">{{ $t('profile.last-name') }}:</label>
        <input type="text" id="last-name" v-model="newLastName"/>
      </div>
      <div class="display-credentials-item">
        <label for="email">{{ $t('profile.email') }}:</label>
        <input type="email" id="email" v-model="newEmail"/>
      </div>
      <div class="display-credentials-item">
        <label for="phoneNumber">{{ $t('profile.phone-number') }}:</label>
        <input type="text" id="phone-number" v-model="newPhoneNumber"/>
      </div>
      <div class="display-credentials-item">
        <label for="Password">{{ $t('profile.password') }}:</label>
        <input type="password" id="password" v-model="newPassword"/>
      </div>
      <button @click="saveChanges">{{ $t('button.save-changes') }}</button>
      <p class="restart-note">{{ $t('update-user-info-warning')}}</p>
    </div>
  </div>
  <SuccessFailModal
      v-if="showResultModal"
      :message="resultModalMessage"
      @close="showResultModal = false"
  />
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

/* Responsive Design for medium screens (max-width: 768px) */
@media (max-width: 768px) {
  .display-page-container {
    width: 80%;
    padding: 20px;
  }

  .display-credentials-content {
    gap: 10px;
  }

  button {
    font-size: 14px;
    padding: 10px;
  }
}

/* Responsive Design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .display-page-container {
    width: 90%;
    padding: 15px;
  }

  .display-credentials-content {
    gap: 8px;
  }

  label {
    font-size: 12px;
  }

  input {
    font-size: 14px;
    padding: 8px;
  }

  button {
    font-size: 12px;
    padding: 8px;
  }

  .restart-note {
    font-size: 12px;
    text-align: center;
  }
}
</style>
