import axios from 'axios';

// Fetch address from coordinates
const apiKey = import.meta.env.VITE_GOOGLE_API_KEY;

export async function getAddressFromCoordinates(latitude, longitude){
  try {
    const response = await axios.get(
      `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=${apiKey}`
    );
    if (response.data.status === 'OK' && response.data.results.length > 0) {
      // Get the formatted address from the first result
      return response.data.results[0]?.formatted_address;
    } else {
        return 'Address not found';
    }
  } catch (err) {
    console.error('Error fetching address:', err);
  }
};