package domain;
import java.awt.Color;

/**
 * Cualquier cosa que puede estar en el safari (elefantes, arbustos, tormentas, etc.).
 * Tiene métodos para actuar cada turno, moverse y desaparecer.
 *
 * @author MoralesS-RojasH
 */
public interface Entity{
   public static final int SQUARE = 2;
   public static final int ROUND = 1;
    
   /**
    * Turno de la entidad. Cada clase decide qué hace.
    */
   public void tic();
  
   /**
    * Se llama después del turno para dejar a la entidad lista para el siguiente.
    * Por defecto no hace nada.
    */
   public default void tac(){
   }

   /**
    * Devuelve la forma de la entidad.
    *
    * @return la forma cuadrada, a menos que la clase la cambie
    */
   public default int shape(){
      return SQUARE;
   }
  
   /**
    * Devuelve el color de la entidad.
    *
    * @return el color de la entidad
    */
   public abstract Color getColor();
  
   /**
    * Dice si la entidad es un organismo.
    *
    * @return false, a menos que la clase lo cambie
    */
    public default boolean isOrganism(){
      return false;
    }

   /**
    * Devuelve el hábitat donde está la entidad.
    *
    * @return el safari de la entidad
    */
   public abstract EcoSafari getHabitat();
    
   /**
    * Quita la entidad del safari.
    *
    * @return true si estaba en el safari y se quitó, false si no la encontró
    */
   public default boolean disappear(){
     boolean ok=false;
     int [] position=this.getHabitat().find(this);
        if (position!=null){
            getHabitat().set(null,position[0],position[1]);
            ok=true;
        }
        return ok;
    }
    
    /**
     * Mueve la entidad sumándole filas y columnas a su posición actual.
     * No se mueve si la nueva posición queda fuera del safari.
     *
     * @param deltaRows cuántas filas se mueve
     * @param deltaColumns cuántas columnas se mueve
     * @return true si se pudo mover, false si no
     */
    public default  boolean move(int deltaRows, int deltaColumns){
        int [] position=getHabitat().find(this);
        EcoSafari habitat=getHabitat();
        boolean ok=false;
        if (position!=null){
            int r = position[0];
            int c = position[1];
            if (habitat.isInside(r+deltaRows,c+deltaColumns)){
                habitat.set(null,r,c);
                habitat.set(this,r+deltaRows,c+deltaColumns);
                ok=true;
            }
        }
        return ok;
    }
    
}