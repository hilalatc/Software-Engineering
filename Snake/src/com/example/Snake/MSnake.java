package com.example.Snake;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Hilal
 * Date: 10.11.2013
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class MSnake extends Activity {
    private SnakeView mSnakeView;

    private static String ICICLE_KEY = "snake-view";

    static final int DIALOG_ABOUT_ID = 0;
    static final int DIALOG_RECORDS_ID = 1;
    static final int DIALOG_WALLS_ID = 2;
    static final int DIALOG_SIZE_ID = 3;
    static final int DIALOG_SPEED_ID = 4;
    static final int DIALOG_NEWS_ID = 5;
    static final int DIALOG_EXIT_ID = 6;
    static final int DIALOG_INPUT_ID = 7;
    static final int DIALOG_SETTINGS_ID = 8;
    static final int DIALOG_WARMSETTINGS_ID = 9;

    private RadioGroup smsrg1;
    private RadioGroup smsrg2;
    private RadioGroup smsrg3;
    private RadioGroup smsrg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snake_layout);


        mSnakeView = (SnakeView) findViewById(R.id.snake);
        mSnakeView.setTextView((TextView) findViewById(R.id.text));
        mSnakeView.setScoreView((TextView) findViewById(R.id.textscore));
        mSnakeView.setRecordView((TextView) findViewById(R.id.textrecord));

        int dHeight = getWindowManager().getDefaultDisplay().getHeight();
        int dWidth = getWindowManager().getDefaultDisplay().getWidth();
        mSnakeView.setTileSizes(dWidth, dHeight);

        // Restore preferences
        SharedPreferences settings = getPreferences(0);
        mSnakeView.restorePreferences(settings);
        setCorrectButtons();
        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
            mSnakeView.setMode(SnakeView.READY);
        } else {
            // We are being restored
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mSnakeView.restoreState(map);
            } else {
                mSnakeView.setMode(SnakeView.READY);
            }
        }
        if (mSnakeView.showNews20) showDialog(DIALOG_NEWS_ID);
//    	Log.d(TAG, "onCreate end");
    }

    protected void setCorrectButtons() {
        Button mButton[] = new Button[6];

        mButton[0] = (Button) findViewById(R.id.button0);
        mButton[1] = (Button) findViewById(R.id.button1);
        mButton[2] = (Button) findViewById(R.id.button2);
        mButton[3] = (Button) findViewById(R.id.button3);
        mButton[4] = (Button) findViewById(R.id.button4);
        mButton[5] = (Button) findViewById(R.id.button5);

        if (mSnakeView.inputMode == SnakeView.INPUT_MODE_2K) {
            mButton[0].setVisibility(View.VISIBLE);
            mButton[1].setVisibility(View.GONE);
            mButton[2].setVisibility(View.GONE);
            mButton[3].setVisibility(View.GONE);
            mButton[4].setVisibility(View.GONE);
            mButton[5].setVisibility(View.VISIBLE);
        } else if (mSnakeView.inputMode == SnakeView.INPUT_MODE_4K) {
            mButton[0].setVisibility(View.GONE);
            mButton[1].setVisibility(View.VISIBLE);
            mButton[2].setVisibility(View.VISIBLE);
            mButton[3].setVisibility(View.VISIBLE);
            mButton[4].setVisibility(View.VISIBLE);
            mButton[5].setVisibility(View.GONE);
        } else {
            mButton[0].setVisibility(View.GONE);
            mButton[1].setVisibility(View.GONE);
            mButton[2].setVisibility(View.GONE);
            mButton[3].setVisibility(View.GONE);
            mButton[4].setVisibility(View.GONE);
            mButton[5].setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Vibrator mvibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        mSnakeView.setVibrator(mvibrator);
//    	Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity if RUNNING!!
        if (mSnakeView.getMode() == SnakeView.RUNNING) {
            mSnakeView.setMode(SnakeView.PAUSE);
        }
//    	Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getPreferences(0);
        mSnakeView.savePreferences(settings);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }

    public void bIzquierda(View view) {
        mSnakeView.bIzquierda();
    }

    public void bArriba(View view) {
        mSnakeView.bArriba();
    }

    public void bAbajo(View view) {
        mSnakeView.bAbajo();
    }

    public void bDerecha(View view) {
        mSnakeView.bDerecha();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.snake_menu, menu);
        menu.findItem(R.id.menu_about).setIcon(
                getResources().getDrawable(android.R.drawable.ic_menu_info_details));
        menu.findItem(R.id.menu_records).setIcon(
                getResources().getDrawable(android.R.drawable.ic_menu_view));
        menu.findItem(R.id.menu_settings).setIcon(
                getResources().getDrawable(android.R.drawable.ic_menu_preferences));
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mSnakeView.getMode() == SnakeView.RUNNING)
            mSnakeView.setMode(SnakeView.PAUSE);
        return true;
    }
}
