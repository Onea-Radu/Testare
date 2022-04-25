package ro.unibuc.link.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.link.dto.EncodeRequest;
import ro.unibuc.link.dto.UrlShowDTO;
import ro.unibuc.link.services.UrlService;
import ro.unibuc.link.validators.LinkValidator;


@Controller
@RequestMapping("/short-link")
public class UrlController {
    @Autowired
    private UrlService urlService;
    @Autowired
    private LinkValidator validator;

    @PostMapping("/set")
    @ResponseBody
    public UrlShowDTO encode(@RequestBody EncodeRequest encodeRequest) {
        validator.validate(encodeRequest);
        return urlService.encode(encodeRequest.getUrl());
    }

    @GetMapping("/redirect/{url}")
    public String redirect(@PathVariable String url) {
        return urlService.getRedirectMapping(url);
    }
}
