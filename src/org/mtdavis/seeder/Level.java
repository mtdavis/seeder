package org.mtdavis.seeder;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;
import org.mtdavis.seeder.entities.terrain.Rock;
import org.mtdavis.seeder.entities.terrain.Soil;
import com.badlogic.gdx.math.Vector2;

/**
 * An example full-screen activity that shows and hides the system UI (i.e. status bar and navigation/system bar) with
 * user interaction.
 */
public class Level
    extends SimpleBaseGameActivity
{
    private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;

    @Override
    public EngineOptions onCreateEngineOptions()
    {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH,
            CAMERA_HEIGHT), camera);
    }

    @Override
    protected void onCreateResources()
    {
        BuildableBitmapTextureAtlas atlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024,
            TextureOptions.NEAREST);
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/svg/");

        Rock.createResources(atlas, this);
        Soil.createResources(atlas, this);

        try
        {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            atlas.load();
        }
        catch(final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }

    @Override
    protected Scene onCreateScene()
    {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();
        scene.setBackground(new Background(0.0f, 0.0f, 0.0f));

        final PhysicsWorld world = new FixedStepPhysicsWorld(30, new Vector2(0, 0), false, 8, 1);

        for(int i = 0; i <= 23*32; i += 32)
        {
            scene.attachChild(new Soil(i, 13*32, this.getVertexBufferObjectManager(), world).getEntity());
            scene.attachChild(new Rock(i, 14*32, this.getVertexBufferObjectManager(), world).getEntity());
        }

        return scene;
    }
}
