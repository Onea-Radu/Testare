package ro.unibuc.link.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;
import ro.unibuc.link.dto.UrlShowDTO;

import java.util.Optional;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlEntityGenerator urlEntityGenerator;

    public String getRedirectMapping(String url) {
        var redirectedUrl = urlRepository.findById(url)
                .map(entity -> "redirect:" + entity.getExternalUrl());
        if (redirectedUrl.isEmpty()) {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found");
        }
        return redirectedUrl.get();
    }

    public UrlShowDTO encode(String externalUrl) {
        Optional<UrlEntity> urlEntity = urlRepository.findByExternalUrl(externalUrl);
        return new UrlShowDTO(urlRepository.save(urlEntity.orElse(urlEntityGenerator.generate(externalUrl))));
    }

}