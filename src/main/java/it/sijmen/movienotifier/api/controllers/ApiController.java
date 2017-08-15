package it.sijmen.movienotifier.api.controllers;

import it.sijmen.jump.JumpRequest;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

abstract class ApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    protected UserRepository userRepository;

    public ApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected String getApiKey(JumpRequest request) {
        return getApiKey(request.getHeaders());
    }

    protected String getApiKey(Map<String, String> requestHeaders) {
        return requestHeaders == null ? null :
                requestHeaders.getOrDefault("APIKEY",
                        requestHeaders.getOrDefault("apikey", null));
    }

    protected void checkApiKeyExistence(JumpRequest request){
        if(getApiKey(request.getHeaders()) == null)
            throw new BadRequestException("apikey is not provided");
    }

    protected User getExecutingUser(@NotNull String apiKey) {
        User executingUser = userRepository.findFirstByApikey(apiKey);
        if(executingUser == null) {
            LOGGER.trace("Could not find user with apikey {}", apiKey);
            throw new UnauthorizedException();
        }
        return executingUser;
    }

}
