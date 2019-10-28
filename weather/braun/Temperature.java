/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.blackburn.cs.cs212.weather.braun;

/**
 * Record temperature in degrees Celcius
 *
 * @author sebastian.braun
 */
public class Temperature extends Measurement {

    public Temperature(double degree) {
        super(degree, "C");
    }

    public String toString() {
        return (int) (this.getValue()) + " C/ " + (int) (this.getValue() * 1.8 + 32) + " F";
    }

    public void tempCloud(Cloud cloud) {
        if (cloud.toString().equals("None")) {
            this.setValue((this.getValue()) + 6);
        } else if (cloud.toString().equals("Light")) {
            this.setValue(this.getValue() + 3);
        } else if (cloud.toString().equals("Medium")) {
            this.setValue(this.getValue() - 3);
        } else if (cloud.toString().equals("Heavy")) {
            this.setValue(this.getValue() - 14);
        }
    }
}
