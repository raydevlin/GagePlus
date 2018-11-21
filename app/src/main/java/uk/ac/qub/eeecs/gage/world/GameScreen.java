package uk.ac.qub.eeecs.gage.world;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

/**
 * Game screen class acting as a container for a coherent section of the game (a
 * level, configuration screen, etc.)
 *
 * @version 1.0
 */
public abstract class GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Name that is given to this game screen
     */
    protected final String mName;

    /**
     * Return the name of this game screen
     *
     * @return Name of this game screen
     */
    public String getName() {
        return mName;
    }

    /**
     * Game to which game screen belongs
     */
    protected final Game mGame;

    /**
     * Return the game to which this game screen is attached
     *
     * @return Game to which screen is attached
     */
    public Game getGame() {
        return mGame;
    }

    /**
     * Number of ticks elapsed
     */
    public int ticks = -1;

    /**
     * Number of ticks required before tick actions will carry out
     */
    protected int tickRefresh = 60;

    /**
     * number of in game frames per second
     */
    public String frameCounter = "0";

    public Paint mPaint;

    public ArrayList<GameObject> mEntities;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new game screen associated with the specified game instance
     *
     * @param game Game instance to which the game screen belongs
     */
    public GameScreen(String name, Game game) {
        mName = name;
        mGame = game;
        mPaint = getPaint();
        mEntities = new ArrayList<>();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private Paint getPaint() {
        Paint paint = new Paint(Color.BLACK);
        paint.setARGB(255,84,192,220);
        float textSize = (float)mGame.getScreenHeight()/20.0f;
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.createFromAsset(mGame.getContext().getAssets(), "font/8bit.TTF"));
        return paint;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Update and Draw
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the game screen. Invoked automatically from the game.
     * <p>
     * NOTE: If the update is multi-threaded control should not be returned from
     * the update call until all update processes have completed.
     *
     * @param elapsedTime Elapsed time information for the frame
     */
    public abstract void update(ElapsedTime elapsedTime);

    /**
     * Draw the game screen. Invoked automatically from the game.
     *
     * @param elapsedTime Elapsed time information for the frame
     * @param graphics2D  Graphics instance used to draw the screen
     */
    public abstract void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D);

    /**
     * Draw the game's frame counter to the screen. Invoked automatically from the
     * current screen's draw method.
     *
     * @param elapsedTime Elapsed time information for the frame
     * @param graphics2D  Graphics instance used to draw the screen
     */
    public void drawFrameCounter(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        float textSize = (float)mGame.getScreenHeight()/12.0f;
        mPaint.setTextSize(textSize);
        mPaint.setFakeBoldText(true);
        int xPadding = 10;
        graphics2D.drawText(frameCounter, 10, textSize, mPaint);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Android Life Cycle
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Invoked automatically by the game whenever the app is paused.
     */
    public void pause() {
    }

    /**
     * Invoked automatically by the game whenever the app is resumed.
     */
    public void resume() {
    }

    /**
     * Invoked automatically by the game whenever the app is disposed.
     */
    public void dispose() {
    }
}