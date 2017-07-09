package it.sijmen.movienotifier.api;

import io.swagger.annotations.ApiParam;
import io.swagger.api.UserApi;
import io.swagger.model.*;
import io.swagger.model.User;
import it.sijmen.movienotifier.controllers.UserControllerImpl;
import it.sijmen.movienotifier.model.LoginDetails;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserApiController implements UserApi {

    private final UserControllerImpl userController;

    @Autowired
    public UserApiController(UserControllerImpl userController) {
        this.userController = userController;
    }

    @Override
    public ResponseEntity<User> userPut(@ApiParam(value = "The user data of the new user.", required = true) @RequestBody UserCreationDetails details) {
        if (details == null)
            throw new BadRequestException("No details provided");

        return new ResponseEntity<>(
                userController.createUser(
                        new it.sijmen.movienotifier.model.User(
                                details.getName(), details.getEmail(), details.getPhonenumber(),
                                details.getPassword())
                ).toSwaggerUser()
                , HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> userLoginPost(@ApiParam(value = "The login details", required = true) @RequestBody UserLoginDetails details) {
        if(details == null)
            throw new BadRequestException("No details provided");

        return new ResponseEntity<>(
                userController.login(
                        new LoginDetails(
                                details.getName(),
                                details.getPassword()
                        )
                ).toSwaggerUser(),
                HttpStatus.OK);
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
                                               @ApiParam(value = "The fields that should be updated. Fields that do not require updates can be omitted.", required = true) @RequestBody UserUpdateDetails details) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
