<script setup>
import { chatStore } from '@/stores/chat.js'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'
import { defineProps } from 'vue'

const chat = chatStore()
const { selectedChat } = storeToRefs(chat)

const props = defineProps({
    isBidder: Boolean,
    bidMessage: String,
    bidPrice: String,
})

const message = ref('')
const price = ref('')

const isVisible = ref(true)

const toggleBox = () => {
	isVisible.value = !isVisible.value
}

const sendBid = () => {
  if (selectedChat) {
    chat.postBid(message.value, price.value)
  }
	toggleBox()
}

const acceptBid = () => {
  if (selectedChat) {
    chat.acceptBid(selectedChat.value.id)
  }
}

const rejectBid = () => {
  if (selectedChat) {
    chat.rejectBid(selectedChat.value.id)
  }
}
</script>

<template>
<div class="bid-box-container" v-if="isVisible">
	<div class="send-bid-box" v-if="isBidder">
			<h3>Make a bid</h3>
			<button @click="toggleBox">X</button>
			<input v-model="price" type="text" placeholder="Price" />
			<input v-model="message" type="text" placeholder="Message" />
			<button @click="sendBid">Send</button>
	</div>
	<div class="get-bid-box" v-else>
			<h3>Bid</h3>
			<p>Price: {{ props.bidPrice }}</p>
			<p>Message: {{ props.bidMessage }}</p>
			<button @click="acceptBid">Accept</button>
			<button @click="rejectBid">Reject</button>
	</div>
</div>
</template>

<style scoped>

</style>