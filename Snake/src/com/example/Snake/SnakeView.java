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
}
