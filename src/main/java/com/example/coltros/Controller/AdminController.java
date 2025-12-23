package com.example.coltros.Controller;

import com.example.coltros.DTO.filter.ColisFilterDTO;
import com.example.coltros.DTO.filter.TransporteurFilterDTO;
import com.example.coltros.DTO.Request.*;
import com.example.coltros.DTO.Reponse.ColisResponse;
import com.example.coltros.DTO.Reponse.TransporteurResponse;
import com.example.coltros.enums.StatutColis;
import com.example.coltros.entity.User;
import com.example.coltros.Service.ColisService;
import com.example.coltros.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.coltros.DTO.Request.ColisRequest;
import com.example.coltros.DTO.Request.ColisUpdateRequest;
import com.example.coltros.DTO.Request.TransporteurRequest;
import com.example.coltros.DTO.Request.TransporteurUpdateRequest;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ColisService colisService;
    private final UserService userService;

    // ========== Gestion des Colis ==========

    @GetMapping("/colis")
    public ResponseEntity<Page<ColisResponse>> getAllColis(@Valid ColisFilterDTO filter) {
        Page<ColisResponse> colis = colisService.findAllColis(filter);
        return ResponseEntity.ok(colis);
    }

    @GetMapping("/colis/search")
    public ResponseEntity<Page<ColisResponse>> searchColisByAdresse(
            @RequestParam String adresse,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ColisResponse> colis = colisService.searchByAdresse(adresse, pageable);
        return ResponseEntity.ok(colis);
    }

    @PostMapping("/colis")

    public ResponseEntity<ColisResponse> createColis(@Valid @RequestBody ColisRequest colisCreateDTO) {
        ColisResponse created = colisService.createColis(colisCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/colis/{id}")

    public ResponseEntity<ColisResponse> updateColis(
            @PathVariable String id,
            @Valid @RequestBody ColisUpdateRequest colisUpdateDTO) {
        ColisResponse updated = colisService.updateColis(id, colisUpdateDTO);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/colis/{colisId}/assigner/{transporteurId}")

    public ResponseEntity<ColisResponse> assignerColis(
            @PathVariable String colisId,
            @PathVariable String transporteurId) {
        ColisResponse updated = colisService.assignerColis(colisId, transporteurId);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/colis/{colisId}/statut")

    public ResponseEntity<ColisResponse> updateStatutColis(
            @PathVariable String colisId,
            @RequestParam StatutColis statut) {
        ColisResponse updated = colisService.updateStatutColis(colisId, statut, null);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/colis/{id}")
    public ResponseEntity<Void> deleteColis(@PathVariable String id) {
        colisService.deleteColis(id);
        return ResponseEntity.noContent().build();
    }

    // ========== Gestion des Transporteurs ==========

    @GetMapping("/transporteurs")

    public ResponseEntity<Page<TransporteurResponse>> getAllTransporteurs(@Valid TransporteurFilterDTO filter) {
        Page<TransporteurResponse> transporteurs = userService.findAllTransporteurs(filter);
        return ResponseEntity.ok(transporteurs);
    }

    @PostMapping("/transporteurs")

    public ResponseEntity<TransporteurResponse> createTransporteur(
            @Valid @RequestBody TransporteurRequest transporteurCreateDTO) {
        TransporteurResponse created = userService.createTransporteur(transporteurCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/transporteurs/{id}")

    public ResponseEntity<TransporteurResponse> updateTransporteur(
            @PathVariable String id,
            @Valid @RequestBody TransporteurUpdateRequest transporteurUpdateDTO) {
        TransporteurResponse updated = userService.updateTransporteur(id, transporteurUpdateDTO);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/transporteurs/{id}/reactiver")

    public ResponseEntity<TransporteurResponse> reactiverTransporteur(@PathVariable String id) {
        TransporteurResponse reactivated = userService.reactiverTransporteur(id);
        return ResponseEntity.ok(reactivated);
    }

    @DeleteMapping("/transporteurs/{id}")

    public ResponseEntity<Void> deleteTransporteur(@PathVariable String id) {
        userService.deleteTransporteur(id);
        return ResponseEntity.noContent().build();
    }

    // ========== Gestion des Utilisateurs ==========

    @GetMapping("/users")

    public ResponseEntity<Page<User>> getAllUsers(
            @PageableDefault(sort = "login", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> users = userService.findAllUsers(pageable);
        return ResponseEntity.ok(users);
    }
}