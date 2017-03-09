package org.curtis.model;

import org.curtis.database.DatabaseItem;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "thing")
public class Thing extends DatabaseItem {
    @Column(name = "name")
    @NotEmpty
    private String name = "";

    @Column(name = "description")
    @NotEmpty
    private String description = "";

    public Thing() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
