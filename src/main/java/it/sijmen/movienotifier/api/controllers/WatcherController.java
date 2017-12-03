package it.sijmen.movienotifier.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.jump.Jump;
import it.sijmen.jump.JumpRequest;
import it.sijmen.jump.listeners.JumpListenerAdapter;
import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

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
        return getExecutingUser(getApiKey(request)).getId().equals(watcher.getUserid());
    }

    @Override
    public Watcher beforeCreateValidation(Watcher model) {
        model.setId(UUID.randomUUID().toString());
        return model;
    }

    @Override
    public void checkUpdateRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public boolean allowUpdate(JumpRequest request, Watcher originalModel) {
        return getExecutingUser(getApiKey(request)).getId().equals(originalModel.getUserid());
    }

    @Override
    public boolean allowDelete(JumpRequest request, Watcher toDelete) {
        return getExecutingUser(getApiKey(request)).getId().equals(toDelete.getUserid());
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
        return getExecutingUser(getApiKey(request)) != null;
    }

    @Override
    public Watcher beforeReadResult(JumpRequest request, Watcher result) {
        User executingUser = getExecutingUser(getApiKey(request));
        if(!executingUser.getId().equals(result.getUserid()))
            result.setId(null);
        return result;
    }

    @Override
    public void checkReadSomeRequest(JumpRequest request) {
        checkApiKeyExistence(request);
    }

    @Override
    public List<Watcher> getReadSomeResult(JumpRequest request) {
        return watcherRepo.getAllByUserid(getExecutingUser(getApiKey(request)).getId());
    }

    @Override
    public Watcher getById(MongoRepository<Watcher, String> repository, String id) {
        return ((WatcherRepository) repository).getFirstByUuid(id);
    }
}
