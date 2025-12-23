package com.example.coltros.Controller;

import com.example.coltros.DTO.Reponse.ColisResponse;
import com.example.coltros.enums.StatutColis;
import com.example.coltros.Service.ColisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transporteur/colis")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TRANSPORTEUR')")
public class TransporteurController {

    private final ColisService colisService;

    @GetMapping
    public ResponseEntity<Page<ColisResponse>> getMesColis(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        String transporteurId = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<ColisResponse> colis = colisService.findColisByTransporteur(transporteurId, pageable);
        return ResponseEntity.ok(colis);
    }

    @GetMapping("/search")

    public ResponseEntity<Page<ColisResponse>> searchMesColisByAdresse(
            @RequestParam String adresse,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        String transporteurId = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<ColisResponse> colis = colisService.searchByAdresseAndTransporteur(transporteurId, adresse, pageable);
        return ResponseEntity.ok(colis);
    }

    @PutMapping("/{colisId}/statut")
    public ResponseEntity<ColisResponse> updateStatutColis(
            @PathVariable String colisId,
            @RequestParam StatutColis statut) {
        String transporteurId = SecurityContextHolder.getContext().getAuthentication().getName();
        ColisResponse updated = colisService.updateStatutColis(colisId, statut, transporteurId);
        return ResponseEntity.ok(updated);
    }
}