package net.deltaplay.tweener

import spock.lang.Specification

import static net.deltaplay.tweener.Tweener.tween

class TweenManagerSpec extends Specification {

    TweenManager manager = new TweenManager()

    void cleanup() {
        manager.clear()
    }

    def "Tweens should be added"() {
        given:
            Tweener.Tween tween1 = tween(new TestAccessor(0, 0))
            Tweener.Tween tween2 = tween(new TestAccessor(0, 0))

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
            Tweener.Tween tween1 = tween(new TestAccessor(0, 0))
            Tweener.Tween tween2 = tween(new TestAccessor(0, 0))
            manager.add(tween1)
            manager.add(tween2)
            manager.clear()

        expect:
            manager.getTweens().size == 0

    }

    def "Values should be updated"() {
        given:
            TestAccessor testAccessor = new TestAccessor(0, 0)
            Tweener.Tween tween = tween(testAccessor)
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

}
