package edu.ntnu.SpringBackend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import edu.ntnu.SpringBackend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageController {

    private final Path imageBasePath = Paths.get("app/uploads");
    private final ListingService listingService;

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

    // TODO: Fix, requests from Postman working, but JS requests not working, error msg: backend-1   | 2025-04-10T16:25:56.432Z ERROR 1 --- [nio-8080-exec-5] e.n.S.exception.GlobalExceptionHandler   : !!! Internal server error: Required part 'images' is not present.: exc: class org.springframework.web.multipart.support.MissingServletRequestPartException
    /**
     * <p>THIS ENDPOINT IS DIABLED DUE TO A BUG.</p>
     *
     * <p>Updates the images associated with a specific listing.</p>
     *
     * <p>This endpoint is secured and requires an authenticated user to perform the operation.
     * The method accepts an array of images and a unique listing identifier. It updates the images
     * for the given listing and returns the updated listing details if the operation is successful.</p>
     *
     * @param user the authenticated user making the request, injected through Spring Security
     * @param images an array of {@code MultipartFile} objects containing the images to be set for the listing
     * @param listingId the {@code UUID} of the listing to which the images belong
     * @return a {@code ResponseEntity} containing the result of updating the listing images, typically
     *         with HTTP status 200 if the update succeeds, otherwise an error status is returned
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/set-image")
    public ResponseEntity<?> setImageOnListing(
            @AuthenticationPrincipal User user,
            @RequestParam("images")MultipartFile[] images,
            @RequestParam("listing-id") UUID listingId
            ) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("The endpoint is not currently available.");
//        return ResponseEntity.ok(listingService.setImagesInListingWithUserCheck(listingId, images, user));
    }


}