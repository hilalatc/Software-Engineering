package com.example.Snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Hilal
 * Date: 10.11.2013
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class TileView extends View {

//	private static final String TAG = "MSnake";
    /**
     * Parameters controlling the size of the tiles and their range within view.
     * Width/Height are in pixels, and Drawables will be scaled to fit to these
     * dimensions. X/Y Tile Counts are the number of tiles that will be drawn.
     */

    protected static int mTileSize = 16;

    protected static int mXTileCount;
    protected static int mYTileCount;

    private static int mXOffset;
    private static int mYOffset;


    /**
     * A hash that maps integer handles specified by the subclasser to the
     * drawable that will be used for that reference
     */
    private Bitmap[] mTileArray;

    /**
     * A two-dimensional array of integers in which the number represents the
     * index of the tile that should be drawn at that locations
     */
    private int[][] mTileGrid;

    private final Paint mPaint = new Paint();

    public TileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void internalRecalcTileGrid(int w, int h) {
        mXTileCount = (int) Math.floor(w / mTileSize);
        mYTileCount = (int) Math.floor(h / mTileSize);

        mXOffset = ((getWidth() - (mTileSize * mXTileCount)) / 2);
        mYOffset = ((getHeight() - (mTileSize * mYTileCount)) / 2);

        mTileGrid = new int[mXTileCount][mYTileCount];
        clearTiles();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//    	Log.d(TAG, "onSizeChanged");
        internalRecalcTileGrid(w, h);
    }

    public void recalcTileGrid() {
        internalRecalcTileGrid(getWidth(), getHeight());
    }

    /**
     * Rests the internal array of Bitmaps used for drawing tiles, and
     * sets the maximum index of tiles to be inserted
     *
     * @param tilecount
     */

    public void resetBitmapTiles(int tilecount) {
        mTileArray = new Bitmap[tilecount];
    }

    /**
     * Function to set the specified Drawable as the tile for a particular
     * integer key.
     *
     * @param key
     * @param tile
     */
    public void loadBitmapTile(int key, Drawable tile) {
        Bitmap bitmap = Bitmap.createBitmap(mTileSize, mTileSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0, 0, mTileSize, mTileSize);
        tile.draw(canvas);

        mTileArray[key] = bitmap;
    }

    /**
     * Resets all tiles to 0 (empty)
     *
     */
    public void clearTiles() {
        for (int x = 0; x < mXTileCount; x++) {
            for (int y = 0; y < mYTileCount; y++) {
                setTile(0, x, y);
            }
        }
    }

    /**
     * Used to indicate that a particular tile (set with loadTile and referenced
     * by an integer) should be drawn at the given x/y coordinates during the
     * next invalidate/draw cycle.
     *
     * @param tileindex
     * @param x
     * @param y
     */
    public void setTile(int tileindex, int x, int y) {
        mTileGrid[x][y] = tileindex;
    }

    public int getTileIndex(int x, int y) {
        return mTileGrid[x][y];
    }

    @Override
    public void onDraw(Canvas canvas) {
//    	Log.d(TAG, "onDraw");
        super.onDraw(canvas);
        for (int x = 0; x < mXTileCount; x += 1) {
            for (int y = 0; y < mYTileCount; y += 1) {
                if (mTileGrid[x][y] > 0) {
                    canvas.drawBitmap(mTileArray[mTileGrid[x][y]],
                            mXOffset + x * mTileSize,
                            mYOffset + y * mTileSize,
                            mPaint);
                }
            }
        }

    }

}
