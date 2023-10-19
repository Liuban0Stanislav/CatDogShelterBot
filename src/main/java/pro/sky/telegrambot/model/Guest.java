package pro.sky.telegrambot.model;


import javax.persistence.*;
import pro.sky.telegrambot.constants.PetType;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "chat_id")
    private long chatId;
    private String username;
    private Timestamp lastVisit;
    private PetType lastMenu;

    public Guest() {

    }

    public Guest(long chatId, Timestamp lastVisit, PetType lastMenu) {
        this.chatId = chatId;
        this.lastVisit = lastVisit;
        this.lastMenu = lastMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return getId() == guest.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String telegramId) {
        this.username = telegramId;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    public PetType getLastMenu() {
        return lastMenu;
    }

    public void setLastMenu(PetType lastMenu) {
        this.lastMenu = lastMenu;
    }
}
