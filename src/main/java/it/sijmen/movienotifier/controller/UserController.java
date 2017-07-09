package it.sijmen.movienotifier.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.api.UserApi;
import io.swagger.model.*;
import io.swagger.model.User;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.util.ApiKeyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController implements UserApi {

    final private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity userPut(@ApiParam(value = "The user data of the new user.", required = true) @RequestBody Details details) {
        if (details == null)
            throw new IllegalArgumentException("No details provided");
        it.sijmen.movienotifier.model.User newUser = new it.sijmen.movienotifier.model.User(
                details.getName(), details.getEmail(), details.getPhonenumber(),
                details.getPassword());
        newUser.validate();

        it.sijmen.movienotifier.model.User save = userRepository.save(newUser);
        return new ResponseEntity<>(save.toSwaggerUser(), HttpStatus.CREATED);
    }

    public ResponseEntity<User> userLoginGet(@ApiParam(value = "The login details", required = true) @RequestBody Details1 details) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> userUseridDelete(@ApiParam(value = "The unique identifier of the user.", required = true) @PathVariable("userid") String userid) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<User> userUseridGet(@ApiParam(value = "The unique identifier of the user.", required = true) @PathVariable("userid") String userid) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<NotificationSetting>> userUseridNotificationsGet(@ApiParam(value = "The unique identifier of the user.", required = true) @PathVariable("userid") String userid) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<NotificationSetting>> userUseridNotificationsPost(@ApiParam(value = "The unique identifier of the user.", required = true) @PathVariable("userid") String userid,
                                                                                 @ApiParam(value = "The new notification settings of a given user. All notification types that are not specified are set to the default value false.", required = true) @RequestBody List<NotificationSetting> notificationsettings) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<User> userUseridPost(@ApiParam(value = "The unique identifier of the user.", required = true) @PathVariable("userid") String userid,
                                               @ApiParam(value = "The fields that should be updated. Fields that do not require updates can be omitted.", required = true) @RequestBody Details2 details) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
