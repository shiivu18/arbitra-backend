package com.arbitra.backend.controller;

import com.arbitra.backend.service.PdfExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfExportService pdfExportService;

    @GetMapping("/download/{disputeId}")
    public ResponseEntity<byte[]> downloadDisputePdf(
            @PathVariable Long disputeId,
            @RequestParam(defaultValue = "Standard merchant defense letter generated via AI.") String letter) {
        try {
            byte[] pdfBytes = pdfExportService.generateDisputePdf(disputeId, letter);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "dispute-" + disputeId + "-defense.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}