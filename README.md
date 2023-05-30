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

# 요청이 동시에 들어올 경우

> 공유자원(critical area)을 동시에 접근 후 수정할 경우 race condition 문제
> 하나의 요청이 수정하기 전 데이터를 다른 요청이 읽고 수정하기 때문에 이전에 수정했던 이력이 사라지는 경우


# 1. Synchronized

> synchronized 키워드는 메서드가 한 번에 하나의 스레드에서만 실행할 수 있도록 locking

> @Transaction과 같이 사용할 경우 문제점은 하나의 트랜잭션이 커밋되기 전 다른 트랜잭션이 실행될 수 있기 때문에 같이 사용할 경우 동시성 문제 해결 x

# 2. Database

1. Pessimistic Lock
> 실제로 데이터에 Lock을 걸어서 정합성을 맞추는 방법
> exclusive lock을 걸게 되면 다른 트랜잭션에서는 lock이 해제되기 전까지 데이터를 가져갈 수 없게 된다. (읽기, 쓰기 불가 / shared lock에서는 읽기 가능)
> dead lock 가능성


2. Optimistic Lock
> 실제로 Lock을 이용하지 않고 버전을 이용하면서 정합성을 맞추는 방법
> 먼저 데이터를 읽은 후에 update를 수행할 때 현재 내가 읽은 버전에 맞는지 확인하여 업데이트 수행
> 내가 읽는 버전에서 수정사항이 생겼을 경우 application에서 다시 읽은 후에 작업 수행 
> JPA @Version

3. Named Lock
> 이름을 가진 metadata locking
> 이름을 가진 lockdㅡㄹ 획득한 후 해제할 때까지 다른 세션은 lock을 확득할 수 없도록 함
> transaction이 종료될 때 lock이 자동으로 해제되지 x. 별도의 명령어로 수행하거나 선점시간이 끝나야 해제  

# 3. Redis