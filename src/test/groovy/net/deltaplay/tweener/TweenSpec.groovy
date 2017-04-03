package net.deltaplay.tweener

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.GdxRuntimeException
import spock.lang.Specification

class TweenSpec extends Specification {
    TestAccessor test1
    TestAccessor test2
    TestAccessor test3

    void setup() {
        test1 = new TestAccessor(0, 0)
        test2 = new TestAccessor(0, 0)
        test3 = new TestAccessor(0, 0)
    }

    void cleanup() {

    }

    def "Values should be updated to"() {
        given:
        Tweener.Tween tween1 = Tweener.tween(test1)
                .to(5f, 5f).duration(1f)

        when:
        tween1.update(1f)
        then:
        test1.a == 5f
        test1.b == 5f
    }

    def "Values should be updated from"() {
        given:
        Tweener.Tween tween1 = Tweener.tween(test1)
                .from(5f, 5f).duration(1f)

        when:
        tween1.update(1f)
        then:
        test1.a == 0f
        test1.b == 0f
    }

    def "Values should be updated relatively"() {
        given:
        TestAccessor test = new TestAccessor(100f, 100f)
        Tweener.Tween tween1 = Tweener.tween(test)
                .to(50f, 50f).duration(1f).relative()

        when:
        tween1.update(1f)
        then:
        test.a == 150f
        test.b == 150f
    }

    def "Should update time and finish tween"() {
        given:
        Tweener.Tween tween1 = Tweener.tween(test1)
                .to(1f, 1f).duration(10f)

        when:
        tween1.update(5f)
        then:
        tween1.getTime() == 5f
        !tween1.finished()

        when:
        tween1.update(5f)
        then:
        tween1.getTime() == 10f
        tween1.finished()

    }

    def "Should not accept wrong number of parameters"() {
        given:
        Tweener.Tween tween1 = Tweener.tween(test1)

        when:
        tween1.to(1f, 1f, 1f)
        then:
        thrown(GdxRuntimeException)

        when:
        tween1.from(1f, 1f, 1f)
        then:
        thrown(GdxRuntimeException)

    }

    def "Should accept an acessor as parameter"() {
        given:
        Color color = new Color(0, 0, 0, 0)
        Tweener.Tween tween = Tweener.tween(color, new ColorAccessor())
            .to(1, 1, 1, 1).duration(1f)

        when:
        tween.update(1f)

        then:
        color == Color.WHITE
    }

    def "Should be restarted"() {
        given:
        Tweener.Tween tween = Tweener.tween(test1)
            .to(1, 1).duration(1f)

        when:
        tween.update(1f)

        then:
        test1.a == 1
        test1.b == 1

        when:
        tween.restart()

        then:
        !tween.finished()
        tween.time == 0
    }

    def "Sequence should update only current tween"() {
        given:
        Tweener.Tween tween1 = Tweener.tween(test1)
                .to(10f, 10f).duration(1f)
        Tweener.Tween tween2 = Tweener.tween(test2)
                .to(50f, 40f).duration(1f)
        Tweener.Tween tween3 = Tweener.tween(test3)
                .to(6.2f, 4.5f).duration(1f)
        SequenceTween sequenceTween = Tweener.sequence(tween1, tween2, tween3)

        when:
        sequenceTween.update(1f)

        then:
        test1.a == 10f
        test1.b == 10f

        when:
        sequenceTween.update(1f)

        then:
        test2.a == 50f
        test2.b == 40f

        when:
        sequenceTween.update(1f)

        then:
        test3.a == 6.2f
        test3.b == 4.5f
    }

    def "Parallel should update all tweens"() {
        given:
        Tweener.Tween tween1 = Tweener.tween(test1)
                .to(10f, 10f).duration(1f)
        Tweener.Tween tween2 = Tweener.tween(test2)
                .to(50f, 40f).duration(1f)
        ParallelTween parallelTween = Tweener.parallel(tween1, tween2)

        when:
        parallelTween.update(1f)

        then:
        test1.a == 10f
        test1.b == 10f
        test2.a == 50f
        test2.b == 40f
    }
}

class TestAccessor implements Tweener.TweenAccessor<TestAccessor> {
    float a, b

    TestAccessor(float a, float b) {
        this.a = a;
        this.b = b;
    }

    @Override
    void set(TestAccessor object, float[] values) {
        a = values[0]
        b = values[1]
    }

    @Override
    void get(TestAccessor object, float[] values) {
        values[0] = a
        values[1] = b
    }

    @Override
    int getCount() {
        return 2
    }
}

class ColorAccessor implements Tweener.TweenAccessor<Color> {

    @Override
    void set(Color color, float[] values) {
        color.set(values[0], values[1], values[2], values[3])
    }

    @Override
    void get(Color color, float[] values) {
        values[0] = color.r
        values[1] = color.g
        values[2] = color.b
        values[3] = color.a
    }

    @Override
    int getCount() {
        return 4
    }
}
