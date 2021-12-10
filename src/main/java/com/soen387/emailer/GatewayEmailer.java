package com.soen387.emailer;

import static com.soen387.emailer.FactoryPlugin.getReceiver;
import static com.soen387.emailer.Transform.transformContentToHTML;

public class GatewayEmailer {

    public static boolean sendEmail () {
        String sender = "pollsystem@gmail.com";
        String receiver = getReceiver();
        String emailHTMLContent = transformContentToHTML();

        try {
            System.out.print("From: " + sender + ", To: " + receiver + "\n" + emailHTMLContent);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
