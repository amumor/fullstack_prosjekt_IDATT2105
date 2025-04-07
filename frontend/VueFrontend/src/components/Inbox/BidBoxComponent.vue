<script setup>
import { chatStore } from '@/stores/chat.js'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'
import { defineProps } from 'vue'

const chat = chatStore()
const { selectedChat } = storeToRefs(chat)

const props = defineProps({
    isBidder: Boolean,
		inChat: Boolean,
    bidMessage: String,
    bidPrice: String,
		bidStatus: String,
})

const emit = defineEmits(['close-bid-box', 'submit-bid'])

const message = ref('')
const price = ref('')

const isVisible = ref(true)

const submitBid = () => {
	if (!message.value.trim() || !price.value.trim()) return

  emit('submit-bid', {
    message: message.value,
    price: price.value
  })
	message.value = ''
	price.value = ''
	emit('close-bid-box')
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
	<div class="inchat-bid-box" v-if="props.inChat">
		<h3>Bid</h3>
		<p>Price: {{ props.bidPrice }}</p>
		<p>Message: {{ props.bidMessage }}</p>
		<button v-if="!props.isBidder && props.bidStatus === 'PENDING'" @click="acceptBid">Accept</button>
		<button v-if="!props.isBidder && props.bidStatus === 'PENDING'" @click="rejectBid">Reject</button>
	</div>
	<div class="send-bid-box" v-if="props.isBidder">
			<h3>Make a bid</h3>
			<button @click="emit('close-bid-box')">X</button>
			<input v-model="price" type="text" placeholder="Price" />
			<input v-model="message" type="text" placeholder="Message" />
			<button @click="submitBid">Send</button>
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