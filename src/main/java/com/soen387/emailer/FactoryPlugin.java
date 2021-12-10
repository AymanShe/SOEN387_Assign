package com.soen387.emailer;

import com.soen387.controller.Utility;
import com.soen387.usermanager.User;

public class FactoryPlugin {

    private static final String env = Utility.readProperties("env.properties").getProperty("env");

    public static String getReceiver(User receiver) {
        String receiverEmail = "";

        if (env.equals("prod")) {
            // get real receiver email from User class
            GenerateRealReceiverEmail realReceiverEmail = new GenerateRealReceiverEmail();
            receiverEmail = realReceiverEmail.getReceiver(receiver);

        }
        else if (env.equals("qa") || env.equals("dev")) {
            // dummy values
            GenerateStubReceiverEmail stubReceiverEmail = new GenerateStubReceiverEmail();
            receiverEmail = stubReceiverEmail.getReceiver(receiver);
        }
        return receiverEmail;
    }
}
