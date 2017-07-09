package it.sijmen.movienotifier.api;

import io.swagger.model.*;
import io.swagger.model.User;
import it.sijmen.movienotifier.controllers.UserController;
import it.sijmen.movienotifier.model.LoginDetails;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserApi {

    private final UserController userController;

    @Autowired
    public UserApi(UserController userController) {
        this.userController = userController;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User create(
            @RequestBody UserCreationDetails details) {
        if (details == null)
            throw new BadRequestException("No details provided");

        return userController.createUser(
                        new it.sijmen.movienotifier.model.User(
                                details.getName(), details.getEmail(), details.getPhonenumber(),
                                details.getPassword())
                ).toSwaggerUser();
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User login(
            @RequestBody UserLoginDetails details) {
        if (details == null)
            throw new BadRequestException("No details provided");

        return userController.login(
                        new LoginDetails(
                                details.getName(),
                                details.getPassword()
                        )
                ).toSwaggerUser();
    }

    @RequestMapping(value = "/user/{userid}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(
            @PathVariable("userid") String userid,
            @RequestHeader("APIKEY") String apikey) {
        if(userid == null || apikey == null)
            throw new BadRequestException();
        userController.delete(userid, apikey);
        System.out.println("finished");
    }

    @RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
    public ResponseEntity<User> get(
            @PathVariable("userid") String userid,
            @RequestHeader("APIKEY") String apikey) {
        return null;
    }

    @RequestMapping(value = "/user/{userid}", method = RequestMethod.POST)
    public ResponseEntity<User> update(
            @PathVariable("userid") String userid,
            @RequestBody UserUpdateDetails userUpdateDetails,
            @RequestHeader("APIKEY") String apikey) {
        return null;
    }

    @RequestMapping(value = "/user/{userid}/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationSetting>> getNotifications(
            @PathVariable("userid") String userid,
            @RequestHeader("APIKEY") String apikey) {
        return null;
    }

    @RequestMapping(value = "/user/{userid}/notifications/", method = RequestMethod.POST)
    public ResponseEntity<List<NotificationSetting>> updateNotifications(
            @PathVariable("userid") String userid,
            @RequestBody List<NotificationSetting> notificationsettings,
            @RequestHeader("APIKEY") String apikey) {
        return null;
    }

}
