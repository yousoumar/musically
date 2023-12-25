package fr.imt.musically.singer;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SingerService {
    private final SingerRepository repository;

    private final Validator validator;

    public SingerService(SingerRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Singer> getAllSingers() {
        return repository.findAll();
    }

    public Singer createSinger(SingerBodyRequest singer) throws IllegalArgumentException {
        if (repository.findByFirstNameAndLastName(singer.getFirstName(), singer.getLastName()) != null) {
            throw new IllegalArgumentException("This singer already exists");
        }

        Set<ConstraintViolation<SingerBodyRequest>> violations = validator.validate(singer);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<SingerBodyRequest> violation : violations) {
                sb.append(violation.getMessage());
                sb.append("\n");
            }

            throw new ConstraintViolationException(sb.toString(), violations);
        }

        return repository.save(new Singer(singer.getFirstName(), singer.getLastName()));
    }

}
