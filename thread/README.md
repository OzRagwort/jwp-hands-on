# Thread

### 학습 순서

- 학습 테스트에서 스레드를 직접 생성해본다.
- 스레드 간에 상태가 어떻게 공유되는지 확인한다.
- 임베디드 톰캣의 스레드의 적절한 설정값을 적용한다.

### 요구 사항

- 스레드를 사용하면서 생길 수 있는 동시성 이슈를 경험해보고 어떻게 해결할 수 있을지 고민해보자.
- 스프링부트에서 톰캣의 스레드를 설정한다.

## 실습

- [x] 0단계 - 스레드 이해하기
- [x] 1단계 - 동시성 이슈 확인하기
- [ ] 2단계 - WAS에 스레드 설정하기

---

corePoolSize : 스레드가 증가된 후 사용되지 않아도 유지될 스레드의 수
maximumPoolSize : 스레드 풀이 관리할 최대의 스레드 수

- newCachedThreadPool()
    - 초기 스레드 : 0
    - corePoolSize : 0
    - maximumPoolSize : Integer.MAX_VALUE (컴퓨터 성능에 따라 다름)

- newFixedThreadPool(int nThreads)
    - 초기 스레드 : 0
    - corePoolSize : nThreads
    - maximumPoolSize : nThreads

- 1단계 상황에 따라 1개 또는 2개가 저장되는 이유
  - 테스트를 그냥 돌리면 두번째 스레드가 먼저 실행되어 저장 후 첫번째 스레드가 실행되어 이미 구구라는 데이터가 있어 저장되지 않는다.
  - 하지만 디버깅을 할 때 if 문을 지나서 브레이크 포인트를 걸면 두번째 스레드가 구구를 저장 하기 전에 잠시 대기를 하고 첫번째 스레드가 if문을 통과할 수 있다.
  - 따라서 두개의 구구 데이터가 저장되는 것이다.

- accept-count, max-connections, threads.max에 대해서
  - [공식문서](https://tomcat.apache.org/tomcat-7.0-doc/config/http.html)
  - accept-count
    - 초기값 : 100
    - 스레드 연결이 안되어서 대기하는 큐의 최대 길이
    - The maximum queue length for incoming connection requests when all possible request processing threads are in use. Any requests received when the queue is full will be refused. The default value is 100.
    - 가능한 모든 요청 처리 스레드가 사용 중일 때 들어오는 연결 요청에 대한 최대 큐 길이입니다. 대기열이 가득 찼을 때 수신된 모든 요청은 거부됩니다. 기본값은 100입니다.
  - max-connections
    - 초기값 : BIO-maxThreads, NIO-10000, APR/native-8192
    - 한번에 연결이 가능한 최대 연결 수
    - The maximum number of connections that the server will accept and process at any given time. When this number has been reached, the server will accept, but not process, one further connection. This additional connection be blocked until the number of connections being processed falls below maxConnections at which point the server will start accepting and processing new connections again. Note that once the limit has been reached, the operating system may still accept connections based on the acceptCount setting. The default value varies by connector type. For BIO the default is the value of maxThreads unless an Executor is used in which case the default will be the value of maxThreads from the executor. For NIO the default is 10000. For APR/native, the default is 8192. 
    - For NIO only, setting the value to -1, will disable the maxConnections feature and connections will not be counted.
    - 서버가 주어진 시간에 수락하고 처리할 최대 연결 수입니다. 이 숫자에 도달하면 서버는 하나의 추가 연결을 수락하지만 처리하지는 않습니다. 이 추가 연결은 처리 중인 연결 수가 maxConnections 아래로 떨어질 때까지 차단되며, 이 지점에서 서버는 새 연결을 다시 수락하고 처리하기 시작합니다. 제한에 도달하면 운영 체제가 acceptCount 설정에 따라 연결을 계속 수락할 수 있습니다. 기본값은 커넥터 유형에 따라 다릅니다. BIO의 경우 기본값은 실행기가 사용되지 않는 한 maxThreads 값입니다. 이 경우 기본값은 실행기의 maxThreads 값이 됩니다. NIO의 경우 기본값은 10000입니다. APR/native의 경우 기본값은 8192입니다.
    - NIO의 경우에만 값을 -1로 설정하면 maxConnections 기능이 비활성화되고 연결이 계산되지 않습니다.
  - threads.max
    - 초기값 : 200
    - 톰캣이 처리하고있는 스레드의 최대 수(순간 처리 가능 트랜잭션 수라고도 한다고 함)
    - max-connections값보다 크면 어차피 연결되는게 max-connections값이므로 그 값을 따라가는 것 같기도...?
    - The maximum number of request processing threads to be created by this Connector, which therefore determines the maximum number of simultaneous requests that can be handled. If not specified, this attribute is set to 200. If an executor is associated with this connector, this attribute is ignored as the connector will execute tasks using the executor rather than an internal thread pool. Note that if an executor is configured any value set for this attribute will be recorded correctly but it will be reported (e.g. via JMX) as -1 to make clear that it is not used.
    - 이 커넥터가 생성할 최대 요청 처리 스레드 수. 따라서 처리할 수 있는 최대 동시 요청 수를 결정합니다. 지정하지 않으면 이 속성은 200으로 설정됩니다. 실행기가 이 커넥터와 연결된 경우 커넥터가 내부 스레드 풀이 아닌 실행기를 사용하여 작업을 실행하므로 이 속성은 무시됩니다. 실행기가 구성된 경우 이 속성에 대해 설정된 값이 올바르게 기록되지만 사용되지 않음을 명확히 하기 위해 -1로 보고됩니다(예: JMX를 통해).

- 스프링 부트 2.7.3 의 starter-tomcat 버전은 2.7.3
  - [tomcat 의존성 버전](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-tomcat/2.7.3)
  - tomcat 버전은 9.0.65 버전을 사용
