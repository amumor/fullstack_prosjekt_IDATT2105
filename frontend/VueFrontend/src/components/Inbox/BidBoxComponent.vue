<script setup>
import { chatStore } from '@/stores/chat.js'

const props = defineProps({
    isBidder: Boolean,
})

const chat = chatStore()
const message = ref(chat.selectedChat)
const price = ref('')

</script>

<template>
<div class="bid-box-container">
  <div class="send-bid-box" v-if="isBidder">
    <h3>Make a bid</h3>
    <input v-model="price" type="text" placeholder="Price" />
    <input v-model="message" type="text" placeholder="Message" />
    <button @click="sendBid">Send</button>
  </div>
<div class="bid-box" v-else>
    <h3>Bid</h3>
    <p>Price: {{ price }}</p>
    <p>Message: {{ message }}</p>
</template>

<style scoped>

</style>