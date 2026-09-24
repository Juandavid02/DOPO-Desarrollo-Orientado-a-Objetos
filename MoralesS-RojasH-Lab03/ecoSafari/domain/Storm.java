package domain;
import java.awt.Color;

/**
 * Tormenta del safari. Cada turno se mueve una casilla en diagonal
 * (arriba a la derecha) y afecta las casillas de alrededor.
 *
 * @author MoralesS-RojasH
 */
public class Storm implements Entity
{
    private EcoSafari habitat;
    private boolean hasCenter;

    /**
     * Crea una tormenta y la pone en el hábitat.
     *
     * @param habitat el safari donde está la tormenta
     * @param row fila donde queda
     * @param column columna donde queda
     */
    public Storm(EcoSafari habitat, int row, int column)
    {
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasCenter = false;
    }

    /**
     * Turno de la tormenta. Quita el efecto de la zona donde estaba,
     * se mueve una casilla arriba a la derecha (si se sale del safari,
     * aparece por el lado contrario) y marca como afectada la nueva zona de 3x3.
     */
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
    
    /**
     * Devuelve el color de la tormenta.
     *
     * @return el color negro
     */
    public Color getColor(){
        return Color.BLACK;
    }
    
    /**
     * Devuelve el hábitat donde está la tormenta.
     *
     * @return el safari de la tormenta
     */
    public EcoSafari getHabitat(){
        return habitat;
    }
}