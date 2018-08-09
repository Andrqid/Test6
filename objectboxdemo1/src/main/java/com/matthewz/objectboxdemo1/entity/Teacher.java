package com.matthewz.objectboxdemo1.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Transient;

@Entity
public class Teacher {
    @Id
    private long id;
    @Index  //数据库会针对这个字段生成索引，当对这个属性进行频繁的查询时可以大大的提高性能
    private String serverId;

    private String name;

    private int age;

    private boolean isMale;

    @NameInDb("lesson") //在数据库中的字段名
    private String course;
    @Transient  //该字段不会存储到数据库中，在声明属性时使用transient关键字也能够实现相同的效果
    private String hobby;

    public Teacher() {
    }

    public Teacher(String serverId, String name, int age, boolean isMale, String course, String hobby) {
        this.serverId = serverId;
        this.name = name;
        this.age = age;
        this.isMale = isMale;
        this.course = course;
        this.hobby = hobby;
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

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Teacher{");
        sb.append("id=").append(id);
        sb.append(", serverId='").append(serverId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append(", isMale=").append(isMale);
        sb.append(", course='").append(course).append('\'');
        sb.append(", hobby='").append(hobby).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
