package ro.unibuc.link.services;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UrlGenerator {
    public String generateUrl() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
