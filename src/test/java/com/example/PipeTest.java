package com.example;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.Test;

public class PipeTest {

    @Test
    public void testCollidesWithUpperPipe() {
        Pipe pipe = new Pipe(10, 10, 20, 250);
        Bird bird = new Bird(10, 5, 20, 20);
        assertTrue(pipe.collidesWith(bird));
    }

    @Test
    public void testCollidesWithLowerPipe() {
        Pipe pipe = new Pipe(10, 10, 20, 250);
        Bird bird = new Bird(10, 265, 20, 20);
        assertTrue(pipe.collidesWith(bird));
    }

    @Test
    public void testDoesNotCollide() {
        Pipe pipe = new Pipe(10, 10, 20, 250);
        Bird bird = new Bird(10, 50, 20, 20);
        assertFalse(pipe.collidesWith(bird));
    }

    @Test
    public void testAboveBothPipes() {
        Pipe pipe = new Pipe(10, 10, 20, 250);
        Bird bird = new Bird(10, -25, 20, 20); // Negative y-coordinate places the bird above the screen.
        assertFalse(pipe.collidesWith(bird));
    }

    @Test
    public void testBelowBothPipes() {
        Pipe pipe = new Pipe(10, 10, 20, 250);
        Bird bird = new Bird(10, FlappyBird.HEIGHT + 10, 20, 20); // Assuming FlappyBird.HEIGHT is the screen height.
        assertFalse(pipe.collidesWith(bird));
    }
}

