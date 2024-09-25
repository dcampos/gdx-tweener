# Tweener
![Build status](https://github.com/dcampos/gdx-tweener/actions/workflows/gradle.yml/badge.svg)

Tweener is a minimal Java/LibGDX library for animating any float property you can write an accessor for.

It uses LibGDX utility classes (like Array, Pool, etc.) under the hood, so it fits nicely with the LibGDX ecosystem.

## Getting Started

In order to add this library to an existing LibGDX Gradle project, add the JitPack repository to your main `build.gradle`:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Then, add the dependency to the core project:

```gradle
project(":core") {
    ...

    dependencies {
        ...
        compile "com.github.dcampos:tweener:0.1.0"
    }
    ...
}
```

## Usage

Define one or more accessors:

```java
    public enum SpriteAccessor implements TweenAccessor<Sprite> {

        POS(2) {
            @Override
            public void get(Sprite object, float[] values) {
                values[0] = object.getX();
                values[1] = object.getY();
            }

            @Override
            public void set(Sprite object, float[] values) {
                object.setPosition(values[0], values[1]);
            }
        },
        
        // ...

        ALPHA(1) {
            @Override
            public void set(Sprite object, float[] values) {
                object.setAlpha(values[0]);
            }

            @Override
            public void get(Sprite object, float[] values) {
                values[0] = object.getColor().a;
            }
        };

        int count;

        SpriteAccessor(int count) {
            this.count = count;
        }

        @Override
        public int getCount() {
            return count;
        }
    }
```

Create a TweenManager:

```java
    private TweenManager tweenManager = new TweenManager();
```

Add tweens to it:

```java
    duration = 1.5f;
    
    tweenManager.add(
        Tweener.repeat().set(
            Tweener.sequence().add(Tweener.parallel()
                .add(Tweener.tween(sprite1, SpriteAccessor.ALPHA)
                    .to(0).duration(duration))
                .add(Tweener.tween(sprite1, SpriteAccessor.ROTATION)
                    .to(360).duration(duration))
                .add(Tweener.tween(sprite1, SpriteAccessor.SIZE)
                    .to(300, 300).duration(duration))
            ).add(Tweener.parallel()
                .add(Tweener.tween(sprite1, SpriteAccessor.ALPHA)
                    .to(1f).duration(duration))
                .add(Tweener.tween(sprite1, SpriteAccessor.ROTATION)
                    .to(0).duration(duration))
                .add(Tweener.tween(sprite1, SpriteAccessor.SIZE)
                    .to(200, 200).duration(duration))
           ).add(Tweener.delay(0.5f))
        );
    );
```

Update it:

```java
tweenManager.update(deltaTime);
```

For a complete example, see the [examples project](https://github.com/dcampos/tweener-examples).

## Related projects

* [Universal Tween Engine](https://github.com/AurelienRibon/universal-tween-engine): this is the original tweening engine for Java, not just for LibGDX. Unfortunately, it hasn't been actively mantained since 2013. 
* [Tween Engine](https://github.com/dorkbox/TweenEngine): an improved, actively mantained fork of the preceding one. It is a very good choice if you want a more mature library.
* [gdx-tween](https://github.com/CypherCove/gdx-tween): another tweening library for LibGDX.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
