package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "shelters")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "about")
    private String about;
    @Column(name = "address")
    private String address;

    @Column(name = "route_map")
    private byte[] routeMap;

    @Column(name = "work_time")
    private String workTime;

    @Column(name = "territory_pass")
    private String territoryPass;

    @Column(name = "security_phone")
    private String securityPhone;

    @Column(name = "inside_restrictions")
    private String insideRestrictions;

    @Column(name = "safety_rules")
    private String safety_rules;

    @Column(name = "animals_advice")
    private String animalsAdvice;

    public Shelter() {
    }

    public Shelter(long id, String about, String address, byte[] routeMap, String workTime, String territoryPass, String securityPhone, String insideRestrictions, String safety_rules, String animalsAdvice) {
        this.id = id;
        this.about = about;
        this.address = address;
        this.routeMap = routeMap;
        this.workTime = workTime;
        this.territoryPass = territoryPass;
        this.securityPhone = securityPhone;
        this.insideRestrictions = insideRestrictions;
        this.safety_rules = safety_rules;
        this.animalsAdvice = animalsAdvice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getRouteMap() {
        return routeMap;
    }

    public void setRouteMap(byte[] routeMap) {
        this.routeMap = routeMap;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getTerritoryPass() {
        return territoryPass;
    }

    public void setTerritoryPass(String territoryPass) {
        this.territoryPass = territoryPass;
    }

    public String getSecurityPhone() {
        return securityPhone;
    }

    public void setSecurityPhone(String securityPhone) {
        this.securityPhone = securityPhone;
    }

    public String getInsideRestrictions() {
        return insideRestrictions;
    }

    public void setInsideRestrictions(String insideRestrictions) {
        this.insideRestrictions = insideRestrictions;
    }

    public String getSafety_rules() {
        return safety_rules;
    }

    public void setSafety_rules(String safety_rules) {
        this.safety_rules = safety_rules;
    }

    public String getAnimalsAdvice() {
        return animalsAdvice;
    }

    public void setAnimalsAdvice(String animalsAdvice) {
        this.animalsAdvice = animalsAdvice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return id == shelter.id && Objects.equals(about, shelter.about) && Objects.equals(address, shelter.address) && Arrays.equals(routeMap, shelter.routeMap) && Objects.equals(workTime, shelter.workTime) && Objects.equals(territoryPass, shelter.territoryPass) && Objects.equals(securityPhone, shelter.securityPhone) && Objects.equals(insideRestrictions, shelter.insideRestrictions) && Objects.equals(safety_rules, shelter.safety_rules) && Objects.equals(animalsAdvice, shelter.animalsAdvice);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, about, address, workTime, territoryPass, securityPhone, insideRestrictions, safety_rules, animalsAdvice);
        result = 31 * result + Arrays.hashCode(routeMap);
        return result;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", about='" + about + '\'' +
                ", address='" + address + '\'' +
                ", routeMap=" + Arrays.toString(routeMap) +
                ", workTime='" + workTime + '\'' +
                ", territoryPass='" + territoryPass + '\'' +
                ", securityPhone='" + securityPhone + '\'' +
                ", insideRestrictions='" + insideRestrictions + '\'' +
                ", safety_rules='" + safety_rules + '\'' +
                ", animalsAdvice='" + animalsAdvice + '\'' +
                '}';
    }
}
