package it.sijmen.movienotifier.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.Jump;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.JumpListenerAdapter;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.util.ApiKeyHelper;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class UserController extends ApiController implements JumpListenerAdapter<User> {

    private List<String> defaultNotifications;
    private ApiKeyHelper apiKeyHelper;

    private WatcherRepository watcherRepository;

    @Inject
    public UserController(
            @Named("default-notifications") List<String> defaultNotifications,
            UserRepository userRepository,
            ApiKeyHelper apiKeyHelper,
            WatcherRepository watcherRepository) {
        super(userRepository);
        this.defaultNotifications = defaultNotifications;
        this.apiKeyHelper = apiKeyHelper;
        this.watcherRepository = watcherRepository;
    }

    @Bean
    public Jump<User> userJump(ObjectMapper mapper, UserRepository userRepository, UserController configuration){
        return new Jump<>(mapper, userRepository, User.class)
                .enableRead(configuration)
                .enableUpdate(configuration)
                .enableCreate(configuration)
                .enableDelete(configuration);
    }

    @Override
    public User beforeCreateStore(User newUser) {
        newUser.validateUniqueness(userRepository);
        newUser.setPassword(PasswordAuthentication.hash(newUser.getPassword()));
        newUser.setId(UUID.randomUUID().toString());
        return newUser;
    }

    @Override
    public User beforeCreateValidation(User newUser) {
        newUser.setEnabledNotifications(defaultNotifications);
        newUser.setApikey(apiKeyHelper.randomAPIKey());
        newUser.setCreated(new Date());
        return newUser;
    }

    @Override
    public void checkReadRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public boolean allowRead(JumpRequest request, User searchUser) {
        return getExecutingUser(getApiKey(request)).getId().equals(searchUser.getId());
    }

    @Override
    public boolean allowDelete(JumpRequest request, User toDelete) {
        return getExecutingUser(getApiKey(request)).getId().equals(toDelete.getId());
    }

    @Override
    public void postDelete(JumpRequest apiRequest, User deleted) {
        watcherRepository.deleteWatchersByUserid(deleted.getId());
    }

    @Override
    public void checkDeleteRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public void checkUpdateRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public boolean allowUpdate(JumpRequest request, User originalModel) {
        return getExecutingUser(getApiKey(request)).getId().equals(originalModel.getId());
    }

    @Override
    public User beforeUpdateValidation(User updatingUser) {
        if(!PasswordAuthentication.isHashed(updatingUser.getPassword()))
            updatingUser.setPassword(PasswordAuthentication.hash(updatingUser.getPassword()));
        return updatingUser;
    }

    @Override
    public User beforeUpdateStore(User updatingUser) {
        List<String> errors = allowNotifications(updatingUser.getEnabledNotifications());
        if(!errors.isEmpty())
            throw new BadRequestException(errors);

        updatingUser.validateUniqueness(userRepository);
        return updatingUser;
    }

    @Override
    public User getById(MongoRepository<User, String> repository, String id) {
        return ((UserRepository) repository).getFirstByUuid(id);
    }

    private List<String> allowNotifications(List<String> notificationKeys) {
        return notificationKeys.stream().map(
                n -> ("FBM".equals(n) || "MIL".equals(n)) ? null :
                        "You do not have permission to use the " + n + " notification type."
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
