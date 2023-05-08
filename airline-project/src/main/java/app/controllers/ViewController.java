package app.controllers;

import app.controllers.api.ViewControllerApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@ApiIgnore
@RestController
public class ViewController implements ViewControllerApi {

    @Override
    public ModelAndView index() {
        log.info("index: logging example");
        return new ModelAndView("index");
    }

    @Override
    public ModelAndView searchResult() {
        return new ModelAndView("searchResult");
    }

    @Override
    public ModelAndView adminPage() {
        return new ModelAndView("admin");
    }

    @Override
    public ModelAndView managerPage() {
        return new ModelAndView("manager");
    }

    @Override
    public ModelAndView passengerPage() {
        return new ModelAndView("passenger");
    }
}