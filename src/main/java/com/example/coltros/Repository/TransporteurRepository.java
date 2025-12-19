package com.example.coltros.Repository;

import com.example.coltros.entity.Transporteur;
import com.example.coltros.enums.Specialite;
import com.example.coltros.enums.Statut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransporteurRepository extends MongoRepository<Transporteur, String> {

    Optional<Transporteur> findByLogin(String login);

    List<Transporteur> findBySpecialite(Specialite specialite);

    Page<Transporteur> findBySpecialite(Specialite specialite, Pageable pageable);

    Page<Transporteur> findByStatut(Statut statut, Pageable pageable);

    Page<Transporteur> findBySpecialiteAndStatut(Specialite specialite, Statut statut, Pageable pageable);

    List<Transporteur> findByActiveTrueAndSpecialite(Specialite specialite);

    @Query("{'active': true, 'specialite': ?0, 'statut': 'DISPONIBLE'}")
    List<Transporteur> findTransporteursDisponiblesBySpecialite(Specialite specialite);
}