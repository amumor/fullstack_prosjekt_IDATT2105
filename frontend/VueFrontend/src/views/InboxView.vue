<script setup>
import Navbar from '@/components/Navbar.vue'
import ListedChatComponent from '@/components/Inbox/ListedChatComponent.vue'

const messages = [
  {
  id: 1,
  listingTitle: 'Boat for sale',
  image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
  lastMessage: 'Hello, 30 mill?',
  lastMessageTime: '03:30',
  isMessageRead: false,
  messengerName: 'Han Karen',
  },
  {
  id: 2,
  listingTitle: 'Car for sale',
  image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
  lastMessage: 'Heieieieiei?',
  lastMessageTime: '22. apr',
  isMessageRead: true,
  messengerName: 'Kar Kar',
  },
  {
  id: 3,
  listingTitle: 'PC for sale',
  image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
  lastMessage: '30kr, yes?',
  lastMessageTime: '09. jan',
  isMessageRead: false,
  messengerName: 'Han han',
  },
]

// Sort messages by read and last message time
</script>

<template>
<div class="display-page-container">
  <Navbar
    :isLoggedIn=true />
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
      class="chat-item" />
  </div>
</div>
</template>

<style scoped>
/* Messages container */
.chats-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 20px;
  max-width: 700px;
}

.chat-item {
  padding-bottom: 15px;
  border-bottom: 1px solid #ddd;
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
</style>
