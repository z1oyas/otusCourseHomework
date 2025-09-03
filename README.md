Запуск:
chcp 65001
mvn clean test -Dbrowser="chrome" -Dbrowser_version="127.0" -Dbase_url="https://otus.ru" -Dremote="true" -Dselenoid_url="http://localhost/wd/hub" -Denable_video="false" -Denable_vnc="true"
