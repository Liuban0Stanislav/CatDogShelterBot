package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date_report")
    private Date dateReport;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "diet")
    private String diet;

    @Column(name = "health")
    private String health;

    @Column(name = "behavior")
    private String behavior;

    public Report() {
    }

    public Report(long id, Date dateReport, Client client, Animal animal, byte[] photo, String diet, String health, String behavior) {
        this.id = id;
        this.dateReport = dateReport;
        this.client = client;
        this.animal = animal;
        this.photo = photo;
        this.diet = diet;
        this.health = health;
        this.behavior = behavior;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateReport() {
        return dateReport;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && Objects.equals(dateReport, report.dateReport) && Objects.equals(client, report.client) && Objects.equals(animal, report.animal) && Arrays.equals(photo, report.photo) && Objects.equals(diet, report.diet) && Objects.equals(health, report.health) && Objects.equals(behavior, report.behavior);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, dateReport, client, animal, diet, health, behavior);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", dateReport=" + dateReport +
                ", client=" + client +
                ", animal=" + animal +
                ", photo=" + Arrays.toString(photo) +
                ", diet='" + diet + '\'' +
                ", health='" + health + '\'' +
                ", behavior='" + behavior + '\'' +
                '}';
    }
}
