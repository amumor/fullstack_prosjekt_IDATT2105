<script setup>
import { ref, computed, nextTick, watch, onUpdated } from 'vue'
import Navbar from '@/components/Navbar.vue'
import { chatStore } from '@/stores/chat'
import { storeToRefs } from 'pinia'
import { userStore } from '@/stores/user'

import ListedChatComponent from '@/components/inbox/ListedChatComponent.vue'
import InitialsDisplayComponent from '@/components/profile/InitialsDisplayComponent.vue'
import BidBoxComponent from '@/components/inbox/BidBoxComponent.vue'

// Dummy initial data
const initialMessages = [
  {
    id: 1,
    listingTitle: 'Boat for sale',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    messengerName: 'Han karen',
    messages: [{id: 1, message: 'Hello', sentAt:'09:05', type: 'MESSAGE'}, {id: 4, message: 'How are you?', sentAt: '10:25', type: 'MESSAGE'}, {id: 1, message: 'I am fine, thank you!', sentAt: '11:35', type: 'MESSAGE'}, {id: 2, message: 'accept please', price: '600', sentAt: '11:35', type: 'BID', status: 'PENDING'}],
    bids: [{id: 2, message: 'accept please', price: '600', sentAt: '11:35', type: 'BID', status: 'PENDING'}],
    lastMessageTime: '11:35',
    isMessageRead: true,
    selected: true
  },
  {
    id: 2,
    listingTitle: 'Car for sale',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    messengerName: 'Kar Kar',
    messages: [{id: 1, message: 'Hello', sentAt:'09:05', type: 'MESSAGE'}, {id: 4, message: 'How are you?', sentAt: '10:25', type: 'MESSAGE'}],
    bids: [],
    lastMessageTime: '11:35',
    isMessageRead: false,
    selected: false
  },
  {
    id: 3,
    listingTitle: 'PC for sale',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    messengerName: 'Han han',
    messages: [{id: 1, message: 'Hello', sentAt:'09:05', type: 'MESSAGE'}, {id: 4, message: 'How are you?', sentAt: '10:25', type: 'MESSAGE'}, {id: 1, message: 'I am fine, thank you!', sentAt: '11:35', type: 'MESSAGE'}],
    bids: [],
    lastMessageTime: '11:35',
    isMessageRead: false,
    selected: false
  },
]

const useChatStore = chatStore()
const { chats, selectedChat } = storeToRefs(useChatStore)

const useUserStore = userStore()

// TODO: Get messages from DB later
useChatStore.setChats(initialMessages)

const newMessage = ref('')
const makingBid = ref({})

// Function to select a chat
const openChat = (chatItem) => {
  useChatStore.selectChat(chatItem) 
}

const isOwner = computed(() => {
  if (!selectedChat.value) return false
  return selectedChat.value.id === useUserStore.id
})

// TODO: Sort messages last message time ???

const sendMessage = () => {
  if (selectedChat) {
    useChatStore.postMessage(newMessage.value)
  }
  newMessage.value = ''
}

const sendBid = (bid) => {
  if (selectedChat) {
    useChatStore.postBid(bid.message, bid.price)
  }
  makingBid.value[selectedChat.value.id] = false
}

const acceptBid = async (message) => {
  if (!message || !message.id) return
  await useChatStore.acceptBid(message.id)
  await nextTick()
}

const rejectBid = async (message) => {
  if (!message || !message.id) return
  await useChatStore.rejectBid(message.id)
  await nextTick()
}

const cancelBid = async (message) => {
  if (!message || !message.id) return
  await useChatStore.cancelBid(message.id)
  await nextTick() 
}

const toggleBid = (chatId) => {
  makingBid.value[chatId] = !makingBid.value[chatId]
}

// Watch for changes in selectedChat and update the makingBid state
const scrollToBottom = () => {
  nextTick(() => {
    const container = document.querySelector('.message-info')
    if (container) {
      container.scrollTop = container.scrollHeight
    }
  })
}

watch(() => selectedChat.value?.messages.length, () => {
  scrollToBottom()
})

onUpdated(() => {
  scrollToBottom()
})

watch(
  () => selectedChat.value?.messages,
  () => {
    scrollToBottom()
  },
  { deep: true }
)


</script>

