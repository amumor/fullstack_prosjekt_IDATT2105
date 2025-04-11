<script setup>
import { ref, onMounted, computed } from 'vue'
import Navbar from '@/components/Navbar.vue'
import { useChatStore } from '@/stores/chat'
import { userStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import ListedChatComponent from '@/components/inbox/ListedChatComponent.vue'
import BidBoxComponent from '@/components/inbox/BidBoxComponent.vue'
import { isTokenExpired } from '@/services/TokenService.js'
import { fetchImage } from '@/services/ImageService.js'
import {
  addMessageToChat,
  getAllChatsForUser
} from '@/services/ChatService.js'
import {
  placeBid,
  acceptBid,
  rejectBid,
  cancelBid
} from '@/services/BidService.js'

const chatStore = useChatStore()
const user = userStore()
const newMessage = ref('')
const bidPrice = ref('')
const showBidBox = ref(false)
const router = useRouter()
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  console.log('InboxView mounted')
  loading.value = true
  error.value = null

  if (!user.isLoggedIn) {
    console.warn('User not logged in. Redirecting to login.')
    router.push('/login')
    return
  }

  try {
    if (isTokenExpired(user.token)) {
      user.logout()
      router.push('/login')
      return
    }

    console.log('Fetching chats...')
    const chats = await getAllChatsForUser(user.token)
    console.log('Chats received:', chats)

    if (chats && Array.isArray(chats)) {
      const processedChats = chats.map(chat => {
        chat.messages = chat.messages || []
        chat.bids = chat.bids || []

        if (!chat.listing && chat.listingId) {
  
          chat.listing = {
            id: chat.listingId,
            title: `Chat with ${chat.sellerFirstName} ${chat.buyerFirstName}`, 
            imageUrls: [] 
          }
        }

        return chat
      })

      console.log('Processed chats:', processedChats)
      chatStore.setChats(processedChats)

      // Select the first chat if available
      if (processedChats.length > 0 && !chatStore.selectedChat) {
        chatStore.selectChat(processedChats[0])
      }
    } else {
      console.warn('Invalid chat data structure:', chats)
      chatStore.setChats([])
    }
  } catch (error) {
    console.error('Error loading chats:', error)
    error.value = 'Failed to load chats. Please try again.'
  } finally {
    loading.value = false
  }
})

// Send a new message in the current chat
const sendMessage = async () => {
  if (!chatStore.selectedChat || !newMessage.value.trim()) return

  try {
    // Send the message to the backend
    const response = await addMessageToChat(
      chatStore.selectedChat.id,
      newMessage.value,
      user.token
    )

    // Update the chat store with the new message
    chatStore.postMessage(newMessage.value, user.id)
    newMessage.value = ''
  } catch (error) {
    console.error('Error sending message:', error)
  }
}

// Handle placing a new bid
const handleSubmitBid = async (bidData) => {
  if (!chatStore.selectedChat) return

  try {
    // Place the bid via the BidService
    const response = await placeBid(
      chatStore.selectedChat.id,
      bidData.price,
      user.token
    )

    // Update the store with the new bid
    chatStore.postBid(bidData.price, user.id)
    showBidBox.value = false
  } catch (error) {
    console.error('Error placing bid:', error)
  }
}

// Handle accepting a bid
const handleAcceptBid = async () => {
  if (!chatStore.selectedChat || !chatStore.selectedChat.hasPendingBids) return

  // Find the pending bid for this chat
  const pendingBid = chatStore.selectedChat.bids.find(
    bid => bid.status === 'PENDING'
  )

  if (!pendingBid) return

  try {
    // Accept the bid via the BidService
    await acceptBid(pendingBid.id, user.token)

    // Update the store
    chatStore.acceptBid(pendingBid.id)
  } catch (error) {
    console.error('Error accepting bid:', error)
  }
}

// Handle rejecting a bid
const handleRejectBid = async () => {
  if (!chatStore.selectedChat || !chatStore.selectedChat.hasPendingBids) return

  // Find the pending bid for this chat
  const pendingBid = chatStore.selectedChat.bids.find(
    bid => bid.status === 'PENDING'
  )

  if (!pendingBid) return

  try {
    // Reject the bid via the BidService
    await rejectBid(pendingBid.id, user.token)

    // Update the store
    chatStore.rejectBid(pendingBid.id)
  } catch (error) {
    console.error('Error rejecting bid:', error)
  }
}

