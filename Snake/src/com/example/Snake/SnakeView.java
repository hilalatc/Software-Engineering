package com.example.Snake;

import android.app.Activity;
import android.os.Vibrator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Hilal
 * Date: 10.11.2013
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class SnakeView extends TileView {

    private int mMode = READY;
    public static final int PAUSE = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int LOSE = 3;

    private int mDirection = NORTH;
    private int mNextDirection = NORTH;
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;

    public int mBoardSize = BS_NORMAL;
    public static final int BS_BIG = 0;
    public static final int BS_NORMAL = 1;
    public static final int BS_SMALL = 2;
    private int tileSizes[] = {8,16,32};

    private static final int BODY_TILE = 1;
    private static final int FOOD_TILE = 2;
    private static final int GREENFOOD_TILE = 3;
    private static final int REDFOOD_TILE = 4;
    private static final int WALL_TILE = 5;
    private static final int HEAD_TILE = 6;
    private static final int HEAD2_TILE = 7;
    private static final int HEADEAT_TILE = 8;
    private static final int HEADBAD_TILE = 9;

    private boolean mDrawHead2 = false;
    private boolean mDrawHeadEat = false;

    private long mScore = 0;
    private long mMoveDelay = 500;
    public static final int RECORD_BIG_WON = 0;
    public static final int RECORD_BIG_WOFF = 1;
    public static final int RECORD_NORMAL_WON = 2;
    public static final int RECORD_NORMAL_WOFF = 3;
    public static final int RECORD_SMALL_WON = 4;
    public static final int RECORD_SMALL_WOFF = 5;
    public static final int RECORD_BIG_WON_FAST = 6;
    public static final int RECORD_BIG_WOFF_FAST = 7;
    public static final int RECORD_NORMAL_WON_FAST = 8;
    public static final int RECORD_NORMAL_WOFF_FAST = 9;
    public static final int RECORD_SMALL_WON_FAST = 10;
    public static final int RECORD_SMALL_WOFF_FAST = 11;
    private int indRecord = RECORD_NORMAL_WON;
    private long mRecords[] = new long[12];

    private int numcollision = 0;
    public boolean firstTime = true;
    public boolean showNews20 = true;

    public boolean noSmallSize = false;

    public static final int INPUT_MODE_4K = 0;
    public static final int INPUT_MODE_2K = 1;
    public static final int INPUT_MODE_OG = 2;
    public int inputMode = INPUT_MODE_OG;

    private long mLastMove;


    private TextView mStatusText;


    private TextView mScoreText;
    private TextView mRecordText;


    public boolean mUseWalls = true;

    public boolean mFast = false;

//    private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
//    private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();
//    private Coordinate mRedApple = new Coordinate(1,1);
//    private boolean mActiveRedApple = false;
//    private Coordinate mGreenApple = new Coordinate(1,1);
//    private boolean mActiveGreenApple = false;

    private static final Random RNG = new Random();

    private Vibrator mVibrator;



}
