package org.example.monikas_frisoersalon;

import java.lang.reflect.Constructor;
import java.util.*;

public class AppContext {

    private final Map<Class<?>, Object> singletons = new HashMap<>();

    // Registrerer manuelt et instance i listen
    public <T> void registerInstance(Class<T> type, T instance) {
        singletons.put(type, instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        Object singleton = singletons.get(type);
        if (singleton != null){
            return (T) singleton;
        }

        throw new IllegalArgumentException("Der er ikke registreret noget instance for: " + type.getName());
    }


    /**
     * 'create' metoden vil som oftest bruges af FXMLLoader controllerFactory
     *
     * 'create' metoden prøver at:
     * - Vælge den offentlige ('public') konstruktør med flest parametre
     * - Finder ud af hver parametertype via ctx.get(...) metoden
     */

    public <T> T create(Class<T> type){
        try {
            try {
                return get(type);
            } catch (IllegalArgumentException ignore){
                // ikke registreret, så der prøves med reflection-baseret instantiation
            }
            //De offentlige konstruktører
            Constructor<?>[] constructors = type.getConstructors();

            //Tjekker om der overhovedet er nogen konstruktører
            if (constructors.length == 0){
                throw new IllegalArgumentException("Ingen 'public' constructor blev fundet for: " + type.getName());
            }

            //Vælger den konstruktør som har flest parametre (Da det typisk er den man er interesseret i)
            Constructor<?> best = Arrays.stream(constructors)
                    .max(Comparator.comparingInt(Constructor::getParameterCount))
                    .orElseThrow();

            // Bygger argumenter ved at hente dem fra context
            Class<?>[] parameterTypes = best.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];

            //Lægger dem ind i listen
            for (int i = 0; i < parameterTypes.length; i++){
                args[i] = get(parameterTypes[i]);
            }

            // Opretter controlleren
            Object instance = best.newInstance(args);
            return type.cast(instance);
        } catch (Exception e) {
            throw new RuntimeException("Kunne ikke oprette instans af: " + type.getName(), e);
        }
    }

}
