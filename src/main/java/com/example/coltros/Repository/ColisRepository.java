package com.example.coltros.Repository;

import com.example.coltros.entity.Colis;
import com.example.coltros.enums.TypeColis;
import com.example.coltros.enums.StatutColis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColisRepository extends MongoRepository<Colis, String> {

    @Query("{'type': ?0}")
    Page<Colis> findByType(TypeColis type, Pageable pageable);

    Page<Colis> findByStatuts(StatutColis statuts, Pageable pageable);

    @Query("{'type': ?0, 'statuts': ?1}")
    Page<Colis> findByTypeAndStatuts(TypeColis type, StatutColis statuts, Pageable pageable);

    @Query("{'transporteurId': ?0}")
    Page<Colis> findByTransporteurId(String transporteurId, Pageable pageable);

    @Query("{'transporteurId': ?0, 'statuts': ?1}")
    Page<Colis> findByTransporteurIdAndStatuts(String transporteurId, StatutColis statuts, Pageable pageable);

    @Query("{'adresse_destination': {$regex: ?0, $options: 'i'}}")
    Page<Colis> findByAdresseDestinationContaining(String adresse, Pageable pageable);

    @Query("{'transporteurId': ?0, 'adresse_destination': {$regex: ?1, $options: 'i'}}")
    Page<Colis> findByTransporteurIdAndAdresseDestinationContaining(String transporteurId, String adresse, Pageable pageable);

    List<Colis> findByTransporteurIdAndStatuts(String transporteurId, StatutColis statuts);

    Long countByTransporteurIdAndStatuts(String transporteurId, StatutColis statuts);

    @Query("{'type': 'STANDARD'}")
    Page<Colis> findStandards(Pageable pageable);

    @Query("{'type': 'FRAGILE'}")
    Page<Colis> findFragiles(Pageable pageable);

    @Query("{'type': 'FRIGO'}")
    Page<Colis> findFrigos(Pageable pageable);
}