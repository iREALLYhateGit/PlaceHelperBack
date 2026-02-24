package edu.services.places.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.*;

@Serdeable
@Entity
@Table(name = "places")
public class Place
{
    @Id
    @GeneratedValue(generator = "places_id_seq")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @JoinColumn(name = "type", nullable = false)
    @ManyToOne
    private PlaceType type;

    @Column(name = "address", nullable = false)
    private String address;

    @Nullable
    @Column(name = "architect")
    private String architect;

    @Column(name = "popularity_score", nullable = false)
    @Check(constraints = "popularity_score >= 1")
    @Check(constraints = "popularityScore <= 5")
    private int popularityScore;

    @Nullable
    @Column(name = "description")
    private String description;



    @JsonIgnore
    @Transient
    private static Set<Integer> allowedPopularityScores = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

    @JsonIgnore
    @Transient
    private boolean isIdSet = false;

    @JsonIgnore
    @Transient
    private boolean isTitleSet = false;

    @JsonIgnore
    @Transient
    private boolean isTypeSet = false;

    @JsonIgnore
    @Transient
    private boolean isAddressSet = false;

    @JsonIgnore
    @Transient
    private boolean isArchitectSet = false;

    @JsonIgnore
    @Transient
    private boolean isPopularityScoreSet = false;

    @JsonIgnore
    @Transient
    private boolean isDescriptionSet = false;



    public void setId(Long id)
    {
        this.id = id;
        this.isIdSet = true;
    }

    public void setTitle(String title)
    {
        this.title = title;
        this.isTitleSet = true;
    }

    public void setType(PlaceType type)
    {
        this.type = type;
        this.isTypeSet = true;
    }

    public void setAddress(String address)
    {
        this.address = address;
        this.isAddressSet = true;
    }

    public void setArchitect(@Nullable String architect)
    {
        this.architect = architect;
        this.isArchitectSet = true;
    }

    public boolean setPopularityScore(int popularityScore)
    {
        if(allowedPopularityScores.contains(popularityScore))
        {
            this.popularityScore = popularityScore;
            this.isPopularityScoreSet = true;

            return true;
        }
        else
            return false;
    }

    public void setDescription(@Nullable String description)
    {
        this.description = description;
        this.isDescriptionSet = true;
    }

    public Long getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public PlaceType getType()
    {
        return type;
    }

    public String getAddress()
    {
        return address;
    }

    @Nullable
    public String getArchitect()
    {
        return architect;
    }

    public int getPopularityScore()
    {
        return popularityScore;
    }

    @Nullable
    public String getDescription()
    {
        return description;
    }


    @JsonIgnore
    public boolean isIdSet()
    {
        return isIdSet;
    }

    @JsonIgnore
    public boolean isTitleSet()
    {
        return isTitleSet;
    }

    @JsonIgnore
    public boolean isTypeSet()
    {
        return isTypeSet;
    }

    @JsonIgnore
    public boolean isAddressSet()
    {
        return isAddressSet;
    }

    @JsonIgnore
    public boolean isArchitectSet()
    {
        return isArchitectSet;
    }

    @JsonIgnore
    public boolean isPopularityScoreSet()
    {
        return isPopularityScoreSet;
    }

    @JsonIgnore
    public boolean isDescriptionSet()
    {
        return isDescriptionSet;
    }


    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return popularityScore == place.popularityScore && Objects.equals(id, place.id) && Objects.equals(title, place.title) && Objects.equals(type, place.type) && Objects.equals(address, place.address) && Objects.equals(architect, place.architect) && Objects.equals(description, place.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, title, type, address, architect, popularityScore, description);
    }

    @Override
    public String toString()
    {
        return "Place{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", address='" + address + '\'' +
                ", architect='" + architect + '\'' +
                ", popularityScore=" + popularityScore +
                ", description='" + description + '\'' +
                ", allowedPopularityScores=" + allowedPopularityScores +
                '}';
    }
}
