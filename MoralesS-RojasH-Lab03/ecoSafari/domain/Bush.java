package domain;
import java.awt.Color;
/**
 * Write a description of class Bush here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bush extends Organism implements Entity

{
    // instance variables - replace the example below with your own
    private EcoSafari habitat;
    private int count;
    private boolean justBorn;

    /**
     * Constructor for objects of class Bush
     */
    public Bush(EcoSafari habitat, int row, int column)
    {
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        count = 0;
        justBorn = false;
    }
    
    @Override
    public Color getColor(){
        return (count<4 ? Color.GREEN : Color.YELLOW);
    }
    
    @Override
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    private boolean elephantNearby(){
        int[] posicion = habitat.find(this);
        int fila = posicion[0];
        int columna = posicion[1];
        int[][] direcciones = {{-1, 0}, {-1, 1}, {0, 1}, {1,1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        for (int[] pos : direcciones){
            Entity e = habitat.get(fila + pos[0], columna+pos[1]);
            if (e instanceof Elephant){
                return true;
            }
        }
        return false;
    }
    
    private int[] reproduce(){
        if (count>=2 && this.getColor().equals(Color.GREEN)){
            int[] posicion = habitat.find(this);
            int fila = posicion[0];
            int columna = posicion[1];
            int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] pos : direcciones){
                Entity e = habitat.get(fila + pos[0], columna + pos[1]);
                if  (e == null){
                    int[] gap = new int[] {fila + pos[0], columna + pos[1]};
                   return gap;
                } 
            }
        }   
        return null;
    }
    
    @Override
    public void tic(){
        if (justBorn){
            justBorn = false;
            return;
        }
        count++;
        if (this.elephantNearby()){
            this.disappear();
        }
        else {
            int[] gap = this.reproduce();
            if (gap !=null){
                Bush hijo  = new Bush(getHabitat(), gap[0], gap[1]);
                hijo.justBorn = true;
            }
        }
    }
}