// Handle cancelling a bid (as the buyer)
const handleCancelBid = async () => {
  if (!chatStore.selectedChat || !chatStore.selectedChat.hasPendingBids) return

  // Find the pending bid for this chat
  const pendingBid = chatStore.selectedChat.bids.find(
    bid => bid.status === 'PENDING' && bid.buyer === user.id
  )

  if (!pendingBid) return

  try {
    // Cancel the bid via the BidService
    await cancelBid(pendingBid.id, user.token)

    // Update the store
    chatStore.cancelBid(pendingBid.id)
  } catch (error) {
    console.error('Error cancelling bid:', error)
  }
}

// Check if there are any chats available
const hasChats = computed(() => {
  return chatStore.chats && chatStore.chats.length > 0
})

// Check if the current user is the buyer in this chat
const isBuyer = computed(() => {
  if (!chatStore.selectedChat) return false
  return chatStore.selectedChat.buyerId === user.id
})

// Check if the current user is the seller in this chat
const isSeller = computed(() => {
  if (!chatStore.selectedChat) return false
  return !isBuyer.value
})
</script>

<template>
  <Navbar />
  <div class="display-page-container">
    <div v-if="loading" class="loading-container">
      <div class="loader"></div>
      <p>Loading chats...</p>
    </div>

    <div v-else-if="error" class="error-container">
      <p>{{ error }}</p>
      <button @click="router.go(0)" class="basic-blue-btn">Reload</button>
    </div>

    <template v-else>
      <div class="display-left-container">
        <h2>{{ $t('header.messages') }}</h2>

        <!-- No chats message -->
        <div v-if="!hasChats" class="no-chats-message">
          <p>You don't have any conversations yet.</p>
        </div>

        <!-- Chat list -->
        <div v-else class="chats-container">
          <ListedChatComponent v-for="chat in chatStore.chats" :key="chat.id" :chatId="chat.id"
            :listingTitle="chat.listing ? chat.listing.title : `Chat #${chat.id.substring(0, 8)}`" :image="chat.listing && chat.listing.imageUrls && chat.listing.imageUrls.length > 0 ?
              fetchImage(chat.listing.imageUrls) : 'https://placehold.co/600x400?text=No+Image'" :lastMessageTime="chat.messages && chat.messages.length > 0 ?
                chat.messages[chat.messages.length - 1].sentAt : ''" :isMessageRead="chat.isMessageRead" :messengerName="chat.buyerFirstName ?
                `${chat.buyerFirstName} ${chat.buyerLastName}` :
                `${chat.sellerFirstName} ${chat.sellerLastName}`" :messages="chat.messages || []"
            :selected="chat === chatStore.selectedChat" />
        </div>
      </div>

      <!-- Chat messages and bids (right side) -->
      <div class="display-right-container" v-if="chatStore.selectedChat">
        <div class="message-info">
          <!-- Messages -->
          <div class="messages">
            <div v-if="!chatStore.selectedChat.messages || chatStore.selectedChat.messages.length === 0"
              class="no-messages-yet">
              <p>No messages yet. Start the conversation!</p>
            </div>

            <div v-else v-for="message in chatStore.selectedChat.messages" :key="message.id">
              <div :class="message.sender === user.id ? 'sent-message' : 'received-message'">
                {{ message.content }}
                <div :id="message.sender === user.id ? 'sent-timestamp' : 'received-timestamp'">
                  {{ new Date(message.sentAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }}
                </div>
              </div>
            </div>
          </div>

          <!-- Bids -->
          <div v-if="chatStore.selectedChat.bids && chatStore.selectedChat.bids.length > 0">
            <div v-for="bid in chatStore.selectedChat.bids" :key="bid.id">
              <BidBoxComponent :isBidder="bid.buyer === user.id" :inChat="true" :bidPrice="bid.price"
                :bidStatus="bid.status" @accept-bid="handleAcceptBid" @reject-bid="handleRejectBid" />
            </div>
          </div>
        </div>

        <div class="message-input">
          <textarea v-model="newMessage" placeholder="Type a message..."
            @keydown.enter.prevent="sendMessage"></textarea>

          <div class="button-container">
            <button @click="sendMessage" class="send-btn">
              {{ $t('button.send') }}
            </button>

            <button v-if="isBuyer && !showBidBox && (!chatStore.selectedChat.hasPendingBids)" @click="showBidBox = true"
              class="bid-btn">
              {{ $t('button.make-bid') }}
            </button>

            <button v-if="isBuyer && chatStore.selectedChat.hasPendingBids" @click="handleCancelBid" class="cancel-btn">
              {{ $t('button.cancel-bid') }}
            </button>
          </div>
        </div>
      </div>


      <div class="display-right-container empty-state" v-else>
        <div class="empty-message">
          <p>{{ hasChats ? $t('chat.select-conversation') : 'Start a conversation by messaging a listing owner!' }}</p>
        </div>
      </div>
    </template>
  </div>
</template>

<style scoped>
.loading-container,
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 60vh;
  width: 100%;
}

