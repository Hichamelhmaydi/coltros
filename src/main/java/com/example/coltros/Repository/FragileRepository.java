package com.example.coltros.Repository;

import com.example.coltros.entity.ColisFragile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragileRepository extends MongoRepository<ColisFragile, String> {
}