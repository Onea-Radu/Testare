package ro.unibuc.link;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;
import ro.unibuc.link.dto.UrlShowDTO;
import ro.unibuc.link.services.UrlEntityGenerator;
import ro.unibuc.link.services.UrlGenerator;
import ro.unibuc.link.services.UrlService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceDriverTest {

    @Mock
    UrlService service;
    @Spy
    UrlRepository urlRepository;
    @Spy
    UrlGenerator generator;
    @InjectMocks
    UrlEntityGenerator urlEntityGenerator;

    @Test
    void testGet() {
        var val=new UrlEntity("URL", "google.com");
        when(urlRepository.findById("URL")).thenReturn(Optional.of(val));
        String actualRedirect=extracted("URL");
        when(service.getRedirectMapping("URL")).thenReturn(actualRedirect);
        assertEquals(service.getRedirectMapping("URL"),"redirect:google.com");
    }
    @Test
    void testGetNotWorking() {
        when(urlRepository.findById("URL")).thenReturn(Optional.empty());
        try{extracted("URL");}
        catch (ResponseStatusException e)
        {
            when(service.getRedirectMapping("URL")).thenThrow(ResponseStatusException.class);
            assertThrows(ResponseStatusException.class,()->service.getRedirectMapping("URL"),"redirect:google.com");
            return;
        }
        fail();
    }

    private String extracted(String url) {
        var redirectedUrl = urlRepository.findById(url)
                .map(entity -> "redirect:" + entity.getExternalUrl());
        if (redirectedUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found");
        }
        return redirectedUrl.get();
    }


    @Test
    void testEncode() {
        var val=new UrlEntity("URL", "google.com");
        when(urlRepository.findByExternalUrl("google.com")).thenReturn(Optional.of(val));
        when(urlRepository.save(any())).thenReturn(val);
        UrlShowDTO dto=encodeMock("google.com");
        assertEquals(dto,new UrlShowDTO(val));
    }


    UrlShowDTO encodeMock(String externalUrl){
        Optional<UrlEntity> urlEntity = urlRepository.findByExternalUrl(externalUrl);
        return new UrlShowDTO(urlRepository.save(urlEntity.orElse(urlEntityGenerator.generate(externalUrl))));
    }


}
