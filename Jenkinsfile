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
        sh 'mvn -v'
      }
    }

    stage('Policy') {
      steps {
        script {
          nexusPolicyEvaluation(
            enableDebugLogging: true,
            iqStage: 'build',
            iqApplication: 'sandbox-application',
            failBuildOnNetworkError: true,
            iqScanPatterns: [
              [scanPattern: 'package.json']
            ],
          )
        }
      }
    }
  }
}
