package it.sijmen.movienotifier.api;

import it.sijmen.jump.JumpRequest;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

abstract class ApiController {

    protected UserRepository userRepository;

    public ApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected String getApiKey(JumpRequest request) {
        return getApiKey(request.getHeaders());
    }

    protected String getApiKey(Map<String, String> requestHeaders) {
        return requestHeaders == null ? null : requestHeaders.getOrDefault("APIKEY", null);
    }

    protected void checkApiKeyExistence(JumpRequest request){
        if(getApiKey(request.getHeaders()) == null)
            throw new BadRequestException("apikey is not provided");
    }

    protected User getExecutingUser(@NotNull String apiKey) {
        User executingUser = userRepository.findFirstByApikey(apiKey);
        if(executingUser == null)
            throw new UnauthorizedException();
        return executingUser;
    }

}
