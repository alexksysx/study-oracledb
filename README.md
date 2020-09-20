# study-oracledb


[Docker container Oracle](https://container-registry.oracle.com/pls/apex/f?p=113:4:9703738010549:::4:P4_REPOSITORY,AI_REPOSITORY,AI_REPOSITORY_NAME,P4_REPOSITORY_NAME,P4_EULA_ID,P4_BUSINESS_AREA_ID:8,8,Oracle%20Database%20Standard%20Edition%202,Oracle%20Database%20Standard%20Edition%202,1,0&cs=3L2p2-22t8ZW7VwclZxbUHnRT5oMb0khu_qnLjUJrQk2W4C1s0zGPgFTHstz_BrUdLZtv7wC1QtewTHM0SI5j9w)

[JDBC driver](https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html)

----
## Запуск

##### Установка драйвера jdbc
Необходимо выполнить команду из файла
`install.sh` в директории `driver`.
После этого обновить maven проект.

##### Запуск БД
Необходимо выполнить команды из файлов
`login.sh` и `start.sh` в директории `docker`.

##### Запуск программы
В Intellij IDEA запустить Main.main()