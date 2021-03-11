package ru.job4j.stream;

public class Address {
    private final String city;
    private final String street;
    private final int home;
    private final int apartment;

    public Address(String city, String street, int home, int apartment) {
        this.city = city;
        this.street = street;
        this.home = home;
        this.apartment = apartment;
    }
}
