# Tweener

Tweener is a minimal Java/LibGDX library for animating any float property you can write an accessor for.

## Getting Started

In order to add this library to a existing LibGDX Gradle project, add the JitPack repository to your main build.gradle:

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Then, add the dependency to the core project:

```
project(":core") {
    ...

    dependencies {
        ...
        compile "com.github.dcampos:tweener:master-SNAPSHOT"
    }
    ...
}
```


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

