package com.arbitra.backend.controller;

import com.arbitra.backend.model.Dispute;
import com.arbitra.backend.repository.DisputeRepository;
import com.arbitra.backend.service.PdfExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disputes")
public class PdfExportController {

    private final PdfExportService pdfExportService;
    private final DisputeRepository disputeRepository;

    public PdfExportController(PdfExportService pdfExportService, DisputeRepository disputeRepository) {
        this.pdfExportService = pdfExportService;
        this.disputeRepository = disputeRepository;
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadDisputePdf(@PathVariable Long id) {
        Dispute dispute = disputeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispute not found"));

        byte[] pdfBytes = pdfExportService.generateDisputePdf(dispute);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "dispute-defense-" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}