package pro.sky.telegrambot.model;

import javax.persistence.*;
import pro.sky.telegrambot.constants.PetType;

import java.util.Collection;
import java.util.Objects;


@Entity
@Table(name = "breeds")
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pet_type")
    private PetType petType;

    private String name;

    @OneToMany(mappedBy = "breedId")
    private Collection<Pet> pets;

    public Breed(){

    }

    public Breed(long id, PetType petType, String name) {
        this.id = id;
        this.petType = petType;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public PetType getPetType() {
        return petType;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        return id == breed.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
