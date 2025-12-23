package com.example.coltros.Service.impl;

import com.example.coltros.DTO.filter.ColisFilterDTO;
import com.example.coltros.DTO.Request.ColisRequest;
import com.example.coltros.DTO.Request.ColisUpdateRequest;
import com.example.coltros.DTO.Reponse.ColisResponse;
import com.example.coltros.Exception.ResourceNotFoundException;
import com.example.coltros.Exception.UnauthorizedException;
import com.example.coltros.Exception.ValidationException;
import com.example.coltros.Mapper.ColisMapper;
import com.example.coltros.entity.*;
import com.example.coltros.entity.Colis.*;
import com.example.coltros.enums.*;
import com.example.coltros.Repository.ColisRepository;
import com.example.coltros.Repository.TransporteurRepository;
import com.example.coltros.Service.ColisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColisServiceImpl implements ColisService {

    private final ColisRepository colisRepository;
    private final TransporteurRepository transporteurRepository;
    private final ColisMapper colisMapper;

    @Override
    public Page<ColisResponse> findAllColis(ColisFilterDTO filter) {
        Pageable pageable = filter.toPageable();

        if (filter.getType() != null && filter.getStatut() != null) {
            return colisRepository.findByTypeAndStatuts(filter.getType(), filter.getStatut(), pageable)
                    .map(colisMapper::toDTO);
        } else if (filter.getType() != null) {
            return colisRepository.findByType(filter.getType(), pageable)
                    .map(colisMapper::toDTO);
        } else if (filter.getStatut() != null) {
            return colisRepository.findByStatuts(filter.getStatut(), pageable)
                    .map(colisMapper::toDTO);
        } else if (filter.getTransporteurId() != null) {
            return colisRepository.findByTransporteurId(filter.getTransporteurId(), pageable)
                    .map(colisMapper::toDTO);
        }

        return colisRepository.findAll(pageable)
                .map(colisMapper::toDTO);
    }

    @Override
    public Page<ColisResponse> findColisByTransporteur(String transporteurId, Pageable pageable) {
        return colisRepository.findByTransporteurId(transporteurId, pageable)
                .map(colisMapper::toDTO);
    }

    @Override
    public Page<ColisResponse> searchByAdresse(String adresse, Pageable pageable) {
        return colisRepository.findByAdresseDestinationContaining(adresse, pageable)
                .map(colisMapper::toDTO);
    }

    @Override
    public Page<ColisResponse> searchByAdresseAndTransporteur(String transporteurId, String adresse, Pageable pageable) {
        return colisRepository.findByTransporteurIdAndAdresseDestinationContaining(transporteurId, adresse, pageable)
                .map(colisMapper::toDTO);
    }

    @Override
    public ColisResponse findColisById(String id) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));
        return colisMapper.toDTO(colis);
    }

    @Override
    @Transactional
    public ColisResponse createColis(ColisRequest dto) {
        Colis colis = createSpecificColis(dto);

        // Validation des champs spécifiques
        validateColisFields(colis);

        Colis saved = colisRepository.save(colis);
        return colisMapper.toDTO(saved);
    }

    private Colis createSpecificColis(ColisRequest dto) {
        switch (dto.getType()) {
            case STANDARD:
                ColisStandard standard = new ColisStandard(dto.getPoids(), dto.getAdresseDestination());
                standard.setType(TypeColis.STANDARD);
                return standard;
            case FRAGILE:
                ColisFragile fragile = new ColisFragile(dto.getPoids(), dto.getAdresseDestination(), dto.getInstructionsManutention());
                fragile.setType(TypeColis.FRAGILE);
                return fragile;
            case FRIGO:
                ColisFrigo frigo = new ColisFrigo(dto.getPoids(), dto.getAdresseDestination(),
                        dto.getTemperatureMin(), dto.getTemperatureMax());
                frigo.setType(TypeColis.FRIGO);
                return frigo;
            default:
                throw new ValidationException("Type de colis non supporté");
        }
    }

    @Override
    @Transactional
    public ColisResponse updateColis(String id, ColisUpdateRequest dto) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        colisMapper.updateEntity(dto, colis);

        // Mise à jour des champs spécifiques
        if (colis instanceof ColisFragile && dto.getInstructionsManutention() != null) {
            ((ColisFragile) colis).setInstructionsManutention(dto.getInstructionsManutention());
        }

        if (colis instanceof ColisFrigo) {
            if (dto.getTemperatureMin() != null) {
                ((ColisFrigo) colis).setTemperatureMin(dto.getTemperatureMin());
            }
            if (dto.getTemperatureMax() != null) {
                ((ColisFrigo) colis).setTemperatureMax(dto.getTemperatureMax());
            }
        }

        // Validation des champs spécifiques
        validateColisFields(colis);

        // Si on assigne un transporteur, valider la spécialité
        if (dto.getTransporteurId() != null) {
            validateTransporteurForColis(dto.getTransporteurId(), colis.getType());
        }

        Colis updated = colisRepository.save(colis);
        return colisMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void deleteColis(String id) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        // Vérifier si le colis est en cours de livraison
        if (colis.getStatuts() == StatutColis.EN_TRANSIT) {
            throw new ValidationException(
                    "Impossible de supprimer un colis en cours de livraison"
            );
        }

        colisRepository.delete(colis);
    }

    @Override
    @Transactional
    public ColisResponse assignerColis(String colisId, String transporteurId) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        Transporteur transporteur = transporteurRepository.findById(transporteurId)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

        if (!transporteur.isActive()) {
            throw new ValidationException("Le transporteur n'est pas actif");
        }

        // Vérifier la spécialité
        validateTransporteurForColis(transporteurId, colis.getType());

        // Vérifier que le transporteur est disponible
        if (transporteur.getStatut() != Statut.DISPONIBLE) {
            throw new ValidationException("Le transporteur n'est pas disponible");
        }

        // Vérifier que le colis est en attente
        if (colis.getStatuts() != StatutColis.EN_ATTENTE) {
            throw new ValidationException("Le colis n'est pas en attente d'assignation");
        }

        colis.setTransporteurId(transporteurId);
        colis.setTransporteurNom(transporteur.getLogin());
        colis.setStatuts(StatutColis.EN_TRANSIT);

        // Mettre à jour le statut du transporteur
        transporteur.setStatut(Statut.EN_LIVRAISON);
        transporteurRepository.save(transporteur);

        Colis updated = colisRepository.save(colis);
        return colisMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public ColisResponse updateStatutColis(String colisId, StatutColis statut, String transporteurId) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        // Vérifier les autorisations
        if (transporteurId != null && !transporteurId.equals(colis.getTransporteurId())) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier ce colis");
        }

        colis.setStatuts(statut);

        // Si le colis est livré ou annulé, libérer le transporteur
        if (statut == StatutColis.LIVRE || statut == StatutColis.ANNULE) {
            if (colis.getTransporteurId() != null) {
                Transporteur transporteur = transporteurRepository.findById(colis.getTransporteurId())
                        .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

                // Vérifier si le transporteur a d'autres colis en cours
                Long autresColis = colisRepository.countByTransporteurIdAndStatuts(
                        transporteur.getId(), StatutColis.EN_TRANSIT);

                if (autresColis <= 1) { // Ce colis est le seul en cours
                    transporteur.setStatut(Statut.DISPONIBLE);
                    transporteurRepository.save(transporteur);
                }
            }
        }

        Colis updated = colisRepository.save(colis);
        return colisMapper.toDTO(updated);
    }

    private void validateColisFields(Colis colis) {
        if (colis instanceof ColisFragile) {
            ColisFragile fragile = (ColisFragile) colis;
            if (fragile.getInstructionsManutention() == null || fragile.getInstructionsManutention().isEmpty()) {
                throw new ValidationException(
                        "Les instructions de manutention sont obligatoires pour les colis fragiles"
                );
            }
        }

        if (colis instanceof ColisFrigo) {
            ColisFrigo frigo = (ColisFrigo) colis;
            if (frigo.getTemperatureMin() == null || frigo.getTemperatureMax() == null) {
                throw new ValidationException(
                        "Les températures min et max sont obligatoires pour les colis frigorifiques"
                );
            }

            if (frigo.getTemperatureMin() >= frigo.getTemperatureMax()) {
                throw new ValidationException(
                        "La température minimale doit être inférieure à la température maximale"
                );
            }
        }
    }

    private void validateTransporteurForColis(String transporteurId, TypeColis typeColis) {
        Transporteur transporteur = transporteurRepository.findById(transporteurId)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

        // Vérifier la spécialité
        if (!transporteur.getSpecialite().name().equals(typeColis.name())) {
            throw new ValidationException(
                    "Le transporteur n'a pas la spécialité requise pour ce type de colis"
            );
        }
    }
}