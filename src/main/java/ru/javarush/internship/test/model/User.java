package ru.javarush.internship.test.model;

import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Range;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@FilterDefs({
    @FilterDef(name = "byName",
        parameters = @ParamDef(name = "name", type = "string")),
    @FilterDef(name = "byAge",
        parameters = @ParamDef(name = "age", type = "integer")),
    @FilterDef(name = "byAdmin",
        parameters = @ParamDef(name = "admin", type = "boolean"))
})
@Filters({
    @Filter(name = "byName", condition = "name like :name"),
    @Filter(name = "byAge", condition = "age = :age"),
    @Filter(name = "byAdmin", condition = "isAdmin = :admin")
})
public class User {

    public static final int MAX_NAME_LENGTH = 255;
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 255;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @Size(min = 1, max = MAX_NAME_LENGTH)
    @NotNull
    private String name;

    @Column(name = "age")
    @NotNull
    @Range(min = MIN_AGE, max = MAX_AGE)
    private int age;

    @Column(name = "isAdmin")
    @NotNull
    private boolean isAdmin;

    @Column(name = "createdDate")
    @UpdateTimestamp
    private Date createdDate;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isAdmin=" + isAdmin +
                ", createdDate=" + createdDate +
                '}';
    }

}
