package net.deltaplay.tweener;

import com.badlogic.gdx.utils.Array;
import net.deltaplay.tweener.Tweener.BaseTween;
import net.deltaplay.tweener.Tweener.Tween;

public abstract class CompositeTween<T extends CompositeTween> extends BaseTween {
    Array<Tween> tweens = new Array<Tween>();

    @Override
    public abstract void update(float delta);

    public T add(Tween tween) {
        tweens.add(tween);
        return getThis();
    }

    public T add(Tween... tweens) {
        this.tweens.addAll(tweens);
        return getThis();
    }

    @Override
    public void restart() {
        finished = false;

        for (int i = 0; i < tweens.size; i++) {
            tweens.get(i).restart();
        }
    }

    @Override
    public void reset() {
        super.reset();
        tweens.clear();
    }

    public Array<Tween> getTweens() {
        return tweens;
    }

    public abstract T getThis();
}
