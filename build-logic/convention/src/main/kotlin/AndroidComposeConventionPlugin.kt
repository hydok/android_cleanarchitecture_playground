import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            val androidExtension = extensions.findByName("android")
            if (androidExtension != null && androidExtension is CommonExtension<*, *, *, *, *, *>) {
                androidExtension.apply {
                    buildFeatures {
                        compose = true
                    }
                    composeOptions {
                        kotlinCompilerExtensionVersion =
                            libs.findVersion("composeCompiler").get().requiredVersion
                    }
                }
            }
        }
    }
}
