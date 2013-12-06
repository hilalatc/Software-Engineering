package com.example.Snake;

import android.app.Activity;

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

}
