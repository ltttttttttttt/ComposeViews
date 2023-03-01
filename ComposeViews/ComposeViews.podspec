Pod::Spec.new do |spec|
    spec.name                     = 'ComposeViews'
    spec.version                  = '1.3.8.1'
    spec.homepage                 = 'https://github.com/ltttttttttttt/ComposeViews'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Jatpack(JetBrains) Compose views'
    spec.vendored_frameworks      = 'build/cocoapods/framework/ComposeViews.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '14.1'
                
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':ComposeViews',
        'PRODUCT_MODULE_NAME' => 'ComposeViews',
    }
                
    spec.script_phases = [
        {
            :name => 'Build ComposeViews',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
    spec.resources = ['src/commonMain/resources/**', 'src/desktopMain/resources/**', 'src/iosMain/resources/**']
end