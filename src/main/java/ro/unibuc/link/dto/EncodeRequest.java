package ro.unibuc.link.dto;

import lombok.Data;

@Data
public class EncodeRequest {
    String url;

    public EncodeRequest(String url) {
        this.url = url;
    }

    public EncodeRequest() {
    }
}
