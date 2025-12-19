package com.example.coltros.Repository;

import com.example.coltros.entity.ColisStandard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardRepository extends MongoRepository<ColisStandard, String> {
}