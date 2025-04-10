/**
 * Fetches an image from the server and returns a blob URL for it.
 *
 * @param {string|Array} imageUrls - The image URL(s) to fetch (either single URL or array of URLs)
 * @returns {string|null} A blob URL representing the image or a default image URL if there's an error
 */
export function fetchImage(imageUrls) {
    // Base URL of your backend (running in Docker)
    const baseURL = 'http://localhost:8080';
    
    // If imageUrls is undefined, null, or empty array, return default image
    if (!imageUrls || (Array.isArray(imageUrls) && imageUrls.length === 0)) {
      return 'https://placehold.co/600x400?text=No+Image';
    }
    
    // If imageUrls is an array, take the first URL
    const imageUrl = Array.isArray(imageUrls) ? imageUrls[0] : imageUrls;
    
    // Check if the URL already starts with http or https
    if (imageUrl.startsWith('http')) {
      return imageUrl;
    }
    
    // Construct the full URL by combining the base URL with the image URL
    return `${baseURL}${imageUrl}`;
  }