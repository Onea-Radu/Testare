package ro.unibuc.link.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface UrlRepository extends MongoRepository<UrlEntity, String> {

    Optional<UrlEntity> findByExternalUrl(String externalUrl);
}