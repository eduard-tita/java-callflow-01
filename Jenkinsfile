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
          try {
            nexusPolicyEvaluation(
                enableDebugLogging: false,
                iqStage: 'build',
                iqApplication: 'sandbox-application',
                failBuildOnNetworkError: true,
                iqScanPatterns: [
                    [scanPattern: '**/target/*.jar'],
                    [scanPattern: '**/target/*.zip']
                ],
                reachability: [
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
          } catch (error) {
            echo "Build failed: ${error.getMessage()}"
          }
        }
      }
    }

    stage('Policy 2') {
      steps {
        script {
          nexusPolicyEvaluation(
            enableDebugLogging: false,
            iqStage: 'build',
            iqApplication: 'sandbox-application-2',
            failBuildOnNetworkError: true,
            iqScanPatterns: [
              [scanPattern: '**/target/*.jar']
              //[scanPattern: '**/target/*.zip']
            ],
            reachability: [
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
        }
      }
    }
  }
}
