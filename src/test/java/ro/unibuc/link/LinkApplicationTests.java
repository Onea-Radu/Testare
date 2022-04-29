package ro.unibuc.link;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.unibuc.link.data.UrlRepository;

@ExtendWith(MockitoExtension.class)
class LinkApplicationTests {

    @Mock
    UrlRepository mockRepository2;

    @Test
    void contextLoads() {
    }

}
