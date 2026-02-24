package edu.services.places.controllers;

import edu.services.places.entities.Place;
import edu.services.places.repositories.PlaceRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.server.cors.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@Controller
public class PlaceController
{
    private final PlaceRepository placeRepository;

    PlaceController(PlaceRepository placeRepository)
    {
        this.placeRepository = placeRepository;
    }

    @Get("/places")
    HttpResponse<List<Place>> getPlaces()
    {
        return HttpResponse.ok(placeRepository.findAll());
    }

    @Get("/places/{id}")
    HttpResponse getPlace(@PathVariable long id)
    {
        final Optional<Place> place = placeRepository.findById(id);

        if(place.isEmpty())
            return HttpResponse.notFound();

        return HttpResponse.ok(place.get());
    }

    @Post("/places")
    HttpResponse<Place> createPlace(@Body Place newPlace)
    {
        placeRepository.save(newPlace);
        return HttpResponse.created(newPlace);
    }

    @Put("/places/{id}")
    HttpResponse<Place> updatePlace(@PathVariable long id, @Body Place updatedPlace)
    {
        final Optional<Place> placeBefore = placeRepository.findById(id);

        if(placeBefore.isEmpty())
            return HttpResponse.notFound();

        if(updatedPlace.isTitleSet())
            placeBefore.get().setTitle(updatedPlace.getTitle());
        if(updatedPlace.isTypeSet())
            placeBefore.get().setType(updatedPlace.getType());
        if(updatedPlace.isAddressSet())
            placeBefore.get().setAddress(updatedPlace.getAddress());
        if(updatedPlace.isArchitectSet())
            placeBefore.get().setArchitect(updatedPlace.getArchitect());
        if(updatedPlace.isPopularityScoreSet())
            placeBefore.get().setPopularityScore(updatedPlace.getPopularityScore());
        if(updatedPlace.isDescriptionSet())
            placeBefore.get().setDescription(updatedPlace.getDescription());

        placeRepository.update(placeBefore.get());

        return HttpResponse.ok(placeBefore.get());
    }

    @Delete("/places/{id}")
    HttpResponse<Place> deletePlace(@PathVariable long id)
    {
        final Optional<Place> placeToDelete = placeRepository.findById(id);

        if(placeToDelete.isEmpty())
            return HttpResponse.notFound();

        placeRepository.deleteById(id);

        return HttpResponse.ok(placeToDelete.get());
    }
}