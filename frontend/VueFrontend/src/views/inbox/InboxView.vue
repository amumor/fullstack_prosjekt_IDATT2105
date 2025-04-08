<script setup>
import { ref, computed, nextTick, watch, onUpdated } from 'vue'
import Navbar from '@/components/Navbar.vue'
import { useChatStore } from '@/stores/chat'
import { storeToRefs } from 'pinia'
import { userStore } from '@/stores/user'
import { useListingStore } from '@/stores/listing'

import ListedChatComponent from '@/components/inbox/ListedChatComponent.vue'
import InitialsDisplayComponent from '@/components/profile/InitialsDisplayComponent.vue'
import BidBoxComponent from '@/components/inbox/BidBoxComponent.vue'

// Set dummy listings
const initialListings = [
  {
    id: 1,
    seller: 50,
    title: 'Boat for sale',
    description: 'A great boat for sale.',
    category: 'Boat',
    status: 'ACTIVE',
    price: 10000,
    location: 'Helsinki, Finland',
    createdAt: '2023-10-01',
    lastEditedAt: '2023-10-01',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
  },
  {
    id: 2,
    seller: 10,
    title: 'Boat for sale',
    description: 'A great boat for sale.',
    category: 'Boat',
    status: 'ACTIVE',
    price: 10000,
    location: 'Helsinki, Finland',
    createdAt: '2023-10-01',
    lastEditedAt: '2023-10-01',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
  },
  {
    id: 3,
    seller: 20,
    title: 'Boat for sale',
    description: 'A great boat for sale.',
    category: 'Boat',
    status: 'ACTIVE',
    price: 10000,
    location: 'Helsinki, Finland',
    createdAt: '2023-10-01',
    lastEditedAt: '2023-10-01',
    image: 'https://iqboatlifts.com/wp-content/uploads/2018/06/Yacht-vs-Boat-Whats-the-Difference-Between-the-Two-1024x571.jpg',
  },
]

const listingStore = useListingStore()
listingStore.setListings(initialListings)

// Set dummy users
const useUserStore = userStore()
useUserStore.login({
  id: 50,
  firstName: 'Alice',
  lastName: 'Smith',
  email: 'alice@smith.com',
  phoneNumber: '555555555',
})

// Set dummy chats
const buyer1 = 30
const buyer2 = 40
const buyer3 = 50
const initialChats = [
  {
    id: 100,
    buyer: buyer1,
    listing: initialListings[0],
    createdAt: '2023-10-01',
    updatedAt: '2023-10-01',
    messages: [{chat: 100, sender: buyer1, content: 'hallo', sentAt:'09:05'}, {chat: 100, sender: initialListings[0].seller, content: 'How are you?', sentAt: '10:25'}],
    bids: [{chat: 100, buyer: 30, price: '600', status: 'PENDING', sentAt: '11:35'}],
    hasPendingBids: true,
    selected: true,
    isMessageRead: true,
  },
  {
    id: 200,
    buyer: buyer2,
    listing: initialListings[1],
    createdAt: '2023-10-01',
    updatedAt: '2023-10-01',
    messages: [{chat: 200, sender: buyer2, content: 'hallo', sentAt:'09:05'}, {chat: 200, sender: initialListings[1].seller, content: 'How are you?', sentAt: '10:25'}],
    bids: [],
    hasPendingBids: false,
    selected: false,
    isMessageRead: false,
  },
  {
    id: 300,
    buyer: buyer3,
    listing: initialListings[2],
    createdAt: '2023-10-01',
    updatedAt: '2023-10-01',
    messages: [{chat: 300, sender: buyer3, content: 'hallo', sentAt:'09:05'}, {chat: 300, sender: initialListings[2].seller, content: 'How are you?', sentAt: '10:25'}],
    bids: [],
    hasPendingBids: false,
    selected: false,
    isMessageRead: false,
  },
]

const chatStore = useChatStore()
const { chats, selectedChat } = storeToRefs(chatStore)
chatStore.setChats(initialChats)

const chatMessages = ref([])
const newMessage = ref('')
const makingBid = ref({})

