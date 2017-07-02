package it.sijmen.movienotifier.controller;

import io.swagger.api.UserApi;
import io.swagger.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController implements UserApi {
    @Override
    public ResponseEntity<User> userLoginGet(Details1 details) {
        return null;
    }

    @Override
    public ResponseEntity<User> userPut(Details details) {
        return null;
    }

    @Override
    public ResponseEntity<Void> userUseridDelete(UUID userid) {
        return null;
    }

    @Override
    public ResponseEntity<User> userUseridGet(UUID userid) {
        return null;
    }

    @Override
    public ResponseEntity<List<NotificationSetting>> userUseridNotificationsGet(UUID userid) {
        return null;
    }

    @Override
    public ResponseEntity<List<NotificationSetting>> userUseridNotificationsPost(UUID userid, List<NotificationSetting> notificationsettings) {
        return null;
    }

    @Override
    public ResponseEntity<User> userUseridPost(UUID userid, Details2 details) {
        return null;
    }
}
