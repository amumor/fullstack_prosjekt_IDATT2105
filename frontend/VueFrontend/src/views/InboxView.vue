<script setup>
import { ref } from 'vue'
import Navbar from '@/components/Navbar.vue'
import ListedChatComponent from '@/components/Inbox/ListedChatComponent.vue'
import InitialsDisplayComponent from '@/components/Profile/InitialsDisplayComponent.vue'

const messages = [
  {
    id: 1,
    listingTitle: 'Boat for sale',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    lastMessageTime: '03:30',
    isMessageRead: false,
    messengerName: 'Han Karen',
    messages: [{id: 1, message: 'Hello', sentAt:'09:05'}, {id: 4, message: 'How are you?', sentAt: '10:25'}, {id: 1, message: 'I am fine, thank you!', sentAt: '11:35'}],
    selected: true
  },
  {
    id: 2,
    listingTitle: 'Car for sale',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    lastMessage: 'Heieieieiei?',
    lastMessageTime: '22. apr',
    isMessageRead: false,
    messengerName: 'Kar Kar',
    messages: [{id: 1, message: 'Hello', sentAt:'09:05'}, {id: 4, message: 'How are you?', sentAt: '10:25'}, {id: 1, message: 'I am fine, thank you!', sentAt: '11:35'}],
    selected: false
  },
  {
    id: 3,
    listingTitle: 'PC for sale',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
    lastMessage: '30kr, yes?',
    lastMessageTime: '09. jan',
    isMessageRead: false,
    messengerName: 'Han han',
    messages: [{id: 1, message: 'Hello', sentAt:'09:05'}, {id: 4, message: 'How are you?', sentAt: '10:25'}, {id: 1, message: 'I am fine, thank you!', sentAt: '11:35'}],
    selected: false
  },
]

// Reactive variable for selected chat
const selectedChat = ref(messages[0]) // Default to first message
const newMessage = ref('')

// Function to select a chat
const openChat = (message) => {
  selectedChat.value.selected = false
  selectedChat.value = message
  selectedChat.value.isMessageRead = true // Mark as read when opened
  selectedChat.value.selected = true
}
// Sort messages by read and last message time

const sendMessage = () => {
  // Logic to send a message
  if (newMessage.value.trim() !== '') {
    selectedChat.value.messages.push({
      id: selectedChat.value.id,
      message: newMessage.value,
      sentAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    })
    newMessage.value = ''
  }
}
</script>

<template>
<Navbar :isLoggedIn=true />
<div class="display-page-container">
  <div class="display-left-container">
    <h2>Messages</h2>
    <div class="chats-container">
      <ListedChatComponent
        v-for="(message, index) in messages"
        :key="index"
        :listingTitle=message.listingTitle
        :image=message.image
        :lastMessageTime=message.lastMessageTime
        :isMessageRead=message.isMessageRead
        :messengerName=message.messengerName
        :messages=message.messages
        :selected=message.selected
        class="chat-item"
        @click="openChat(message)" />
    </div>
  </div>
  <div class="display-right-container" v-if="selectedChat">
    <div class="message-info">
      <InitialsDisplayComponent
        :name=selectedChat.messengerName
        :width=120
        :height=120 />
      <h2>{{ selectedChat.messengerName }}</h2>
      <div class="messages" v-for="(message, index) in selectedChat.messages" :key="index">
        <div class="sent-message" v-if="message.id === selectedChat.id">
          <p>{{ message.message }}</p>
          <p id="sent-timestamp">{{message.sentAt}}</p>
        </div>
        <!-- Fix: receives messages from other users than sender id -->
        <div class="received-message" v-else>
          <p>{{ message.message }}</p>
          <p id="received-timestamp">{{message.sentAt}}</p>
        </div>
      </div>
    </div>
    <div class="message-input">
      <textarea v-model="newMessage" placeholder="Type a message..."></textarea>
      <button class="send-button" @click="sendMessage">Send</button>
    </div>
  </div>
</div>
</template>

<style scoped>
/* Page container */
.display-page-container{
  display: flex;
  justify-content: space-between;
  gap: 20px;
  margin: auto;
}
/* Left container */
.display-left-container {
  flex: 1;
  max-width: 40%;
}

/* Right container */
.display-right-container {
  flex: 1;
  max-width: 55%;
  background: #f5f5f5;
  padding: 20px;
  margin-top: 67px;
  border-radius: 8px;
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

/* Header */
h2 {
  font-size: 24px;
  color: #333333;
  font-weight: 500;
  font-family: 'Inter', sans-serif;

  text-align: left;
  margin-bottom: 20px;
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
  font-family: 'Inter', sans-serif;

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
  font-family: 'Inter', sans-serif;
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
