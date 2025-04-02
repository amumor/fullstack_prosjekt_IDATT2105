<script setup>

const categories = [
  { id: 1, name: 'Boats' },
  { id: 2, name: 'Cars' },
  { id: 3, name: 'Motorcycles' },
  { id: 4, name: 'Real Estate' },
];

const createListing = () => {
  // Logic to save the listing
};
</script>

<template>
<div class="new-listing-container">
  <div class="fields">
    <h2>Create a new listing</h2>
    <div class="text-fields">
      <input type="text" placeholder="Header" required />
      <textarea type="text" id="description" placeholder="Description" required />
      <input type="text" placeholder="Price" required />
      <input type="text" placeholder="Location" required />
      <div v-for="category in categories" :key="category.id">
        <label>
          <input type="checkbox" :name=category.name :value=category.name>{{ category.name }}
        </label>
      </div>
    </div>
    <div class="image-field">
      <form action="/upload" method="post" enctype="multipart/form-data">
        <label for="file">Upload image:</label>
        <input type="file" id="file" name="file" accept="image/*">
        <button type="submit">Upload</button>
      </form>
    </div>
    <div class="submit-button">
      <button type="submit" @click="createListing">Create</button>
    </div>
  </div>
</div>
</template>

<style scoped>
.new-listing-container {
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  font-family: 'Inter', sans-serif;

  width: 40%;
  max-width: 600px;
  padding: 40px;
  border-radius: 10px;
  margin: 70px auto;
  position: relative;
}

/* Header */
h2 {
  font-size: 24px;
  color: #333333;
  font-weight: 500;

  text-align: center;
  margin-bottom: 20px;
}

/* Input fields */
.text-fields {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.text-fields input {
  font-size: 16px;
  transition: border-color 0.3s ease;

  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.text-fields input:focus {
  border-color: #1C64FF;
  outline: none;
}

#description {
  font-size: 16px;
  border: 1px solid #ccc;
  font-family: 'Inter', sans-serif;

  width: 100%;
  height: 100px;
  padding: 10px;
  border-radius: 5px;
  resize: vertical;
  text-align: left;
  vertical-align: top;
}

/* Category selection */
.text-fields div {
  color: #333333;

  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
}

.text-fields label {
  font-size: 14px;
  color: #333333;

  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
}

.text-fields input[type="checkbox"] {
  accent-color: #1C64FF;

  width: 16px;
  height: 16px;
}

/* Image upload */
.image-field {
  color: #333333;
  margin-top: 20px;
}

.image-field label {
  font-weight: 500;
}

.image-field input {
  margin-top: 5px;
}

/* Submit button */
.submit-button {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.submit-button button {
  background: #1C64FF;
  color: white;
  font-size: 16px;

  padding: 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.2s ease;
  width: 100%;
}

.submit-button button:hover {
  background: #0056b3;
  transform: scale(1.05);
}

/* Responsive Design */
@media (max-width: 768px) {
  .new-listing-container {
    width: 80%;
    padding: 20px;
  }
}
</style>
