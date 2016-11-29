package ca.csci4100u.jasdeep_melvin.mobileapp;

/**
 * Created by penguin on 28/11/16.
 */

public class Chat {
    String email;
    String yourLastMessage;
    String theirLastMessage;

    public Chat(String email) {
        this.email = email;
    }

    public void setTheirLastMessage (String message) {
        theirLastMessage = message;
    }

    public String getTheirLastMessage () {
        return theirLastMessage;
    }

    public void setYourLastMessage (String message) {
        yourLastMessage = message;
    }

    public String getYourLastMessage () {
        return yourLastMessage;
    }
}
