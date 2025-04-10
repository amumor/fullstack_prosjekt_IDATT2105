<script setup>
import { LCircle, LMap, LTileLayer } from '@vue-leaflet/vue-leaflet'

const props = defineProps({
  location: Array,
})

</script>

<template>
  <!-- Map -->
  <div v-if="!location">{{ $t('map.loading-map') }}...</div>
  <div class="map-container" v-else>
    <LMap :zoom="12" :center=location>
      <!-- Map Background -->
      <LTileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="© OpenStreetMap contributors"
      />
      <l-circle
        :lat-lng=location
        :radius="1000"
        color="#1C64FF"
        fill-color="#00A3FF"
        :fill-opacity="0.2" />
    </LMap>
  </div>
</template>


<style scoped>
.map-container {
  height: 150px;
  width: 100%;
}

</style>
