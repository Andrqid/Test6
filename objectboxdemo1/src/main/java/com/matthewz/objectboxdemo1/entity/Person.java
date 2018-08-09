package com.matthewz.objectboxdemo1.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Person {
    @Id
    private long id;

    private String name;

    private int age;

    public ToOne<Pet> pet;

    public Person() {
    }

    public Person(String name, int age, Pet pet) {
        this.name = name;
        this.age = age;
        this.pet = new ToOne<Pet>(this, Person_.pet);
        this.pet.setTarget(pet);
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ToOne<Pet> getPet() {
        return pet;
    }

    public void setPet(ToOne<Pet> pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", pet=").append(pet.getTarget() != null ? pet.getTarget() : "null null");
        sb.append('}');
        return sb.toString();
    }
}
