package net.deltaplay.tweener;

import net.deltaplay.tweener.Tweener.Tween;

public class TweenManager extends CompositeTween<TweenManager> {

    @Override
    public void update(float delta) {
        for (int i = 0; i < tweens.size; i++) {
            tweens.get(i).update(delta);
        }
    }

    @Override
    public TweenManager getThis() {
        return this;
    }

    public void remove(Tween tween) {
        tweens.removeValue(tween, false);
    }

    public void clear() {
        tweens.clear();
    }
}
