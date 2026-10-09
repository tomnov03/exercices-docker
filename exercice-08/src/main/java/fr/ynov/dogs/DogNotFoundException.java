package fr.ynov.dogs;

public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException(Long id) {
        super("Chien introuvable : " + id);
    }
}
