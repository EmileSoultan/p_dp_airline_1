package app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class MainController {

    @GetMapping("/")
    public ModelAndView index() {
        log.info("index: logging example");
        return new ModelAndView("s7");
    }
}
