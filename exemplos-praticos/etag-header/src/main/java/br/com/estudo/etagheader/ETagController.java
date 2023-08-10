package br.com.estudo.etagheader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ETagController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello etag Header";
    }
}
