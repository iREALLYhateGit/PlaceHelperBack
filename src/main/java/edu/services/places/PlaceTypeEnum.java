package edu.services.places;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum PlaceTypeEnum
{
    CATHEDRAL("cathedral"),
    CHURCH("church"),
    MUSEUM("museum"),
    THEATRE("theatre"),
    PARK("park"),
    GARDEN("garden"),
    PALACE("palace"),
    BRIDGE("bridge"),
    MONUMENT("monument"),
    EMBANKMENT("embankment"),
    VIEWPOINT("viewpoint"),
    STREET("street"),
    CAFE("cafe"),
    RESTAURANT("restaurant"),
    BAR("bar"),
    OTHER("other");

    private final String value;

    PlaceTypeEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}