package com.example.coltros.DTO.filter;

import com.example.coltros.enums.TypeColis;
import com.example.coltros.enums.StatutColis;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class ColisFilterDTO {
    private TypeColis type;
    private StatutColis statut;
    private String adresseDestination;
    private String transporteurId;
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "createdAt";
    private Sort.Direction direction = Sort.Direction.DESC;

    public Pageable toPageable() {
        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }
}