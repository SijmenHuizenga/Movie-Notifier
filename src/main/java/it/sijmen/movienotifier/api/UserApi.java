package it.sijmen.movienotifier.api;

import io.swagger.model.*;
import io.swagger.model.User;
import io.swagger.model.UserUpdateDetails;
import it.sijmen.movienotifier.controllers.UserController;
import it.sijmen.movienotifier.model.*;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserApi {

    private final UserController userController;

    @Autowired
    public UserApi(UserController userController) {
        this.userController = userController;
    }

    @RequestMapping(method = RequestMethod.PUT)
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
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

    @RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(
            @PathVariable("userid") String userid,
            @RequestHeader("APIKEY") String apikey) {
        if(userid == null)
            throw new BadRequestException("userid not provided");
        if(apikey == null)
            throw new BadRequestException("apikey is not provided");
        userController.delete(userid, apikey);
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User get(
            @PathVariable("userid") String userid,
            @RequestHeader("APIKEY") String apikey) {
        if(userid == null)
            throw new BadRequestException("userid not provided");
        if(apikey == null)
            throw new BadRequestException("apikey is not provided");
        return userController.get(userid, apikey).toSwaggerUser();
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User update(
            @PathVariable("userid") String userid,
            @RequestBody UserUpdateDetails userUpdateDetails,
            @RequestHeader("APIKEY") String apikey) {
        if(userid == null)
            throw new BadRequestException("userid not provided");
        if(apikey == null)
            throw new BadRequestException("apikey is not provided");
        if(userUpdateDetails == null)
            throw new BadRequestException("user details not provided");
        return userController.update(
                userid,
                apikey,
                new it.sijmen.movienotifier.model.UserUpdateDetails(
                        userUpdateDetails.getName(),
                        userUpdateDetails.getEmail(),
                        userUpdateDetails.getPhonenumber(),
                        userUpdateDetails.getPassword()
                )
        ).toSwaggerUser();
    }

    @RequestMapping(value = "/{userid}/notifications/", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationSetting>> getNotifications(
            @PathVariable("userid") String userid,
            @RequestHeader("APIKEY") String apikey) {
        return null;
    }

    @RequestMapping(value = "/{userid}/notifications/", method = RequestMethod.POST)
    public ResponseEntity<List<NotificationSetting>> updateNotifications(
            @PathVariable("userid") String userid,
            @RequestBody List<NotificationSetting> notificationsettings,
            @RequestHeader("APIKEY") String apikey) {
        return null;
    }

}
