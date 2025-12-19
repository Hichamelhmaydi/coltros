package com.example.coltros.DTO.filter;

import com.example.coltros.enums.Specialite;
import com.example.coltros.enums.Statut;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class TransporteurFilterDTO {
    private Specialite specialite;
    private Statut statut;
    private Boolean active;
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "login";
    private Sort.Direction direction = Sort.Direction.ASC;

    public Pageable toPageable() {
        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }
}