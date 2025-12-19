package com.example.coltros.Mapper;

import com.example.coltros.entity.Colis;
import com.example.coltros.DTO.Request.ColisRequest;
import com.example.coltros.DTO.Request.ColisUpdateRequest;
import com.example.coltros.DTO.Reponse.ColisResponse;
import com.example.coltros.entity.ColisFragile;
import com.example.coltros.entity.ColisFrigo;
import com.example.coltros.entity.ColisStandard;
import org.mapstruct.*;
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ColisMapper {

    ColisResponse toDTO(Colis colis);

    default Colis toEntity(ColisRequest dto) {
        return switch (dto.getType()) {
            case STANDARD -> toStandardEntity(dto);
            case FRAGILE -> toFragileEntity(dto);
            case FRIGO -> toFrigoEntity(dto);
        };
    }

    @Mapping(target = "type", expression = "java(com.example.coltros.model.enums.TypeColis.STANDARD)")
    ColisStandard toStandardEntity(ColisRequest dto);

    @Mapping(target = "type", expression = "java(com.example.coltros.model.enums.TypeColis.FRAGILE)")
    @Mapping(target = "instructionsManutention", source = "instructionsManutention")
    ColisFragile toFragileEntity(ColisRequest dto);

    @Mapping(target = "type", expression = "java(com.example.coltros.model.enums.TypeColis.FRIGO)")
    @Mapping(target = "temperatureMin", source = "temperatureMin")
    @Mapping(target = "temperatureMax", source = "temperatureMax")
    ColisFrigo toFrigoEntity(ColisRequest dto);

    void updateEntity(ColisUpdateRequest dto, @MappingTarget Colis colis);

    @Mapping(target = "instructionsManutention", source = "instructionsManutention")
    void updateFragileEntity(ColisUpdateRequest dto, @MappingTarget ColisFragile fragile);

    @Mapping(target = "temperatureMin", source = "temperatureMin")
    @Mapping(target = "temperatureMax", source = "temperatureMax")
    void updateFrigoEntity(ColisUpdateRequest dto, @MappingTarget ColisFrigo frigo);
}