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
- [ ] 1단계 - 동시성 이슈 확인하기
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
