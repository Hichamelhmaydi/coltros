package com.example.coltros.Mapper;

import com.example.coltros.entity.Transporteur;
import com.example.coltros.DTO.Reponse.TransporteurResponse;
import com.example.coltros.DTO.Request.TransporteurRequest;
import com.example.coltros.DTO.Request.TransporteurUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransporteurMapper {

    TransporteurResponse toDTO(Transporteur transporteur);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "TRANSPORTEUR")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "statut", constant = "DISPONIBLE")
    Transporteur toEntity(TransporteurRequest dto);

    void updateEntity(TransporteurUpdateRequest dto, @MappingTarget Transporteur transporteur);
}