package com.soen387.emailer;

import com.soen387.usermanager.User;

import static com.soen387.usermanager.UserManager.generateToken;

public class Transform {

    public static String transformContentToHTML() {
        String content = "Here is an email for the receiver.";
        String token = generateToken();

        String contentToHTML = "<HTML>" + content + "<br><br>Activation Code: " + token + "</HMTL>";

        return contentToHTML;
    }
}
