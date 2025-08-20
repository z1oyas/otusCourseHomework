import groovy.json.JsonSlurperClassic

def testsStatistics = [:]

timeout(1200){
    node("maven") {
        try {
            sh "mkdir -p envs"

            def yamlConfig = readYaml text: $YAML_CONFIG

            stage("Create environment variables") {
                dir("envs") {
                    sh "BROWSER=${yamlConfig['browser']}" > ".env"
                    sh "BASE_URL=${yamlConfig['base.url']}" >> ".env"
                    sh "REMOTE=${yamlConfig['remote']}" >> ".env"
                    sh "BROWSER_VERSION=${yamlConfig['browser.version']}" >> ".env"
                    sh "ENABLE_VIDEO=${yamlConfig['enable.video']}" >> ".env"
                    sh "ENABLE_VNC=${yamlConfig['enable.vnc']}" >> ".env"
                    sh "SELENOID_URL=${yamlConfig['selenoid.url']}" >> ".env"
                    sh "CHAT_ID=${chat_id}" >> ".env"
                    sh "BOT_TOKEN=${bot_token}" >> ".env"
                }
            }
            stage("Running UI Automation") {
                def status = sh(
                        script: "docker run --rm --name=ui_tests --env-file envs/.env --network=host -v $pwd/allure:/root/ui_tests/allure-results localhost:5005/ui_tests:latest",
                        returnStatus: true
                )
                if (status > 0) {
                    currentBuild.result = 'UNSTABLE'
                }
            }

            stage("Allure report publisher") {
                allure([
                        includeProperties: false,
                        jdk              : '',
                        properties       : [],
                        reportBuildPolicy: 'ALWAYS',
                        results          : [[path: 'allure']]
                ])
            }

            stage("Gets statistics from allure artifacts") {
                def jsonLines = readFile ".allure-report/widget/summary.json"
                def slurped = new JsonSlurperClassic().parseText(jsonLines)

                slurped.each{k, v ->
                    testsStatistics[k] =v
                }

            }


            stage("Telegram notification") {
            def BROWSER = yamlConfig['browser']
            def REMOTE = yamlConfig['remote']
            def BROWSER_VERSION = yamlConfig['browser.version']
                def message = """=============UI TESTS RESULT ================
                browser name: $BROWSER
                remote: $REMOTE
                browser version: $BROWSER_VERSION
                """

                testsStatistics.each{k,v ->
                    message += "\t\t$k: $v\n"
                }
                withCredentials([string(credentialsId: 'chat_id', variable: 'chatId'), string(credentialsId: 'bot_token',variable: 'botToken')]){
                    sh"curl -X POST -H 'Content-Type: application/json' -d '{\"chat_id\": \"$chatId\", \"text\": \"$message\"'" +
                            " \"https://api.telegram.org/bot$botToken/sendMessage\""
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