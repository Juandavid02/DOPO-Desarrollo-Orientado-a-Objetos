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

public class EcoSafariTest
{
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
    
    @Test
    public void shouldWrapAroundAtEdge(){}
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