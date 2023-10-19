package pro.sky.telegrambot.model;

import javax.persistence.*;
import pro.sky.telegrambot.constants.Color;
import pro.sky.telegrambot.constants.PetType;
import pro.sky.telegrambot.constants.Sex;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nickName;
    private PetType petType;
    private Color color;
    private Sex sex;
    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breedId;
    private byte[] picture;
    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopterId;

    @OneToMany(mappedBy = "petId")
    private Collection<AdoptionReport> adoptionReports;

    public Pet() {

    }

    public Pet(long id, String nickName, PetType petType, Color color, Sex sex) {
        this.id = id;
        this.nickName = nickName;
        this.petType = petType;
        this.color = color;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public PetType getPetType() {
        return petType;
    }

    public Color getColor() {
        return color;
    }

    public Sex getSex() {
        return sex;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Adopter getAdopterId() {
        return adopterId;
    }

    public Breed getBreedId() {
        return breedId;
    }

    public byte[] getPicture() {
        return picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
