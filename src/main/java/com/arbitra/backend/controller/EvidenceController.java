package com.arbitra.backend.controller;

import com.arbitra.backend.service.EvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evidence")
public class EvidenceController {

    @Autowired
    private EvidenceService evidenceService;

    @PostMapping("/generate/{id}")
    public ResponseEntity<String> generateEvidencePacket(@PathVariable Long id) {
        String packet = evidenceService.generateDefensePacket(id);
        return ResponseEntity.ok(packet);
    }
}