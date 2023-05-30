동시성 문제 해결
===========

# start

```
$ docker pull mysql

$ docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name mysql mysql

$ docker exec -it mysql bash

$ mysql -u root -p

$ create database stock_example

$ use stock_example

# start spring project
```

1. Synchronized
2. Database
3. Redis