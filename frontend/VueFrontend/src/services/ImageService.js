const baseURL = 'http://localhost:8080'; // TODO: replace test URL with actual backend URL


/**
 * Fetches an image from the server and returns a blob URL for it.
 *
 * @param {string|Array} imageUrls - The image URL(s) to fetch (either single URL or array of URLs)
 * @returns {string|null} A blob URL representing the image or a default image URL if there's an error
 */
export function fetchImage(imageUrls) {
    // Base URL of your backend (running in Docker)

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


/**
 * Sends a POST request to associate images with a specific listing.
 *
 * @async
 * @function setImageOnListing
 * @param {string} listingId - The ID of the listing to associate the images with.
 * @param {Array<File>} images - An array of image files to upload and associate with the listing.
 * @param {string} token - A valid JWT token for authentication.
 * @throws {Error} Throws an error if no token is provided or if the request fails.
 * @returns {Promise<Object>} A promise that resolves to the JSON response from the server.
 *
 * @example
 * const listingId = "12345";
 * const images = [file1, file2];
 * const token = "your-jwt-token";
 *
 * try {
 *     const response = await setImageOnListing(listingId, images, token);
 *     console.log("Images successfully associated:", response);
 * } catch (error) {
 *     console.error("Error associating images:", error);
 * }
 */
export async function setImageOnListing(listingId, images, token) {
    // Build FormData and append all images and the listing-id.
    const formData = new FormData();

    formData.append("images", images);
    formData.append("listing-id", listingId);

    // Create fetch options, including authorization header if token is provided.
    const options = {
        method: "POST",
        body: formData,
        headers: {}
    };

    if (token) {
        options.headers["Authorization"] = `Bearer ${token}`;
    } else {
        console.warn("No token provided for authorization.");
        throw new Error('No token provided for authorization.');
    }

    try {
        const response = await fetch(baseURL + "/api/v1/set-image", options);

        if (!response.ok) {
            throw new Error(`Request failed with status ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error("Error setting image on listing:", error);
        throw error;
    }
}

/**
 * Uploads image files to the specified listing using JWT authentication.
 *
 * @param {string} listingId - The UUID of the listing to update.
 * @param {File} imageFile - An array of File objects representing the images.
 * @param {string} token - The JWT token to be used for authentication.
 * @returns {Promise<Object>} - A promise resolving to the JSON response from the backend.
 */
export function sendImagesToListing(listingId, imageFile, token) {
    // Create a FormData instance to hold the files and additional parameters.
    const formData = new FormData();

    // Append each image file to FormData using the key expected by your Spring Boot endpoint.
    // imageFiles.forEach(file => formData.append('images', file));
    formData.append("images", imageFile);

    // Append the listing ID with the key that matches the controller parameter "listing-id"
    formData.append('listing-id', listingId);

    // Send a POST request to the /set-image endpoint.
    const url = 'http://localhost:8080/api/v1/set-image';
    return fetch(url, {
        method: 'POST',
        body: formData,
        // Include the JWT in the Authorization header
        headers: {
            'Authorization': `Bearer ${token}`
        },

    })
        .then(response => {
            // Check if the response status is OK
            if (!response.ok) {
                throw new Error('Failed to upload images. Status: ' + response.status);
            }
            // Parse the response as JSON
            return response.json();
        })
        .then(data => {
            console.log('Images updated successfully:', data);
            return data;
        })
        .catch(error => {
            console.error('Error while updating images:', error);
            throw error;
        });
}