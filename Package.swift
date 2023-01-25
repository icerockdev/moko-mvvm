// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "mokoMvvmFlowSwiftUI",
    products: [
        .library(
            name: "mokoMvvmFlowSwiftUI",
            targets: ["mokoMvvmFlowSwiftUI"])
    ],
    targets: [
        .target(
            name: "mokoMvvmFlowSwiftUI",
            path: "mvvm-flow/apple/xcode/mokoMvvmFlowSwiftUI"
        )
    ]
)
