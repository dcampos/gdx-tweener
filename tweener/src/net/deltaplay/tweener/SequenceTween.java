package net.deltaplay.tweener;

import com.badlogic.gdx.utils.Pool.Poolable;
import net.deltaplay.tweener.Tweener.Tween;

public class SequenceTween extends ParallelTween implements Poolable {

    public SequenceTween() {
    }

    public SequenceTween(Tween... tweens) {
        this.tweens.addAll(tweens);
    }

    private int current = 0;

    @Override
    public void update(float delta) {
        if (tweens.size <= current) {
            finished = true;
            return;
        }

        tweens.get(current).update(delta);

        if (tweens.get(current).finished()) {
            current++;
        }
    }

    @Override
    public void restart() {
        super.restart();
        current = 0;
    }

    @Override
    public void reset() {
        super.reset();
        current = 0;
    }
}
