package com.example;

import java.util.ArrayList;

public class Username {
    ArrayList username = new ArrayList<String>();
    public boolean addUsername(String us){
        if(username.contains(us)){
            return true;
        }
        username.add(us);
        return false;
    }
    
}
