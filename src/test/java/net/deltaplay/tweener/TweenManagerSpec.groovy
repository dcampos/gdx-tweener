package net.deltaplay.tweener

import spock.lang.Specification

class TweenManagerSpec extends Specification {
    TweenManager manager

    void setup() {
        manager = new TweenManager()
    }

    void cleanup() {
        manager.clear()
    }

    def "Values should be updated"() {
        given:
            TestAccessor testAccessor = new TestAccessor();
            Tweener.Tween tween = Tweener.tween(testAccessor)
                .to(2f, 4f).duration(1.0f)
            manager.add(tween)

        when:
            manager.update(0.5f)

        then:
            testAccessor.a == 1f
            testAccessor.b == 2f

        when:
            manager.update(1.0f)

        then:
            testAccessor.a == 2f
            testAccessor.b == 4f
    }

    def "Tweens should be added"() {
        given:
            TestAccessor testAccessor = new TestAccessor()
            Tweener.Tween tween1 = Tweener.tween(testAccessor)
            Tweener.Tween tween2 = Tweener.tween(testAccessor)

        when:
            manager.add(tween1)
        then:
            manager.getTweens().size == 1

        when:
            manager.add(tween2)
        then:
            manager.getTweens().size == 2


    }

    def "Tweens should be cleared"() {
        given:
            TestAccessor testAccessor = new TestAccessor()
            Tweener.Tween tween1 = Tweener.tween(testAccessor)
            Tweener.Tween tween2 = Tweener.tween(testAccessor)
            manager.add(tween1)
            manager.add(tween2)
            manager.clear()

        expect:
            manager.getTweens().size == 0

    }

    class TestAccessor implements Tweener.TweenAccessor<TestAccessor> {
        float a, b

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
}
