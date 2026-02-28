package org.example.monikas_frisoersalon.logic;

import org.example.monikas_frisoersalon.dal.LoginRepository;
import org.example.monikas_frisoersalon.models.Person;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class LoginService {
    private final LoginRepository loginRepo;

    public LoginService(LoginRepository loginRepo){
        this.loginRepo = loginRepo;
    }


    public boolean manageLogin(String email, String password){
        if (email.isBlank() || password.isBlank()){
            throw new IllegalArgumentException("Både email og kodeord skal udfyldes");
        }
        Person testedPerson;
        Optional<Person> person = loginRepo.getPasswordHash(email);
        if (person.isPresent()){
            testedPerson = person.get();
        } else {
            return false;
        }
        return validatePassword(password, testedPerson.getPassword());
    }


    private boolean validatePassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }


}