.loader {
  border: 5px solid #f3f3f3;
  border-top: 5px solid #1C64FF;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

.error-container p {
  color: #d9534f;
  margin-bottom: 20px;
}

.no-chats-message {
  padding: 20px;
  text-align: center;
  color: #666;
}

.no-messages-yet {
  text-align: center;
  padding: 20px;
  color: #666;
  font-style: italic;
}

.display-page-container {
  display: flex;
  min-height: calc(100vh - 60px);
}

.display-left-container {
  flex: 1;
  max-width: 40%;
  overflow: auto;
  padding: 20px;
}

.display-right-container {
  flex: 1;
  max-width: 60%;
  background: #f5f5f5;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: calc(94vh - 56px);
  margin: 10px;
}

.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
}

.empty-message {
  font-size: 18px;
  color: #666;
  text-align: center;
}

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
  align-items: right;
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
  padding: 10px;
  gap: 12px;
  height: 100%;
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
  background: linear-gradient(135deg, #1C64FF, #0056b3);
  color: white;
  align-self: flex-end;
  text-align: left;
  padding: 12px 16px;
  border-radius: 18px 18px 4px 18px;
  margin-left: 40px;
  margin-bottom: 4px;
  max-width: 70%;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  position: relative;
  word-wrap: break-word;
  line-height: 1.4;
  align-items: flex-end;
  display: flex;
  flex-direction: column;
}

.received-message {
  background: white;
  color: #333;
  align-self: flex-start;
  text-align: left;
  padding: 12px 16px;
  border-radius: 18px 18px 18px 4px;
  margin-right: 40px;
  margin-bottom: 4px;
  max-width: 70%;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  position: relative;
  word-wrap: break-word;
  line-height: 1.4;
  border: 1px solid #e1e1e1;
  align-self: flex-start;
  display: flex;
  flex-direction: column;
}

#sent-timestamp {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.7);
  text-align: right;
  margin-top: 4px;
  align-self: flex-end; 
  width: 100%; 
}

#received-timestamp {
  font-size: 11px;
  color: #888;
  text-align: left;
  margin-top: 4px;
  align-self: flex-start; 
  width: 100%; 
}

.message-input {
  display: flex;
  flex-direction: column;
  padding: 15px;
  background-color: white;
  border-top: 1px solid #e0e0e0;
  border-radius: 0 0 8px 8px;
  margin-top: auto;
}

.message-input textarea {
  width: 100%;
  padding: 14px;
  border-radius: 20px;
  border: 1px solid #ddd;
  resize: none;
  margin-bottom: 12px;
  font-size: 14px;
  min-height: 60px;
  background-color: #f9f9f9;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.message-input textarea:focus {
  outline: none;
  border-color: #1C64FF;
  box-shadow: 0 0 0 2px rgba(28, 100, 255, 0.1);
  background-color: white;
}

.button-container {
  display: flex;
  gap: 10px;
}

.send-btn {
  flex: 3;
  padding: 12px;
  border: none;
  background-color: #1C64FF;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease;
}

.bid-btn {
  flex: 1; 
  padding: 12px;
  border: none;
  background-color: #34c759;
  color: white;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 500;
  transition: background-color 0.2s;
}

.bid-btn {
  flex: 1; 
  padding: 12px;
  border: none;
  background-color: #34c759;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.cancel-btn {
  flex: 1; 
  padding: 12px;
  border: none;
  background-color: #ff3b30;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease;
}

.send-btn:hover {
  background-color: #0056b3;
  transform: translateY(-1px);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.bid-btn:hover {
  background-color: #2eb350;
  transform: translateY(-1px);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.cancel-btn:hover {
  background-color: #e0352b;
  transform: translateY(-1px);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .sent-message, .received-message {
    max-width: 85%;
    padding: 10px 14px;
  }
  
  .message-input textarea {
    min-height: 50px;
    padding: 10px;
  }
  
  #sent-timestamp, #received-timestamp {
    font-size: 10px;
  }
}

@media (max-width: 480px) {
  .sent-message, .received-message {
    max-width: 90%;
    padding: 8px 12px;
    margin-left: 20px;
    margin-right: 20px;
  }
  
  .button-container {
    gap: 6px;
  }
  
  .send-btn, .bid-btn, .cancel-btn {
    padding: 10px;
    font-size: 12px;
    border-radius: 16px;
  }
}
</style>