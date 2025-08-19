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
    stage('Read POM Version') {
      steps {
        script {
          // Read the pom.xml file (assuming it's in the current directory)
          def pom = readMavenPom()
          // Access the version property
          def projectVersion = pom.getVersion()
          echo "Project Version: ${projectVersion}"
          // You can also set this as an environment variable for later use
          env.APP_VERSION = projectVersion
        }
      }
    }

    stage('Build') {
      steps {
        echo "Building version: ${env.APP_VERSION}"
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
              [scanPattern: '*.jar']
            ],
            reachability: [
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
