#!/bin/bash

if [ "$1" == "macos-latest" ]; then
  ./gradlew detektIosArm64Main \
    detektIosSimulatorArm64Main \
    detektIosX64Main \
    detektMacosArm64Main \
    detektMacosX64Main \
    detektTvosArm64Main \
    detektTvosSimulatorArm64Main \
    detektTvosX64Main \
    detektWatchosArm32Main \
    detektWatchosArm64Main \
    detektWatchosSimulatorArm64Main \
    detektWatchosX64Main \
    detektWatchosX86Main \
    iosSimulatorArm64Test \
    iosX64Test \
    macosArm64Test \
    macosX64Test \
    tvosSimulatorArm64Test \
    tvosX64Test \
    watchosSimulatorArm64Test \
    watchosX64Test \
    watchosX86Test \
    publishIosArm64PublicationToMavenLocal \
    publishIosSimulatorArm64PublicationToMavenLocal \
    publishIosX64PublicationToMavenLocal \
    publishMacosArm64PublicationToMavenLocal \
    publishMacosX64PublicationToMavenLocal \
    publishTvosArm64PublicationToMavenLocal \
    publishTvosSimulatorArm64PublicationToMavenLocal \
    publishTvosX64PublicationToMavenLocal \
    publishWatchosArm32PublicationToMavenLocal \
    publishWatchosArm64PublicationToMavenLocal \
    publishWatchosSimulatorArm64PublicationToMavenLocal \
    publishWatchosX64PublicationToMavenLocal \
    publishWatchosX86PublicationToMavenLocal
elif [ "$1" == "windows-latest" ]; then
  ./gradlew detektMingwX64Main \
    mingwX64Test \
    publishMingwX64PublicationToMavenLocal
elif [ "$1" == "ubuntu-latest" ]; then
  ./gradlew detektWithoutTests build publishToMavenLocal
else
  ./gradlew detektWithoutTests build publishToMavenLocal
fi