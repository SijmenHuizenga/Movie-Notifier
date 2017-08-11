package it.sijmen.movienotifier.service.notification.notifiers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import it.sijmen.movienotifier.model.Notifier;
import it.sijmen.movienotifier.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Service
public class SMSNotifier extends Notifier {

    public static final String ID = "SMS";

    private AmazonSNS snsClient;

    @Inject
    public SMSNotifier(
            @Value("${notification.awssns.key}") String accessKey,
            @Value("${notification.awssns.secret}") String secretKey
    ) {
        snsClient = AmazonSNSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey)))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    @Override
    public void notify(User recipient, String message) throws IOException {
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.25")
                .withDataType("Number"));

        try{
            snsClient.publish(new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber(recipient.getPhonenumber())
                    .withMessageAttributes(smsAttributes));
        }catch (Exception e){
            throw new IOException("Could not send sms message. Error " + e.getMessage(), e);
        }
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "SMS Message";
    }

    @Override
    public String getDescription() {
        return "Get a message directly to your phone through SMS";
    }

}
