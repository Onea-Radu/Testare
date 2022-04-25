package ro.unibuc.link.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.link.data.UrlEntity;

@Component
public class UrlEntityGenerator {
    @Autowired
    private UrlGenerator urlGenerator;

    public UrlEntity generate(String externalUrl) {
        return new UrlEntity(urlGenerator.generateUrl(), externalUrl);
    }
}