<template>
<Navbar />
<div class="display-page-container" >
  <div class="display-left-container">
    <h2>Messages</h2>
    <div class="chats-container">
      <ListedChatComponent
        v-for="(chatItem) in chats"
        :key="chatItem.id"
        :listingTitle="chatItem.listingTitle"
        :image="chatItem.image"
        :lastMessageTime="chatItem.lastMessageTime"
        :isMessageRead="chatItem.isMessageRead"
        :messengerName="chatItem.messengerName"
        :messages="chatItem.messages"
        :selected="chatItem.selected"
        :chatId="chatItem.id"
        class="chat-item"
        @click="openChat(chatItem)" />
    </div>
  </div>
  <div class="display-right-container" v-if="selectedChat">
    <!-- Fix scroll! -->
    <div class="message-info">
      <div class="initials">
        <InitialsDisplayComponent
        :name="selectedChat.messengerName"
        :width="120"
        :height="120" />
        <h2>{{ selectedChat.messengerName }}</h2>
      </div>
      <div class="messages" v-for="(message, index) in selectedChat.messages" :key="index">

        <!-- Sent messages -->
        <div class="sent-message" v-if="message.id === selectedChat.id">
          <!-- Bids -->
          <div v-if="message.type === 'BID'" class="bid-message">
            <BidBoxComponent
              :isBidder="true"
              :inChat="true"
              :bidMessage="message.message"
              :bidPrice="message.price"
              :bidStatus="message.status" />
            <!-- Cancel bid -->
            <button 
              class="basic-blue-btn" 
              id="cancel-btn" 
              v-if="message.status === 'PENDING'" 
              @click="cancelBid(message)">Cancel</button>
            <p id="cancelled" v-if="message.status === 'CANCELLED'">Cancelled</p>
            <p id="sent-timestamp">{{message.sentAt}}</p>
          </div>
          <!-- Text messages -->
          <div v-else-if="message.type === 'MESSAGE'" class="text-message">
            <p>{{ message.message }}</p>
            <p id="sent-timestamp">{{message.sentAt}}</p>
          </div>
        </div>

        <!-- Received messages -->
        <!-- Fix: receives messages from other users than sender id??? -->
        <div class="received-message" v-else>
          <!-- Bids -->
          <div v-if="message.type === 'BID'" class="bid-message">
            <BidBoxComponent
              :isBidder="false"
              :inChat="true"
              :bidMessage="message.message"
              :bidPrice="message.price"
              :bidStatus="message.status"
              @accept-bid="acceptBid(message)"
              @reject-bid="rejectBid(message)" />
            <p id="received-timestamp">{{message.sentAt}}</p>
          </div>
          <!-- Text messages -->
          <div v-else-if="message.type === 'MESSAGE'" class="text-message">
            <p>{{ message.message }}</p>
            <p id="received-timestamp">{{message.sentAt}}</p>
          </div>
        </div>
      </div>
      <!-- New bid -->
      <div class="bid-box" v-if="makingBid[selectedChat.id]"> 
        <BidBoxComponent 
          :isBidder="true"
          :inChat="false"
          :bidStatus="'PENDING'"
          @submit-bid="sendBid"
          @close-bid-box="makingBid[selectedChat.id] = false" />
      </div>
    </div>

    
    <!-- New message -->
    <div class="message-input">
      <textarea v-model="newMessage" placeholder="Type a message..." @keydown.enter.prevent="sendMessage"></textarea>
      <button class="basic-blue-btn" @click="sendMessage">Send</button>
      <button 
        class="basic-blue-btn"  
        :id="selectedChat.hasPendingBids ? 'pending-bid-button' : 'make-bid-button'" 
        :disabled="selectedChat.hasPendingBids"
        v-if="!isOwner" 
        @click="toggleBid(selectedChat.id)"
        >Make bid</button>
    </div>
    
  </div>
</div>
</template>

<style scoped>
/* Page container */
.display-page-container{
  display: flex;
}

/* Left container */
.display-left-container {
  flex: 1;
  max-width: 40%;
}

/* Right container */
.display-right-container {
  flex: 1;
  max-width: 100%;
  background: #f5f5f5;
  text-align: center;
  border-radius: 2%;
  display: flex;
  flex-direction: column;
  height: calc(94vh - 56px); 
}

/* Messages container */
.chats-container {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
  max-width: 700px;
}

.chat-item {
  padding-bottom: 15px;
  border-bottom: 1px solid #ddd;
  cursor: pointer;
}

/* Right side chat display */
.message-info {
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
  
  padding: 20px;
  gap: 10px;
}

.initials {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto; 
}

/* Message display */
.messages {
  display: flex;
  flex-direction: column;
  width: 100%;
  
}

.messages::-webkit-scrollbar {
  width: 6px;
}
.messages::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 10px;
}

.sent-message {
  background: #1C64FF;
  color: white;

  align-self: flex-end;
  text-align: right;
  padding: 5px 10px;
  border-radius: 10px;
  margin-bottom: 10px;
  max-width: 70%;
}

.received-message {
  background: #e5e5ea;
  color: black;

  align-self: flex-start;
  text-align: left;
  padding: 5px 10px;
  border-radius: 10px;
  margin-bottom: 10px;
  max-width: 70%;
}

/* Timestamp styling */
#sent-timestamp,
#received-timestamp {
  font-size: 12px;
  margin-top: 5px;
  opacity: 0.7;
}


/* Message input */
.message-input {
  display: flex;
  align-items: center;
}

.message-input textarea {
  flex: 1;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  resize: none;
}

.message-input button {
  padding: 10px 20px;
  margin-left: 10px;
  border: none;
  background-color: #1C64FF;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.message-input button:hover {
  background-color: #155ab6;
}

/* buttons */
.basic-blue-btn {
  width: 15%;
}

#pending-bid-button {
  background-color: #ccc;
  color: white;
}

#cancel-btn {
  background-color: #e5e5ea;
  color: #333;

  width:auto;
}


</style>
