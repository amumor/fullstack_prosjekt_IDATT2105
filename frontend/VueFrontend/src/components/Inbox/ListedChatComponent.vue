<script setup>
import { defineProps } from 'vue'
import { useChatStore } from '@/stores/chat.js'
import InitialsDisplayComponent from '@/components/profile/InitialsDisplayComponent.vue'

const props = defineProps({
  listingTitle: String,
  image: String,
  lastMessageTime: String,
  isMessageRead: Boolean,
  messengerName: String,
  messages: Array,
  selected: Boolean,
  chatId: Number,
})

// Access the chat store to update the selected chat
const chatStore = useChatStore()

// Function to handle chat selection
const selectChat = () => {
  const chat = chatStore.chats.find(c => c.id === props.chatId)
  if (chat) {
    chatStore.selectChat(chat)
  }
}
</script>

<template>
  <div
  :class="['listed-chat-container', { 'selected-chat': props.selected, 'chat-read': props.isMessageRead }]"
  @click="selectChat">
  <div class="chat-image-container">
    <img :src="props.image" class="chat-image" alt="Chat Image" />
    <InitialsDisplayComponent
      :name=props.messengerName
      :height=35
      :width=35
      class="initials"/>
  </div>
  <div class="chat-unread">
    <h3>{{ props.listingTitle }}</h3>
    <p>{{ props.lastMessageTime }}</p>
    <!-- Remove? -->
    <p v-if="props.messages && props.messages.length > 0">
      {{ props.messages.at(-1).message }}
    </p>
    <p v-else>No messages yet</p>

  </div>
</div>
</template>

<style scoped>
/* Container for each chat */
.listed-chat-container {
  display: flex;
  align-items: center;
  background: white;
  padding: 15px;
  transition: background 0.3s ease, transform 0.2s ease;
  width: 100%;
  max-width: 600px;
  cursor: pointer;
}

.listed-chat-container:hover {
  background: #f5f5f5;
}

/* Grey background for selected chat */
.selected-chat {
  background: #f5f5f5;
}

/* Image container */
.chat-image-container {
  display: flex;
  align-items: center;
  position: relative;
}

.chat-image {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  object-fit: cover;
  margin-right: 15px;
}

.initials {
  position: absolute;
  bottom: -5px;
  right: 5px;
}

/* Unread message styling */
.chat-unread {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.chat-unread h3 {
  font-size: 16px;
  color: #333;
  margin-bottom: 5px;
  font-weight: 600;
}

.chat-unread p{
  font-size: 14px;
  color: #666;
  margin: 2px 0;
}

/* Read message styling */
.chat-read h3{
  font-weight: 400;
  color: #999;  
}


@media (max-width: 768px) {
  .listed-chat-container {
    flex-direction: row;
    align-items: flex-start;
    padding: 10px;
  }

  .chat-image {
    width: 50px;
    height: 50px;
  }

  .chat-unread h3 {
    font-size: 14px;
  }

  .chat-unread p {
    font-size: 12px;
  }
}
</style>
