package com.yuyang.db.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yuyang on 2017/4/13.
 */
@Entity
public class User {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Date brithday;
    @Convert(converter = SexConverter.class, columnType = Integer.class)
    private Sex sex;
    private int height;

    @Generated(hash = 1539161721)
    public User(Long id, String firstName, String lastName, Date brithday, Sex sex,
            int height) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.brithday = brithday;
        this.sex = sex;
        this.height = height;
    }

    public User(String firstName, String lastName, Date brithday, Sex sex, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.brithday = brithday;
        this.sex = sex;
        this.height = height;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBrithday() {
        return this.brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public Sex getSex() {
        return this.sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public enum Sex{
        MAN(0),WOMAN(1);

        final int id;

        Sex(int id){
            this.id = id;
        }
    }

    public static class SexConverter implements PropertyConverter<Sex, Integer> {
        @Override
        public Sex convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (Sex role : Sex.values()) {
                if (role.id == databaseValue) {
                    return role;
                }
            }
            return Sex.MAN;
        }

        @Override
        public Integer convertToDatabaseValue(Sex entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }
}
