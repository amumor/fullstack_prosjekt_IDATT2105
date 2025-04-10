package edu.ntnu.SpringBackend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    // Define the base directory where your images are stored on disk.
    // Adjust this path as needed for your environment.
    private final Path imageBasePath = Paths.get("C:/path/to/your/image/folder").toAbsolutePath().normalize();

    /**
     * Loads an image file from the file system and returns it as a Spring Resource.
     *
     * @param folder   the folder (or folder id) where the image is stored.
     * @param filename the actual file name.
     * @return the image file as a {@link Resource}.
     * @throws IOException if the file does not exist or is unreadable.
     */
    public Resource loadImage(String folder, String filename) throws IOException {
        // Construct the full file path based on the base directory, folder, and filename.
        Path filePath = imageBasePath.resolve(folder).resolve(filename).normalize();

        // Create a Resource from the file path.
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("Could not read file: " + filename);
        }
        return resource;
    }

    /**
     * Determines the content type for the given resource.
     *
     * @param resource the resource (file) whose content type is to be determined.
     * @return the content type if detected; otherwise "application/octet-stream".
     * @throws IOException if an error occurs while reading the file.
     */
    public String getContentType(Resource resource) throws IOException {
        String contentType = Files.probeContentType(resource.getFile().toPath());
        // Fallback to a default content type if unable to determine.
        return (contentType != null) ? contentType : "application/octet-stream";
    }
}