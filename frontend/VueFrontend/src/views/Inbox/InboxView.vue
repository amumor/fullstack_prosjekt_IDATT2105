<script setup>
import { ref, computed } from 'vue'
import { vInfiniteScroll } from '@vueuse/components'
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
    messages: [{id: 1, message: 'Hello', sentAt:'09:05', type: 'MESSAGE'}, {id: 4, message: 'How are you?', sentAt: '10:25', type: 'MESSAGE'}, {id: 1, message: 'I am fine, thank you!', sentAt: '11:35', type: 'MESSAGE'}],
    lastMessageTime: '11:35',
    isMessageRead: true,
    selected: true
  },
  {
    id: 2,
    listingTitle: 'Car for sale',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    messengerName: 'Kar Kar',
    messages: [{id: 1, message: 'Hello', sentAt:'09:05', type: 'MESSAGE'}, {id: 4, message: 'How are you?', sentAt: '10:25', type: 'MESSAGE'}, {id: 1, message: 'I am fine, thank you!', sentAt: '11:35', type: 'MESSAGE'}],
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
    lastMessageTime: '11:35',
    isMessageRead: false,
    selected: false
  },
  {
    id: 4,
    listingTitle: 'Boat for sale',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    messengerName: 'Han Karen',
    messages: [{id: 1, message: 'Hello', price: '600', sentAt:'09:05', type: 'BID', status:'PENDING'}],
    lastMessageTime: '11:35',
    isMessageRead: true,
    selected: false
  }
]

const chat = chatStore()
const { chats, selectedChat, hasPendingBids } = storeToRefs(chat)

const user = userStore()

// TODO: Get messages from DB later
chat.setChats(initialMessages)

const newMessage = ref('')
const makingBid = ref(false)

// Function to select a chat
const openChat = (chatItem) => {
  chat.selectChat(chatItem) 
}

const isOwner = computed(() => {
  if (!selectedChat.value) return false
  return selectedChat.value.id === user.id
})

// TODO: Sort messages last message time ???

const sendMessage = () => {
  if (selectedChat) {
    chat.postMessage(newMessage.value)
  }
  newMessage.value = ''
}

const sendBid = (bid) => {
  if (selectedChat) {
    chat.postBid(bid.message, bid.price)
  }
  makingBid.value = false
}

const toggleBid = () => {
  makingBid.value = !makingBid.value
}

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
      <InitialsDisplayComponent
        :name="selectedChat.messengerName"
        :width="120"
        :height="120" />
      <h2>{{ selectedChat.messengerName }}</h2>
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
              :bidStatus="message.status" />
            <p id="received-timestamp">{{message.sentAt}}</p>
          </div>
          <!-- Text messages -->
          <div v-else-if="message.type === 'MESSAGE'" class="text-message">
            <p>{{ message.message }}</p>
            <p id="received-timestamp">{{message.sentAt}}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- New message -->
    <div class="message-input">
      <textarea v-model="newMessage" placeholder="Type a message..." @keydown.enter.prevent="sendMessage"></textarea>
      <button class="send-button" @click="sendMessage">Send</button>
      <button class="send-button" v-if="!selectedChat.hasPendingBids && !isOwner" @click="toggleBid">Make bid</button>
    </div>
    <div class="bid-box" v-if="makingBid"> 
      <BidBoxComponent 
        :isBidder="true"
        :inChat="false"
        :bidStatus="'PENDING'"
        @submit-bid="sendBid"
        @close-bid-box="makingBid = false" />
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
  padding: 20px;
  margin-top: 56px;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
  text-align: center;
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
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

/* Message display */
.messages {
  display: flex;
  flex-direction: column;
  width: 100%;
  overflow-y: auto;
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

#sent-timestamp {
  font-size: 12px;
  color: #ddd;
  margin-top: 5px;
  text-align: right;
}

#received-timestamp {
  font-size: 12px;
  color: #666;
  margin-top: 5px;
  text-align: left;
}

/* Message input */
.message-input {
  display: flex;
  align-items: center;
  margin-top: 20px;
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
</style>
