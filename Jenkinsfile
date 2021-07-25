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
                sh "sed -i 's/%VERSION%/${BUILD_NUMBER}/' gradle.properties"
                sh "./gradlew clean compileJava classes jar remapJar"
            }
        }

        stage("Publish") {
            steps {
                script {
                    artifactPath = 'build/libs/nibbles-${BUILD_NUMBER}.jar';

                    if (env.BRANCH_NAME == "master") {
                        groupId = "team.bits"
                        artifactId = "nibbles"
                    } else {
                        groupId = "team.bits.nibbles.dev"
                        artifactId = "nibbles-${BRANCH_NAME}"
                    }

                    nexusArtifactUploader(
                        nexusVersion: NEXUS_VERSION,
                        protocol: NEXUS_PROTOCOL,
                        nexusUrl: NEXUS_URL,
                        groupId: groupId,
                        version: "${BUILD_NUMBER}",
                        repository: NEXUS_REPOSITORY,
                        credentialsId: NEXUS_CREDENTIAL_ID,
                        artifacts: [
                            [artifactId: artifactId,
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