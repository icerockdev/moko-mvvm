Pod::Spec.new do |s|
  s.name             = 'mokoMvvmFlowSwiftUI'
  s.version          = '0.13.0'
  s.summary          = 'MOKO MVVM SwiftUI additions for Flow'
  s.description      = 'some description here'
  s.homepage         = 'localhost'
  s.license          = { :type => 'Apache 2' }
  s.authors          = 'IceRock Development'
  s.source           = {
    :http => "https://repo1.maven.org/maven2/dev/icerock/moko/mvvm-flow-swiftui/#{s.version}/mvvm-flow-swiftui-#{s.version}.zip",
    :type => "zip"
  }

  s.platform = :ios
  s.ios.deployment_target = '13.0'
  s.ios.vendored_framework = 'mokoMvvmFlowSwiftUI.xcframework'

  s.requires_arc = true
end