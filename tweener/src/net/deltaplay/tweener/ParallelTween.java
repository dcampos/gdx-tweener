package net.deltaplay.tweener;

import com.badlogic.gdx.utils.Array;
import net.deltaplay.tweener.Tweener.BaseTween;
import net.deltaplay.tweener.Tweener.Tween;

public class ParallelTween extends BaseTween {

    Array<Tween> tweens = new Array<Tween>();

    public ParallelTween() {
    }

    @Override
    public void update(float delta) {
        if (finished) return;

        finished = true;

        for (int i = 0; i < tweens.size; i++) {
            tweens.get(i).update(delta);
            if (!tweens.get(i).finished()) finished = false;
        }
    }

    public ParallelTween add(Tween tween) {
        tweens.add(tween);
        return this;
    }

    public ParallelTween add(Tween... tweens) {
        this.tweens.addAll(tweens);
        return this;
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
}
