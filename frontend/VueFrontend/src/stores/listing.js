import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useListingStore = defineStore('listing', () => {
  // State
  const id = ref(null);

  // Actions
  const setListing = (newId) => {
    id.value = newId;
  };

  return {
    id,
    setListing,
  };
});