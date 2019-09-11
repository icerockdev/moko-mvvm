[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Download](https://api.bintray.com/packages/icerockdev/moko/moko-mvvm/images/download.svg) ](https://bintray.com/icerockdev/moko/moko-mvvm/_latestVersion)

# Mobile Kotlin model-view-viewmodel architecture components
This is a Kotlin MultiPlatform library that provide access to resources on iOS & Android with localization support based on system.

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Samples](#samples)
- [Set Up Locally](#setup-locally)
- [Contributing](#contributing)
- [License](#license)

## Features
- **111** 222;

## Requirements
- Gradle version 5.4.1+
- Android API 21+
- iOS version 9.0+

## Installation
root build.gradle  
```groovy
allprojects {
    repositories {
        maven { url = "https://dl.bintray.com/icerockdev/moko" }
    }
}
```

project build.gradle
```groovy
dependencies {
    commonMainApi("dev.icerock.moko:mvvm:0.2.0")
}
```

settings.gradle  
```groovy
enableFeaturePreview("GRADLE_METADATA")
```

On iOS in addition to Kotlin library exist Pod - add in Podfile
```ruby
pod 'MultiPlatformLibraryMvvm', :git => 'https://github.com/icerockdev/moko-mvvm.git', :tag => 'release/0.2.0'
```
**MultiPlatformLibraryMvvm cocoapod requires that the framework compiled from kotlin be named 
MultiPlatformLibrary and be connected as cocoapod named MultiPlatformLibrary. 
Example [here](sample/ios-app/Podfile).
To simplify configuration with MultiPlatformFramework you can use [mobile-multiplatform-plugin](https://github.com/icerockdev/mobile-multiplatform-gradle-plugin)**
`MultiPlatformLibraryMvvm` cocoapod contains extension to `UIView`s for bind to `LiveData`.

## Usage


## Samples
More examples can be found in the [sample directory](sample).

## Set Up Locally 
- In [mvvm directory](mvvm) contains `mvvm` library;
- In [sample directory](sample) contains samples on android, ios & mpp-library connected to apps;
- For test changes locally use `:mvvm:publishToMavenLocal` gradle task, after it samples will use locally published version.

## Contributing
All development (both new features and bug fixes) is performed in `develop` branch. This way `master` sources always contain sources of the most recently released version. Please send PRs with bug fixes to `develop` branch. Fixes to documentation in markdown files are an exception to this rule. They are updated directly in `master`.

The `develop` branch is pushed to `master` during release.

More detailed guide for contributers see in [contributing guide](CONTRIBUTING.md).

## License
        
    Copyright 2019 IceRock MAG Inc
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.