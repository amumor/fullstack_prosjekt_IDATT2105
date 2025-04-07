import { defineStore } from 'pinia'
import { ref } from 'vue'

export const chatStore = defineStore('chat', () => {
  const chats = ref([]) // All chats
  const bids = ref([]) // All bids
  const hasPendingBids = ref(false) // Flag to check if there are pending bids
  const selectedChat = ref(null)

  function setChats(newChats) {
    chats.value = newChats
    selectedChat.value = chats.value[0] || null
  }

  function selectChat(chat) {
    selectedChat.value.selected = false
    selectedChat.value = chat
    selectedChat.value.selected = true
    selectedChat.value.isMessageRead = true
  }

  function postMessage(messageText) {
    if (!selectedChat.value || !messageText.trim()) return
    selectedChat.value.messages.push({
      id: selectedChat.value.id,
      message: messageText,
      sentAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      type: 'MESSAGE',
    })
  }

  function postBid(messageText, price) {
    if(!selectedChat.value || !messageText.trim() || !price || !hasPendingBids) return

    const bid ={
      id: selectedChat.value.id,
      message: messageText,
      price: price,
      sentAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      type: 'BID',
      status: 'PENDING',
    }
    selectedChat.value.messages.push(bid)
    
    hasPendingBids.value = true
  }

  function acceptBid(bidId) {
    const bid = selectedChat.value.bids.find(b => b.id === bidId)
    if (bid) {
      bid.status = 'ACCEPTED'
      hasPendingBids.value = false
    }
  }
  function rejectBid(bidId) {
    const bid = selectedChat.value.bids.find(b => b.id === bidId)
    if (bid) {
      bid.status = 'REJECTED'
      hasPendingBids.value = false
    }
  }

  return {
    chats,
    bids,
    hasPendingBids,
    selectedChat,
    setChats,
    selectChat,
    postMessage,
    postBid,
    acceptBid,
    rejectBid,
  }
})
