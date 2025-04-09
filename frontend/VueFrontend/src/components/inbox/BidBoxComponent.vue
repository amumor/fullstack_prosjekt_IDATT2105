<script setup>
import { ref, watch } from 'vue'
import { defineProps } from 'vue'

const props = defineProps({
    isBidder: Boolean,
		inChat: Boolean,
    bidPrice: {
			type: String,
			default: ''
		},
		bidStatus: {
			type: String,
			default: 'PENDING'
		}
})

const emit = defineEmits(['close-bid-box', 'submit-bid', 'accept-bid', 'reject-bid'])

// Bid price
const price = ref(props.bidPrice)
watch(() => props.bidPrice, (newVal) => {
  price.value = newVal
})

// Validation regex for price
const priceRegex = /^\d+(\.\d{1,2})?$/

// Bid options
const submitBid = () => {
  if (!priceRegex.test(price.value)) {
    console.error('Invalid price format')
    return
  }

  emit('submit-bid', { price: price.value })
  price.value = ''
}

const closeBidBox = () => {
  emit('close-bid-box')
}

const acceptBid = () => {
  emit('accept-bid')
}

const rejectBid = () => {
  emit('reject-bid')
}

</script>

<template>
<div class="bid-box-container">

	<!-- Bids in chat -->
	<div class="inchat-bid-box" v-if="props.inChat">
		<h3>Bid</h3>
		<p>Price: {{ props.bidPrice }}</p>

		<!-- Options for bid receiver on pending bids -->
		<div v-if="!isBidder && bidStatus === 'PENDING'" class="action-buttons">
			<button class="basic-blue-btn" @click="acceptBid">Accept</button>
			<button class="basic-blue-btn" @click="rejectBid">Reject</button>
		</div>

		<!-- Status display -->
		<p v-if="bidStatus === 'ACCEPTED'" class="status accepted">Accepted</p>
		<p v-if="bidStatus === 'REJECTED'" class="status rejected">Rejected</p>
	</div>

	<!-- Make new bid -->
	<div class="send-bid-box" v-if="props.isBidder && !props.inChat">
			<h3>Make a bid</h3>
			<button class="close-btn" @click="closeBidBox">✕</button>
			<input v-model="price" type="text" placeholder="Price" />
			<button class="submit-btn" @click="submitBid">Send</button>
	</div>
</div>
</template>

<style scoped>
/* General container styling */
.bid-box-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  margin: 1rem auto;
}

/* In-chat bid box styling */
.inchat-bid-box {
  width: 100%;
  padding: 1rem;
  border: 1px solid #007bff;
  border-radius: 8px;
  background-color: #e9f5ff;
}

.inchat-bid-box h3 {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
  color: #007bff;
}

.inchat-bid-box p {
  margin: 0.5rem 0;
  font-size: 1rem;
  color: #333;
}

.action-buttons {
  display: flex;
  gap: 0.5rem;
  margin-top: 1rem;
}

.action-buttons .basic-blue-btn {
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.action-buttons .basic-blue-btn:hover {
  background-color: #0056b3;
}

.status {
  font-size: 1rem;
  font-weight: bold;
  margin-top: 1rem;
}

.status.accepted {
  color: #28a745;
}

.status.rejected {
  color: #dc3545;
}

/* Send bid box styling */
.send-bid-box {
  width: 100%;
  padding: 1rem;
  border: 1px solid #007bff;
  border-radius: 8px;
  background-color: #fff;
  position: relative;
}

.send-bid-box h3 {
  font-size: 1.2rem;
  margin-bottom: 1rem;
  color: #007bff;
}

.send-bid-box .close-btn {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  background: none;
  border: none;
  font-size: 1.2rem;
  color: #333;
  cursor: pointer;
}

.send-bid-box .close-btn:hover {
  color: #007bff;
}

.send-bid-box input {
  width: calc(100% - 2rem);
  padding: 0.5rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

.send-bid-box input:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 4px rgba(0, 123, 255, 0.5);
}

.send-bid-box .submit-btn {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.send-bid-box .submit-btn:hover {
  background-color: #0056b3;
}

/* Responsive design for smaller screens */
@media (max-width: 768px) {
  .bid-box-container {
    max-width: 90%; /* Reduce the width for smaller screens */
    padding: 0.5rem; /* Reduce padding */
  }

  .inchat-bid-box {
    padding: 0.5rem; /* Reduce padding */
  }

  .inchat-bid-box h3 {
    font-size: 1rem; /* Adjust font size */
  }

  .inchat-bid-box p {
    font-size: 0.9rem; /* Adjust font size */
  }

  .action-buttons .basic-blue-btn {
    padding: 0.4rem 0.8rem; /* Reduce button padding */
    font-size: 0.8rem; /* Adjust font size */
  }

  .send-bid-box {
    padding: 0.5rem; /* Reduce padding */
  }

  .send-bid-box h3 {
    font-size: 1rem; /* Adjust font size */
  }

  .send-bid-box input {
    padding: 0.4rem; /* Reduce input padding */
    font-size: 0.9rem; /* Adjust font size */
  }

  .send-bid-box .submit-btn {
    padding: 0.4rem; /* Reduce button padding */
    font-size: 0.9rem; /* Adjust font size */
  }
}

/* Responsive design for very small screens (max-width: 480px) */
@media (max-width: 480px) {
  .bid-box-container {
    max-width: 100%; /* Take full width */
    padding: 0.5rem; /* Reduce padding */
  }

  .inchat-bid-box {
    padding: 0.5rem; /* Reduce padding */
  }

  .inchat-bid-box h3 {
    font-size: 0.9rem; /* Adjust font size */
  }

  .inchat-bid-box p {
    font-size: 0.8rem; /* Adjust font size */
  }

  .action-buttons {
    flex-direction: column; /* Stack buttons vertically */
    gap: 0.5rem; /* Add spacing between buttons */
  }

  .action-buttons .basic-blue-btn {
    padding: 0.4rem; /* Reduce button padding */
    font-size: 0.8rem; /* Adjust font size */
    width: 100%; /* Make buttons take full width */
  }

  .send-bid-box {
    padding: 0.5rem; /* Reduce padding */
  }

  .send-bid-box h3 {
    font-size: 0.9rem; /* Adjust font size */
  }

  .send-bid-box input {
    padding: 0.4rem; /* Reduce input padding */
    font-size: 0.8rem; /* Adjust font size */
  }

  .send-bid-box .submit-btn {
    padding: 0.4rem; /* Reduce button padding */
    font-size: 0.8rem; /* Adjust font size */
    width: 100%; /* Make button take full width */
  }

  .send-bid-box .close-btn {
    font-size: 1rem; /* Adjust font size */
  }
}
</style>
