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
        sh 'mvn -B -V -e -U clean package -Pdist -Pindex'
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
              [scanPattern: '**/target/*.jar'],
              [scanPattern: '**/target/*.zip']
            ],
            callflow: [
              enable: true,
              logLevel: 'INFO',
              java: [
                tool: 'Java 11',
                options: [
                  '-Xmx2G'
                ],
                properties: [
                  foo: 'bar'
                ]
              ],
              javaAnalysis: [
                enable: true,
                algorithm: 'RTA_PLUS',
                includes: [
                  [pattern: '**/target/jenkins-examples-callflow-*-dist.zip']
                ],
                entrypointStrategy: 'JAVA_MAIN',
                namespaces: [
                  [namespace: '/^org\\..+\\.example$/']
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
