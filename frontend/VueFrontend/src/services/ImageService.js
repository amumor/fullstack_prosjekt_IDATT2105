/**
 * Fetches an image from the server and returns a blob URL for it.
 *
 * @returns {Promise<string>} A promise that resolves to a blob URL representing the image.
 * @param {string} url - The URL of the image to fetch.
 */
export async function fetchImage(url) {
    try {
        const baseURL = 'http://localhost:8080';
        url = baseURL + url;
        console.log(url);
        const response = await fetch(url, { method: 'GET' });

        if (!response.ok) {
            throw new Error(`Error fetching image from ${url}: ${response.statusText}`);
        }

        // Convert the response into a blob (binary data)
        const blob = await response.blob();

        // Convert the blob into an object URL that can be used as the src of an img element
        const imageURL = URL.createObjectURL(blob);
        return imageURL;
    } catch (error) {
        console.error('Error fetching image:', error);
        throw error;
    }
}
