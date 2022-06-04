package com.example.ex3.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    private String useryName;
    private String displayName;
    private String lastMessage;
    private String lastTime;
    private int pic;

    public Contact(String useryName, String displayName) {
        this.useryName = useryName;
        this.displayName = displayName;
        //  this.pic = pic;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getUseryName() {
        return useryName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPic() {
        return pic;
    }
}
