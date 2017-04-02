package net.deltaplay.tweener;

import net.deltaplay.tweener.Tweener.BaseTween;

public class TimeTween<T extends TimeTween> extends BaseTween<T> {
    float duration;
    float time;

    TimeTween() {

    }

    @Override
    public void updateImpl(float delta) {
        if (finished()) return;

        time += delta;

        if (time >= duration) {
            time = duration;
            finished = true;
        }
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

    @Override
    public T getThis() {
        return (T) this;
    }
}
