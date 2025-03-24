package main.java.model.person;

public abstract class Person {
    private String name;

    public Person(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public abstract String whoyouare();

    @Override
    public String toString() {
        return "Person{name='" + name + "'}";
    }
}
