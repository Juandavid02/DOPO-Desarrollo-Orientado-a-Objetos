package domain;
import java.util.ArrayList;


/**
 * Write a description of class Animal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Animal extends Organism implements Entity{
    
    private EcoSafari habitat;
    /**
     * Constructor for objects of class Animal
     */
    public Animal(EcoSafari habitat, int row, int col){
        this.habitat = habitat;
        habitat.set((Entity)this, row, col);
    }
    
    public EcoSafari getHabitat(){
        return this.habitat;
    }
    
    public boolean isAlive(){
        if (this.getEnergy()<=0){
            return false;
        }
        return true;
    }
    
    /**
     * Devuelve las posiciones de las casillas vecinas que están dentro del safari.
     *
     * @param row la fila
     * @param col la columna
     * @return lista con las posiciones {fila, columna} de las vecinas
     */
    public ArrayList<int[]> neighborsNearby(int row, int col){
        ArrayList<int[]> vecinos = new ArrayList<int[]>();
        int[][] direcciones = {{-1, 0}, {-1, 1}, {0, 1}, {1,1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        for (int[] pos : direcciones){
            int r = row + pos[0];
            int c = col + pos[1];
            if (habitat.isInside(r, c)){
                vecinos.add(new int[]{r, c});
            }
        }
        return vecinos;
    }
    
    public int[] findFood(int row, int col){
        ArrayList<int[]> vecinos = neighborsNearby(row, col);
        for (int[] v : vecinos){
            Entity e = habitat.get(v[0], v[1]);
            if (e != null && isFood(e)){
                return v;
            }
        }
        return null;
    }
    
    public abstract boolean isFood(Entity e);
    
}