package domain;
import java.awt.Color;

/**
 * Write a description of class Lion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lion extends Animal
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Lion
     */
    public Lion(EcoSafari habitat, int row, int col)
    {
        super(habitat, row, col);
    }
    
    @Override
    public boolean isFood(Entity e){
        if (e instanceof Zebra){
            return true;    
        } 
        return false;
    }
    
    @Override
    public int speed(){
        return 1;
    }
    
    @Override
    public float energyGain(){
        return 0.5f;
    }
    
    @Override
    public void createOffspring(int row, int col){
         new Lion(this.getHabitat(), row, col);    
    }
    
    public Color getColor(){
        return Color.ORANGE;
    }
}