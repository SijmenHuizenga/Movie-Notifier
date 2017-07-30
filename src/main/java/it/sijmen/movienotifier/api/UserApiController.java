package it.sijmen.movienotifier.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.JumpListenerAdapter;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.model.requests.LoginDetails;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.jump.Jump;
import it.sijmen.movienotifier.util.ApiKeyHelper;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserApiController extends JumpListenerAdapter<User> {

    private final Jump<User> userJump;

    private final UserRepository userRepository;
    private final List<String> defaultNotifications;

    @Inject
    public UserApiController(UserRepository userRepository,
                           @Named("default-notifications") List<String> defaultNotifications,
                           ObjectMapper mapper) {
        this.userJump = new Jump<>(mapper, userRepository, User.class);
        this.userRepository = userRepository;
        this.defaultNotifications = defaultNotifications;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User login(
            @RequestBody LoginDetails loginDetails) {
        if (loginDetails == null)
            throw new BadRequestException("No details provided");

        loginDetails.validate();

        User user = userRepository.findFirstByName(loginDetails.getName());
        if(user == null)
            throw new UnauthorizedException();

        if(!PasswordAuthentication.authenticate(loginDetails.getPassword(), user.getPassword()))
            throw new UnauthorizedException();
        return user;
    }

    @RequestMapping(value = {"/{urldata:.*}", "/"})
    public HttpEntity<?> genericUserMethod(HttpMethod requestMethod,
                                           @RequestHeader(required = false) Map<String, String> requestHeaders,
                                           @PathVariable(required = false) String urldata,
                                           @RequestBody(required = false) String body) throws Exception{
        return userJump.handle(new JumpRequest(
                requestMethod, requestHeaders, urldata, body
        ));
    }

    @Override
    public User beforeCreationStore(User newUser) {
        newUser.validateUniqueness(userRepository);
        return newUser;
    }

    @Override
    public User beforeCreationValidation(User newUser) {
        newUser.setPassword(PasswordAuthentication.hash(newUser.getPassword()));
        newUser.setEnabledNotifications(defaultNotifications);
        newUser.setApikey(ApiKeyHelper.randomAPIKey());
        newUser.setCreated(new Date());
        return newUser;
    }

    @Override
    public boolean allowCreation(String apikey, User model) {
        return true;
    }

    @Override
    public boolean allowRead(String apiKey, User searchUser) {
        return getExecutingUser(apiKey).getId().equals(searchUser.getId());
    }

    @Override
    public boolean allowReadAll(String apiKey, List<User> result) {
        return false;
    }

    @Override
    public boolean allowDelete(String apiKey, User toDelete) {
        return getExecutingUser(apiKey).getId().equals(toDelete.getId());
    }

    @Override
    public boolean allowUpdate(String apiKey, User originalModel) {
        return getExecutingUser(apiKey).getId().equals(originalModel.getId());
    }

    private User getExecutingUser(String apiKey) {
        if(apiKey == null)
            throw new BadRequestException("apikey is not provided");
        User executingUser = userRepository.findFirstByApikey(apiKey);
        if(executingUser == null)
            throw new UnauthorizedException();
        return executingUser;
    }

    @Override
    public User beforeUpdateValidation(User updatingUser) {
        if(!PasswordAuthentication.isHashed(updatingUser.getPassword()))
            updatingUser.setPassword(PasswordAuthentication.hash(updatingUser.getPassword()));
        return updatingUser;
    }

    @Override
    public User beforeUpdateStore(User updatingUser) {
        List<String> errors;
        if((errors = allowNotifications(updatingUser.getEnabledNotifications())).size() != 0)
            throw new BadRequestException(errors);

        updatingUser.validateUniqueness(userRepository);
        return updatingUser;
    }

    private List<String> allowNotifications(List<String> notificationKeys) {
        return notificationKeys.stream().map(
                n -> ("FBM".equals(n) || "MIL".equals(n)) ? null :
                        "You do not have permission to use the " + n + " notification type."
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
