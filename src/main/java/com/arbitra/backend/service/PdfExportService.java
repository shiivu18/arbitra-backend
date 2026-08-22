package com.arbitra.backend.service;

import com.arbitra.backend.model.Dispute;
import com.arbitra.backend.repository.DisputeRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfExportService {

    private final DisputeRepository disputeRepository;

    public PdfExportService(DisputeRepository disputeRepository) {
        this.disputeRepository = disputeRepository;
    }

    // Overload 1: Supports PdfExportController (passing a Dispute object)
    public byte[] generateDisputePdf(Dispute dispute) {
        return buildPdf(dispute.getId(), "Standard automated defense portfolio.");
    }

    // Overload 2: Supports PdfController (passing a Long disputeId and String letter)
    public byte[] generateDisputePdf(Long disputeId, String letterText) {
        return buildPdf(disputeId, letterText);
    }

    // Shared PDF generation logic
    private byte[] buildPdf(Long disputeId, String letterText) {
        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() -> new RuntimeException("Dispute not found"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("ARBITRA - FINANCIAL DISPUTE DEFENSE PORTFOLIO"));
            document.add(new Paragraph("--------------------------------------------------"));
            document.add(new Paragraph("Dispute ID: " + dispute.getId()));
            document.add(new Paragraph("Status: " + dispute.getStatus()));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Defense Letter / Notes:"));
            document.add(new Paragraph(letterText != null ? letterText : "Standard automated defense portfolio."));

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF defense portfolio", e);
        }

        return out.toByteArray();
    }
}