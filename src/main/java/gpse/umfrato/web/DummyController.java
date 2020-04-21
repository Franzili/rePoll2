package gpse.umfrato.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/dummy-api")
public class DummyController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World! Lorem ipsum dolor sit amet.";
    }
}
