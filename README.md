# DMU-BackEnd
동양미래대학교 홈페이지의 정보들을 활용하여 더 나은 편리성을 제공하기 위해 만들어진 API 서버입니다. <br>

## 주요 기능
- 원하는 학과, 키워드에 해당하는 대학 공지를 알림으로 받아볼 수 있습니다.
  - 각각의 알림은 ON / OFF 할 수 있습니다.
  - 키워드는 총 20개가 존재합니다.
- 학사 일정, 식단표 정보를 확인할 수 있습니다.
- 학교, 대학 공지사항을 검색 및 확인할 수 있습니다.

<br>

## 아키텍처
### Infrastructure
<img width="800" alt="DMforU 아키텍처" src="https://github.com/user-attachments/assets/dd70b92a-f255-42d6-93e3-9c7a6ff20688">

### Project
<img height="230" alt="API 서버 아키텍처" src="https://github.com/user-attachments/assets/96102a3a-18e2-4667-ab6b-fc93df4e7831">
<img height="230" alt="Admin 서버 아키텍처" src="https://github.com/user-attachments/assets/4e92e493-3510-40c3-8ec7-b147378190b3">

<br>

## 기여 내용
### Java로 작성된 프로젝트를 Kotlin으로 리팩토링하여 코드의 가독성 및 유지 보수성 향상시켰습니다.
- Kotlin을 사용하여 Lombok 라이브러리에 의존하지 않고, 코드의 간결성과 반복적인 작업을 줄였습니다.
- Kotlin의 Null Safety 특징으로, Null Point Exception의 가능성을 줄일 수 있었습니다.

### 단일 모듈 프로젝트를 멀티 모듈 구조로 리팩토링하여 의존성 분리 및 확장성 개선
- 각 모듈별, 의존성을 최대한 분리함으로써, 단일 책임 원칙을 준수할 수 있는 모듈 환경을 만들었습니다. 
- 필요한 도구가 생기는 경우, 모듈을 추가 생성하고 모듈을 Implementation하는 방법으로 확장성을 개선하였습니다.

### Runnable한 서버를 API 서버와 Admin 서버로 분리
- 서버를 분리함으로써 API 서버가 크롤링 및 파싱 기능의 영향을 받지 않도록 하였습니다.
- 또한, 추후 사용자의 트래픽이 많이 발생하게 될 경우, API 서버를 증설 할 수 있는 환경을 만들었습니다.

### 크롤링 결과를 MongoDB에 임시 저장하여 매 요청마다 크롤링을 수행하지 않도록 최적화하여 응답 속도를 약 500ms에서 30ms로 단축 (94% 성능 개선)
- 기존 크롤링 로직은 매 요청마다 크롤링을 수행하여, 데이터를 전달해주는 방식이었습니다.
- 이를 개선하기 위해, 크롤링 데이터를 저장할 수 있도록 하였고, Json 형식으로 데이터를 저장할 수 있는 MongoDB를 활용하였습니다.
- 이를 통해, 매 요청마다 크롤링 및 파싱 작업하는 서버의 부담을 줄일 수 있었고, 응답 속도 또한 개선할 수 있었습니다.

### Junit5로 단위/통합 테스트 작성 및 자동화하고 JaCoCo와 Codecov로 테스트 커버리지 95%이상을 유지하여 코드의 안정성 향상
- 최대한 단위 테스트를 중심으로 테스트를 작성하였습니다.
- FCM과 같이 외부에 종속되는 테스트는 Mock을 활용하여 실제 알림을 전송하지 않는 방법으로 테스트를 작성하였습니다.
- 영속성 모듈의 경우, test용 Profiles을 설정하여 테스트를 작성하였습니다.
- 통합 테스트의 경우, 각 모듈별로 테스트 환경을 통합하여 테스트의 비용을 절감하였습니다.

### Jenkins를 활용하여 CI/CD 파이프라인을 구축하고 자동화된 빌드 및 배포 환경 구축
- Github WebHook를 사용하여 특정 Merge의 경우에만 CI/CD가 작동되록 하였습니다.

