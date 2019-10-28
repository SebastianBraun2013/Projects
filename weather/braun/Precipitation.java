/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.blackburn.cs.cs212.weather.braun;

/**
 *Uses centimeters to create precipitation conditions
 * @author sebastian.braun
 */
public class Precipitation {

    private Centimeters amount;
    private boolean frozen;

    public Precipitation(Temperature temp) {
        double random = (Math.random() * 10);
        this.frozen = temp.getValue() <= 0;
        if (this.frozen) {
            random *= 10;
        }
        this.amount = new Centimeters(random);
    }

    public boolean isSnow() {
        return this.frozen;
    }

    public Centimeters getAmount() {
        return this.amount;
    }

    public String toString() {
        String rainSnow = "";
        if (frozen) {
            rainSnow = "Snow";
        } else {
            rainSnow = "Rain";
        }
        int sum = (int) amount.getValue();
        return "" + sum + "" + amount.getUnit() + "/ " + (int) (sum / 2.54) + "in "
                + rainSnow;
    }
    
    public int tempPrecipitation(){
        int change;
        if(frozen){
            change = (int) (0.15 * this.amount.getValue());
        }else { change = (int) (0.9 * this.amount.getValue());}
        return change;
    }
}
