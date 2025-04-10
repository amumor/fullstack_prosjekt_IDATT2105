package edu.ntnu.SpringBackend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageController {

    private final Path imageBasePath = Paths.get("app/uploads");

    /**
     * Serves an image from the local file system.
     *
     * @param folder the folder identifier where the image is stored
     * @param filename the name of the image file
     * @return ResponseEntity containing the image as a Resource
     */
    @GetMapping("/images/{folder}/{filename:.+}")
    public ResponseEntity<Resource> serveImage(
            @PathVariable String folder,
            @PathVariable String filename) {

        try {
            // Build the full file path
            Path filePath = imageBasePath.resolve(folder).resolve(filename).normalize();

            // Create a Resource from the file path.
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                // If file does not exist or is not readable, throw an exception or handle accordingly.
                return ResponseEntity.notFound().build();
            }

            // Optionally, determine the file's content type
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                // Fallback to the default content type if type could not be determined
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}