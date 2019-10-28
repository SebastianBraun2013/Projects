/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.blackburn.cs.cs212.weather.braun;

/**
 *Super-duper class
 * @author sebastian.braun
 */
public class Measurement {

    private double value;
    private String unit;

    public Measurement(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public String toString() {
        return this.getValue() + " " + this.getUnit();
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double change) {
        this.value = change;
    }

    public String getUnit() {
        return this.unit;
    }
}
