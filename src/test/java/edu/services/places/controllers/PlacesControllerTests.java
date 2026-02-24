package edu.services.places.controllers;

import edu.services.places.entities.Place;
import edu.services.places.repositories.PlaceRepository;
import edu.services.places.entities.PlaceType;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PlacesControllerTests
{
    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceController controller;

    final static List<Place> places = new ArrayList<>();

    @BeforeAll
    static void setUp()
    {
        final PlaceType typeCathedral = new PlaceType(1L, "cathedral");
        final PlaceType typeMuseum = new PlaceType(2L, "museum");

        final Place place1 = new Place();
        place1.setId(1L);
        place1.setTitle("Isaac cathedral");
        place1.setType(typeCathedral);
        place1.setAddress("Nevsky prospekt");
        place1.setArchitect("Stratchattelli");
        place1.setPopularityScore(5);
        place1.setDescription(null);


        final Place place2 = new Place();
        place2.setId(2L);
        place2.setTitle("Faberzhe");
        place2.setType(typeMuseum);
        place2.setAddress("Nevsky prospekt");
        place2.setArchitect(null);
        place2.setPopularityScore(4);
        place2.setDescription("Museum of eggs");

        places.add(place1);
        places.add(place2);
    }


    @Test
    void testGetAllPlacesWithOkResponse()
    {
        Mockito.when(placeRepository.findAll()).thenReturn(places);

        final HttpResponse<List<Place>> response = controller.getPlaces();

        Mockito.verify(placeRepository, Mockito.only()).findAll();
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertEquals(places, response.body());
    }


    @Test
    void testCreateNewPlace()
    {
        final Place newPlace = new Place();
        newPlace.setId(3L);
        newPlace.setType(new PlaceType(3L, "park"));
        newPlace.setAddress("Some street");
        newPlace.setArchitect(null);
        newPlace.setPopularityScore(4);
        newPlace.setDescription("Park");

        Mockito.when(placeRepository.save(newPlace)).thenReturn(newPlace);

        final HttpResponse<Place> response = controller.createPlace(newPlace);

        Mockito.verify(placeRepository, Mockito.only()).save(newPlace);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
        Assertions.assertEquals(newPlace, response.body());
    }


    @Test
    void testUpdatePlaceWithOkResponse()
    {
        final long intendedPlaceId = 2L;

        final Optional<Place> intendedPlace = places.stream()
                .filter(place -> place.getId() == intendedPlaceId).findFirst();

        final Place updatedPlace = new Place();
        updatedPlace.setId(4L);
        updatedPlace.setArchitect("Bellini");
        updatedPlace.setPopularityScore(7);
        updatedPlace.setDescription(null);


        final Place expectedResultPlace = new Place();
        expectedResultPlace.setId(2L);
        expectedResultPlace.setTitle("Faberzhe");
        expectedResultPlace.setType(new PlaceType(2L, "museum"));
        expectedResultPlace.setAddress("Nevsky prospekt");
        expectedResultPlace.setArchitect("Bellini");
        expectedResultPlace.setPopularityScore(4);
        expectedResultPlace.setDescription(null);


        Mockito.when(placeRepository.findById(intendedPlaceId)).thenReturn(intendedPlace);
        Mockito.when(placeRepository.update(Mockito.any())).thenReturn(Mockito.any());

        final HttpResponse<Place> response = controller.updatePlace(intendedPlaceId, updatedPlace);

        Mockito.verify(placeRepository, Mockito.times(1)).findById(intendedPlaceId);
        Mockito.verify(placeRepository, Mockito.times(1)).update(Mockito.any());
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertEquals(expectedResultPlace, response.body());
    }


    @Test
    void testUpdatePlaceWithNotFoundResponse()
    {
        final long intendedPlaceId = 4L;

        final Optional<Place> intendedPlace = places.stream()
                .filter(place -> place.getId() == intendedPlaceId).findFirst();

        final Place updatedPlace = new Place();
        updatedPlace.setId(4L);
        updatedPlace.setArchitect("Bellini");
        updatedPlace.setPopularityScore(7);
        updatedPlace.setDescription(null);

        Mockito.when(placeRepository.findById(intendedPlaceId)).thenReturn(intendedPlace);

        final HttpResponse<Place> response = controller.updatePlace(intendedPlaceId, updatedPlace);

        Mockito.verify(placeRepository, Mockito.only()).findById(intendedPlaceId);
        Mockito.verify(placeRepository, Mockito.never()).update(Mockito.any());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        Assertions.assertNull(response.body());
    }


    @Test
    void testDeletePlaceWithOkResponse()
    {
        final long intendedPlaceId = 2L;

        final Optional<Place> placeToDelete = places.stream()
                .filter(place -> place.getId() == intendedPlaceId).findFirst();

        Mockito.when(placeRepository.findById(intendedPlaceId)).thenReturn(placeToDelete);
        Mockito.doNothing().when(placeRepository).deleteById(intendedPlaceId);

        final HttpResponse<Place> response = controller.deletePlace(intendedPlaceId);

        Mockito.verify(placeRepository, Mockito.times(1)).findById(intendedPlaceId);
        Mockito.verify(placeRepository, Mockito.times(1)).deleteById(intendedPlaceId);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertEquals(placeToDelete.get(), response.body());
    }

    @Test
    void testDeletePlaceWithNotFoundResponse()
    {
        final long intendedPlaceId = 4L;

        final Optional<Place> placeToDelete = places.stream()
                .filter(place -> place.getId() == intendedPlaceId).findFirst();

        Mockito.when(placeRepository.findById(intendedPlaceId)).thenReturn(placeToDelete);

        final HttpResponse<Place> response = controller.deletePlace(intendedPlaceId);

        Mockito.verify(placeRepository, Mockito.only()).findById(intendedPlaceId);
        Mockito.verify(placeRepository, Mockito.never()).deleteById(intendedPlaceId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        Assertions.assertNull(response.body());
    }


    @Test
    void testGetPlaceWithOkResponse()
    {
        final long intendedPlaceId = 2L;

        final Optional<Place> placeToRetrieve = places.stream()
                .filter(place -> place.getId() == intendedPlaceId).findFirst();

        Mockito.when(placeRepository.findById(intendedPlaceId)).thenReturn(placeToRetrieve);

        final HttpResponse<Place> response = controller.getPlace(intendedPlaceId);

        Mockito.verify(placeRepository, Mockito.times(1)).findById(intendedPlaceId);
        Assertions.assertEquals(HttpStatus.OK, response.getStatus());
        Assertions.assertEquals(placeToRetrieve.get(), response.body());
    }

    @Test
    void testGetPlaceWithNotFoundResponse()
    {
        final long intendedPlaceId = 4L;

        final Optional<Place> placeToRetrieve = places.stream()
                .filter(place -> place.getId() == intendedPlaceId).findFirst();

        Mockito.when(placeRepository.findById(intendedPlaceId)).thenReturn(placeToRetrieve);

        final HttpResponse<Place> response = controller.getPlace(intendedPlaceId);

        Mockito.verify(placeRepository, Mockito.only()).findById(intendedPlaceId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        Assertions.assertNull(response.body());
    }
}
