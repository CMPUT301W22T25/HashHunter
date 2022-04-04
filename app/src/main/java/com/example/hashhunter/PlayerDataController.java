package com.example.hashhunter;

import java.util.ArrayList;

/**
 * Wrapper for information in Player model
 */
public class PlayerDataController {
    Player ThePlayer;

    public PlayerDataController(Player p){
        this.ThePlayer = p;
    }
    public String getPlayerUsername() {
        return this.ThePlayer.getUsername();
    }

    public String getPlayerEmail(String uniqueID){
        if (uniqueID == ThePlayer.getProfileCode()){
            return null;
        }
        else{
            return null;
        }
    }
}



