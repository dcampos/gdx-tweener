# Tweener
[![Build Status](https://travis-ci.org/dcampos/tweener.svg?branch=master)](https://travis-ci.org/dcampos/tweener)

Tweener is a minimal Java/LibGDX library for animating any float property you can write an accessor for.

It uses LibGDX utility classes (like Array, Pool, etc.) under the hood, so it fits nicely with the LibGDX ecosystem.

## Getting Started

In order to add this library to an existing LibGDX Gradle project, add the JitPack repository to your main build.gradle:

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
        compile "com.github.dcampos:tweener:master-SNAPSHOT"
    }
    ...
}
```

## Usage

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

## Related projects

* [Universal Tween Engine](https://github.com/AurelienRibon/universal-tween-engine): this is the original tweening engine for Java, not just for LibGDX. Unfortunately, it hasn't been actively mantained since 2013. 
* [Tween Engine](https://github.com/dorkbox/TweenEngine): an improved, actively mantained fork of the preceding one. It is a very good choice if you want a more mature library.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

