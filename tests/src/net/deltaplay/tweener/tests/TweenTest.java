package net.deltaplay.tweener.tests;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.deltaplay.tweener.Tweener;
import net.deltaplay.tweener.Tweener.TimeTween;
import net.deltaplay.tweener.Tweener.Tween;
import net.deltaplay.tweener.Tweener.TweenAccessor;

public class TweenTest extends ApplicationAdapter {
    private SpriteBatch batch;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private Texture texture;
    private Sprite sprite1, sprite2, sprite3;
    private Color color1 = Color.valueOf("#6BB4D5");
    private Color color2 = Color.valueOf("#F04A4A");
    private Tween tween1, tween3, tween2;
    private TimeTween progressTween;

    public static void main(String[] args) {
        new LwjglApplication(new TweenTest(), new LwjglApplicationConfiguration());
    }

    @Override
    public void create() {
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();

        texture = new Texture(Gdx.files.internal("data/circle.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        sprite1 = new Sprite(texture);
        sprite2 = new Sprite(texture);
        sprite2.setSize(50, 50);
        sprite3 = new Sprite(texture);
        sprite3.setSize(50, 50);

        float duration = 0.8f;

        progressTween = Tweener.delay(duration * 4 + 0.5f * 2);

        tween1 = Tweener.repeat().set(
                Tweener.sequence().add(Tweener.parallel()
                        .add(Tweener.tween(sprite1, SpriteAccessor.ALPHA)
                                .to(0).duration(duration))
                        .add(Tweener.tween(sprite1, SpriteAccessor.ROTATION)
                                .to(360).duration(duration))
                        .add(Tweener.tween(sprite1, SpriteAccessor.SIZE)
                                .to(300, 300).duration(duration))
                ).add(Tweener.parallel()
                        .add(Tweener.tween(sprite1, SpriteAccessor.ALPHA)
                                .to(1f).duration(duration))
                        .add(Tweener.tween(sprite1, SpriteAccessor.ROTATION)
                                .to(0).duration(duration))
                        .add(Tweener.tween(sprite1, SpriteAccessor.SIZE)
                                .to(200, 200).duration(duration))
               ).add(Tweener.delay(0.5f))
        );

        duration = 1.5f;

        tween2 = Tweener.repeat().set(
                Tweener.sequence().add(Tweener.parallel()
                        .add(Tweener.tween(sprite2, SpriteAccessor.POS)
                                .to(viewport.getWorldWidth() - sprite2.getWidth(), 0).duration(duration)
                                .interp(Interpolation.bounceOut))
                        .add(Tweener.tween(sprite2, SpriteAccessor.ROTATION)
                                .to(360).duration(duration))
                        .add(Tweener.tween(sprite2, SpriteAccessor.RGB)
                                .to(color1.r, color1.g, color1.b).duration(duration))
                ).add(Tweener.parallel()
                        .add(Tweener.tween(sprite2, SpriteAccessor.POS)
                                .to(0, 0).duration(duration))
                        .add(Tweener.tween(sprite2, SpriteAccessor.ROTATION)
                                .to(0).duration(duration))
                        .add(Tweener.tween(sprite2, SpriteAccessor.RGB)
                                .to(color2.r, color2.g, color2.b).duration(duration))
                ).add(Tweener.delay(0.5f))
        );

        tween3 = Tweener.repeat().set(
                Tweener.parallel(
                        Tweener.sequence(
                                Tweener.tween(sprite3, SpriteAccessor.ROTATION)
                                        .to(0),
                                Tweener.tween(sprite3, SpriteAccessor.SCALE)
                                        .to(1f, 0.5f).duration(1f).interp(Interpolation.elastic),
                                Tweener.tween(sprite3, SpriteAccessor.SCALE)
                                        .to(1f, 1f).duration(1f),
                                Tweener.tween(sprite3, SpriteAccessor.ROTATION)
                                        .to(true, 360).duration(0.5f),
                                Tweener.delay(0.1f)
                        ), Tweener.sequence(
                                Tweener.tween(sprite3, SpriteAccessor.ALPHA)
                                        .to(0).duration(1f).interp(Interpolation.pow2In),
                                Tweener.tween(sprite3, SpriteAccessor.ALPHA)
                                        .to(1f).duration(1f),
                                Tweener.delay(0.1f)
                        )));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Keys.SPACE) {
                    tween1.restart();
                    tween2.restart();
                    tween3.restart();
                    progressTween.restart();
                }
                return false;
            }
        });
    }

    @Override
    public void render() {
        render(Color.DARK_GRAY);
    }

    private void render(Color color) {

        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tween1.update(Gdx.graphics.getDeltaTime());
        tween2.update(Gdx.graphics.getDeltaTime());
        tween3.update(Gdx.graphics.getDeltaTime());

        progressTween.update(Gdx.graphics.getDeltaTime());

        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        sprite1.setPosition((width - sprite1.getWidth()) / 2, (height - sprite1.getHeight()) / 2);
        sprite1.setOrigin(sprite1.getWidth() / 2, sprite1.getHeight() / 2);
        sprite2.setOrigin(sprite2.getWidth() / 2, sprite2.getHeight() / 2);
        sprite3.setOrigin(sprite3.getWidth() / 2, sprite3.getHeight() / 2);

        sprite3.setPosition(0, viewport.getScreenHeight() - sprite3.getHeight());

        batch.begin();
        sprite1.draw(batch);
        sprite2.draw(batch);
        sprite3.draw(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private enum SpriteAccessor implements TweenAccessor<Sprite> {

        POS(2) {
            @Override
            public void get(Sprite object, float[] values) {
                values[0] = object.getX();
                values[1] = object.getY();
            }

            @Override
            public void set(Sprite object, float[] values) {
                object.setPosition(values[0], values[1]);
            }
        },

        SIZE(2) {
            @Override
            public void get(Sprite object, float[] values) {
                values[0] = object.getWidth();
                values[1] = object.getHeight();
            }

            @Override
            public void set(Sprite object, float[] values) {
                object.setSize(values[0], values[1]);
            }
        },

        SCALE(2) {
            @Override
            public void get(Sprite object, float[] values) {
                values[0] = object.getScaleX();
                values[1] = object.getScaleY();
            }

            @Override
            public void set(Sprite object, float[] values) {
                object.setScale(values[0], values[1]);
            }
        },

        ROTATION(1) {

            @Override
            public void set(Sprite object, float[] values) {
                object.setRotation(values[0]);
            }

            @Override
            public void get(Sprite object, float[] values) {
                values[0] = object.getRotation();
            }
        },

        RGB(3) {
            @Override
            public void set(Sprite object, float[] values) {
                Color c = object.getColor();
                c.set(values[0], values[1], values[2], c.a);
                object.setColor(c);
            }

            @Override
            public void get(Sprite object, float[] values) {
                values[0] = object.getColor().r;
                values[1] = object.getColor().g;
                values[2] = object.getColor().b;
            }
        },

        ALPHA(1) {
            @Override
            public void set(Sprite object, float[] values) {
                object.setAlpha(values[0]);
            }

            @Override
            public void get(Sprite object, float[] values) {
                values[0] = object.getColor().a;
            }
        };

        int count;

        SpriteAccessor(int count) {
            this.count = count;
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}
