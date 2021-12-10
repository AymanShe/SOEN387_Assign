package com.soen387.emailer;

import com.soen387.usermanager.User;

public class GenerateRealReceiverEmail implements GenerateReceiverEmail {

    public String getReceiver() {
        User user = new User();
        user.setEmail("realReceiverEmail@gmail.com");
        String receiver = user.getEmail();
        return receiver;
    }
}
