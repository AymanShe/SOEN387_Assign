package com.soen387.emailer;

import com.soen387.controller.Utility;

public class FactoryPlugin {

    private static final String env = Utility.readProperties("env.properties").getProperty("env");

    public static String getReceiver() {
        String receiver = "";

        if (env.equals("prod")) {
            // get real receiver email from User class
            GenerateRealReceiverEmail realReceiverEmail = new GenerateRealReceiverEmail();
            receiver = realReceiverEmail.getReceiver();

        }
        else if (env.equals("qa") || env.equals("dev")) {
            // dummy values
            GenerateStubReceiverEmail stubReceiverEmail = new GenerateStubReceiverEmail();
            receiver = stubReceiverEmail.getReceiver();
        }
        return receiver;
    }
}
