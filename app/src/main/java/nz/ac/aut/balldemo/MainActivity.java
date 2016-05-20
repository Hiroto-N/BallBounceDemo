package nz.ac.aut.balldemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BallView bv = new BallView(this);

        Button button = new Button(this);

        setContentView(bv);


    }

    public class BallView extends View {

        Paint paint;
        Ball ball;
        Paddle p;
        Handler h = new Handler();
        Random rand = new Random();


        Runnable r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        public BallView(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.RED);
            ball = new Ball();
            p = new Paddle();


        }


        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (event.getX() > getWidth()/2) {
                        p.velocity = 20;
                    } else {
                        p.velocity = -20;
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    p.velocity = 0;
                    break;

            }
            return false;
        }


        @Override
        protected void onDraw(Canvas c) {
            super.onDraw(c);

            paint.setStyle(Paint.Style.FILL);

            if (ball.loc_x + 100 >= this.getWidth() || ball.loc_x - 100 <= 0) {
                ball.velocity_x *= -1;
                //paint.setColor(new Color().rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            }
            if (ball.loc_y + 100 >= this.getHeight() || ball.loc_y - 100 <= 0) {
                ball.velocity_y *= -1;
                //paint.setColor(new Color().rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            }

            ball.loc_x += ball.velocity_x;
            ball.loc_y += ball.velocity_y;
            p.loc_x = p.loc_x+p.velocity;
            Log.d("yolo", String.valueOf(p.velocity));

            c.drawCircle(ball.loc_x, ball.loc_y, 100, paint);
            c.drawRect(p.loc_x-100, getHeight()-70, p.loc_x+100,getHeight()-40, paint);
            h.postDelayed(r, 10);
        }
    }

    public class Ball {
        int loc_x = 600;
        int loc_y = 600;
        int velocity_x = 10;
        int velocity_y = 10;
    }

    public class Paddle {
        int loc_x = 500;
        int velocity = 0;
    }

}
