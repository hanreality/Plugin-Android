package com.hanreality.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin implements Plugin<Project> {

    @Override
     void apply(Project project) {
        project.tasks.register("greeting") {
            doLast {
                println("hello world from plugin 'com.hanreality.plugin.hello.greet'")
            }
        }
    }
}