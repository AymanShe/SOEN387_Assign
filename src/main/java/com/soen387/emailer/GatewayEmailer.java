package com.soen387.emailer;

import com.soen387.usermanager.User;

import static com.soen387.emailer.FactoryPlugin.getReceiver;
import static com.soen387.emailer.Transform.transformContentToHTML;

public class GatewayEmailer {

    public static boolean sendEmail (User receiver, MessageType messageType) {
        String sender = "pollsystem@gmail.com";
        String receiverEmail = getReceiver(receiver);
        String emailHTMLContent = transformContentToHTML(receiver, messageType);

        try {
            System.out.println("\nEMAIL:\nFrom: " + sender + ", To: " + receiverEmail + "\n\n" + emailHTMLContent);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
