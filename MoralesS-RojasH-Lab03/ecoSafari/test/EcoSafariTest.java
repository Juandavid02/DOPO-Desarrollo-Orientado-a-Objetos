package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import domain.EcoSafari;
import domain.Elephant;
import domain.Bush;
import domain.Storm;
import domain.SickElephant;
import domain.Entity;
import domain.SaltLick;
import domain.Grass;
import domain.Soil;
import java.util.Random;

public class EcoSafariTest
{
    /** Generador aleatorio con una respuesta fija, de modo que las pruebas del suelo siempre den el mismo resultado */
    private static class FixedRandom extends Random
    {
        private final double value;
        FixedRandom(double value){ this.value = value; }
        @Override public double nextDouble(){ return value; }
    }
    private EcoSafari safari;

    @BeforeEach
    public void setUp()
    {
        safari = new EcoSafari();
    }
    
    //Pruenas de los elefantes
    
    @Test
    public void testTicTacMuevePosicionDelElephant()
    {
        Elephant dumbo = new Elephant(safari, 5, 5);

        safari.ticTac();

        int[] posicion = safari.find(dumbo);
        assertEquals(6, posicion[0]); // fila
        assertEquals(6, posicion[1]); // columna
    }

    @Test
    public void shouldLessEnergy()
    {
        Elephant dumbo = new Elephant(safari, 5, 5);
        safari.ticTac();
        assertEquals(90, dumbo.getEnergy());
    }
    
    //Pruenas de los arbusto
    @Test
    public void shouldChangeColor()
    {
        Bush arbustin = new Bush (safari, 1, 1);
        assertEquals(Color.GREEN, arbustin.getColor());
        safari.ticTac();
        safari.ticTac();
        safari.ticTac();
        safari.ticTac();
        assertEquals(Color.YELLOW, arbustin.getColor());
    }
    
    @Test
    public void shouldDisappearWhenElephantIsNearby()
    {
        Elephant dumbo = new Elephant(safari, 5, 5);
        Bush arbustin = new Bush (safari, 5, 6);
        safari.ticTac();
        assertNull(safari.find(arbustin));
    }
    
    @Test
    public void shouldReproduce()
    {
        Bush arbustin = new Bush (safari, 2, 2);
        safari.ticTac();
        safari.ticTac();
        assertTrue(safari.get(1, 2) instanceof Bush);
        safari.ticTac();
        assertTrue(safari.get(3, 2) instanceof Bush);
    }
    
    //Pruebas de la tormenta
    @Test
    public void shouldMoveCenterNortheast()
    {
        Storm tormenta = new Storm(safari, 1, 1);
        safari.ticTac();
        assertTrue(safari.get(0, 2)instanceof Storm);
        
    }

    @Test
    public void shouldbeBlack(){
        Storm tormenta = new Storm(safari, 1, 1);
        assertEquals(Color.BLACK, tormenta.getColor());
    }

    //Pruebas del Elefante Enfermo 
    @Test 
    public void shouldMoveSlowly(){
        SickElephant rayo = new SickElephant(safari, 2, 3);
        safari.ticTac();
        safari.ticTac();
        assertTrue(safari.get(3, 4) instanceof SickElephant);
    }    

    @Test
    public void shouldLoseHealthFaster(){
        SickElephant rayo = new SickElephant(safari, 2, 3);
        Elephant dumbo = new Elephant(safari, 5, 5);
        safari.ticTac();
        assertEquals(80, rayo.getEnergy());
        assertTrue(rayo.getEnergy() < dumbo.getEnergy()); 
    }
    
    @Test
    public void shouldMaintainTheEnergyAndTheEnergySourcesAreIndependent(){
        SickElephant rayo = new SickElephant(safari, 15, 24);
        SickElephant casco = new SickElephant(safari, 6, 4);
        safari.ticTac();
        assertEquals(100, rayo.getEnergy());
        assertTrue(safari.get(15, 24) instanceof SickElephant);
        safari.ticTac();
        assertEquals(70, casco.getEnergy());
    }
    
    // Pruebas del SaltLick
    /**
     * Prueba que el SaltLick restaure la energía de un elefante vecino en su vecindad.
     */
    @Test
    public void shouldRestoreEnergyOfNeighborElephant()
    {
        Elephant dumbo = new Elephant(safari, 20, 4);
        dumbo.changeEnergy(-50);
        SaltLick lick = new SaltLick(safari, 20, 5);
        lick.tic();
        assertEquals(60, dumbo.getEnergy());
    }
    
