<script setup>
import { ref } from 'vue'
import Navbar from '@/components/Navbar.vue'
import { LMap, LTileLayer, LMarker, LPopup } from '@vue-leaflet/vue-leaflet';
import { Icon } from "leaflet";

// Fix for missing Leaflet icons
delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: new URL("leaflet/dist/images/marker-icon-2x.png", import.meta.url).href,
  iconUrl: new URL("leaflet/dist/images/marker-icon.png", import.meta.url).href,
  shadowUrl: new URL("leaflet/dist/images/marker-shadow.png", import.meta.url).href,
});

// Set initial map center
const center = ref([63.417152858467574, 10.404550601471463]);

// Array to store pins
const markers = ref([
  { id: 1, position: [59.9139, 10.7522], popup: "Oslo" },
  { id: 2, position: [63.417152858467574, 10.404550601471463], popup: "TIHLDE kontoret" }
]);
</script>

<template>
  <Navbar :isLoggedIn=true />
  <div class="map-container">
    <LMap :zoom="10" :center="center">
      <!-- Map Background -->
      <LTileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="© OpenStreetMap contributors"
      />

      <!-- Add pins -->
      <LMarker
        v-for="marker in markers"
        :key="marker.id"
        :lat-lng="marker.position">
        <l-popup>{{ marker.popup }}</l-popup>
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
