import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useListingStore = defineStore('listing', () => {
  const id = ref(null)

  const setListing = (newId) => {
    id.value = newId
  }

  return {
    id,
    setListing
  }
}, {
  persist: true
})
