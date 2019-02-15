package com.rafalsky.dragandswipe.model;

import java.util.ArrayList;
import java.util.List;

public class PeopleStorage {
    private static PeopleStorage storage;

    private List<Person> people;

    public static PeopleStorage get() {
        if (storage == null) {
            storage = new PeopleStorage();
        }
        return storage;
    }

    public List<Person> getPeople() {
        return people;
    }

    private PeopleStorage() {
        people = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            people.add(new Person("Name " + i, i));
        }
    }
}
