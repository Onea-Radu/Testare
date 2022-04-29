package ro.unibuc.link;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.link.controller.UrlController;
import ro.unibuc.link.dto.EncodeRequest;
import ro.unibuc.link.dto.UrlShowDTO;
import ro.unibuc.link.services.UrlService;
import ro.unibuc.link.validators.LinkValidator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlControllerTest {

    private static final Class<? extends Throwable> EXCEPTION = ResponseStatusException.class;

    @Mock
    private UrlService urlService;
    @Mock
    private LinkValidator linkValidator;
    @InjectMocks
    private UrlController urlController;


    @Test
    public void encode_invalidUrd_throw_Exception() {
        String input = "";

        doThrow(EXCEPTION).when(linkValidator).validate(any());

        EncodeRequest in = createEncodeRequest(input);
        Assert.assertThrows(EXCEPTION, () -> urlController.encode(in));

        Mockito.verify(linkValidator).validate(any());
        Mockito.verify(urlService, times(0)).encode(any());
    }

    @Test
    public void encode_validUrd_return_UrlShowDto() {
        String input = "www.google.com";
        String output = "123e45";
        UrlShowDTO expectedOutput = createUrlDTO(input, output);

        when(urlService.encode(input)).thenReturn(expectedOutput);

        EncodeRequest in = createEncodeRequest(input);
        Assertions.assertEquals(expectedOutput, urlController.encode(in));

        Mockito.verify(linkValidator).validate(any());
        Mockito.verify(urlService, times(1)).encode(input);
    }

    @Test
    public void redirect_invalidUrl_throw_ResponseStatusException() {
        String input = "";
        String output = "";

        when(urlService.getRedirectMapping(input)).thenThrow(EXCEPTION);

        Assert.assertThrows(EXCEPTION, () -> urlController.redirect(input));

        Mockito.verify(urlService, times(1)).getRedirectMapping(input);
    }

    @Test
    public void redirect_validUrl_return_LinkToRedirect() {
        String input = "123e45";
        String output = "www.google.com";

        when(urlService.getRedirectMapping(input)).thenReturn(output);

        String actualOutput = urlController.redirect(input);

        Assertions.assertEquals(output, actualOutput);

        Mockito.verify(urlService, times(1)).getRedirectMapping(input);
    }


    private EncodeRequest createEncodeRequest(String input) {
        return new EncodeRequest(input);
    }

    private UrlShowDTO createUrlDTO(String input, String output) {
        return UrlShowDTO.builder()
                .externalUrl(input)
                .internalUrl(output)
                .build();
    }

}
