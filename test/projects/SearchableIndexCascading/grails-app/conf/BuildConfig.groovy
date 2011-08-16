grails.plugin.location.searchable = "../../.."

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }
    plugins {
        test 'org.spockframework:spock:0.5-groovy-1.7'
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        test 'org.codehaus.groovy.modules.http-builder:http-builder:0.5.0', {
            excludes "xml-apis", "groovy"
        }

        runtime "hsqldb:hsqldb:1.8.0.10"
    }
}