// Set initial chat messages
for (const chat of initialChats) {
  for (const message of chat.messages) {
    chatMessages.value.push({
      chatId: chat.id,
      message: message.content,
      sender: message.sender,
      sentAt: message.sentAt,
      type: 'MESSAGE',
    })
  }
  for (const bid of chat.bids) {
    chatMessages.value.push({
      id: chatMessages.value.length + 1,
      chatId: chat.id,
      price: bid.price,
      sender: bid.buyer,
      sentAt: bid.sentAt,
      type: 'BID',
      status: bid.status,
    })
  }
}


// Function to select a chat
const openChat = (chatItem) => {
  chatStore.selectChat(chatItem) 
}

// Check if the user is the owner of the listing
const isOwner = computed(() => {
  if (!selectedChat.value) return false
  return selectedChat.value.listing.seller === useUserStore.id
})

// TODO: Sort messages last message time ???

const sendMessage = () => {
  if (selectedChat) {
    chatStore.postMessage(newMessage.value, useUserStore.id)
    chatMessages.value.push({
      chatId: selectedChat.value.id,
      message: newMessage.value,
      sender: useUserStore.id,
      sentAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      type: 'MESSAGE',
    })
  }
  newMessage.value = ''
}

const sendBid = (bid) => {
  if (selectedChat) {
    chatStore.postBid(bid.price, selectedChat.value.buyer)
    chatMessages.value.push({
      id: chatMessages.value.length + 1,
      chatId: selectedChat.value.id,
      price: bid.price,
      sender: useUserStore.id,
      sentAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      type: 'BID',
      status: 'PENDING',
    })
  }
  selectedChat.value.hasPendingBids = true
  makingBid.value[selectedChat.value.id] = false
}

const acceptBid = async (bid) => {
  if (!bid || !bid.id) return
  await chatStore.acceptBid(bid.chatId)

  const index = chatMessages.value.findIndex(b => b.id === bid.id)
  if (index !== -1) {
    chatMessages.value[index].status = 'ACCEPTED'
  }
  
  console.log(chatMessages.value)
  console.log(selectedChat.value.bids)
  await nextTick()
}

const rejectBid = async (bid) => {
  if (!bid || !bid.id) return
  await chatStore.rejectBid(bid.chatId)

  const index = chatMessages.value.findIndex(b => b.id === bid.id)
  if (index !== -1) {
    chatMessages.value[index].status = 'REJECTED'
  }
  
  console.log(chatMessages.value)
  console.log(selectedChat.value.bids)
  await nextTick()
}

const cancelBid = async (bid) => {
  if (!bid || !bid.id) return
  await chatStore.cancelBid(bid.chatId)

  const index = chatMessages.value.findIndex(b => b.id === bid.id)
  if (index !== -1) {
    chatMessages.value[index].status = 'CANCELLED'
  }
  
  console.log(chatMessages.value)
  console.log(selectedChat.value.bids)
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

watch(() => selectedChat.value?.messages.length || selectedChat.value?.bids.length, () => {
  scrollToBottom()
})

onUpdated(() => {
  scrollToBottom()
})


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
        :listingTitle="chatItem.listing.title"
        :image="chatItem.listing.image"
        :lastMessageTime="chatItem.messages[chatItem.messages.length - 1]?.sentAt"
        :isMessageRead="chatItem.isMessageRead"
        :messengerName="'Han Karen'"
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
      <div class="initials" v-if="isOwner">
        <InitialsDisplayComponent 
        :name="'Kar selger'"
        :width="120"
        :height="120" />
        <h2>{{ selectedChat.listing.seller }}</h2>
      </div>
      <div class="initials" v-else>
        <InitialsDisplayComponent
        :name="'Karen kjøper'"
        :width="120"
        :height="120" />
        <h2>{{ selectedChat.buyer }}</h2>
      </div>

      <div class="messages" v-for="(message, index) in chatMessages" :key="index">
        <template v-if="message.chatId === selectedChat.id">
          <!-- Sent messages -->
          <div class="sent-message" v-if="message.sender === useUserStore.id">
            <!-- Bids -->
            <div v-if="message.type === 'BID'" class="bid-message">
              <BidBoxComponent
                :isBidder="true"
                :inChat="true"
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
        </template>
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
  justify-content: space-between;
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
  padding: 0 10px 10px 10px;
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
