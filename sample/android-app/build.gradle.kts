plugins {
    id("android-app-convention")
    id("detekt-convention")
    id("kotlin-kapt")
}

android {
    buildFeatures.dataBinding = true

    defaultConfig {
        applicationId = "dev.icerock.moko.samples.mvvm"

        versionCode = 1
        versionName = "0.1.0"
    }

    lintOptions {
        disable("Instantiatable") // bug Error: SimpleActivity must extend android.app.Activity [Instantiatable]
        disable("NotificationPermission") // https://github.com/bumptech/glide/issues/4940
    }
}

dependencies {
    implementation(libs.appCompat)

    implementation(projects.sample.mppLibrary)
    implementation(projects.mvvmDatabinding)
}
