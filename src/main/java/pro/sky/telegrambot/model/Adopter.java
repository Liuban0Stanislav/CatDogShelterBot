package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "adopters")
public class Adopter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @OneToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @Column(name = "probation_start")
    private Date probationStart;

    @Column(name = "probation_end")
    private Date probationEnd;

    @Column(name = "probation_result")
    private Boolean probationResult;

    public Adopter() {
    }

    public Adopter(long id, Client client, Animal animal, Shelter shelter, Date probationStart, Date probationEnd, Boolean probationResult) {
        this.id = id;
        this.client = client;
        this.animal = animal;
        this.shelter = shelter;
        this.probationStart = probationStart;
        this.probationEnd = probationEnd;
        this.probationResult = probationResult;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Date getProbationStart() {
        return probationStart;
    }

    public void setProbationStart(Date probationStart) {
        this.probationStart = probationStart;
    }

    public Date getProbationEnd() {
        return probationEnd;
    }

    public void setProbationEnd(Date probationEnd) {
        this.probationEnd = probationEnd;
    }

    public Boolean getProbationResult() {
        return probationResult;
    }

    public void setProbationResult(Boolean probationResult) {
        this.probationResult = probationResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adopter adopter = (Adopter) o;
        return id == adopter.id && Objects.equals(client, adopter.client) && Objects.equals(animal, adopter.animal) && Objects.equals(shelter, adopter.shelter) && Objects.equals(probationStart, adopter.probationStart) && Objects.equals(probationEnd, adopter.probationEnd) && Objects.equals(probationResult, adopter.probationResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, animal, shelter, probationStart, probationEnd, probationResult);
    }

    @Override
    public String toString() {
        return "Adopter{" +
                "id=" + id +
                ", client=" + client +
                ", animal=" + animal +
                ", shelter=" + shelter +
                ", probationStart=" + probationStart +
                ", probationEnd=" + probationEnd +
                ", probationResult=" + probationResult +
                '}';
    }
}
