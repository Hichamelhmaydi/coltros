package com.example.coltros.Service;

import com.example.coltros.DTO.filter.ColisFilterDTO;
import com.example.coltros.DTO.Request.ColisRequest;
import com.example.coltros.DTO.Request.ColisUpdateRequest;
import com.example.coltros.DTO.Reponse.ColisResponse;
import com.example.coltros.enums.StatutColis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ColisService {

    Page<ColisResponse> findAllColis(ColisFilterDTO filter);

    Page<ColisResponse> findColisByTransporteur(String transporteurId, Pageable pageable);

    Page<ColisResponse> searchByAdresse(String adresse, Pageable pageable);

    Page<ColisResponse> searchByAdresseAndTransporteur(String transporteurId, String adresse, Pageable pageable);

    ColisResponse findColisById(String id);

    ColisResponse createColis(ColisRequest dto);

    ColisResponse updateColis(String id, ColisUpdateRequest dto);

    void deleteColis(String id);

    ColisResponse assignerColis(String colisId, String transporteurId);

    ColisResponse updateStatutColis(String colisId, StatutColis statut, String transporteurId);
}