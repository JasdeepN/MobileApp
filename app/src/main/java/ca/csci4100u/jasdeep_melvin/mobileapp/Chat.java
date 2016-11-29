package ca.csci4100u.jasdeep_melvin.mobileapp;

/**
 * Created by penguin on 28/11/16.
 */

public class Chat {
    String email;
    String yourLastMessage;
    String theirLastMessage;

    public Chat(String email, String yours, String my) {
        this.email = email;
        this.yourLastMessage = yours;
        this.theirLastMessage = my;
    }

    public String getEmail() {
        return email;
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
