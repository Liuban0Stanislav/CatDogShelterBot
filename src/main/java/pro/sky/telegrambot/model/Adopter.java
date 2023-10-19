package pro.sky.telegrambot.model;

import javax.persistence.*;
import pro.sky.telegrambot.constants.AdopterStatus;
import pro.sky.telegrambot.constants.UpdateStatus;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "adopters")
public class Adopter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String passport;
    private int age;
    private String phone1;
    private String phone2;

    @Column(name = "chat_id")
    private long chatId;

    private String username;
    private int volunteerId;
    private AdopterStatus status;

    @Column(name = "update_status")
    private UpdateStatus updateStatus;

    @Column(name = "prob_extend")
    private Integer probExtend;

    @OneToMany(mappedBy = "adopterId")
    private Collection<Pet> pets;

    public Adopter(long id, String firstName, String lastName, String phone1, long chatId, String username, int probExtend) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone1 = phone1;
        this.chatId = chatId;
        this.username = username;
        this.probExtend = probExtend;
    }

    public Adopter(String firstName, String lastName, String phone1, long chatId, String username, int probExtend) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone1 = phone1;
        this.chatId = chatId;
        this.username = username;
        this.probExtend = probExtend;
    }

    public Adopter() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adopter)) return false;
        Adopter adopter = (Adopter) o;
        return getId().equals(adopter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public AdopterStatus getStatus() {
        return status;
    }

    public void setStatus(AdopterStatus status) {
        this.status = status;
    }

    public long getChatId() {
        return chatId;
    }

    public String getUsername() {
        return username;
    }

    public UpdateStatus getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getProbExtend() {
        return probExtend;
    }

    public void setProbExtend(Integer probExtend) {
        this.probExtend = probExtend;
    }
}
