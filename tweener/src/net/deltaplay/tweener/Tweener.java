package net.deltaplay.tweener;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import sun.util.locale.provider.FallbackLocaleProviderAdapter;

public class Tweener {

    public static ValueTween tween(TweenAccessor<?> object) {
        return new ValueTween().object(object).accessor(object);
    }

    public static ValueTween tween(Object object, TweenAccessor accessor) {
        return new ValueTween().object(object).accessor(accessor);
    }

    public static SequenceTween sequence() {
        return new SequenceTween();
    }

    public static SequenceTween sequence(Tween... tweens) {
        return new SequenceTween(tweens);
    }

    public static RepeatTween repeat() {
        return new RepeatTween();
    }

    public static RepeatTween repeat(int count) {
        return new RepeatTween().count(count);
    }

    public static ParallelTween parallel() {
        return new ParallelTween();
    }

    public static ParallelTween parallel(Tween... tweens) {
        return new ParallelTween(tweens);
    }

    public static TimeTween delay(float delay) {
        return new TimeTween().duration(delay);
    }

    public interface Tween {
        void update(float delta);
        boolean finished();
        void restart();
    }

    public abstract static class BaseTween implements Tween {
        boolean finished;

        @Override
        public boolean finished() {
            return finished;
        }

    }

    public static class ParallelTween extends BaseTween {

        Array<Tween> tweens = new Array<Tween>();

        public ParallelTween() {
        }

        public ParallelTween(Tween... tweens) {
            this.tweens.addAll(tweens);
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

        @Override
        public void restart() {
            finished = false;

            for (int i = 0; i < tweens.size; i++) {
                tweens.get(i).restart();
            }
        }

   }

    public static class SequenceTween extends ParallelTween {

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

    }

    public static class RepeatTween extends BaseTween {

        private Tween tween;
        private int count = -1;
        private int repeated;

        public RepeatTween() {
            this.tween = tween;
        }

        public RepeatTween set(Tween tween) {
            this.tween = tween;
            return this;
        }

        @Override
        public void update(float delta) {
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

        public RepeatTween count(int count) {
            this.count = count;
            return this;
        }

    }

    public static class TimeTween extends BaseTween {
        float duration;
        float time;

        @Override
        public void update(float delta) {
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
    }

    public static class ValueTween extends TimeTween {
        public static final int MAX_SIZE = 10;

        private float[] from = new float[MAX_SIZE];
        private float[] to = new float[MAX_SIZE];
        private float[] current = new float[MAX_SIZE];
        private float[] start = new float[MAX_SIZE];
        private boolean hasFrom, hasTo, isRelative;
        private TweenAccessor<Object> accessor;
        private boolean initialized;
        private Interpolation interpolation = Interpolation.linear;
        private Object object;

        @Override
        public void restart() {
            super.restart();
            initialized = false;
        }

        public ValueTween object(Object object) {
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

        public ValueTween from(float... values) {
            return from(false, values);
        }

        public ValueTween from(boolean relative, float... values) {
            if (values.length != accessor.getCount())
                throw new GdxRuntimeException("Wrong value count!");

            isRelative = relative;

            System.arraycopy(values, 0, from, 0, accessor.getCount());
            hasFrom = true;
            return this;
        }

        public ValueTween to(float... values) {
            return to(false, values);
        }

        public ValueTween to(boolean relative, float... values) {
            if (values.length != accessor.getCount())
                throw new GdxRuntimeException("Wrong value count!");

            isRelative = relative;

            System.arraycopy(values, 0, to, 0, accessor.getCount());
            hasTo = true;
            return this;
        }

        public ValueTween duration(float duration) {
            super.duration(duration);
            return this;
        }

        public ValueTween accessor(TweenAccessor accessor) {
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
    }

    public interface TweenAccessor<T> {
        void set(T object, float[] values);
        void get(T object, float[] values);
        int getCount();
    }

}
