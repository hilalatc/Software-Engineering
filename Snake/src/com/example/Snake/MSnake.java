package com.example.Snake;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;

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
    }
}
