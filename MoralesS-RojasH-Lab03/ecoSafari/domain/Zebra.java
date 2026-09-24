package domain;
import java.awt.Color;

/**
 * Cebra. Avanza dos casillas por turno sobre tierra,
 * come el pasto que le queda vecino (gana 25% de energía) y se reproduce
 * si queda a una casilla de tierra de otra cebra.
 */
public class Zebra extends Animal{
    private boolean hasActed;
    
    /**
     * Crea una cebra y la pone en el safari.
     *
     * @param habitat el safari donde vive
     * @param row la fila donde nace
     * @param col la columna donde nace
     */
    public Zebra(EcoSafari habitat, int row, int col){
        super(habitat, row, col);
        hasActed = false;
    }
    
    /**
     * Acción de la cebra en el tic: camina, come y se reproduce.
     * Solo actúa una vez por tic-tac.
     */
    @Override
    public void tic(){
        if (hasActed || !isAlive()){
            return;
        }
        hasActed = true;
        int[] position = getHabitat().find(this);
        if (position == null){
            return;
        }
        for (int i = 0; i < speed(); i++){
            int[] land = findLand(position[0], position[1]);
            if (land == null){
                break;
            }
            moveTo(land[0], land[1]);
            position = land;
            loseEnergy();
            if (!isAlive()){
                disappear();
                return;
            }
        }
        int[] food = findFood(position[0], position[1]);
        if (food != null){
            eat(food[0], food[1]);
        }
        reproduce(position[0], position[1]);
    }
    
    /**
     * Prepara a la cebra para actuar en el siguiente tic-tac.
     */
    @Override
    public void tac(){
        hasActed = false;
    }
    
    /**
     * Las cebras comen pasto.
     *
     * @param e la entidad a revisar
     * @return true si es pasto
     */
    @Override
    public boolean isFood(Entity e){
        return e instanceof Grass;
    }
    
    /**
     * Las cebras avanzan dos casillas por turno.
     *
     * @return 2
     */
    @Override
    public int speed(){
        return 2;
    }
    
    /**
     * Al comer pasto ganan 25% de energía.
     *
     * @return 0.25f
     */
    @Override
    public float energyGain(){
        return 0.25f;
    }
    
    /**
     * Crea una cebra nueva. Nace con el turno ya gastado para que no
     * actúe en el mismo tic-tac en que nació.
     *
     * @param row la fila donde nace
     * @param col la columna donde nace
     */
    @Override
    public void createOffspring(int row, int col){
        Zebra baby = new Zebra(getHabitat(), row, col);
        baby.hasActed = true;
    }
    
    @Override
    public Color getColor(){
        return Color.BLUE;
    }
}