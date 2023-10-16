package pro.sky.telegrambot.model;

import com.pengrad.telegrambot.model.Message;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "telegram_id")
    private long telegramId;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "client_choice")
    private boolean clientСhoice;

    public Client() {
    }

    public Client(long id, String firstName, String lastName, long telegramId, long chatId, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telegramId = telegramId;
        this.chatId = chatId;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isClientСhoice() {
        return clientСhoice;
    }

    public void setClientСhoice(boolean clientСhoice) {
        this.clientСhoice = clientСhoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && telegramId == client.telegramId && chatId == client.chatId && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(phone, client.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, telegramId, chatId, phone);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telegramId=" + telegramId +
                ", chatId=" + chatId +
                ", phone='" + phone + '\'' +
                '}';
    }
}
