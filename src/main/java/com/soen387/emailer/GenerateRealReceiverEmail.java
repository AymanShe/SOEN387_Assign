package com.soen387.emailer;

import com.soen387.usermanager.User;

public class GenerateRealReceiverEmail implements GenerateReceiverEmail {

    public String getReceiver(User receiver) {
        String receiverEmail = receiver.getEmail();
        return receiverEmail;
    }
}
