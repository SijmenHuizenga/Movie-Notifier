package it.sijmen.movienotifier.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:https://github.com/SijmenHuizenga/Movie-Notifier";
    }

}
