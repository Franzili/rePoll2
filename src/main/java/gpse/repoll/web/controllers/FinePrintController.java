package gpse.repoll.web.controllers;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/fineprint")
public class FinePrintController {

    @GetMapping("/")
    public String getFinePrint() {

        Path path = Paths.get("./finePrint.html");

        try {
            return String.join("\n", Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }
}
