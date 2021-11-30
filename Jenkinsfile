pipeline {
    agent {
        label "master"
    }

    tools {
        jdk 'jdk_17'
        maven 'Maven'
    }

    environment {
        DISCORD_WEBHOOK = credentials('discord-webhook')
    }

    stages {
        stage("Build") {
            steps {
                sh "sed -i 's/%VERSION%/${BUILD_NUMBER}/' gradle.properties"
                sh "./gradlew clean build"
            }
        }

        stage("Publish") {
            steps {
                script {
                    artifactPath = 'build/libs/nibbles-${BUILD_NUMBER}.jar';
                    sourcesPath = 'build/libs/nibbles-${BUILD_NUMBER}-sources.jar';

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
                            type: "jar"],
                            [artifactId: artifactId,
                            classifier: 'sources',
                            file: sourcesPath,
                            type: "jar"]
                        ]
                    );
                }
            }
        }
    }

    post {
        always {
            discordSend link: env.BUILD_URL, result: currentBuild.currentResult, title: JOB_NAME, webhookURL: DISCORD_WEBHOOK
        }
    }
}