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
        sh 'mvn clean'
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
            reachability: [
              logLevel: 'DEBUG',
              javaAnalysis: [
                enable: true,
                entrypointStrategy: 'JAVA_MAIN',
                namespaces: [
                  [namespace: 'org.h2.tools'],
                  [namespace: 'org.h2.server'],
                  [namespace: 'org.h2.util']
                ]
              ]
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
