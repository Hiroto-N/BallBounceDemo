package nz.ac.aut.balldemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BallView bv = new BallView(this);

        Button button = new Button(this);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(bv);


    }

    public class BallView extends View {

        Paint paint;
        GameLogic game ;
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
            paint.setColor(Color.WHITE);
            game = new GameLogic();
            this.setBackgroundColor(Color.BLACK);

        }

        public boolean onTouchEvent(MotionEvent event) {

            if (game.gameRunning) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (event.getX() > getWidth() / 2) {
                            game.p.velocity = 20;
                        } else {
                            game.p.velocity = -20;
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        game.p.velocity = 0;
                        break;

                }
                return true;
            }
            else {
                game.init(getWidth(), getHeight());
                return false;
            }
        }

        @Override
        protected void onDraw(Canvas c) {
            super.onDraw(c);
            if (game.gameRunning) {

                paint.setStyle(Paint.Style.FILL);
                game.update();

                c.drawCircle(game.ball.loc_x, game.ball.loc_y, game.ball.getRadius(), paint);
                c.drawRect(game.p.getPaddleDim(), paint);

                h.postDelayed(r, 10);
            }
            else {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(getWidth()/15);
                c.drawText("Touch Screen to Start", getWidth()/2, getHeight()/2, paint);
                h.postDelayed(r, 30);
            }
        }
    }

}
