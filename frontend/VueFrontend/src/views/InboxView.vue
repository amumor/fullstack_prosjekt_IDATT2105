<script setup>
import Navbar from '@/components/Navbar.vue'
import ListedChatComponent from '@/components/Inbox/ListedChatComponent.vue'

</script>

<template>
<div class="display-page-container">
  <Navbar
    :isLoggedIn=true />
  <h2>Messages</h2>
  <div class="chats-container">
    <ListedChatComponent
      listingTitle="Boat for sale"
      image="https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg"
      lastMessage="Hello, 30 mill?"
      lastMessageTime="03:30"
      isMessageRead="false"
      messengerName="Han Karen" />
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
