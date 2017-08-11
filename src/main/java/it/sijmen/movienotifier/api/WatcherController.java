package it.sijmen.movienotifier.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.Jump;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.JumpListenerAdapter;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class WatcherController extends ApiController implements JumpListenerAdapter<Watcher> {

    private final WatcherRepository watcherRepo;

    @Inject
    public WatcherController(UserRepository userRepo, WatcherRepository watcherRepo) {
        super(userRepo);
        this.watcherRepo = watcherRepo;
    }

    @Bean
    public Jump<Watcher> watcherJump(ObjectMapper mapper, WatcherRepository watcherRepo, WatcherController configuration){
        return new Jump<>(mapper, watcherRepo, Watcher.class)
                .enableCreate(configuration)
                .enableUpdate(configuration)
                .enableDelete(configuration)
                .enableRead(configuration)
                .enableReadSome(configuration);
    }

    @Override
    public void checkCreateRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public boolean allowCreate(JumpRequest request, Watcher watcher) {
        return getExecutingUser(getApiKey(request)).getId().equals(watcher.getUser());
    }

    @Override
    public void checkUpdateRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public boolean allowUpdate(JumpRequest request, Watcher originalModel) {
        return getExecutingUser(getApiKey(request)).getId().equals(originalModel.getUser());
    }

    @Override
    public boolean allowDelete(JumpRequest request, Watcher toDelete) {
        return getExecutingUser(getApiKey(request)).getId().equals(toDelete.getUser());
    }

    @Override
    public void checkDeleteRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public void checkReadRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public boolean allowRead(JumpRequest request, Watcher searchUser) {
        return getExecutingUser(getApiKey(request)).getId().equals(searchUser.getUser());
    }

    @Override
    public void checkReadSomeRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public List<Watcher> getReadSomeResult(JumpRequest request) {
        return watcherRepo.getAllByUser(getExecutingUser(getApiKey(request)).getId());
    }
}
