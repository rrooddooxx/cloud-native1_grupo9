package com.skravetz.cn1grupo9.controller;

import com.skravetz.cn1grupo9.entity.PriceChangeLog;
import com.skravetz.cn1grupo9.repository.PriceChangeLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final PriceChangeLogRepository priceChangeLogRepository;

    @Value("${app.price-change.storage-path:/app/price-changes}")
    private String storagePath;

    @GetMapping("/price-changes")
    public ResponseEntity<List<PriceChangeLog>> listPriceChangeFiles() {
        try {
            List<PriceChangeLog> files = priceChangeLogRepository.findAllOrderByChangeDateDesc();
            log.info("Retrieved {} price change files", files.size());
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            log.error("Error retrieving price change files", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/price-changes/product/{productId}")
    public ResponseEntity<List<PriceChangeLog>> listPriceChangeFilesByProduct(@PathVariable Integer productId) {
        try {
            List<PriceChangeLog> files = priceChangeLogRepository.findByProductId(productId);
            log.info("Retrieved {} price change files for product {}", files.size(), productId);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            log.error("Error retrieving price change files for product: {}", productId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/price-changes/download/{fileId}")
    public ResponseEntity<Resource> downloadPriceChangeFile(@PathVariable Long fileId) {
        try {
            PriceChangeLog priceChangeLog = priceChangeLogRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("File not found with ID: " + fileId));

            Path filePath = Paths.get(priceChangeLog.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("File not found or not readable: " + priceChangeLog.getFileName());
            }

            log.info("Downloading file: {}", priceChangeLog.getFileName());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=\"" + priceChangeLog.getFileName() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            log.error("Error creating file URL for file ID: {}", fileId, e);
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("Error downloading file with ID: {}", fileId, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/price-changes/view/{fileId}")
    public ResponseEntity<Resource> viewPriceChangeFile(@PathVariable Long fileId) {
        try {
            PriceChangeLog priceChangeLog = priceChangeLogRepository.findById(fileId)
                    .orElseThrow(() -> new RuntimeException("File not found with ID: " + fileId));

            Path filePath = Paths.get(priceChangeLog.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("File not found or not readable: " + priceChangeLog.getFileName());
            }

            log.info("Viewing file: {}", priceChangeLog.getFileName());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resource);

        } catch (MalformedURLException e) {
            log.error("Error creating file URL for file ID: {}", fileId, e);
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            log.error("Error viewing file with ID: {}", fileId, e);
            return ResponseEntity.notFound().build();
        }
    }
}