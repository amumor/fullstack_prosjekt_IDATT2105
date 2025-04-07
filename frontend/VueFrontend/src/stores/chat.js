import { defineStore } from 'pinia'
import { ref } from 'vue'

export const chatStore = defineStore('chat', () => {
  const chats = ref([]) // All chats
  const bids = ref([]) // All bids
  const hasPendingBids = ref(false) 
  const selectedChat = ref(null)

  function setChats(newChats) {
    chats.value = newChats.map(chat => ({
      ...chat,
      bids: chat.bids || [],
    }))
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
    selectedChat.value.bids.push(bid)
    selectedChat.value.hasPendingBids = true
  }

  function acceptBid(bidId) {
    const chat = selectedChat.value
    console.log(selectChat.value)
    const bidMessage = chat.messages.find(msg => msg.id === bidId && msg.type === 'BID')
    if (bidMessage) {
      bidMessage.status = 'ACCEPTED'
      chat.hasPendingBids = false

      const bid = chat.bids.find(b => b.id === bidId)
      if (bid !== -1) {
        bid.status = 'ACCEPTED'
      }
      console.log(`Bid ${bidId} accepted in chat ${chat.id}`)
    }
  }

  function rejectBid(bidId) {
    const chat = selectedChat.value
    const bidMessage = chat.messages.find(msg => msg.id === bidId && msg.type === 'BID')
    if (bidMessage) {
      bidMessage.status = 'REJECTED'
      chat.hasPendingBids = false

      const bid = chat.bids.find(b => b.id === bidId)
      if (bid !== -1) {
        bid.status = 'REJECTED'
      }
      console.log(`Bid ${bidId} rejected in chat ${chat.id}`)
    }
  }

  function cancelBid(bidId) {
    const chat = selectedChat.value
    const bidMessage = chat.messages.find(msg => msg.id === bidId && msg.type === 'BID')
    if (bidMessage) {
      bidMessage.status = 'CANCELLED'
      chat.hasPendingBids = false

      const bid = chat.bids.findIndex(b => b.id === bidId)
      if (bid !== -1) {
        bid.status = 'CANCELLED'
      }
      console.log(`Bid ${bidId} cancelled in chat ${chat.id}`)
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
    cancelBid,
  }
})
