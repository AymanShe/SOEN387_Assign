package com.soen387.emailer;

import com.soen387.usermanager.User;

public interface GenerateReceiverEmail {

    String getReceiver(User receiver);
}
