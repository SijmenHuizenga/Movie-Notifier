package it.sijmen.movienotifier.api;

import it.sijmen.jump.Jump;
import it.sijmen.jump.JumpRequest;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.Watcher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class JumpConfiguration {

    private final Jump<User> userJump;
    private final Jump<Watcher> watcherJump;

    @Inject
    public JumpConfiguration(Jump<User> userJump, Jump<Watcher> watcherJump) {
        this.userJump = userJump;
        this.watcherJump = watcherJump;
    }

    @RequestMapping(value = {"/user/{urldata:.*}", "/user"})
    public HttpEntity genericUserMethod(HttpMethod requestMethod,
                                           @RequestHeader(required = false) Map<String, String> requestHeaders,
                                           @PathVariable(required = false) String urldata,
                                           @RequestBody(required = false) String body) {
        return userJump.handle(new JumpRequest(
                requestMethod, requestHeaders, urldata, body
        ));
    }

    @RequestMapping(value = {"/watchers/{urldata:.*}", "/watchers"})
    public HttpEntity genericWatchersMethod(HttpMethod requestMethod,
                                           @RequestHeader(required = false) Map<String, String> requestHeaders,
                                           @PathVariable(required = false) String urldata,
                                           @RequestBody(required = false) String body) {
        return watcherJump.handle(new JumpRequest(
                requestMethod, requestHeaders, urldata, body
        ));
    }
}
