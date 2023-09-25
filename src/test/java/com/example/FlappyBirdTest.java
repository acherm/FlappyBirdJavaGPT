package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.awt.Rectangle;

import org.junit.Test;


public class FlappyBirdTest {

    @Test
    public void testBirdJump() {
        FlappyBird game = new FlappyBird();
        int initialYMotion = game.getYMotion();
        game.jump();
        assertTrue(game.getYMotion() < initialYMotion);
    }

    @Test
    public void testAddPipe() {
        FlappyBird game = new FlappyBird();
        int initialSize = game.getPipes().size();
        game.addPipe(true);
        assertEquals(initialSize + 1, game.getPipes().size());
    }

    

    @Test
    public void testGameOver2() {
        FlappyBird game = new FlappyBird();
        game.getBird().setBounds(10, 10, 20, 20);
        Rectangle testPipe = new Rectangle(10, 10, 20, 20);
        assertTrue(testPipe.intersects(game.getBird().getBounds()));
    }

    @Test
public void testGameOver3() {
    FlappyBird game = new FlappyBird();
    game.getBird().setBounds(10, 9, 20, 20);
    game.getPipes().add(new Pipe(10, 10, 20, 20));
    assertTrue(game.checkCollision());
}

@Test
public void testGameOver4() {
    FlappyBird game = new FlappyBird();
    game.getBird().setBounds(10, 9, 20, 20);
    Pipe testPipe = new Pipe(10, 10, 20, 250);
    game.getPipes().add(testPipe);

    assertTrue(testPipe.collidesWith(game.getBird()));
    // assertFalse(testPipe.collidesWithLower(game.getBird()));
}






    


}

