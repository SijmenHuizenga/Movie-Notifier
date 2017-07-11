package it.sijmen.movienotifier.api;

import io.swagger.model.User;
import io.swagger.model.UserCreationDetails;
import io.swagger.model.UserLoginDetails;
import io.swagger.model.UserUpdateDetails;
import it.sijmen.movienotifier.model.exceptions.BadRequestException;
import it.sijmen.movienotifier.model.requests.LoginDetails;
import it.sijmen.movienotifier.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
@RequestMapping("/user")
public class UserApiController {

    private final UserService userService;

    @Inject
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User create(
            @RequestBody UserCreationDetails details) {
        if (details == null)
            throw new BadRequestException("No details provided");

        return userService.createUser(
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

        return userService.login(
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
        checkUseridApikeyAvailable(userid, apikey);
        userService.delete(userid, apikey);
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User get(
            @PathVariable("userid") String userid,
            @RequestHeader("APIKEY") String apikey) {
        checkUseridApikeyAvailable(userid, apikey);

        return userService.get(userid, apikey).toSwaggerUser();
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public User update(
            @PathVariable("userid") String userid,
            @RequestBody UserUpdateDetails userUpdateDetails,
            @RequestHeader("APIKEY") String apikey) {
        checkUseridApikeyAvailable(userid, apikey);

        if(userUpdateDetails == null)
            throw new BadRequestException("user details not provided");
        return userService.update(
                userid,
                apikey,
                new it.sijmen.movienotifier.model.requests.UserUpdateDetails(
                        userUpdateDetails.getName(),
                        userUpdateDetails.getEmail(),
                        userUpdateDetails.getPhonenumber(),
                        userUpdateDetails.getPassword(),
                        userUpdateDetails.getNotifications()
                )
        ).toSwaggerUser();
    }

    private void checkUseridApikeyAvailable(String userid, String apikey){
        if(userid == null)
            throw new BadRequestException("userid not provided");
        if(apikey == null)
            throw new BadRequestException("apikey is not provided");
    }

}
