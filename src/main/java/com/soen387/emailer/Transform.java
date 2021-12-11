package com.soen387.emailer;

import com.soen387.usermanager.User;

public class Transform {

    // TODO: int messageType to enum
    public static String transformContentToHTML(User receiver, MessageType messageType) {
        String receiverName = receiver.getName();
        String token = receiver.getActivationCode();

        String contentToHTML = "";
        String link = "";

        switch (messageType) {
            //Sign-up Email
            case Activate:
                link = "Activate?username=" + receiver.getName() + "&token=" + token;
                contentToHTML = "<HTML>Hello" + receiverName + "! Welcome to our Polling System.<br><br>Please click " +
                        "on the following verification link to activate your account: " + link + "</HTML>";
                break;
            //Forget Password Email
            case Forget:
                link = "Change?username=" + receiver.getName() + "&token=" + token;
                contentToHTML = "<HTML>" + receiverName + ", you have requested to change your forgotten password." +
                        "<br><br>Please click on the following link to change your password: " + link + "</HTML>";
                break;
            default:
        }

        return contentToHTML;
    }
}
