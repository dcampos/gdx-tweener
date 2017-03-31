package net.deltaplay.tweener;

public class ParallelTween extends CompositeTween<ParallelTween> {

    public ParallelTween() {
    }

    @Override
    public void updateImpl(float delta) {
        if (finished) return;

        finished = true;

        for (int i = 0; i < tweens.size; i++) {
            tweens.get(i).update(delta);
            if (!tweens.get(i).finished()) finished = false;
        }
    }

    @Override
    public ParallelTween getThis() {
        return this;
    }

}
