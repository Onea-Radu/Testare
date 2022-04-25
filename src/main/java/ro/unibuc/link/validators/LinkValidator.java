package ro.unibuc.link.validators;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.dto.EncodeRequest;
import ro.unibuc.link.dto.UrlDeleteDTO;

@Component
public class LinkValidator {
    public void validate(EncodeRequest entity) {
        if (entity.getUrl() == null || entity.getUrl().length() <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Internal url must be at least over 1 character");
        }
    }
}
