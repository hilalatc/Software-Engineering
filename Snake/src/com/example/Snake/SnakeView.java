package com.example.Snake;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.Toast;

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

    private RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
           // SnakeView.this.update();
            SnakeView.this.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };


    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i=0; i<12; i++) mRecords[i] = 0;
        initSnakeView();
    }

    public SnakeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        for (int i=0; i<12; i++) mRecords[i] = 0;
        initSnakeView();
    }

    public void setTileSizes(int w, int h) {
        if (h < 300 || w < 300) noSmallSize = true; // qvga device
        if (h < 400 || w < 400) { // qvga & hvga devices
            tileSizes[0] = 6;
            tileSizes[1] = 12;
            tileSizes[2] = 24;
        }
    }

    private void initSnakeView() {
        setFocusable(true);

        Resources r = this.getContext().getResources();

        resetBitmapTiles(10);
        loadBitmapTile(BODY_TILE, r.getDrawable(R.drawable.bodytile));
        loadBitmapTile(FOOD_TILE, r.getDrawable(R.drawable.appletile));
        loadBitmapTile(GREENFOOD_TILE, r.getDrawable(R.drawable.peppertile));
        loadBitmapTile(REDFOOD_TILE, r.getDrawable(R.drawable.redpeppertile));
        loadBitmapTile(WALL_TILE, r.getDrawable(R.drawable.walltile2));
        loadBitmapTile(HEAD_TILE, r.getDrawable(R.drawable.headtile));
        loadBitmapTile(HEAD2_TILE, r.getDrawable(R.drawable.head2tile));
        loadBitmapTile(HEADEAT_TILE, r.getDrawable(R.drawable.headeattile));
        loadBitmapTile(HEADBAD_TILE, r.getDrawable(R.drawable.headbadtile));

    }

    public void initNewGame() {
//        mSnakeTrail.clear();
//        mAppleList.clear();
//
//        // For now we're just going to load up a short default eastbound snake
//        // that's just turned north
//        mSnakeTrail.add(new Coordinate(7, 7));
//        mSnakeTrail.add(new Coordinate(6, 7));
//        mSnakeTrail.add(new Coordinate(5, 7));
//        mSnakeTrail.add(new Coordinate(4, 7));
//        mSnakeTrail.add(new Coordinate(3, 7));
//        mSnakeTrail.add(new Coordinate(2, 7));
//        mNextDirection = NORTH;
//
//        // Two apples to start with
//        mActiveGreenApple = false;
//        mActiveRedApple = false;
//        addRandomApple();
//        addRandomApple();

        mMoveDelay = (mFast) ? 250 : 500;
        mScore = 0;
    }

    public void setVibrator(Vibrator v) {
        mVibrator = v;
    }

    private void setRecordIndex() {
        if (mUseWalls) {
            if (mBoardSize == BS_SMALL) indRecord = RECORD_SMALL_WON;
            else if (mBoardSize == BS_NORMAL) indRecord = RECORD_NORMAL_WON;
            else indRecord = RECORD_BIG_WON;
        } else {
            if (mBoardSize == BS_SMALL) indRecord = RECORD_SMALL_WOFF;
            else if (mBoardSize == BS_NORMAL) indRecord = RECORD_NORMAL_WOFF;
            else indRecord = RECORD_BIG_WOFF;
        }
        if (mFast) indRecord += 6;
    }

    private int indrtline[] = {R.string.records_line0, R.string.records_line1,
            R.string.records_line2, R.string.records_line3, R.string.records_line4,
            R.string.records_line5, R.string.records_line6, R.string.records_line7,
            R.string.records_line8, R.string.records_line9, R.string.records_line10,
            R.string.records_line11};

    public String getRecordsText() {
        String str = "";
        Resources res = getContext().getResources();

        str = res.getString(R.string.records_linet);
        for (int i=0; i<12; i++) {
            if (noSmallSize && (i==4 || i==5 || i==10 || i==11)) continue;
//        	if (i == indRecord) str = str + "<b>";
            str = str + res.getString(indrtline[i]) + mRecords[i];
            if (i == indRecord) str = str + "*";
//        	if (i == indRecord) str = str + "</b>";
//        	str = str + "<br>";
        }
        str = str + "\n";
        return str;
    }

    public void zoomTileSize(int size){
        Resources res = getContext().getResources();
        CharSequence str = "";

        if (size == BS_NORMAL) {
            mBoardSize = BS_NORMAL;
            mTileSize = tileSizes[BS_NORMAL];
            str = res.getText(R.string.toast_zoomnormal);
        }
        else if (size == BS_BIG) {
            mBoardSize = BS_BIG;
            mTileSize = tileSizes[BS_BIG];
            str = res.getText(R.string.toast_zoombig);
        }
        else {
            // Don't use the small size in QVGA devices
            if (!noSmallSize){
                mBoardSize = BS_SMALL;
                mTileSize = tileSizes[BS_SMALL];
                str = res.getText(R.string.toast_zoomsmall);
            } else {
                mBoardSize = BS_NORMAL;
                mTileSize = tileSizes[BS_NORMAL];
                str = res.getText(R.string.toast_zoomnormal);
            }
        }
        setRecordIndex();
        recalcTileGrid();
        initSnakeView();
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }


    public void changeWalls(){
        Resources res = getContext().getResources();
        CharSequence str = "";

        mUseWalls = !mUseWalls;
        clearTiles();
        if (mUseWalls) {
            str = res.getText(R.string.toast_wallson);
        }
        else {
            str = res.getText(R.string.toast_wallsoff);
        }
        setRecordIndex();
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void changeSpeed(){
        Resources res = getContext().getResources();
        CharSequence str = "";

        mFast = !mFast;
        clearTiles();
        if (mFast) {
            str = res.getText(R.string.toast_faston);
        }
        else {
            str = res.getText(R.string.toast_fastoff);
        }
        setRecordIndex();
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }



}
