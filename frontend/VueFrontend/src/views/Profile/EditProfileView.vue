<script setup>
import Navbar from '@/components/Navbar.vue'
import EditProfileComponent from '@/components/profile/EditProfileComponent.vue'

const firstName = 'Fyfasan'
const lastName = 'Ben Reddik'
const email = 'fasan@redikk.ben'
const phoneNumber = '1234567890'

</script>

<template>
<Navbar :isLoggedIn=true />
<EditProfileComponent
  :firstName=firstName
  :lastName=lastName
  :email=email
  :phoneNumber=phoneNumber
  />
</template>

<style scoped>

</style>
