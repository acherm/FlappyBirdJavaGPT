package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlappyBirdV1 implements ActionListener {
    private JFrame jFrame;
    private Timer timer;
    private List<Rectangle> pipes;
    private int ticks, yMotion;
    private boolean gameOver, started;
    private static final int WIDTH = 800, HEIGHT = 600;

    public FlappyBirdV1() {
        jFrame = new JFrame();
        pipes = new ArrayList<>();

        jFrame.setTitle("Flappy Bird");
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    jump();
                }
            }
        });

        addPipe(true);
        addPipe(true);
        addPipe(true);
        addPipe(true);

        timer = new Timer(20, this);
        timer.start();
    }

    private void jump() {
        if (!started) {
            started = true;
        }

        if (yMotion > 0) {
            yMotion = 0;
        }

        yMotion -= 10;
    }

    private void addPipe(boolean start) {
        int space = 300;
        int width = 50;
        int height = 50 + new Random().nextInt(300);
        
        if (start) {
            pipes.add(new Rectangle(WIDTH + width + pipes.size() * 300, HEIGHT - height, width, height));
            pipes.add(new Rectangle(WIDTH + width + (pipes.size() - 1) * 300, 0, width, HEIGHT - height - space));
        } else {
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, HEIGHT - height, width, height));
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        ticks++;

        if (started) {
            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= speed;
            }

            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }

            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                
                if (pipe.x + pipe.width < 0) {
                    pipes.remove(pipe);
                    
                    if (pipe.y == 0) {
                        addPipe(false);
                    }
                }
            }

            Rectangle bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10 + yMotion, 20, 20);

            for (Rectangle pipe : pipes) {
                if (pipe.intersects(bird)) {
                    gameOver = true;
                    bird.x = pipe.x - bird.width;
                }
            }

            if (bird.y > HEIGHT || bird.y < 0) {
                gameOver = true;
            }

            if (bird.y + yMotion >= HEIGHT) {
                bird.y = HEIGHT - bird.height - yMotion;
            }
        }

        jFrame.repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(WIDTH / 2 - 10, HEIGHT / 2 - 10 + yMotion, 20, 20);

        for (Rectangle pipe : pipes) {
            g.setColor(Color.green);
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));

        if (!started) {
            g.drawString("Press SPACE to Start", 50, HEIGHT / 2 - 50);
        }

        if (gameOver) {
            g.drawString("Game Over", 100, HEIGHT / 2 - 50);
        }
    }

    public static void main(String[] args) {
        new FlappyBirdV1();
    }
}