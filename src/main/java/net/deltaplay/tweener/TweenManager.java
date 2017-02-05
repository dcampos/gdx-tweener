package net.deltaplay.tweener;

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
}
