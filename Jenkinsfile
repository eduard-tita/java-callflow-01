pipeline {
  agent any

  tools {
    maven 'Maven 3.9'
    jdk 'Java 17'
  }

  options {
    buildDiscarder logRotator(
      artifactDaysToKeepStr: '',
      artifactNumToKeepStr: '',
      daysToKeepStr: '1',
      numToKeepStr: '5'
    )
  }

  stages {
    stage('Build') {
      steps {
        echo "skip"
      }
    }

    stage('Policy') {
      steps {
        script {
          nexusPolicyEvaluation(
            enableDebugLogging: false,
            iqStage: 'build',
            iqApplication: 'honda-04',
            failBuildOnNetworkError: true,
            iqScanPatterns: [
              [scanPattern: 'h2-1.4.196.jar']
            ],
            callflow: [
              enable: true,
              //includes: [
              //  '**/target/jenkins-examples-callflow-*-dist.zip'
              //],
              entrypointStrategy: [
                $class: 'NamedStrategy',
                name: 'JAVA_MAIN',
                namespaces: [
                  'org.h2.tools', 'org.h2.server', 'org.h2.util'
                ]
              ],
              logLevel: 'DEBUG'
            ]
          )

          archiveArtifacts(
            artifacts: '**/bomxray.log',
            followSymlinks: false
          )
        }
      }
    }
  }
}
