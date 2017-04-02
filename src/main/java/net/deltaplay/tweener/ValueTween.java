package net.deltaplay.tweener;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.GdxRuntimeException;
import net.deltaplay.tweener.Tweener.TweenAccessor;

import java.util.Arrays;

public class ValueTween extends TimeTween<ValueTween> {
    private int size = 0;

    private float[] from;
    private float[] to;
    private float[] current;
    private float[] start;

    private boolean hasFrom, hasTo, isRelative;
    private boolean initialized;

    private TweenAccessor<Object> accessor;
    private Interpolation interpolation = Interpolation.linear;
    private Object object;

    public ValueTween() {
    }

    ValueTween size(int size) {
        if (this.size != size || this.from == null) {
            this.size = size;
            from = new float[size];
            to = new float[size];
            current = new float[size];
            start = new float[size];
        }

        return this;
    }

    ValueTween object(Object object) {
        this.object = object;
        return this;
    }

    private void initialize() {
        if (!hasTo) {
            accessor.get(object, to);
        } else if (!hasFrom) {
            accessor.get(object, from);
        }

        accessor.get(object, start);

        initialized = true;
    }

    public ValueTween from(float v1) {
        if (accessor.getCount() != 1)
            throw new GdxRuntimeException("Wrong value count!");

        from[0] = v1;

        hasFrom = true;
        return this;
    }

    public ValueTween from(float v1, float v2) {
        if (accessor.getCount() != 2)
            throw new GdxRuntimeException("Wrong value count!");

        from[0] = v1;
        from[1] = v2;

        hasFrom = true;
        return this;
    }

    public ValueTween from(float v1, float v2, float v3) {
        if (accessor.getCount() != 3)
            throw new GdxRuntimeException("Wrong value count!");

        from[0] = v1;
        from[1] = v2;
        from[1] = v3;

        hasFrom = true;
        return this;
    }

    public ValueTween from(float... values) {
        if (values.length != accessor.getCount())
            throw new GdxRuntimeException("Wrong value count!");

        System.arraycopy(values, 0, from, 0, accessor.getCount());
        hasFrom = true;
        return this;
    }

    public ValueTween to(float v1) {
        if (accessor.getCount() != 1)
            throw new GdxRuntimeException("Wrong value count!");

        to[0] = v1;

        hasTo = true;
        return this;
    }

    public ValueTween to(float v1, float v2) {
        if (accessor.getCount() != 2)
            throw new GdxRuntimeException("Wrong value count!");

        to[0] = v1;
        to[1] = v2;

        hasTo = true;
        return this;
    }

    public ValueTween to(float v1, float v2, float v3) {
        if (accessor.getCount() != 3)
            throw new GdxRuntimeException("Wrong value count!");

        to[0] = v1;
        to[1] = v2;
        to[1] = v3;

        hasTo = true;
        return this;
    }

    public ValueTween to(float... values) {
        if (values.length != accessor.getCount())
            throw new GdxRuntimeException("Wrong value count!");

        System.arraycopy(values, 0, to, 0, accessor.getCount());
        hasTo = true;
        return this;
    }

    public ValueTween relative() {
        this.isRelative = true;
        return this;
    }

    public ValueTween duration(float duration) {
        super.duration(duration);
        return this;
    }

    ValueTween accessor(TweenAccessor accessor) {
        this.accessor = (TweenAccessor<Object>) accessor;
        return this;
    }

    public ValueTween interp(Interpolation interpolation) {
        this.interpolation = interpolation;
        return this;
    }

    public void update(float delta) {
        super.update(delta);

        if (!initialized) initialize();

        float percent = duration == 0 ? 1f : Math.min(1f, time / duration);

        if (interpolation != null) percent = interpolation.apply(percent);

        accessor.get(object, current);

        float absFrom, absTo;

        for (int i = 0; i < accessor.getCount(); i++) {
            absFrom = from[i];
            absTo = to[i];

            if (isRelative) {
                if (hasFrom) {
                    absFrom += start[i];
                }
                if (hasTo) {
                    absTo += start[i];
                }
            }

            current[i] = absFrom + (absTo - absFrom) * percent;
        }

        accessor.set(object, current);
    }

    @Override
    public ValueTween getThis() {
        return this;
    }

    @Override
    public void restart() {
        super.restart();
        initialized = false;
    }

    @Override
    public void reset() {
        super.reset();
        accessor = null;
        object = null;
        initialized = false;
        isRelative = false;
        hasFrom = false;
        hasTo = false;
        interpolation = null;
        Arrays.fill(from, 0);
        Arrays.fill(to, 0);
        Arrays.fill(current, 0);
        Arrays.fill(start, 0);
    }
}
