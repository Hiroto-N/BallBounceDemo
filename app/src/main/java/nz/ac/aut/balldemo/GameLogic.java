package nz.ac.aut.balldemo;

import android.graphics.Rect;

/**
 * Created by Naki on 24/05/2016.
 */
public class GameLogic {

    public boolean gameRunning;
    public int wins;
    public Ball ball;
    public Paddle p;
    public int screenWidth;
    public int screenHeight;

    public void init(int width, int height) {
        gameRunning = true;
        wins = 0;
        p = new Paddle(width, height);
        ball = new Ball(width, height);
        screenHeight = height;
        screenWidth = width;
    }

    public void update() {
        if (ball.loc_x + ball.getRadius() >= screenWidth || ball.loc_x - ball.getRadius() <= 0) {
            ball.velocity_x *= -1;
        }
        if (ball.loc_y - ball.getRadius() <= 0) {
            ball.velocity_y *= -1;
            //TODO this is where the info will send through bluetooth
        }
        if (ball.loc_y + ball.getRadius() >= p.loc_y) {
            if ((ball.loc_x <= p.getPaddleDim().right*1.05 && ball.loc_x >= p.getPaddleDim().left*0.95)) {
                ball.velocity_y *= -1;
                changeAngle();
            }
            else
                gameRunning = false;
        }
        moveBall();
        movePaddle();
    }


    private void movePaddle() {
        if (p.velocity > 0 && p.getPaddleDim().right <= screenWidth*0.98)
            p.loc_x += p.velocity;
        if (p.velocity < 0 && p.getPaddleDim().left >= screenWidth*0.02) {
            p.loc_x += p.velocity;
        }
    }

    private void moveBall() {
        ball.loc_x += ball.velocity_x;
        ball.loc_y += ball.velocity_y;
    }

    private void changeAngle() {
        if (ball.loc_x < p.loc_x)
            ball.velocity_x = -15;
        if (ball.loc_x > p.loc_x)
            ball.velocity_x = 15;
        if (ball.loc_x == p.loc_x)
            ball.velocity_x = 0;

    }

    public class Ball {
        int loc_x;
        int loc_y;
        int velocity_x = -15;
        int velocity_y = 15;
        int radius;

        public Ball(int width, int height) {
            radius = 32;
            loc_x = width/2;
            loc_y = p.loc_y - (width/radius)-50;
        }

        public int getRadius() {
            return screenWidth/radius;
        }

    }

    public class Paddle {
        int loc_x;
        int loc_y;
        int velocity;

        public Paddle(int width, int height) {
            loc_x = width/2;
            loc_y = (int) (height*0.95);
        }

        public Rect getPaddleDim() {
            return new Rect(loc_x - screenWidth/16,loc_y, loc_x + screenWidth/16, (int)(screenHeight*0.98));
        }
    }
}
