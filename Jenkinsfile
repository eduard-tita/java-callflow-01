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
            iqScanPatterns: [
              [scanPattern: '**/target/*.jar'],
              [scanPattern: '**/*.zip']
            ],
            reachability: [
              logLevel: 'INFO',
              javaAnalysis: [
                enable: true,
                entrypointStrategy: 'JAVA_MAIN',
                includes: [
                  [pattern: '**/target/jenkins-examples-callflow-*-dist.zip']
                ],
                namespaces: [
                  [namespace: 'org.sonatype.lifecycle.jenkins.examples.callflow']
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
