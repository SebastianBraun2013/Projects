/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sebastianbraun.dictionary;

/**
 *
 * @author sebastian.braun
 */
public class HashTableSlot<Key, E> {
    public Key key;
    public E value;
    public boolean occupied;
    
    public void setHashTableSlot(Object key, Object value){
        this.key = (Key) key;
        this.value = (E) value;
        this.occupied = false;
    }

    
    public Key getKey(){
        return this.key;
    }
    
    public E getValue(){
        return this.value;
    }
    
    public void filled(){
        this.occupied = true;
    }
    
    public void emptied(){
        this.occupied = false;
    }
    
    @Override
    public String toString(){
        return "key = " + this.key + "value = " + this.value;
    }
}
