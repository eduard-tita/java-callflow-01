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
        //sh 'mvn -B -V -e -U clean package -Pdist -Pindex'
        sh 'mvn -v'
      }
    }

    stage('Policy') {
      steps {
        script {
          nexusPolicyEvaluation(
            enableDebugLogging: false,
            iqStage: 'build',
            iqApplication: 'sandbox-application',
            failBuildOnNetworkError: true,
            iqScanPatterns: [
              [scanPattern: 'nexus-iq-bamboo-plugin-3.3.1.jar']
            ],
            reachability: [
              logLevel: 'DEBUG',
              javaAnalysis: [
                enable: true,
                namespaces: [
                  [namespace: 'com.sonatype.clm.ci.bamboo']
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
