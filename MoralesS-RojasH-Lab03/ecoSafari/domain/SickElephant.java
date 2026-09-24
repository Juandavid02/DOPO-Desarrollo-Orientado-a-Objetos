package domain;


/**
 * Write a description of class SickElephant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SickElephant extends Elephant
{
    private int countTicTac;
    private boolean hasActed;
    /**
     * Constructor for objects of class SickElephant
     */
    public SickElephant(EcoSafari habitat,int row, int column)
    {
        super(habitat, row, column);
        countTicTac = 0;
        hasActed=false;
    }
    
    @Override
    public void tic() {
        if (!hasActed){
            if (countTicTac % 2 == 0) {
                if (move(1, 1)){
                    changeEnergy(-20);
                    countTicTac++;
                    if (getEnergy()<=0){
                        disappear();
                    }
                } 
            }
            else {
                changeEnergy(-10);
                countTicTac++;
                if (getEnergy()<=0){
                    disappear();
                }
            }
            hasActed=true;
        }
    }
    
    @Override
    public void tac(){
        hasActed=false;
    }    
}