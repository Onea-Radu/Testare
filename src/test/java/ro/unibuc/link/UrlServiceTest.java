package ro.unibuc.link;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.data.UrlEntity;
import ro.unibuc.link.data.UrlRepository;
import ro.unibuc.link.dto.UrlShowDTO;
import ro.unibuc.link.services.UrlEntityGenerator;
import ro.unibuc.link.services.UrlService;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    private static final Class<? extends Throwable> EXCEPTION = ResponseStatusException.class;

    @Mock
    private UrlRepository urlRepository;
    @Mock
    private UrlEntityGenerator urlEntityGenerator;
    @InjectMocks
    private UrlService urlService;


    @Test
    public void whenInvalidUrlRedirect_throw_exception() {

        String input = "12we21";
        String output = "www.google.com";

        when(urlRepository.findById(input)).thenReturn(Optional.empty());

        Assertions.assertThrows(EXCEPTION, () -> urlService.getRedirectMapping(input));

        Mockito.verify(urlRepository, times(1)).findById(input);
    }

    @Test
    public void whenExistingUrlEncode_return_UrlShowDTO() {
        String input = "www.google.com";
        String output = "12we21";

        UrlShowDTO expectedOutput = new UrlShowDTO(output, input);

        Optional<UrlEntity> urlEntity = Optional.of(generateUrlEntity(output, input));
        when(urlRepository.findByExternalUrl(input)).thenReturn(urlEntity);
        when(urlRepository.save(urlEntity.get())).thenReturn(urlEntity.get());

        UrlShowDTO actualOutput = urlService.encode(input);
        Assertions.assertEquals(expectedOutput, actualOutput);

        Mockito.verify(urlRepository, times(1)).findByExternalUrl(input);
        Mockito.verify(urlRepository, times(1)).save(urlEntity.get());
    }

    @Test
    public void whenNewUrlEncode_return_UrlShowDTO() {
        String input = "www.google.com";
        String output = "12we21";

        UrlShowDTO expectedOutput = new UrlShowDTO(output, input);

        UrlEntity urlEntity = generateUrlEntity(output, input);
        when(urlRepository.findByExternalUrl(input)).thenReturn(Optional.empty());
        when(urlRepository.save(urlEntity)).thenReturn(urlEntity);
        when(urlEntityGenerator.generate(input)).thenReturn(generateUrlEntity(output, input));

        UrlShowDTO actualOutput = urlService.encode(input);
        Assertions.assertEquals(expectedOutput, actualOutput);

        Mockito.verify(urlRepository, times(1)).findByExternalUrl(input);
        Mockito.verify(urlRepository, times(1)).save(urlEntity);
    }

    private UrlEntity generateUrlEntity(String output, String input) {
        return new UrlEntity(output, input);
    }


}
