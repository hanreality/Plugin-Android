package com.hanreality.apk

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApkDistPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('apkDistConf', ApkDistExtension)

        project.afterEvaluate {

            //只可以在 android application 或者 android lib 项目中使用
            if (!project.android) {
                throw new IllegalStateException('Must Apply \'com.android.application\' or \'com.android.library\' first!')
            }
            //配置不能为空
            if (project.apkDistConf == null || project.apkDistConf.targetApkName == null) {
                project.logger.info('ApkDistConf should be set!')
                return
            }

            String targetApkName = project['apkDistConf'].targetApkName

            //枚举每一个 build variant
            project.android.applicationVariants.all { variant ->
                variant.outputs.all { output ->
                    def outputFile = output.outputFile
                    if (outputFile.name.contains("release")) {
                        def fileName = targetApkName
                        if (outputFile.name.contains("signed")) {
                            fileName = targetApkName + "_signed.apk"
                        } else if (outputFile.name.contains("unsigned")) {
                            fileName = targetApkName + "_unsigned.apk"
                        }
                        outputFileName = new File('../release/', fileName)
                    } else if (outputFile.name.contains("debug")) {
                        def fileName = targetApkName + "_debug.apk"
                        outputFileName = new File('../debug/', fileName)
                    }
                }
            }
        }
    }
}