package net.deltaplay.tweener;

import com.badlogic.gdx.utils.Pool.Poolable;

public class SequenceTween extends CompositeTween<SequenceTween> implements Poolable {

    public SequenceTween() {
    }

    private int current = 0;

    @Override
    public void updateImpl(float delta) {
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

    @Override
    public SequenceTween getThis() {
        return this;
    }
}
