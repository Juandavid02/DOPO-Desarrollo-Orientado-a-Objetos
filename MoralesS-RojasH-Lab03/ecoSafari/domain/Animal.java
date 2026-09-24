package domain;


/**
 * Animal de la zona depredador-presa (cebra o león). Tiene energía,
 * puede comer, moverse sobre tierra y reproducirse.
 *
 * @author MoralesS-RojasH
 */
public abstract class Animal extends Organism implements Entity{

    /**
     * Crea un animal y lo pone en el safari.
     *
     * @param habitat el safari donde vive
     * @param row la fila donde nace
     * @param col la columna donde nace
     */
    public Animal(EcoSafari habitat, int row, int col){
        this.habitat = habitat;
        habitat.set((Entity)this, row, col);
    }
    
    /**
     * Devuelve el safari donde está el animal.
     *
     * @return el safari del animal
     */
    public EcoSafari getHabitat(){
        return this.habitat;
    }
    
    /**
     * Dice si el animal sigue vivo.
     *
     * @return true si tiene energía, false si se quedó sin ella
     */
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
    
    /**
     * Busca una comida en las casillas vecinas.
     *
     * @param row la fila desde donde se mira
     * @param col la columna desde donde se mira
     * @return la posición {fila, columna} de la primera comida, o null si no hay
     */
    public Animal()
    {

    }

}