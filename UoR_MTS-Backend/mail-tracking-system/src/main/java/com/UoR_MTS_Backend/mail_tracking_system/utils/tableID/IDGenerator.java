package com.UoR_MTS_Backend.mail_tracking_system.utils.tableID;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class IDGenerator {

    public String generateID(String prefix, int length) {
        int power = (int) Math.pow(10, length - 1);
        int randomNum = new Random().nextInt(9*power) + 1*power;
        return prefix.toUpperCase()+"-"+String.valueOf(randomNum);
    }
}
