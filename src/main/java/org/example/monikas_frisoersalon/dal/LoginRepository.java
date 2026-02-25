package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.models.Person;

import java.util.Optional;

public interface LoginRepository {
    Optional<Person> getPasswordHash(String email);
}
