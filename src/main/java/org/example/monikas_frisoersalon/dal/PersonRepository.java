package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.models.Customer;
import org.example.monikas_frisoersalon.models.Hairdresser;
import org.example.monikas_frisoersalon.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<Hairdresser> showAllHairdressers();

    Optional<Customer> existsInCustomer(String name, String phoneNumber);

    int addPerson(String name, String phoneNumber);

    int addCustomer(int personId);
}
