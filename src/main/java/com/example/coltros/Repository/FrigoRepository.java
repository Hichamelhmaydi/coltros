package com.example.coltros.Repository;

import com.example.coltros.entity.ColisFrigo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrigoRepository extends MongoRepository<ColisFrigo, String> {

}
