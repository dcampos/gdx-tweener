package net.deltaplay.tweener;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import net.deltaplay.tweener.Tweener.BaseTween;
import net.deltaplay.tweener.Tweener.Tween;

public abstract class CompositeTween<T extends CompositeTween> extends BaseTween<T> {
    DelayedRemovalArray<Tween> tweens = new DelayedRemovalArray<>();

    public T add(Tween<?> tween) {
        tweens.add(tween);
        return getThis();
    }

    public T add(Tween<?>... tweens) {
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

}
