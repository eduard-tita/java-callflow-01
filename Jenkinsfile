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
    stage('Build Java') {
      steps {
        sh 'mvn -B -V -e -U clean package -Pdist -Pindex'
      }
    }

    stage('Build Javascript') {
      steps {
        sh 'npm install'
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
              [scanPattern: '**/target/*.zip'],
              [scanPattern: 'package.json'],
              [scanPattern: 'package-lock.json']
            ],
            reachability: [
              //logLevel: 'DEBUG',
              javaAnalysis: [
                enable: true,
                entrypointStrategy: 'JAVA_MAIN',
                includes: [
                  [pattern: '**/target/jenkins-examples-callflow-*-dist.zip']
                ],
                namespaces: [
                  [namespace: 'org.sonatype.lifecycle.jenkins.examples.callflow']
                ]
              ],
              jsAnalysis: [
                  enable: true,
                  sourceFiles: [
                      [pattern: 'app/**']
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
