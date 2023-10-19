package pro.sky.telegrambot.model;

import javax.persistence.*;
import pro.sky.telegrambot.constants.PetType;

import java.util.Objects;

@Entity
public class BranchParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String country;
    private String city;
    private String zip;
    private String address;
    private String workHours;
    private byte[] map;
    private String info;
    @Column(name = "prob_period")
    private int probPeriod;

    @Column(name = "security_info")
    private String securityInfo;

    @Column(name = "security_contact")
    private String securityContact;

    @Column(name = "pet_type")
    private PetType petType;
    public BranchParams(){

    }

    public BranchParams(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkHours() {
        return workHours;
    }

    public byte[] getMap() {
        return map;
    }

    public String getInfo() {
        return info;
    }

    public int getProbPeriod() {
        return probPeriod;
    }

    public void setProbPeriod(int probPeriod) {
        this.probPeriod = probPeriod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecurityInfo() {
        return securityInfo;
    }

    public String getSecurityContact() {
        return securityContact;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSecurityInfo(String securityInfo) {
        this.securityInfo = securityInfo;
    }

    public void setSecurityContact(String securityContact) {
        this.securityContact = securityContact;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchParams that = (BranchParams) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