    /**
     * Prueba que el SaltLick no afecte a un elefante que se encuentra fuera de su vecindad.
     */
    @Test
    public void shouldNotAffectElephantThatIsNotNeighbor()
    {
        Elephant dumbo = new Elephant(safari, 20, 4);
        dumbo.changeEnergy(-50);
        SaltLick lick = new SaltLick(safari, 20, 6);
        lick.tic();
        assertEquals(50, dumbo.getEnergy());
    }
 
    /**
     * Prueba que la restauración de energía del SaltLick no supere el límite máximo de energía del organismo (100).
     */
    @Test
    public void shouldNotExceedMaximumEnergy()
    {
        Elephant dumbo = new Elephant(safari, 20, 4);
        dumbo.changeEnergy(-5);
        SaltLick lick = new SaltLick(safari, 21, 4);
        lick.tic();
        assertEquals(100, dumbo.getEnergy());
    }
 
    /**
     * Prueba que el SaltLick permanezca estático en su posición original tras varios ciclos de simulación.
     */
    @Test
    public void shouldStayInPlace()
    {
        SaltLick lick = new SaltLick(safari, 20, 5);
        safari.ticTac();
        safari.ticTac();
        safari.ticTac();
        assertSame(lick, safari.get(20, 5));
    }
 
    /**
     * Prueba que el SaltLick devuelva el color rosa, la forma cuadrada y valide que no es un organismo vivo.
     */
    @Test
    public void shouldBePinkSquareAndNotOrganism()
    {
        SaltLick lick = new SaltLick(safari, 20, 5);
        assertEquals(Color.PINK, lick.getColor());
        assertEquals(Entity.SQUARE, lick.shape());
        assertFalse(lick.isOrganism());
    }
 
    /**
     * Prueba significativa (de aceptación): un par de SaltLicks, cesar y Juan,
     * colocados a lo largo del camino diagonal de un elefante lo mantienen con la energía al máximo
     * durante cuatro tic-tacs, mientras que un elefante idéntico sin SaltLicks (control)
     * pierde 10 puntos de energía por paso.
     */
    @Test
    public void shouldKeepElephantHealthyBetweenPairOfSaltLicks()
    {
        Elephant babar   = new Elephant(safari, 16, 1);
        Elephant control = new Elephant(safari, 15, 15);
        SaltLick cesar     = new SaltLick(safari, 18, 2);
        SaltLick juan = new SaltLick(safari, 20, 4);
        for (int i = 0; i < 4; i++){
            safari.ticTac();
        }
        assertEquals(100, babar.getEnergy());
        assertEquals(Color.DARK_GRAY, babar.getColor());
        assertEquals(60, control.getEnergy());
        assertEquals(Color.LIGHT_GRAY, control.getColor());
    }
    
    //pruebas de la tierra y el pasto
    /**
     * Pruebaque la tierra genere pasto cuando la probabilidad aleatoria es menor o igual
     * al límite permitido (en este caso, 0.05 es menor que el 10%).
     */
    @Test
    public void shouldGrowGrassWhenProbabilityAllows()
    {
        safari.setRandom(new FixedRandom(0.05));
        Soil soil = new Soil(safari, 20, 15);
        soil.tic();
        assertTrue(safari.get(20, 15) instanceof Grass);
    }
 
    /**
     * Prueba que la tierra no genere pasto cuando la probabilidad aleatoria supera el límite
     * establecido (en este caso, 0.5 es mayor que el 10%).
     */
    @Test
    public void shouldNotGrowGrassWhenProbabilityDoesNotAllow()
    {
        safari.setRandom(new FixedRandom(0.5));
        Soil soil = new Soil(safari, 20, 15);
        soil.tic();
        assertSame(soil, safari.get(20, 15));
    }
 
    /**
     * Prueba que al desaparecer el pasto (por ejemplo, al ser comida), la celda sea
     * reemplazada correctamente por una instancia de suelo.
     */
    @Test
    public void shouldLeaveSoilWhenGrassDisappears()
    {
        Grass grass = new Grass(safari, 20, 15);
        assertTrue(grass.disappear());
        assertTrue(safari.get(20, 15) instanceof Soil);
    }
 
    /**
     * Prueba de integración que verifica que el crecimiento de el pasto ocurra de forma normal
     * durante la ejecución de un ciclo completo de la simulación (ticTac).
     */
    @Test
    public void shouldGrowGrassDuringTicTac()
    {
        safari.setRandom(new FixedRandom(0.05));
        Soil soil = new Soil(safari, 20, 15);
        safari.ticTac();
        assertTrue(safari.get(20, 15) instanceof Grass);
    }

    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}