# **학습 목표**

- 데이터베이스 트랜잭션에 대해 학습한다.
- 스프링에서 지원하는 트랜잭션을 학습한다.

# **학습 순서**

- 트랜잭션에 대한 개념 정리
- 학습한 내용을 바탕으로 실습 진행한다.
    - 트랜잭션 격리 레벨이 실제로 어떻게 동작하는지 stage0에서 확인한다.
    - 트랜잭션 전파가 실제로 어떻게 동작하는지 stage1에서 확인한다.

# **저장소**

- jwp-hands-on
    - [transaction](https://github.com/woowacourse/jwp-hands-on/tree/main/transaction)

# **트랜잭션이란?**

- [Database transaction](https://en.wikipedia.org/wiki/Database_transaction)
- 데이터의 정합성을 보장하기 위해 고안된 방법이다.
- 다른 트랜잭션과 독립적이고 일관되고 신뢰할 수 있는 방식으로 처리되어야 한다.
- 트랜잭션의 목적
    - 오류로부터 복구를 허용하고 데이터베이스를 일관성 있게 유지하는 안정적인 작업 단위를 제공한다.
    - 동시에 접근하는 여러 프로그램 간에 격리를 제공한다.

# **ACID**

- 앞에서 설명한 트랜잭션의 목적을 달성하기 위해 트랜잭션이 가져야 하는 속성
- **원자성(Atomicity)**
    - 트랜잭션에 포함된 모든 작업이 모두 성공(Commit)하거나 실패(Rollback)해야 한다.
- **일관성(Consistency)**
    - 트랜잭션을 실행한 전후에는 데이터의 일관성이 손상되지 않아야 한다.
- **격리성(독립성)(Isolation)**
    - 동시에 실행하는 여러 개의 트랜잭션이 서로 영향을 주지 않아야 한다.
- **영속성(지속성)(Durability)**
    - 커밋이 완료된 트랜잭션은 손상되지 않는 성질을 말한다.

# **Isolation Level**

- 트랜잭션이 얼마나 서로 독립적인지 나타내는 개념이다.
    - 왜 트랜잭션이 독립적이어야 할까?
        - 다양한 비정상(Anomaly) 상태, 현상을 방지하기 위해서다.
    - 비정상적인 상태가 왜 나타나는가?
        - 동시에 여러 클라이언트가 데이터에 접근하고 수정하기 때문이다.
    - 어떤 읽기 현상이 발생 할 수 있을까?
        - **Dirty reads**
        - **Non-repeatable reads**
        - **Phantom reads**
- 격리 레벨은 어떤 것이 있을까?
    - **Read Uncommitted**
    - **Read Committed**
    - **Repeatable Read**
    - **Serializable**
- 격리 레벨에 따라 어떤 현상이 발생하는지 실습에서 관찰해보자.

# **Propagation**

- 트랜잭션의 경계에서 이미 진행 중인 트랜잭션이 있을 때 또는 없을 때 어떻게 동작할 것인가를 결정하는 방식을 말한다.
- 어떤 트랜잭션 전파가 있는지 실습을 통해 확인하자.

![image](https://user-images.githubusercontent.com/32123302/195781152-9fcd7a78-ec68-4dd5-a1af-2080eb6dc2cd.png)

![image](https://user-images.githubusercontent.com/32123302/195781172-a0d2dd15-ac69-4d04-871d-4bca9316a836.png)

# **실습 요구 사항**

- stage0는 ACID가 각각 어떤 것을 의미하는지 학습하고 테스트를 통과시킨다.
- stage1은 격리 레벨에 따라 발생하는 현상을 관찰하고 주석의 표를 채운다.
- stage2는 전파에 따른 현상을 관찰하고 테스트를 통과시킨다.

# **0단계 - ACID**

- ACID가 어떤 의미인지 학습하고 테스트를 통과시켜보자.

# **1단계 - Isolation Level**

- 격리 레벨(isolation level)을 학습하고 실제로 어떤 현상이 발생하는지 테스트로 확인해보자.
- phantomReading() 테스트 케이스는 **`Docker 🐳`**를 실행한 상태에서 테스트를 돌려야 정상 동작한다.

# **2단계 - Propagation**

- **`@Transactional`**의 속성 중에 propagation이 있다. DB에 없는 기능을 왜 스프링에서 제공할까?
- 테스트를 돌려보고 콘솔창에서 예상했던 대로 트랜잭션이 동작하는지 확인해보자.
- [블로그](https://webcache.googleusercontent.com/search?q=cache:6YsDneUJIQoJ:https://deveric.tistory.com/86&cd=2&hl=ko&ct=clnk&gl=kr)
