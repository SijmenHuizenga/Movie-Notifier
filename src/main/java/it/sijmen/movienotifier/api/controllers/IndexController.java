package it.sijmen.movienotifier.api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @Value("${git.commit.id}")
    private String commitId;

    @RequestMapping("/")
    public ModelAndView redirectSwaggerDocs() {
        return new ModelAndView("redirect:http://petstore.swagger.io/?url=https://raw.githubusercontent.com/SijmenHuizenga/Movie-Notifier/"+commitId+"/swagger.yaml");
    }


}
