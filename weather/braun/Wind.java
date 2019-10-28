/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.blackburn.cs.cs212.weather.braun;

/**
 *References Kilometres to create wind conditions
 * @author sebastian.braun
 */
public class Wind {
    private Kilometres speed;
    private String direction;
    
    public Wind() {
          double random = (Math.random() * 20);
          this.speed = new Kilometres(random);
          int randomDirection = (int) (Math.random() * 2);
        if (randomDirection == 0) {
            direction = "N";
        } else {
            direction = "S";
        }
    }
    
    public String toString(){
        int sum = (int) speed.getValue();
        return "" + sum + "" + speed.getUnit() + "/ " + (int) (sum / 1.609) + "mph "
                + this.direction;
    }
    public int tempWind(){
        int change;
        if(direction.equals("S")){
            change = (int) (0.5 * this.speed.getValue());
        }else{ change = (int) (0.65 * this.speed.getValue());
        
        }return change;
    }
}
