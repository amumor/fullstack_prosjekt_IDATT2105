<script setup>
import { defineProps, computed } from 'vue'
import { useChatStore } from '@/stores/chat.js'
import InitialsDisplayComponent from '@/components/profile/InitialsDisplayComponent.vue'

const props = defineProps({
  chatId: {
    type: [Number, String],
    required: true
  },
  listingTitle: {
    type: String,
    default: 'Unknown Listing'
  },
  image: {
    type: String,
    default: 'https://placehold.co/600x400?text=No+Image'
  },
  lastMessageTime: {
    type: String,
    default: ''
  },
  isMessageRead: {
    type: Boolean,
    default: false
  },
  messengerName: {
    type: String,
    default: 'Unknown User'
  },
  messages: {
    type: Array,
    default: () => []
  },
  selected: {
    type: Boolean,
    default: false
  },
})

const formattedTime = computed(() => {
  if (!props.lastMessageTime) return '';
  
  const date = new Date(props.lastMessageTime);
  if (isNaN(date.getTime())) return '';
  
  // Format: dd.mm HH:MM
  const day = date.getDate().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  
  return `${day}.${month} ${hours}:${minutes}`;
})

// Get last message for display
const lastMessage = computed(() => {
  if (props.messages && props.messages.length > 0) {
    const message = props.messages[props.messages.length - 1];
    return message.content || 'No message content';
  }
  return 'No messages yet';
})

// Access the chat store to update the selected chat
const chatStore = useChatStore()

// Function to handle chat selection
const selectChat = () => {
  try {
    const chat = chatStore.chats.find(c => {
      return c.id === props.chatId || c.id === parseInt(props.chatId)
    })
    
    if (chat) {
      chatStore.selectChat(chat)
    } else {
      console.error('Chat not found with ID:', props.chatId)
    }
  } catch (error) {
    console.error('Error selecting chat:', error)
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
        :name="props.messengerName"
        :height="35"
        :width="35"
        class="initials"/>
    </div>
    <div class="chat-content">
      <h3>{{ props.listingTitle }}</h3>
      <p class="timestamp" v-if="formattedTime">{{ formattedTime }}</p>
      <p v-if="!props.isMessageRead" class="chat-unread">
        {{ lastMessage }}
      </p>
    </div>
  </div>
</template>


<style scoped>
.listed-chat-container {
  display: flex;
  align-items: center;
  background: white;
  padding: 15px;
  transition: background 0.3s ease, transform 0.2s ease;
  width: 100%;
  max-width: 600px;
  cursor: pointer;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.listed-chat-container:hover {
  background: #f5f5f5;
}

/* Grey background for selected chat */
.selected-chat {
  background: #f5f5f5;
  transform: translateY(-2px);
}

/* Image container */
.chat-image-container {
  display: flex;
  align-items: center;
  position: relative;
  flex: 0 0 auto;
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
.chat-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.chat-content h3 {
  font-size: 16px;
  color: #333;
  margin-bottom: 5px;
  font-weight: 600;
}

.chat-content p{
  font-size: 14px;
  color: #666;
  margin: 2px 0;
}

.timestamp {
  font-size: 14px;
  color: #666;
  margin: 2px 0;
  align-self: left;
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

/* Responsive design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .listed-chat-container {
    flex-direction: column; /* Stack items vertically */
    align-items: flex-start; /* Align items to the left */
    padding: 8px; /* Reduce padding */
    max-width: 100%; /* Ensure it takes full width */
  }

  .chat-image-container {
    margin-bottom: 10px; /* Add spacing between the image and text */
  }

  .chat-image {
    width: 40px; /* Reduce image size */
    height: 40px;
    margin-right: 0; /* Remove right margin */
  }

  .initials {
    bottom: -3px; /* Adjust positioning for smaller images */
    right: 3px;
  }

  .chat-unread {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 5px; /* Add spacing between text elements */
  }

  .chat-unread h3 {
    font-size: 12px; /* Reduce font size */
    margin-bottom: 3px; /* Adjust spacing */
  }

  .chat-unread p {
    font-size: 10px; /* Reduce font size */
    color: #777; /* Slightly lighter color for readability */
  }

  .chat-read h3 {
    font-size: 12px; /* Reduce font size for read messages */
    font-weight: 400;
    color: #aaa; /* Lighter color for read messages */
  }
}
</style>
