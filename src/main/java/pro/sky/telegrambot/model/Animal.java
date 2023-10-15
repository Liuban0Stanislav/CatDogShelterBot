package pro.sky.telegrambot.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex")
    private String sex;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public Animal() {
    }

    public Animal(long id, String name, Integer age, String sex, Shelter shelter) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.shelter = shelter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && Objects.equals(name, animal.name) && Objects.equals(age, animal.age) && Objects.equals(sex, animal.sex) && Objects.equals(shelter, animal.shelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, sex, shelter);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", shelter=" + shelter +
                '}';
    }
}
