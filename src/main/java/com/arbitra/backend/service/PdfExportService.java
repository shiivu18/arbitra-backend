package com.arbitra.backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfExportService {

    public byte[] generateDisputePdf(Long disputeId, String defenseLetter) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Title
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("ARBITRA - CHARGEBACK DEFENSE REBUTTAL");
                contentStream.endText();

                // Dispute Metadata
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Dispute ID: " + disputeId);
                contentStream.endText();

                // Defense Letter Body (Splitting lines safely for simple rendering)
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
                contentStream.newLineAtOffset(50, 680);
                
                String[] lines = defenseLetter.split("\n");
                int yOffset = 0;
                for (String line : lines) {
                    if (yOffset > 550) { // prevent overflow off the page
                        break;
                    }
                    // Clean up markdown characters for clean PDF text
                    String cleanLine = line.replaceAll("[#*>]", "").trim();
                    if (!cleanLine.isEmpty()) {
                        contentStream.showText(cleanLine.length() > 90 ? cleanLine.substring(0, 90) : cleanLine);
                        contentStream.newLineAtOffset(0, -15);
                        yOffset += 15;
                    }
                }
                contentStream.endText();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        }
    }
}