# Tweener

Tweener is a minimal Java/LibGDX library for animating any float property you can write an accessor for.

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

For now, please have a look at the [samples project](https://github.com/dcampos/tweener-samples).

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

