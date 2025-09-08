import groovy.json.JsonSlurperClassic
import java.util.Date
import java.text.SimpleDateFormat

def slurped = [:]

timeout(1200){
    node("maven") {
        try {

            stage("Checkout") {
                checkout scm
            }

            stage("Build & Push Docker image") {
                sh """
                docker build -f Dockerfile.mobile -t localhost:5005/ui_tests:latest .
                docker push localhost:5005/ui_tests:latest
                """
            }
            sh "mkdir -p envs"

            def yamlConfig = readYaml text: params.YAML_CONFIG

            stage("Create environment variables") {
                dir("envs") {
                   sh "echo 'BROWSER=${yamlConfig['browser']}' > .env"
                   sh "echo 'BASE_URL=${yamlConfig['base_url']}' >> .env"
                   sh "echo 'REMOTE=${yamlConfig['remote']}' >> .env"
                   sh "echo 'BROWSER_VERSION=${yamlConfig['browser_version']}' >> .env"
                   sh "echo 'ENABLE_VIDEO=${yamlConfig['enable_video']}' >> .env"
                   sh "echo 'ENABLE_VNC=${yamlConfig['enable_vnc']}' >> .env"
                   sh "echo 'SELENOID_URL=${yamlConfig['selenoid_url']}' >> .env"
                }
            }
            stage("Prepare Allure results") {
                sh "mkdir -p allure-results"
            }
            stage("Running UI Automation") {
                def status = sh(
                        script: "docker run --rm --name=ui_tests --env-file envs/.env --network=host -v \$PWD/allure-results:/app/allure-results localhost:5005/ui_tests:latest",
                        returnStatus: true
                )
                if (status > 0) {
                    currentBuild.result = 'UNSTABLE'
                }
            }
//
//             stage("Debug allure mount") {
//                 sh "ls -la /home/jenkins/workspace/ui-test-runner/allure-results || true"
//                 sh "ls -la allure-results || true"
//             }

            stage("Allure report publisher") {
                allure([
                        includeProperties: false,
                        jdk              : '',
                        properties       : [],
                        reportBuildPolicy: 'ALWAYS',
                        results          : [[path: 'allure-results']]
                ])
            }

            stage("Gets statistics from allure artifacts") {
                def jsonLines = readFile "allure-report/widgets/summary.json"
                slurped = new JsonSlurperClassic().parseText(jsonLines)

                sh "echo $slurped"
            }

            stage("Telegram notification") {
                def BROWSER = yamlConfig['browser']
                def REMOTE = yamlConfig['remote']
               def BROWSER_VERSION = yamlConfig['browser_version']

                def dateUnixStart = slurped.time.start as long
                def dateUnixStop = slurped.time.stop as long


                def duration = ((slurped.time.duration as long) / 1000) as long
                Date dateObjStart = new Date(dateUnixStart)
                Date dateObjStop = new Date(dateUnixStop)

                def cleanDateStart = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(dateObjStart)
                def cleanDateStop = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(dateObjStop)
//
                def durationMin = (duration /60) as long
                def durationSec = (duration %60) as long

                def message = """==== UI TESTS RESULT ====
            browser name: $BROWSER
            remote: $REMOTE
            browser version: $BROWSER_VERSION

            Test Results:
            Passed: ${slurped.statistic.passed}
            Failed: ${slurped.statistic.failed}
            Broken: ${slurped.statistic.broken}
            Skipped: ${slurped.statistic.skipped}
            Total: ${slurped.statistic.total}

            Start: $cleanDateStart
            Finish: $cleanDateStop
            Duration: $durationMin min $durationSec sec"""

            withCredentials([string(credentialsId: 'chat_id', variable: 'chatId'),
                                 string(credentialsId: 'bot_token', variable: 'botToken')]) {
                    sh """
                    curl -X POST \
                    -H 'Content-Type: application/json' \
                    -d '{"chat_id": "${chatId}", "text": "${message}"}' \
                    "https://api.telegram.org/bot${botToken}/sendMessage"
                    """
                }
            }
        }
        finally {
            stage("Cleanup") {
                sh "docker rm -f ui_tests"
            }
        }
    }
}