import { defineStore } from 'pinia'

export const useChatStore = defineStore('chat', {
  state: () => ({
    chats: [],
    selectedChat: null,
  }),

  actions: {
    setChats(newChats) {
      this.chats = newChats.map(chat => ({
        id: chat.id || 1,
        buyer: chat.buyer || null,
        listing: chat.listing || null,
        createdAt: chat.createdAt || new Date().getTime(),
        messages: chat.messages || [],
        bids: chat.bids || [],
        hasPendingBids: chat.hasPendingBids || false,
        selected: chat.selected || false,
        isMessageRead: chat.isMessageRead || false,
      }))

      // Set the first chat as the selected chat
      this.selectedChat = this.chats[0] || null
    },
    addChat(chat) {
      const newChat = {
        id: chat.id || null,
        buyer: chat.buyer || null,
        listing: chat.listing || null,
        createdAt: chat.createdAt || new Date().toISOString(),
        messages: chat.messages || [],
        bids: chat.bids || [],
        hasPendingBids: false,
        selected: false,
        isMessageRead: false,
      }
      this.chats.push(newChat)
    },

    selectChat(chat) {
      if (this.selectedChat) this.selectedChat.selected = false
      this.selectedChat = chat
      this.selectedChat.selected = true
      this.selectedChat.isMessageRead = true
    },

    postMessage(messageText, senderId) {
      if (!this.selectedChat || !messageText.trim()) return
      this.selectedChat.messages.push({
        chat: this.selectedChat.id,
        sender: senderId,
        content: messageText,
        sentAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      })
    },

    postBid(price, buyerId) {
      if (!this.selectedChat || !price) return

      const hasPending = this.selectedChat.bids.some(bid => bid.status === 'PENDING')
      if (hasPending) {
        console.error('A pending bid already exists for this chat.')
        return
      }

      const bid = {
        chat: this.selectedChat.id,
        buyer: buyerId,
        price: price,
        status: 'PENDING',
        sentAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      }
      this.selectedChat.bids.push(bid)
      this.selectedChat.hasPendingBids = true
    },

    acceptBid(bidId) {
      const bid = this.selectedChat?.bids.find(b => b.chat === bidId && b.status === 'PENDING')
      if (bid) {
        bid.status = 'ACCEPTED'
      }
    },

    rejectBid(bidId) {
      const bid = this.selectedChat?.bids.find(b => b.chat === bidId && b.status === 'PENDING')
      if (bid) {
        bid.status = 'REJECTED'
        this.selectedChat.hasPendingBids = false
      }
      console.log('storeselect: ', this.selectedChat.bids)
    },

    cancelBid(bidId) {
      const bid = this.selectedChat?.bids.find(b => b.chat === bidId && b.status === 'PENDING')
      if (bid) {
        bid.status = 'CANCELLED'
        this.selectedChat.hasPendingBids = false
      }
    },
  },
})
