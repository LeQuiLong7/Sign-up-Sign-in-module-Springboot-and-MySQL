package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.exception.model.StudentNotFoundException;
import com.lql.hellospringsecurity.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PersonRepository {

    private final List<Person> list = new ArrayList<>((Arrays.asList(
            new Person(1, "long", 19),
            new Person(2, "nam", 16),
            new Person(3, "cuong", 34),
            new Person(5, "hieu", 23)
    )));
    public Person get(int id) {
        return list
                .stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("No student has the id of " + id));
    }

    public List<Person> getAll() {
        return this.list;
    }

    public Person add(Person person) {
        list.add(person);
        return person;
    }

    public boolean deleteById(Integer id) {

        return list.remove(list.stream()
                .filter(person -> id.equals(person.getId()))
                .findFirst()
                .orElseThrow(() ->new StudentNotFoundException("Not found student with the id of" + id)));

    }

    public Person replace(Integer id, Person newPerson) {
        list.set(list.indexOf(
                    list.stream()
                    .filter(person ->  id.equals(person.getId()))
                    .findFirst()
                    .orElseThrow(StudentNotFoundException::new))
                , newPerson);
        return newPerson;
    }


}
