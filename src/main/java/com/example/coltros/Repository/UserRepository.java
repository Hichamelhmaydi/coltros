package com.example.coltros.Repository;

import com.example.coltros.entity.User;
import com.example.coltros.enums.Role;
import com.example.coltros.enums.Specialite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLogin(String login);

    @Query("{'role': 'TRANSPORTEUR'}")
    Page<User> findTransporteurs(Pageable pageable);

    @Query("{'role': 'TRANSPORTEUR', 'specialite': ?0}")
    Page<User> findTransporteursBySpecialite(Specialite specialite, Pageable pageable);

    @Query("{'role': 'TRANSPORTEUR', 'active': ?0}")
    Page<User> findTransporteursByActive(boolean active, Pageable pageable);

    boolean existsByLogin(String login);

    @Query("{'role': 'ADMIN'}")
    Page<User> findAdmins(Pageable pageable);

    @Query("{ 'role': 'TRANSPORTEUR' }")
    Page<User> findAllTransporteurs(Pageable pageable);
}