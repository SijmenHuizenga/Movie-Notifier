package it.sijmen.movienotifier.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.Jump;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.JumpListenerAdapter;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

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
                .enableCreate(configuration);
    }

    @Override
    public void checkCreateRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public boolean allowCreate(JumpRequest request, Watcher watcher) {
        return getExecutingUser(getApiKey(request)).getId().equals(watcher.getUser());
    }
}
