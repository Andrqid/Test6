package com.matthewz.objectboxdemo1.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Uid;

@Entity
@Uid(6077369892606433278L)
public class Fruitage {
    @Id
    private long id;

    private String type;
    @Uid(7093812212759619293L)
    private String age;
    @Uid(6917135703708256431L)
    private int cost;

    public Fruitage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Fruitage(String type, String age, int price) {
        this.type = type;
        this.age = age;
        this.cost = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Fruit{");
        sb.append("id=").append(id);
        sb.append(", type='").append(type).append('\'');
        sb.append(", age=").append(age);
        sb.append(", cost=").append(cost);
        sb.append('}');
        return sb.toString();
    }
}
