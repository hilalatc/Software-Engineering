package com.example.Snake;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
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

    public void savePreferences(SharedPreferences settings) {
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putBoolean("mUseWalls", mUseWalls);
        editor.putBoolean("mFast", mFast);
        editor.putInt("mTileSize", mTileSize);
        editor.putInt("mBoardSize", mBoardSize);
        editor.putLong("mRecords0", mRecords[0]);
        editor.putLong("mRecords1", mRecords[1]);
        editor.putLong("mRecords2", mRecords[2]);
        editor.putLong("mRecords3", mRecords[3]);
        editor.putLong("mRecords4", mRecords[4]);
        editor.putLong("mRecords5", mRecords[5]);
        editor.putLong("mRecords6", mRecords[6]);
        editor.putLong("mRecords7", mRecords[7]);
        editor.putLong("mRecords8", mRecords[8]);
        editor.putLong("mRecords9", mRecords[9]);
        editor.putLong("mRecords10", mRecords[10]);
        editor.putLong("mRecords11", mRecords[11]);
        editor.putBoolean("showNews20", showNews20);
        editor.putInt("inputMode", inputMode);
        editor.commit();
    }

    public void restorePreferences(SharedPreferences settings) {
        mUseWalls = settings.getBoolean("mUseWalls", true);
        mFast = settings.getBoolean("mFast", false);
        mTileSize = settings.getInt("mTileSize", tileSizes[BS_NORMAL]);
        mBoardSize = settings.getInt("mBoardSize", BS_NORMAL);
        mRecords[0] = settings.getLong("mRecords0", 0);
        mRecords[1] = settings.getLong("mRecords1", 0);
        mRecords[2] = settings.getLong("mRecords2", 0);
        mRecords[3] = settings.getLong("mRecords3", 0);
        mRecords[4] = settings.getLong("mRecords4", 0);
        mRecords[5] = settings.getLong("mRecords5", 0);
        mRecords[6] = settings.getLong("mRecords6", 0);
        mRecords[7] = settings.getLong("mRecords7", 0);
        mRecords[8] = settings.getLong("mRecords8", 0);
        mRecords[9] = settings.getLong("mRecords9", 0);
        mRecords[10] = settings.getLong("mRecords10", 0);
        mRecords[11] = settings.getLong("mRecords11", 0);
        showNews20 = settings.getBoolean("showNews20", true);
        inputMode = settings.getInt("inputMode", INPUT_MODE_OG);
        initSnakeView();
        setRecordIndex();
    }

    private int[] coordArrayListToArray(ArrayList<Coordinate> cvec) {
        int count = cvec.size();
        int[] rawArray = new int[count * 2];
        for (int index = 0; index < count; index++) {
            Coordinate c = cvec.get(index);
            rawArray[2 * index] = c.x;
            rawArray[2 * index + 1] = c.y;
        }
        return rawArray;
    }

    public Bundle saveState() {
        Bundle map = new Bundle();

        map.putIntArray("mAppleList", coordArrayListToArray(mAppleList));
        map.putInt("mDirection", Integer.valueOf(mDirection));
        map.putInt("mNextDirection", Integer.valueOf(mNextDirection));
        map.putInt("mTileSize", Integer.valueOf(mTileSize));
        map.putInt("mBoardSize", Integer.valueOf(mBoardSize));
        map.putLong("mMoveDelay", Long.valueOf(mMoveDelay));
        map.putLong("mScore", Long.valueOf(mScore));
        map.putLongArray("mRecords", mRecords);
        map.putInt("indRecord", Integer.valueOf(indRecord));
        map.putBoolean("mUseWalls", Boolean.valueOf(mUseWalls));
        map.putBoolean("mFast", Boolean.valueOf(mFast));
        map.putIntArray("mSnakeTrail", coordArrayListToArray(mSnakeTrail));
        map.putInt("RedApplex", mRedApple.x);
        map.putInt("RedAppley", mRedApple.y);
        map.putBoolean("mActiveRedApple", Boolean.valueOf(mActiveRedApple));
        map.putInt("GreenApplex", mGreenApple.x);
        map.putInt("GreenAppley", mGreenApple.y);
        map.putBoolean("mActiveGreenApple", Boolean.valueOf(mActiveGreenApple));
        map.putInt("inputMode", inputMode);

        return map;
    }

    private ArrayList<Coordinate> coordArrayToArrayList(int[] rawArray) {
        ArrayList<Coordinate> coordArrayList = new ArrayList<Coordinate>();

        int coordCount = rawArray.length;
        for (int index = 0; index < coordCount; index += 2) {
            Coordinate c = new Coordinate(rawArray[index], rawArray[index + 1]);
            coordArrayList.add(c);
        }
        return coordArrayList;
    }

    public void restoreState(Bundle icicle) {
        setMode(PAUSE);

        mAppleList = coordArrayToArrayList(icicle.getIntArray("mAppleList"));
        mDirection = icicle.getInt("mDirection");
        mNextDirection = icicle.getInt("mNextDirection");
        mTileSize = icicle.getInt("mTileSize");
        mBoardSize = icicle.getInt("mBoardSize");
        mMoveDelay = icicle.getLong("mMoveDelay");
        mScore = icicle.getLong("mScore");
        mRecords = icicle.getLongArray("mRecords");
        indRecord = icicle.getInt("indRecord");
        mUseWalls = icicle.getBoolean("mUseWalls");
        mFast = icicle.getBoolean("mFast");
        mSnakeTrail = coordArrayToArrayList(icicle.getIntArray("mSnakeTrail"));
        mRedApple.x = icicle.getInt("RedApplex");
        mRedApple.y = icicle.getInt("RedAppley");
        mActiveRedApple = icicle.getBoolean("mActiveRedApple");
        mGreenApple.x = icicle.getInt("GreenApplex");
        mGreenApple.y = icicle.getInt("GreenAppley");
        mActiveGreenApple = icicle.getBoolean("mActiveGreenApple");
        inputMode = icicle.getInt("inputMode");
    }

    /*
     * handles key events in the game. Update the direction our snake is traveling
     * based on the DPAD. Ignore events that would cause the snake to immediately
     * turn back on itself.
     *
     * (non-Javadoc)
     *
     * @see android.view.View#onKeyDown(int, android.os.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {

        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            if (mMode == READY) {
                // no initNewGame() because we have initiated it in setMode
                setMode(RUNNING);
                return (true);
            }

            if (mMode == LOSE) {
                initNewGame();
                setMode(RUNNING);
                return (true);
            }

            if (mMode == PAUSE) {
                setMode(RUNNING);
                return (true);
            }

            setMode(PAUSE);
            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (mDirection != SOUTH) {
                mNextDirection = NORTH;
            }
            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (mDirection != NORTH) {
                mNextDirection = SOUTH;
            }
            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (mDirection != EAST) {
                mNextDirection = WEST;
            }
            return (true);
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (mDirection != WEST) {
                mNextDirection = EAST;
            }
            return (true);
        }

        return super.onKeyDown(keyCode, msg);
    }

    private float savedX;
    private float savedY;
    private int savedMode;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            savedX = event.getX();
            savedY = event.getY();
            savedMode = mMode;
            return (true);
        }

        if (event.getAction() != MotionEvent.ACTION_UP)
            return (true);

        if (savedMode != mMode)
            return (true);

        float distX = savedX - event.getX();
        float distY = savedY - event.getY();
        float adistX = Math.abs(distX);
        float adistY = Math.abs(distY);

        if (adistX+adistY >= 10) {
            if (adistX > adistY) {
                if (distX > 0) {
                    if (mDirection != EAST) mNextDirection = WEST;
                } else {
                    if (mDirection != WEST) mNextDirection = EAST;
                }
            } else {
                if (distY > 0) {
                    if (mDirection != SOUTH) mNextDirection = NORTH;
                } else {
                    if (mDirection != NORTH) mNextDirection = SOUTH;
                }
            }
        }

        if (mMode == READY) {
            // no initNewGame() because we have initiated it in setMode
            setMode(RUNNING);
            // return (true);
        } else if (mMode == LOSE) {
            initNewGame();
            setMode(RUNNING);
            // return (true);
        } else if (mMode == PAUSE) {
            setMode(RUNNING);
            //return (true);
        } else if (mMode == RUNNING) {
            if (adistX+adistY < 10) {
                setMode(PAUSE);
                //return (true);
            }
        }

        return (true);
    }

    public void bIzquierda() {
        if (inputMode == INPUT_MODE_2K) {
            if (mDirection == EAST) mNextDirection = NORTH;
            else if (mDirection == NORTH) mNextDirection = WEST;
            else if (mDirection == WEST) mNextDirection = SOUTH;
            else mNextDirection = EAST;
            return;
        }
        if (mDirection != EAST) {
            mNextDirection = WEST;
        }
    }

    public void bArriba() {
        if (inputMode == INPUT_MODE_2K) return;
        if (mDirection != SOUTH) {
            mNextDirection = NORTH;
        }
    }

    public void bAbajo() {
        if (inputMode == INPUT_MODE_2K) return;
        if (mDirection != NORTH) {
            mNextDirection = SOUTH;
        }
    }


    public void bDerecha() {
        if (inputMode == INPUT_MODE_2K) {
            if (mDirection == EAST) mNextDirection = SOUTH;
            else if (mDirection == NORTH) mNextDirection = EAST;
            else if (mDirection == WEST) mNextDirection = NORTH;
            else mNextDirection = WEST;
            return;
        }
        if (mDirection != WEST) {
            mNextDirection = EAST;
        }
    }

    /**
     * Sets the TextView that will be used to give information (such as "Game
     * Over" to the user.
     *
     * @param newView
     */
    public void setTextView(TextView newView) {
        mStatusText = newView;
    }

    /**
     * Sets the TextView that will be used to show score and record
     *
     * @param newView
     */
    public void setScoreView(TextView newView) {
        mScoreText = newView;
    }

    public void setRecordView(TextView newView) {
        mRecordText = newView;
    }

    /**
     * Updates the current mode of the application (RUNNING or PAUSED or the like)
     * as well as sets the visibility of textview for notification
     *
     * @param newMode
     */
    public void setMode(int newMode) {
        int oldMode = mMode;
        mMode = newMode;

        updateScore();

        if (newMode == RUNNING & oldMode != RUNNING) {
            mStatusText.setVisibility(View.INVISIBLE);
            update();
            invalidate();
            return;
        }

        Resources res = getContext().getResources();
        CharSequence str = "";
        if (newMode == PAUSE) {
            str = res.getText(R.string.mode_pause);
        }
        if (newMode == READY) {
            if (!firstTime) {
                initNewGame();
                updateElements();
                invalidate();
            }
            str = res.getText(R.string.mode_ready);
        }
        if (newMode == LOSE) {
            if (mScore > mRecords[indRecord]) {
                mRecords[indRecord] = mScore;
                str = res.getString(R.string.mode_lose_prefix_cr) + mScore
                        + res.getString(R.string.mode_lose_suffix);
            } else {
                str = res.getString(R.string.mode_lose_prefix) + mScore
                        + res.getString(R.string.mode_lose_suffix);
            }
        }

        mStatusText.setText(str);
        mStatusText.setVisibility(View.VISIBLE);
    }

    public int getMode() {
        return mMode;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (firstTime) {
            if (mMode == READY) {
                initNewGame();
                updateElements();
            }
            else if (mMode == PAUSE) {
                updateElements();
            }
        }
        firstTime = false;
    }

    /**
     * Selects a random location within the garden that is not currently covered
     * by the snake. Currently _could_ go into an infinite loop if the snake
     * currently fills the garden, but we'll leave discovery of this prize to a
     * truly excellent snake-player.
     *
     */

    private Coordinate findFreeCoordinate() {
        Coordinate newCoord = null;
        boolean found = false;
        while (!found) {
            // Choose a new location for our apple
            int newX = 1 + RNG.nextInt(mXTileCount - 2);
            int newY = 1 + RNG.nextInt(mYTileCount - 2);
            newCoord = new Coordinate(newX, newY);

            // Make sure it's not already under the snake
            boolean collision = false;
            int snakelength = mSnakeTrail.size();
            for (int index = 0; index < snakelength; index++) {
                if (mSnakeTrail.get(index).equals(newCoord)) {
                    collision = true;
                    numcollision++;
                }
            }
            int applelength = mAppleList.size();
            for (int index = 0; index < applelength; index++) {
                if (mAppleList.get(index).equals(newCoord)) {
                    collision = true;
                    numcollision++;
                }
            }
            if (mActiveRedApple && mRedApple.equals(newCoord)) {
                collision = true;
                numcollision++;
            }
            if (mActiveGreenApple && mGreenApple.equals(newCoord)) {
                collision = true;
                numcollision++;
            }
            // if we're here and there's been no collision, then we have
            // a good location for an apple. Otherwise, we'll circle back
            // and try again
            found = !collision;
        }
        return newCoord;
    }

    private void addRandomApple() {
        Coordinate newCoord = null;

        newCoord = findFreeCoordinate();
        mAppleList.add(newCoord);
    }

    private void addRedApple() {
        mRedApple = findFreeCoordinate();
        mActiveRedApple = true;
    }

    private void addGreenApple() {
        mGreenApple = findFreeCoordinate();
        mActiveGreenApple = true;
    }

    /**
     * Handles the basic update loop, checking to see if we are in the running
     * state, determining if a move should be made, updating the snake's location.
     */
    public void update() {
        if (mMode == RUNNING) {
            long now = System.currentTimeMillis();

            if (now - mLastMove >= mMoveDelay) {
                updateElements();
                mLastMove = now;
            }
            mRedrawHandler.sleep(mMoveDelay);
        }
    }

    public void updateElements() {
        clearTiles();
        updateWalls();
        updateSnake();
        updateApples();
    }

    /**
     * Draws some walls.
     *
     */
    private void updateWalls() {
        if (!mUseWalls) return;
        for (int x = 0; x < mXTileCount; x++) {
            setTile(WALL_TILE, x, 0);
            setTile(WALL_TILE, x, mYTileCount - 1);
        }
        for (int y = 1; y < mYTileCount - 1; y++) {
            setTile(WALL_TILE, 0, y);
            setTile(WALL_TILE, mXTileCount - 1, y);
        }
    }

    /**
     * Draws some apples.
     *
     */
    private void updateApples() {
        for (Coordinate c : mAppleList) {
            setTile(FOOD_TILE, c.x, c.y);
        }
        if (mActiveRedApple) {
            setTile(REDFOOD_TILE, mRedApple.x, mRedApple.y);
        }
        if (mActiveGreenApple) {
            setTile(GREENFOOD_TILE, mGreenApple.x, mGreenApple.y);
        }
    }



}
