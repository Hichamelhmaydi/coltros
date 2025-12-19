package com.example.coltros.Mapper;

import com.example.coltros.entity.Admin;
import com.example.coltros.entity.Transporteur;
import com.example.coltros.entity.User;
import com.example.coltros.DTO.Request.TransporteurRequest;
import com.example.coltros.DTO.Request.TransporteurUpdateRequest;
import com.example.coltros.DTO.Reponse.TransporteurResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "nombreColisEnLivraison", ignore = true)
    @Mapping(target = "nombreColisLivre", ignore = true)
    @Mapping(target = "nombreColisTotal", ignore = true)
    TransporteurResponse toTransporteurResponse(Transporteur transporteur);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "TRANSPORTEUR")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "statut", constant = "DISPONIBLE")
    Transporteur toTransporteurEntity(TransporteurRequest dto);

    void updateTransporteurEntity(TransporteurUpdateRequest dto, @MappingTarget Transporteur transporteur);

    default User toUserEntity(TransporteurRequest dto) {
        return toTransporteurEntity(dto);
    }
}