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

    //stage('Policy') {
    //  steps {
    //    script {
    //      nexusPolicyEvaluation(
    //        enableDebugLogging: false,
    //        iqStage: 'build',
    //        iqApplication: 'ci-config-app',
    //        iqScanPatterns: [
    //          [scanPattern: '**/target/*.jar'],
    //          [scanPattern: '**/*.zip']
    //        ],
    //        reachability: [
    //          logLevel: 'INFO',
    //          javaAnalysis: [
    //            enable: true,
    //            entrypointStrategy: 'JAVA_MAIN',
    //            includes: [
    //              [pattern: '**/target/jenkins-examples-callflow-*-dist.zip']
    //            ],
    //            namespaces: [
    //              [namespace: 'org.sonatype.lifecycle.jenkins.examples.callflow']
    //            ]
    //          ]
    //        ]
    //      )
    //    }
    //  }
    //}

    stage('NXRM Upload') {
      steps {
        nexusPublisher(
            nexusInstanceId: 'NXRM3',
            nexusRepositoryId: 'maven-releases',
            packages: [
                [
                    $class: 'MavenPackage',
                    mavenAssetList: [
                        [classifier: '', extension: 'jar', filePath: 'target/jenkins-examples-callflow-1-SNAPSHOT.jar']
                    ],
                    mavenCoordinate: [
                        artifactId: 'jenkins-examples-callflow',
                        groupId: 'org.sonatype.lifecycle.jenkins.examples',
                        packaging: 'jar',
                        version: '1.0'
                    ]
                ]
            ]
        )
      }
    }
  }
}
