package domain;
import java.awt.Color;

public class Storm implements Entity
{
    private EcoSafari habitat;
    private boolean hasCenter;

    public Storm(EcoSafari habitat, int row, int column)
    {
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasCenter = false;
    }

    public void tic(){
        int[] posicion = habitat.find(this);
        int fila = posicion[0] - 1;
        int columna = posicion[1] + 1;
        if (fila == -1){
            fila = habitat.getSize()-1; 
        }
        if (columna == habitat.getSize()){
            columna = 0;
        }

        if (hasCenter){
            for (int i = -1; i<=1; i++){
                for (int j = -1; j<=1; j++)
                    habitat.unsetAffected(posicion[0]+i, posicion[1]+j);
            }
        }

        habitat.set(null, posicion[0], posicion[1]);
        habitat.set(this, fila, columna);

        for (int i = -1; i<=1; i++){
            for (int j = -1; j<=1; j++)
                habitat.setAffected(fila+i, columna+j);
        }
        hasCenter = true;
    }
    
    public Color getColor(){
        return Color.BLACK;
    }
    
    public EcoSafari getHabitat(){
        return habitat;
    }
}