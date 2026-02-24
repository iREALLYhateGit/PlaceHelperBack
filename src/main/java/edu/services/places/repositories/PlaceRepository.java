package edu.services.places.repositories;

import edu.services.places.entities.Place;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> { }
