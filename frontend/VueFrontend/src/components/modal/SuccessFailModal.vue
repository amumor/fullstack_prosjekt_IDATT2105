<template>
  <div class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <p>{{ message }}</p>
      <button class="modal-close-btn" @click="closeModal">Close</button>
    </div>
  </div>
</template>

<script setup>
import { defineEmits, defineProps } from 'vue';

const props = defineProps({
  message: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['close']);

function closeModal() {
  emit('close');
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 6px;
  text-align: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}
.modal-close-btn {
  margin-top: 15px;
  padding: 8px 16px;
  border: none;
  background-color: #1c64ff;
  color: #fff;
  border-radius: 4px;
  cursor: pointer;
}
</style>