package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import pro.sky.telegrambot.constants.DocType;

import java.util.Objects;

@Entity
@Table(name = "adoption_docs")
public class AdoptionDoc {
    @Id
    private DocType id;

    private String description;

    public AdoptionDoc() {
    }

    public AdoptionDoc(DocType id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdoptionDoc that = (AdoptionDoc) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public DocType getId() {
        return id;
    }

    public void setId(DocType id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
