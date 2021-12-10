package com.soen387.emailer;

import com.soen387.usermanager.User;

public class GenerateStubReceiverEmail implements GenerateReceiverEmail {

    public String getReceiver(User receiver) {
        return "stubReceiverEmail@gmail.com";
    }
}
