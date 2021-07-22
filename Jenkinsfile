pipeline {
    agent {
        label "master"
    }

    tools {
        jdk 'jdk_16'
        maven 'Maven'
    }

    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "172.17.0.1:8081/nexus"
        NEXUS_REPOSITORY = "bits-maven-repo"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
    }

    stages {
        stage("Build") {
            steps {
                sh "sed -i 's/%VERSION%/${BRANCH_NAME}-${BUILD_NUMBER}/' gradle.properties"
                sh "./gradlew clean build jar"
            }
        }

        stage("Publish") {
            steps {
                script {
                    files = findFiles(glob: 'build/libs/*.jar')
                    artifactPath = files[0].path;
                    nexusArtifactUploader(
                        nexusVersion: NEXUS_VERSION,
                        protocol: NEXUS_PROTOCOL,
                        nexusUrl: NEXUS_URL,
                        groupId: "team.bits",
                        version: "${BUILD_NUMBER}",
                        repository: NEXUS_REPOSITORY,
                        credentialsId: NEXUS_CREDENTIAL_ID,
                        artifacts: [
                            [artifactId: "fabric-utils",
                            classifier: '',
                            file: artifactPath,
                            type: "jar"]
                        ]
                    );
                }
            }
        }
    }
}