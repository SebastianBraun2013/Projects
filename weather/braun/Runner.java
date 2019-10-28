/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.blackburn.cs.cs212.weather.braun;

/**
 * 8:15 - 9:30 1/30/18
 *5:15 - 10:05
 * iterates and impliments the aspects of the weather forcast
 * @author sebastian.braun
 */
public class Runner {

    public static void main(String[] args) {

        System.out.println("Day " + 1 + ":");
        Temperature t1 = new Temperature(0.0);
        System.out.println("Morning Temperature: " + t1);

        Cloud c1 = new Cloud();
        System.out.println("Clouds: " + c1);
        t1.tempCloud(c1);
        System.out.println("Midday Temperature: " + t1);

        Precipitation p1 = new Precipitation(t1);
        System.out.println("Precipitation: " + p1);

        Wind w1 = new Wind();
        System.out.println("Wind: " + w1);
        System.out.println("");
        
        double yesterday = t1.getValue() + w1.tempWind() - p1.tempPrecipitation();
        
        for (int i = 0; i < 9; i++) {

        System.out.println("Day " + (i+2) + ":");
        Temperature t2 = new Temperature((yesterday));
        System.out.println("Morning Temperature: " + t2);

        Cloud c2 = new Cloud();
        System.out.println("Clouds: " + c2);
        t2.tempCloud(c2);
        System.out.println("Midday Temperature: " + t2);

        Precipitation p2 = new Precipitation(t2);
        System.out.println("Precipitation: " + p2);

        Wind w2 = new Wind();
        System.out.println("Wind: " + w2);
        System.out.println("");
        yesterday = t2.getValue() + w2.tempWind() - p2.tempPrecipitation();
        }


    }
}
