package com.digdes.school.entity;

public class User implements Entity{
    private Long id;
    private String lastName;
    private Double cost;
    private Long age;
    private Boolean active;

    public boolean hasNonNullFields() {
        return id != null || lastName != null || cost != null || age != null || active != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be non-negative");
        }
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        if (lastname.length() == 0) {
            throw new IllegalArgumentException("Last name must be non-empty");
        }
        this.lastName = lastname;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must be non-negative");
        }
        this.cost = cost;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be non-negative");
        }
        this.age = age;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastname='" + lastName + '\'' +
                ", cost=" + cost +
                ", age=" + age +
                ", active=" + active +
                '}';
    }
}
