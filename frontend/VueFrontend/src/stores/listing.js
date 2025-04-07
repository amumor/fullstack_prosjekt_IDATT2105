import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useListingStore = defineStore('listing', () => {
  const listings = ref([]) // All listings

  const setListings = (newListings) => {
    listings.value = newListings.map(listing => ({
      id: listing.id ?? null,
      seller: listing.seller ?? null,
      title: listing.title ?? '',
      description: listing.description ?? '',
      category: listing.category ?? '',
      status: listing.status ?? 'ACTIVE',
      location: Array.isArray(listing.location) ? listing.location : [],
      createdAt: listing.createdAt ?? new Date().toISOString(),
      lastEditedAt: listing.lastEditedAt ?? new Date().toISOString(),
      image: listing.image ?? '',
    }))
  }

  return {
    listings,
    setListings,
  }
})
