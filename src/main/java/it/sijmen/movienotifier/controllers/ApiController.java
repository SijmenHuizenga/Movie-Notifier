package it.sijmen.movienotifier.controllers;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

abstract class ApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    protected UserRepository userRepository;

    public ApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected String getApiKey(Map<String, String> requestHeaders) {
        return requestHeaders == null ? null :
                requestHeaders.getOrDefault("APIKEY",
                        requestHeaders.getOrDefault("apikey", null));
    }

    protected void checkApiKeyExistence(Map<String, String> requestHeaders){
        if(getApiKey(requestHeaders) == null)
            throw new BadRequestException("apikey is not provided");
    }

    protected User getExecutingUser(String apiKey) {
        User executingUser = userRepository.findFirstByApikey(apiKey);
        if(executingUser == null) {
            LOGGER.trace("Could not find user with apikey {}", apiKey);
            throw new UnauthorizedException();
        }
        return executingUser;
    }

}
