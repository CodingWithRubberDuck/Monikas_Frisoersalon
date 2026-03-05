package org.example.monikas_frisoersalon.dal;

import org.example.monikas_frisoersalon.models.HairTreatment;

import java.util.List;

public interface TreatmentRepository {
    List<HairTreatment> getAllHairTreatments();

    List<HairTreatment> getSpecificTreatments(int bookingId);
}
