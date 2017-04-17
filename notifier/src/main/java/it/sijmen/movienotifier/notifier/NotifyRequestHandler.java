package it.sijmen.movienotifier.notifier;

import com.google.inject.Inject;
import it.sijmen.movienotifier.notifier.requests.NotifyRequest;
import it.sijmen.movienotifier.coder.Coder;
import spark.HaltException;

import java.io.IOException;

import static spark.Spark.halt;

/**
 * Created by Sijmen on 15-4-2017.
 */
class NotifyRequestHandler {

    private Coder coder;
    private NotificationSender sender;

    @Inject
    public NotifyRequestHandler(NotificationSender sender, Coder coder) {
        this.coder = coder;
        this.sender = sender;
    }

    void notify(String requestBody) {
        try{
            NotifyRequest notifyRequest = makeRequest(requestBody);

            checkAuthorize(notifyRequest);
            checkValid(notifyRequest);

            sender.sendNotifaction(notifyRequest.getRecipient(), notifyRequest.getSettings(), notifyRequest.getMessage());
        }catch (Exception e){
            if(e instanceof HaltException)
                throw e;
            throw halt(500, "Internal server error");
        }
        //todo: error handling sending
    }

    private NotifyRequest makeRequest(String requestBody) {
        try {
            return coder.decode(requestBody, NotifyRequest.class);
        } catch (IOException e) {
            throw halt(400, "Bad request. Could not parse json.");
        }
    }

    private void checkValid(NotifyRequest notifyRequest) {
        if(!notifyRequest.isValid())
            throw halt(400, "Not authorized. The request does not match the api specifications.");
    }

    private void checkAuthorize(NotifyRequest request){
        if(!request.authorized())
            throw halt(403, "Not authorized. The provided apikey is not valid.");
    }

}
