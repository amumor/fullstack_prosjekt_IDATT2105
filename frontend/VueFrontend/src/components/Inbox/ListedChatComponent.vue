<script setup>
import InitialsDisplayComponent from '@/components/Profile/InitialsDisplayComponent.vue'

const props = defineProps({
  listingTitle: String,
  image: String,
  lastMessage: String,
  lastMessageTime: String,
  isMessageRead: Boolean,
  messengerName: String,
})


</script>

<template>
<div class="listed-chat-container">
  <div class="chat-image-container">
    <img :src="props.image" class="chat-image" alt="Chat Image" />
    <InitialsDisplayComponent
      :name=props.messengerName
      :height=40
      :width=40 />
  </div>
  <div class="chat-unread" :class="{ 'chat-read': props.isMessageRead }">
    <h3>{{ props.listingTitle }}</h3>
    <p>{{ props.lastMessageTime }}</p>
    <p>{{ props.lastMessage }}</p>
  </div>
</div>
</template>

<style scoped>
/* Container for each chat */
.listed-chat-container {
  display: flex;
  align-items: center;
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  padding: 15px;
  border-radius: 10px;
  transition: background 0.3s ease, transform 0.2s ease;
  width: 100%;
  max-width: 600px;
  cursor: pointer;
}

.listed-chat-container:hover {
  background: #f5f5f5;
  transform: scale(1.02);
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
}

.chat-unread p {
  font-size: 14px;
  color: #666;
  margin: 2px 0;
}

/* Styling for read messages */
.chat-read p {
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
