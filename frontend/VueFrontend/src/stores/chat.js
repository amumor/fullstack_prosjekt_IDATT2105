import { defineStore } from 'pinia'
import { ref } from 'vue'

export const chatStore = defineStore('chat', () => {
  const chats = ref([]) // All chats
  const selectedChat = ref(null)

  function setChats(newChats) {
    chats.value = newChats
    selectedChat.value = chats.value[0] || null
  }

  function selectChat(chat) {
    if (selectedChat.value) selectedChat.value.selected = false
    selectedChat.value = chat
    selectedChat.value.selected = true
    selectedChat.value.isMessageRead = true
  }

  function sendMessage(messageText) {
    if (!selectedChat.value || !messageText.trim()) return
    selectedChat.value.messages.push({
      id: selectedChat.value.id,
      message: messageText,
      sentAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    })
  }

  return {
    chats,
    selectedChat,
    setChats,
    selectChat,
    sendMessage,
  }
})
