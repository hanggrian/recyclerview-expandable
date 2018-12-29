buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(android())
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(bintrayRelease())
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    tasks.withType<Javadoc> {
        isEnabled = false
    }
}

tasks {
    register<Delete>("clean") {
        delete(buildDir)
    }
    named<Wrapper>("wrapper") {
        gradleVersion = VERSION_GRADLE
    }
}
