package ro.unibuc.link;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;
import ro.unibuc.link.services.UrlEntityGenerator;
import ro.unibuc.link.services.UrlGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlGeneratorDriverTest {

    @Spy
    UrlGenerator urlGenerator;
    @Mock
    UrlEntityGenerator urlEntityGenerator;

    private static final String URL="https:\\www.google.com";
    private String internal;

    @Test
    void generate(){
        UrlEntity urlEntity=getValue();
        when(urlEntityGenerator.generate(any())).thenReturn(urlEntity);
        assertEquals(new UrlEntity(internal,URL),urlEntityGenerator.generate(URL));
    }

    private UrlEntity getValue() {
        internal=urlGenerator.generateUrl();
        return new UrlEntity(internal, URL);
    }

}
