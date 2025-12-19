package com.example.coltros.Mapper;

import com.example.coltros.DTO.Request.ColisRequest;
import com.example.coltros.DTO.Request.ColisUpdateRequest;
import com.example.coltros.DTO.Reponse.ColisResponse;
import com.example.coltros.entity.*;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ColisMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transporteurId", ignore = true)
    @Mapping(target = "transporteurNom", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Colis toEntity(ColisRequest request);

    ColisResponse toResponse(Colis colis);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "transporteurNom", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ColisUpdateRequest request, @MappingTarget Colis colis);

    default Colis createColisFromRequest(ColisRequest request) {
        switch (request.getType()) {
            case STANDARD:
                ColisStandard standard = new ColisStandard();
                standard.setPoids(request.getPoids());
                standard.setAdresseDestination(request.getAdresseDestination());
                return standard;
            case FRAGILE:
                ColisFragile fragile = new ColisFragile();
                fragile.setPoids(request.getPoids());
                fragile.setAdresseDestination(request.getAdresseDestination());
                fragile.setInstructionsManutention(request.getInstructionsManutention());
                return fragile;
            case FRIGO:
                ColisFrigo frigo = new ColisFrigo();
                frigo.setPoids(request.getPoids());
                frigo.setAdresseDestination(request.getAdresseDestination());
                frigo.setTemperatureMin(request.getTemperatureMin());
                frigo.setTemperatureMax(request.getTemperatureMax());
                return frigo;
            default:
                throw new IllegalArgumentException("Type de colis non supporté");
        }
    }
}