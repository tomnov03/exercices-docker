package fr.ynov.dogs;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dogs")
public class DogController {

    private final DogRepository repository;

    public DogController(DogRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Dog> list() {
        return repository.findAll();
    }

    @GetMapping("/{dogId}")
    public Dog get(@PathVariable Long dogId) {
        return repository.findById(dogId).orElseThrow(() -> new DogNotFoundException(dogId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dog create(@Valid @RequestBody Dog dog) {
        dog.setId(null);
        return repository.save(dog);
    }

    @PutMapping("/{dogId}")
    public Dog update(@PathVariable Long dogId, @Valid @RequestBody Dog dog) {
        if (!repository.existsById(dogId)) {
            throw new DogNotFoundException(dogId);
        }
        dog.setId(dogId);
        return repository.save(dog);
    }

    @DeleteMapping("/{dogId}")
    public ResponseEntity<Void> delete(@PathVariable Long dogId) {
        if (!repository.existsById(dogId)) {
            throw new DogNotFoundException(dogId);
        }
        repository.deleteById(dogId);
        return ResponseEntity.noContent().build();
    }
}
