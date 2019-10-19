Pod::Spec.new do |spec|
    spec.name                     = 'MultiPlatformLibraryMvvm'
    spec.version                  = '0.3.0'
    spec.homepage                 = 'https://github.com/icerockdev/moko-mvvm'
    spec.source                   = { :git => "https://github.com/icerockdev/moko-mvvm.git", :tag => "release/#{spec.version}" }
    spec.authors                  = 'IceRock Development'
    spec.license                  = { :type => 'Apache 2', :file => 'LICENSE.md' }
    spec.summary                  = 'Swift additions to moko-mvvm Kotlin/Native library'
    spec.module_name              = "#{spec.name}"
    
    spec.dependency 'MultiPlatformLibrary'
    spec.dependency 'MultiPlatformLibraryResources'

    spec.ios.deployment_target  = '9.0'
    spec.swift_version = '4.2'

    spec.subspec 'Core' do |sp|
      sp.source_files = "mvvm/src/iosMain/swift/Core/**/*.{h,m,swift}"
    end

    spec.subspec 'AlamofireImage' do |sp|
      sp.source_files = "mvvm/src/iosMain/swift/AlamofireImage/**/*.{h,m,swift}"
      sp.dependency 'AlamofireImage'
    end

    spec.subspec 'SkyFloatingLabelTextField' do |sp|
      sp.source_files = "mvvm/src/iosMain/swift/SkyFloatingLabelTextField/**/*.{h,m,swift}"
      sp.dependency 'SkyFloatingLabelTextField'
    end

    spec.pod_target_xcconfig = {
        'VALID_ARCHS' => '$(ARCHS_STANDARD_64_BIT)'
    }
end
