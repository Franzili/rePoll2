package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Design;
import gpse.repoll.domain.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class DesignController {

    private DesignService designService;

    @Autowired
    public DesignController(DesignService designService) {
        this.designService = designService;
    }

    @GetMapping("/{pollId}/design/")
    public Design getDesign(@PathVariable("pollId") final UUID pollId) {
        return designService.getDesign(pollId);
    }

}
