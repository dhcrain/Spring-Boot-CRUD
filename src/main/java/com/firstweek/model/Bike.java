package com.firstweek.model;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min=1, max=25, message = "Make length must be between 1 and 25 characters")
    @NotEmpty(message = "Make cannot be empty")
    private String make;
    @Size(min=1, max=25, message = "Model length must be between 1 and 25 characters")
    @NotEmpty(message = "Model cannot be empty")
    private String model;
    @Range(min=1900, max=2099, message = "Model Year must be between 1900 and 2099")
    private Integer modelYear;
    @Size(min=3, max=5, message = "You are very sneaky, Choose one of the predefined options")
    private String wheelSize;
    @Size(min=4, max=15, message = "You are very sneaky, Choose one of the predefined options")
    private String frameType;
    @Size(max=100, message = "Build Kit cannot be longer than 100 characters")
    private String buildKit;
    private LocalDateTime created;
    private LocalDateTime updated;

//    A no arg constructor is required for Hibernate
    public Bike() {
    }

    public Bike(String make, String model, Integer modelYear, String wheelSize, String frameType, String buildKit) {
        this.make = make;
        this.model = model;
        this.modelYear = modelYear;
        this.wheelSize = wheelSize;
        this.frameType = frameType;
        this.buildKit = buildKit;
        this.created = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public String getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(String wheelSize) {
        this.wheelSize = wheelSize;
    }

    public String getFrameType() {
        return frameType;
    }

    public void setFrameType(String frameType) {
        this.frameType = frameType;
    }

    public String getBuildKit() {
        return buildKit;
    }

    public void setBuildKit(String buildKit) {
        this.buildKit = buildKit;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", modelYear=" + modelYear +
                ", wheelSize=" + wheelSize +
                ", frameType='" + frameType + '\'' +
                ", buildKit='" + buildKit + '\'' +
                ", updated='" + updated + '\'' +
                ", created=" + created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bike bike = (Bike) o;

        return id == bike.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
