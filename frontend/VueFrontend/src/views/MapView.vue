<script setup>
import { ref, onMounted } from 'vue'
import { LMap, LTileLayer, LMarker, LPopup } from '@vue-leaflet/vue-leaflet';
import { Icon } from "leaflet";
import Navbar from '@/components/Navbar.vue'
import BackToComponent from '@/components/BackToComponent.vue'
import { getListingSuggestions } from '../services/ListingService';

// Fix for missing Leaflet icons
delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: new URL("leaflet/dist/images/marker-icon-2x.png", import.meta.url).href,
  iconUrl: new URL("leaflet/dist/images/marker-icon.png", import.meta.url).href,
  shadowUrl: new URL("leaflet/dist/images/marker-shadow.png", import.meta.url).href,
});

// Set initial map center
const center = ref([63.417152858467574, 10.404550601471463]);

const listings = ref([]);
onMounted(async () => {
  try {
    const data = await getListingSuggestions();
  
    
    if (data && Array.isArray(data.listings)) {
      listings.value = data.listings;  
    } else {
      console.error('No listings found or listings is not an array:', data);
    }

  } catch (err) {
    console.error('Error fetching listings:', err);
  }
})



</script>

<template>
  <Navbar />
  <BackToComponent />
  <div class="map-container">
    <LMap :zoom="5" :center="center">
      <!-- Map Background -->
      <LTileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="© OpenStreetMap contributors"
      />

      <!-- Add pins -->
      <LMarker
        v-for="listing in listings"
        :key="listing.id"
        :lat-lng="[listing.latitude, listing.longitude]">
        <l-popup>{{ listing.title }}</l-popup>
      </LMarker>
    </LMap>
  </div>
</template>

<style scoped>
.map-container {
  height: 650px;
  width: 100%;
}
</style>
