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
        categoryName: listing.category ?? '',
        listingStatus: listing.status ?? 'ACTIVE',
        price: listing.price ?? null,
        latitude: listing.latitude ?? null,
        longitude: listing.longitude ?? null,
        createdAt: listing.createdAt ?? new Date().toISOString(),
        lastEditedAt: listing.lastEditedAt ?? new Date().toISOString(),
        image: listing.image ?? [],
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
        categoryName: listing.category ?? '',
        listingStatus: listing.status ?? 'ACTIVE',
        price: listing.price ?? null,
        latitude: listing.latitude ?? null,
        longitude: listing.longitude ?? null,
        createdAt: listing.createdAt ?? new Date().toISOString(),
        lastEditedAt: listing.lastEditedAt ?? new Date().toISOString(),
        image: listing.image ?? [],
      }
      this.listings.push(newListing)
    },
  }
})
