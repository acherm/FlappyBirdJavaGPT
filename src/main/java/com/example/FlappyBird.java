package com.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlappyBird implements ActionListener {
    public static final int WIDTH = 800, HEIGHT = 600;

    private JFrame frame;
    private Timer timer;
    private Random rand;

    private List<Pipe> pipes;
    private Bird bird;

    private boolean gameOver, started;
    private int ticks, yMotion, score;

    public FlappyBird() {
        frame = new JFrame();
        timer = new Timer(20, this);
        rand = new Random();
        pipes = new ArrayList<>();
        bird = new Bird(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    jump();
                }
            }
        });

        frame.setTitle("Flappy Bird");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Renderer());
        frame.setVisible(true);

        addPipe(true);
        addPipe(true);
        addPipe(true);
        addPipe(true);

        timer.start();
    }

    public int getScore() {
        return score;
    }
    

    public void jump() {
        if (gameOver) {
            bird = new Bird(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            pipes.clear();
            yMotion = 0;
            score = 0;

            addPipe(true);
            addPipe(true);
            addPipe(true);
            addPipe(true);

            gameOver = false;
        }

        if (!started) {
            started = true;
        }

        if (yMotion > 0) {
            yMotion = 0;
        }

        yMotion -= 10;
    }

    public void addPipe(boolean start) {
        int space = 250;
        int width = 20;
        int height = 50 + rand.nextInt(300);

        if (start) {
            pipes.add(new Pipe(WIDTH + width + pipes.size() * 300, height, width, space));
        } else {
            pipes.add(new Pipe(pipes.get(pipes.size() - 1).getLowerPipe().x + 600, height, width, space));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 4;

        ticks++;

        if (started) {
            for (Pipe pipe : pipes) {
                pipe.move(-speed);

                // Check if bird just passed the pipe to update the score
                if (!gameOver && bird.getX() + bird.getWidth() > pipe.getUpperPipe().x && bird.getX() + bird.getWidth() <= pipe.getUpperPipe().x + speed) {
                    score++;
                }
            }

            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }

            bird.setY(bird.getY() + yMotion);

            for (Pipe pipe : pipes) {
                if (pipe.collidesWith(bird)) {
                    gameOver = true;
                    bird.setY(pipe.getUpperPipe().y + pipe.getUpperPipe().height);
                }
            }

            if (bird.getY() > HEIGHT || bird.getY() < 0) {
                gameOver = true;
            }

            if (!gameOver && ticks % 100 == 0) {
                addPipe(false);
            }
        }

        frame.repaint();
    }

    private class Renderer extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.cyan);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            g.setColor(Color.orange);
            g.fillRect(0, HEIGHT - 150, WIDTH, 150);

            g.setColor(Color.green);
            g.fillRect(0, HEIGHT - 150, WIDTH, 20);

            g.setColor(Color.red);
            g.fillRect(bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());

            for (Pipe pipe : pipes) {
                g.setColor(Color.GREEN);
                g.fillRect(pipe.getUpperPipe().x, pipe.getUpperPipe().y, pipe.getUpperPipe().width, pipe.getUpperPipe().height);
                g.fillRect(pipe.getLowerPipe().x, pipe.getLowerPipe().y, pipe.getLowerPipe().width, pipe.getLowerPipe().height);
            }

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 100));

            if (!started) {
                g.drawString("Press SPACE to Start", 50, 100);
            }

            if (gameOver) {
                g.drawString("Game Over", 100, 100);
            }

            if (!gameOver && started) {
                g.drawString(String.valueOf(score), 10, 100);
            }
        }
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

    public int getYMotion() {
        return yMotion;
    }

    public Bird getBird() {
        return bird;
    }

    public List<Pipe> getPipes() {
        return pipes;
    }

    public boolean isGameOver() {
        return gameOver;
    }

   

    public boolean checkCollision() {
        for (Pipe pipe : pipes) {
            if (pipe.collidesWith(bird)) {
                return true;
            }
        }
        return false;
    }
    
}

class Bird {
    private int x, y, width, height;
    private Rectangle bounds;

    public Bird(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void setY(int i) {
        this.y = i;
        this.bounds.y = i;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds.setBounds(x, y, width, height);
    }

    // Additional methods for bird movement or other functionalities can be added here

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

class Pipe {
    private Rectangle upperPipe;
    private Rectangle lowerPipe;

    public Pipe(int x, int y, int width, int heightGap) {
        upperPipe = new Rectangle(x, 0, width, y);
        lowerPipe = new Rectangle(x, y + heightGap, width, FlappyBird.HEIGHT - y - heightGap);
    }

    public void move(int speed) {
        upperPipe.x += speed;
        lowerPipe.x += speed;
    }

    public Rectangle getUpperPipe() {
        return upperPipe;
    }

    public Rectangle getLowerPipe() {
        return lowerPipe;
    }

    public boolean collidesWith(Bird bird) {
        return upperPipe.intersects(bird.getBounds()) || lowerPipe.intersects(bird.getBounds());
    }


        
}
