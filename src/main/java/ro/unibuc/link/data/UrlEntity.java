package ro.unibuc.link.data;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UrlEntity {
    @Id
    private String internalUrl;
    private String externalUrl;

    
    public UrlEntity(String internalUrl, String externalUrl) {
        this.internalUrl = internalUrl;
        this.externalUrl = externalUrl;
    }
}