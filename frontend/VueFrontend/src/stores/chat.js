import { defineStore } from 'pinia'

export const useChatStore = defineStore('chat', {
  state: () => ({
    chats: [],
    selectedChat: null,
  }),

  actions: {
    setChats(chats) {
      this.chats = chats.map(chat => {
        if (!chat.listing && chat.listingId) {
          chat.listing = {
            id: chat.listingId,
            title: `Chat with ${chat.sellerFirstName || chat.buyerFirstName}`,
            imageUrls: []
          }
        }
        
        return {
          ...chat,
          messages: chat.messages || [],
          bids: chat.bids || [],
          selected: false,
          isMessageRead: chat.isMessageRead || false,
          hasPendingBids: chat.bids ? chat.bids.some(bid => bid.status === 'PENDING') : false
        }
      })
    },

    addChat(chat) {
      const processedChat = {
        ...chat,
        messages: chat.messages || [],
        bids: chat.bids || [],
        selected: false,
        isMessageRead: chat.isMessageRead || false,
        hasPendingBids: chat.bids ? chat.bids.some(bid => bid.status === 'PENDING') : false
      };

      if (!processedChat.listing && processedChat.listingId) {
        processedChat.listing = {
          id: processedChat.listingId,
          title: `Chat with ${processedChat.sellerFirstName || processedChat.buyerFirstName}`,
          imageUrls: []
        };
      }

      this.chats.unshift(processedChat);
      
      this.selectChat(processedChat);
    },
    
    addMessageToChat(chatId, message) {
      const chat = this.chats.find((c) => c.id === chatId)
      if (chat) {
        if (!chat.messages) chat.messages = []
        chat.messages.push(message)
      }
    },

    selectChat(chat) {
      if (this.selectedChat) this.selectedChat.selected = false
      this.selectedChat = chat
      if (chat) {
        chat.selected = true
        chat.isMessageRead = true
      }
    },

    postMessage(messageText, senderId) {
      if (!this.selectedChat || !messageText.trim()) return
      
      if (!this.selectedChat.messages) {
        this.selectedChat.messages = []
      }
      
      this.selectedChat.messages.push({
        id: Date.now(), // Temporary ID
        chat: this.selectedChat.id,
        sender: senderId,
        content: messageText,
        sentAt: new Date().toISOString(),
      })
    },

    postBid(price, buyerId) {
      if (!this.selectedChat || !price) return

      if (!this.selectedChat.bids) {
        this.selectedChat.bids = []
      }

      const hasPending = this.selectedChat.bids.some(bid => bid.status === 'PENDING')
      if (hasPending) {
        console.error('A pending bid already exists for this chat.')
        return
      }

      const bid = {
        id: Date.now(),
        chat: this.selectedChat.id,
        buyer: buyerId,
        price: price,
        status: 'PENDING',
        sentAt: new Date().toISOString(),
      }
      this.selectedChat.bids.push(bid)
      this.selectedChat.hasPendingBids = true
    },

    acceptBid(bidId) {
      if (!this.selectedChat || !this.selectedChat.bids) return
      
      const bid = this.selectedChat.bids.find(b => b.id === bidId && b.status === 'PENDING')
      if (bid) {
        bid.status = 'ACCEPTED'
        this.selectedChat.hasPendingBids = false
      }
    },

    rejectBid(bidId) {
      if (!this.selectedChat || !this.selectedChat.bids) return
      
      const bid = this.selectedChat.bids.find(b => b.id === bidId && b.status === 'PENDING')
      if (bid) {
        bid.status = 'REJECTED'
        this.selectedChat.hasPendingBids = false
      }
    },

    cancelBid(bidId) {
      if (!this.selectedChat || !this.selectedChat.bids) return
      
      const bid = this.selectedChat.bids.find(b => b.id === bidId && b.status === 'PENDING')
      if (bid) {
        bid.status = 'CANCELLED'
        this.selectedChat.hasPendingBids = false
      }
    },
  },
})