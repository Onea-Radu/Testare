package ro.unibuc.link;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.services.UrlEntityGenerator;
import ro.unibuc.link.services.UrlGenerator;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlEntityGeneratorTest {

    @Mock
    private UrlGenerator urlGenerator;
    @InjectMocks
    private UrlEntityGenerator urlEntityGenerator;

    @Test
    public void whenInvalidUrlRedirect_throw_exception() {

        String input = "www.google.com";
        String output = "12we21";

        when(urlGenerator.generateUrl()).thenReturn(output);

        UrlEntity expectedOutput = generateUrlEntity(output, input);
        UrlEntity actualOutput = urlEntityGenerator.generate(input);

        Assertions.assertEquals(expectedOutput, actualOutput);

        Mockito.verify(urlGenerator, times(1)).generateUrl();
    }

    private UrlEntity generateUrlEntity(String output, String input) {
        return new UrlEntity(output, input);
    }

}
