package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;


public class FlappyScoreTest {

    private FlappyBird game;

    @Before
    public void setUp() {
        game = new FlappyBird();
    }


    @Test
    public void testBirdJumpsWhenCalled() {
        int originalYMotion = game.getYMotion();
        game.jump();
        assertTrue("Expected yMotion to decrease when jump is called", game.getYMotion() < originalYMotion);
    }

    @Test
public void testBirdHitsGround() {
    game = new FlappyBird();
    game.getBird().setY(17); // Place the bird near the ground.
    game.jump();  // Start the game
    game.actionPerformed(null);  // Progress the game
    game.actionPerformed(null);  // Progress the game
    assertTrue("Expected game to be over when bird hits the ground", game.isGameOver());
}

@Test
public void testGameResetsOnGameOver() {
    game.getBird().setY(FlappyBird.HEIGHT - 20); // Force a game over scenario
    game.jump();  // Start the game
    game.actionPerformed(null);  // Progress the game
    game.jump();  // This should reset the game after game over
    assertFalse("Expected game to reset after jump is called post game over", game.isGameOver());
    assertEquals("Expected score to reset to 0 after game reset", 0, game.getScore());
}

@Test
public void testPipeCreation() {
    int initialPipeCount = game.getPipes().size();
    for (int i = 0; i < 150; i++) { // Simulate game progression
        game.actionPerformed(null);
    }
    assertTrue("Expected new pipes to be added as game progresses", game.getPipes().size() >= initialPipeCount);
}


@Test
public void testBirdFallsFromTop() {
    game = new FlappyBird();
    game.getBird().setY(0);  // Place the bird at the top.
    game.jump();  // Start the game
    for (int i = 0; i < 10; i++) {  // Simulating multiple game ticks to allow the bird to fall
        game.actionPerformed(null);
    }
    assertTrue("Expected game to be over when bird hits the ground from top", game.isGameOver());
}


@Test
public void testBirdUpwardMotionNearGround() {
    game = new FlappyBird();
    game.getBird().setY(FlappyBird.HEIGHT - 30);  // Place the bird just slightly above the ground.
    game.jump();  // Start the game and make it move upwards
    game.actionPerformed(null);  // Progress the game
    assertFalse("Expected game not to be over when bird jumps upwards near the ground", game.isGameOver());
    for (int i = 0; i < 100; i++) {  // Allow the bird to fall down after jumping
        game.actionPerformed(null);
    }
    assertTrue("Expected game to be over when bird hits the ground after jumping", game.isGameOver());
}






    
}
