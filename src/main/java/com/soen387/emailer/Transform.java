package com.soen387.emailer;

import com.soen387.usermanager.User;

public class Transform {

    // TODO: int messageType to enum
    public static String transformContentToHTML(User receiver, int messageType) {
        String content = "Here is an email for the receiver.";
        String token = receiver.getActivationCode();

        String contentToHTML = "";

        switch(messageType) {
            case 1:
                contentToHTML = "<HTML>" + content + "<br><br>Activation Code: " + token + "</HTML>";
                break;
            case 2:
            default:
        }

        return contentToHTML;
    }
}
