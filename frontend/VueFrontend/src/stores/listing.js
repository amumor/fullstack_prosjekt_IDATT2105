import { defineStore } from 'pinia'

export const useListingStore = defineStore('listing', {
  state: () => ({
    listings: [], // All listings
    selectedListing: null, // Currently selected listing
  }),

  actions: {
    setListings(newListings) {
      this.listings = newListings.map(listing => ({
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
    },
    selectListing(listing) {
      this.selectedListing = listing
    },
    addListing(listing) {
      const newListing = {
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
      }
      this.listings.push(newListing)
    },
  }
})
