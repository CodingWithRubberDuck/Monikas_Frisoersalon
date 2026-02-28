package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.models.Hairdresser;
import java.util.List;

public interface PersonRepository {
    List<Hairdresser> showAllHairdressers();
}
