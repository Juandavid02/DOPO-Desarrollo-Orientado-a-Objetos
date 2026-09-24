package domain;
import java.awt.Color;

/**
 * Soil (tierra o suelo) de la zona.
 * Los animales solo pueden caminar sobre el suelo. En cada tic, una celda de suelo puede generar pasto
 * con una probabilidad de GRASS_PROBABILITY (10%).
 */
public class Soil implements Entity{
    public static final double GRASS_PROBABILITY = 0.10;

    private final EcoSafari habitat;
    private boolean hasActed;

    /**
     * Creates a new Soil and places it in the habitat
     * @param habitat the EcoSafari where it lives
     * @param row the row
     * @param column the column
     */
    public Soil(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasActed = false;
    }

    /**
     * Con una probabilidad del 10% de que la tierra se convierta en pasto
     */
    @Override
    public void tic(){
        if (!hasActed){
            hasActed = true;
            if (habitat.getRandom().nextDouble() < GRASS_PROBABILITY){
                int[] position = habitat.find(this);
                if (position != null){
                    new Grass(habitat, position[0], position[1]);
                }
            }
        }
    }

    @Override
    public void tac(){
        hasActed = false;
    }

    @Override
    public Color getColor(){
        return new Color(181, 136, 99);
    }

    @Override
    public EcoSafari getHabitat(){
        return habitat;
    }
}