package com.musala.musalatest.util;


import com.musala.musalatest.business.model.Drone;

import java.util.Date;
import java.util.TimerTask;


public class ScheduledTask extends TimerTask {
    Date now;
    public void run() {
        // Write code here that you want to execute periodically.
        var drone = new Drone();

        var batteryLife = drone.getBatteryCapacity();

        for (batteryLife=100; batteryLife>=0; batteryLife--) {

            System.out.println("Battery level now at: " + batteryLife);
        }
        now = new Date();                      // initialize date
        System.out.println("Time is :" + now); // Display current time
    }
}
