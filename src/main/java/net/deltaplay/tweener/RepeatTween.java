package net.deltaplay.tweener;

import net.deltaplay.tweener.Tweener.BaseTween;
import net.deltaplay.tweener.Tweener.Tween;

public class RepeatTween extends BaseTween<RepeatTween> {

    private Tween tween;
    private int count = -1;
    private int repeated;

    public RepeatTween() {
    }

    public RepeatTween(Tween tween) {
        this.tween = tween;
    }

    public RepeatTween set(Tween tween) {
        this.tween = tween;
        return this;
    }

    @Override
    public void updateImpl(float delta) {
        tween.update(delta);
        if (tween.finished()) {
            repeated++;
            if (count >= 0 && repeated >= count) {
                finished = true;
            } else {
                tween.restart();
            }
        }
    }

    @Override
    public void restart() {
        tween.restart();
        this.repeated = 0;
        this.finished = false;
    }

    @Override
    public RepeatTween getThis() {
        return this;
    }

    public RepeatTween count(int count) {
        this.count = count;
        return this;
    }

}
