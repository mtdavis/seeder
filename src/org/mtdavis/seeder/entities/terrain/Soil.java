package org.mtdavis.seeder.entities.terrain;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import android.content.Context;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Soil
{
    private final Sprite sprite;
    private final Body body;

    private static final FixtureDef S_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1f, .5f, .5f);
    
    private static ITextureRegion S_TEXTURE_REGION;

    public Soil(final int pX, final int pY, final VertexBufferObjectManager vbom, final PhysicsWorld world)
    {
        this.sprite = new Sprite(pX, pY, 32, 32, S_TEXTURE_REGION, vbom);
        //this.sprite.setColor(.8f, .5f, 0f);

        this.body = PhysicsFactory.createBoxBody(world, sprite, BodyType.StaticBody, S_FIXTURE_DEF);

        world.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));
    }
    
    public static void createResources(BuildableBitmapTextureAtlas atlas, Context context)
    {
        S_TEXTURE_REGION = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(
            atlas, context, "terrain/Soil.svg", 64, 64);
    }
    
    public IEntity getEntity()
    {
        return sprite;
    }
}
