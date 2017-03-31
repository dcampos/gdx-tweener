package net.deltaplay.tweener;

import net.deltaplay.tweener.Tweener.BaseTween;

public class TimeTween extends BaseTween {
    float duration;
    float time;

    @Override
    public void update(float delta) {
        if (finished()) return;

        time += delta;

        runOnUpdate();

        if (time >= duration) {
            time = duration;
            finished = true;
        }
    }

    private void runOnUpdate() {
        if (onUpdate != null)
            onUpdate.run();
    }

    @Override
    public void restart() {
        time = 0;
        finished = false;
    }

    public TimeTween duration(float delay) {
        this.duration = delay;
        return this;
    }

    public float getTime() {
        return time;
    }

    public float getDuration() {
        return duration;
    }

    @Override
    public void reset() {
        super.reset();
        time = 0;
        duration = 0;
    }
}
