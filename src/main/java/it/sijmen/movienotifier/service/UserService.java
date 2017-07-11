package it.sijmen.movienotifier.service;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.exceptions.UnauthorizedException;
import it.sijmen.movienotifier.model.requests.LoginDetails;
import it.sijmen.movienotifier.model.requests.UserUpdateDetails;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final List<String> defaultNotifications;

    @Inject
    public UserService(UserRepository userRepository, AuthenticationService authenticationService,
                       @Named("default-notifications") List<String> defaultNotifications) {
        this.userRepository = userRepository;
        this.authService = authenticationService;
        this.defaultNotifications = defaultNotifications;
    }

    public User createUser(@NotNull User newUser) {
        newUser.validate();
        newUser.validateUniqueness(userRepository);
        newUser.setPassword(PasswordAuthentication.hash(newUser.getPassword()));
        newUser.setEnabledNotifications(defaultNotifications);
        return userRepository.save(newUser);
    }

    public User login(@NotNull LoginDetails loginDetails) {
        loginDetails.validate();

        User user = userRepository.findFirstByName(loginDetails.getName());
        if(user == null)
            throw new UnauthorizedException();

        if(!PasswordAuthentication.authenticate(loginDetails.getPassword(), user.getPassword()))
            throw new UnauthorizedException();

        return user;
    }

    public void delete(@NotNull String userid, @NotNull String apikey) {
        User executingUser = userRepository.findFirstByApikey(apikey);
        User toDeleteUser = userRepository.findFirstById(userid);
        if(executingUser == null || toDeleteUser == null)
            throw new UnauthorizedException();
        if(!authService.canDelete(executingUser, toDeleteUser))
            throw new UnauthorizedException();
        userRepository.delete(toDeleteUser);
        //todo: delete from all other places?
        //todo: set inactive? Soft deletes?
    }

    public User get(String userid, String apikey) {
        User executingUser = userRepository.findFirstByApikey(apikey);
        User searchUser = userRepository.findFirstById(userid);
        if(executingUser == null || searchUser == null)
            throw new UnauthorizedException();
        if(authService.canSeeFullDetails(executingUser, searchUser))
            return searchUser;
        if(authService.canSeeUsername(executingUser, searchUser))
            return new User(searchUser.getName());
        throw new UnauthorizedException();
    }

    public User update(@NotNull String userid, @NotNull String apikey, @NotNull UserUpdateDetails details) {
        User executingUser = userRepository.findFirstByApikey(apikey);
        User updatingUser = userRepository.findFirstById(userid);
        if(executingUser == null || updatingUser == null)
            throw new UnauthorizedException();
        if(!authService.canUpdate(executingUser, updatingUser))
            throw new UnauthorizedException();
        details.validate();
        List<String> errors;
        if((errors = allowNotifications(executingUser, details.getEnabledNotifications())).size() != 0)
            throw new BadRequestException(errors);
        if(details.getPassword() != null)
            updatingUser.setPassword(PasswordAuthentication.hash(details.getPassword()));
        if(details.getEmail() != null)
            updatingUser.setEmail(details.getEmail());
        if(details.getName() != null)
            updatingUser.setName(details.getName());
        if(details.getPhonenumber() != null)
            updatingUser.setPhonenumber(details.getPhonenumber());
        if(details.getEnabledNotifications() != null)
            updatingUser.setEnabledNotifications(details.getEnabledNotifications());
        return userRepository.save(updatingUser);
    }

    private List<String> allowNotifications(User user, List<String> notificationKeys) {
        return notificationKeys.stream().map(
                n -> authService.allowNotification(user, n) ? null :
                        "You do not have permission to use the " + n + " notification type."
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
