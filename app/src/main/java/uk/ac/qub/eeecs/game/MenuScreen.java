package uk.ac.qub.eeecs.game;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.cardDemo.CardDemoScreen;
import uk.ac.qub.eeecs.game.platformDemo.PlatformDemoScreen;
import uk.ac.qub.eeecs.game.spaceDemo.SpaceshipDemoScreen;

/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class MenuScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for playing the 'games'
     */
    private PushButton mSpaceshipDemoButton;
    private PushButton mPlatformDemoButton;
    private PushButton mCardDemoButton;
    private GameObject mBackground;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public MenuScreen(Game game) {
        super("MenuScreen", game);
        game.setTargetFramesPerSecond(60);

        // Load in the bitmaps used on the main menu screen
        AssetStore assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("SpaceshipDemoIcon", "img/Spaceship1.png");
        assetManager.loadAndAddBitmap("CardDemoIcon", "img/CardBackground1.png");
        assetManager.loadAndAddBitmap("PlatformDemoIcon", "img/Platform1.png");
        assetManager.loadAndAddBitmap("Background", "img/stage.png");

        // Define the spacing that will be used to position the buttons
        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        // Create the trigger buttons
        mSpaceshipDemoButton = new PushButton(
                spacingX * 1.0f, spacingY * 1.5f, spacingX, spacingY, "SpaceshipDemoIcon", this);
        mCardDemoButton = new PushButton(
                spacingX * 2.5f, spacingY * 1.5f, spacingX, spacingY, "CardDemoIcon", this);
        mPlatformDemoButton = new PushButton(
                spacingX * 4.0f, spacingY * 1.5f, spacingX, spacingY, "PlatformDemoIcon", this);

        mBackground = new GameObject(
                game.getScreenWidth()/2, game.getScreenHeight()/2, game.getScreenWidth(), game.getScreenHeight(),
                assetManager.getBitmap("Background"), this
        );

        mEntities.add(mBackground);
        mEntities.add(mSpaceshipDemoButton);
        mEntities.add(mCardDemoButton);
        mEntities.add(mPlatformDemoButton);

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        ticks++;

        if(ticks % tickRefresh == 0) {
            frameCounter = String.format("%.0f", getGame().getAverageFramesPerSecond());
        }

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            // Just check the first touch event that occurred in the frame.
            // It means pressing the screen with several fingers may not
            // trigger a 'button', but, hey, it's an exceedingly basic menu.
            TouchEvent touchEvent = touchEvents.get(0);

            // Update each button and transition if needed

            mSpaceshipDemoButton.update(elapsedTime);
            mCardDemoButton.update(elapsedTime);
            mPlatformDemoButton.update(elapsedTime);

            if (mSpaceshipDemoButton.isPushTriggered())
                changeToScreen(new SpaceshipDemoScreen(mGame));
            else if (mCardDemoButton.isPushTriggered())
                changeToScreen(new CardDemoScreen(mGame));
            else if (mPlatformDemoButton.isPushTriggered())
                changeToScreen(new PlatformDemoScreen(mGame));
        }
    }

    /**
     * Remove the current game screen and then change to the specified screen
     *
     * @param screen game screen to become active
     */
    private void changeToScreen(GameScreen screen) {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);
    }

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        for(GameObject obj:mEntities)
            obj.draw(elapsedTime, graphics2D);

        drawFrameCounter(elapsedTime, graphics2D);
    }
}
