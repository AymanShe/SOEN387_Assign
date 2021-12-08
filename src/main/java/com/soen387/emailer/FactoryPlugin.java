package com.soen387.emailer;

import com.soen387.controller.Utility;

public class FactoryPlugin {

    private static String env = Utility.readProperties("env.properties").getProperty("env");

    public static String getEnv() {
        String senderEmailAddress = "";

        if (env.equals("prod")) {
            // get real receiver email from User class
            //senderEmailAddress = new genRealSenderEmailAddress();
        }
        else if (env.equals("qa") || env.equals("dev")) {
            // dummy values
            //senderEmailAddress = new genStubSenderEmailAddress();
        }
        return senderEmailAddress;
    }
}
