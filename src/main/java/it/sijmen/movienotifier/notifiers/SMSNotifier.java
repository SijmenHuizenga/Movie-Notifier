package it.sijmen.movienotifier.notifiers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.*;
import it.sijmen.movienotifier.str.Recipient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class SMSNotifier extends Notifier{

    private AmazonSNS snsClient;

    @Inject
    public SMSNotifier(
            @Named("aws-sns-accesskey") String accessKey,
            @Named("aws-sns-secretkey") String secretKey
    ) {
        snsClient = AmazonSNSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey)))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    @Override
    public void notify(Recipient recipient, String message) throws NotificationException {
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
            throw new NotificationException("Could not send sms message. Error " + e.getMessage(), e);
        }
    }

}