### SQS와 Lambda를 활용하여 FCM 푸시 알림 로직의 부하를 분산 처리
- 토큰 개수의 제한 없이 메세지를 보내고, Admin 서버의 부하를 줄이기 위해, SQS 대기열을 만들어, Lambda에 트리거로 연결하였습니다.
- Lambda를 통해 FCM 푸시 알림 전송을 할 수 있도록 Python을 활용하여 로직을 작성하였습니다.

### Prometheus와 Grafana를 활용하여 운영 서버 및 개발 서버 모니터링 시스템 구축
- 기존에는 서버의 문제가 있는지 확인하려면 서버에 접속하여 Log를 직접 살펴봐야 하는 환경이었습니다.
- 해당 과정이 번거롭기 때문에 Promethues와 Grafana를 활용하여 서버 모니터링 대시보드를 구축하였고, 이를 통해 서버의 상태 및 에러 로그의 유무 등을 확인할 수 있게 되었습니다.

<br>

## 아쉬운 점
### 무중단 배포를 적용시키지 못한 것
- 이를 구현하려면 트래픽을 수신하고 분산 처리할 수 있는 Nginx와 같은 도구가 필요합니다.

Nignix
- 애플리케이션이 Static IP로 서버와 연결되어 있어서, Nginx를 사용한다면 구버전 사용자와의 호환성을 위해 기존 서버에 Nginx를 적용하고 새로운 환경을 추가로 구축해야 합니다.
- 또한, 새로운 환경을 구축하였다고 할 지라도 EC2에서 Runanble한 애플리케이션이 3개 리소스 부족 없이 돌아갈 수 있을지도 의문입니다.

AWS ELB
- AWS의 로드밸런서를 사용하여 무중단 배포를 구현할 수도 있지만, 이 경우 동일한 VPC 내에서 EC2 인스턴스를 수평 확장해야 하므로 추가 비용이 발생하게 됩니다.

위와 같은 비용적인 문제로 인해 무중단 배포를 적용시키지 못하였습니다.

<br>

## 기록
- [서버 앞으로의 개선 방향](https://rlwnd2577.tistory.com/entry/DMforU-%EC%84%9C%EB%B2%84-%EC%95%9E%EC%9C%BC%EB%A1%9C%EC%9D%98-%EA%B0%9C%EC%84%A0-%EB%B0%A9%ED%96%A5)
- [Kotlin + Spring / Multi Module 구조 적용](https://rlwnd2577.tistory.com/entry/DMforU-%EC%84%9C%EB%B2%84-%EC%95%9E%EC%9C%BC%EB%A1%9C%EC%9D%98-%EA%B0%9C%EC%84%A0-%EB%B0%A9%ED%96%A5) 
- [Redis에서 MongoDB로 전환](https://rlwnd2577.tistory.com/entry/DMforU-Redis%EC%97%90%EC%84%9C-MongoDB%EB%A1%9C-%EC%A0%84%ED%99%98) 
- [Junit5, Mocktito를 사용한 단위 / 통합 테스트 작성](https://rlwnd2577.tistory.com/entry/DMforU-Jnuit5-Mocktito-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1) 
- [JaCoCo를 사용한 테스트 커버리지 관리 및 테스트 자동화](https://rlwnd2577.tistory.com/entry/DMforU-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BB%A4%EB%B2%84%EB%A6%AC%EC%A7%80-%EA%B4%80%EB%A6%AC-%EB%B0%8F-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%9E%90%EB%8F%99%ED%99%94) 
- [Jenkins를 사용한 CI / CD 구축](https://rlwnd2577.tistory.com/entry/DMforU-Multi-Module-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-Jenkins-CI-CD-%EA%B5%AC%EC%B6%95) 
- [Prometheus, Grafana를 사용한 모니터링 시스템 구축](https://rlwnd2577.tistory.com/entry/DMforU-Prometheus-Grafana-%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%B5%AC%EC%B6%95) 
- [SQS, Lambda를 사용한 FCM 푸시 알림 로직 분리](https://rlwnd2577.tistory.com/entry/DMforU-FCM-%EB%A9%94%EC%8B%9C%EC%A7%80-%EC%A0%84%EC%86%A1%EC%97%90-SQS-%ED%99%9C%EC%9A%A9) 
- [Release 1.2.0 운영 환경 배포 완료](https://rlwnd2577.tistory.com/entry/DMforU-Release-120-%EB%B0%B0%ED%8F%AC-%EC%99%84%EB%A3%8C) 

  

