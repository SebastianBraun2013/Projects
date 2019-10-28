/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.blackburn.cs.cs212.weather.braun;

/**
 *Records the cloud conditions
 * @author sebastian.braun
 */
public class Cloud {

    private String cloud;

    public Cloud() {
        int level = (int) (Math.random() * 4);
        if (level == 0) {
            this.cloud = "None";
        } else if (level == 1) {
            this.cloud = "Light";
        } else if (level == 2) {
            this.cloud = "Medium";
        } else if (level == 3) {
            this.cloud = "Heavy";

        }
    }

    public String toString() {
        return this.cloud;
    }

}
