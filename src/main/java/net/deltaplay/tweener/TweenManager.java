package net.deltaplay.tweener;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import net.deltaplay.tweener.Tweener.Tween;

public class TweenManager extends CompositeTween<TweenManager> {
    private Array<Tween> toRemove = new Array<Tween>();

    @Override
    public void update(float delta) {
        for (int i = 0; i < tweens.size; i++) {
            Tween tween = tweens.get(i);
            tween.update(delta);
            if (tween.finished()) remove(tween);
        }
    }

    @Override
    public void updateImpl(float delta) {
    }

    @Override
    public TweenManager getThis() {
        return this;
    }

    public void remove(Tween tween) {
        tweens.removeValue(tween, false);
        Pools.free(tween);
    }

    public void clear() {
        tweens.clear();
    }
}
