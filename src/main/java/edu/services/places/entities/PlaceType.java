package edu.services.places.entities;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Serdeable
@Entity
@Table(name = "place_types")
public class PlaceType
{
    @Id
    @GeneratedValue(generator = "place_types_id_seq")
    private Long id;

    @ColumnDefault(value = "other")
    @Column(name = "title", nullable = false, unique = true)
    private String title;


    public PlaceType(Long id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public PlaceType(){}

    public Long getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }


    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        PlaceType placeType = (PlaceType) o;
        return Objects.equals(id, placeType.id) && Objects.equals(title, placeType.title);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, title);
    }
}