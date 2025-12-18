-- 기존 데이터 삭제 및 AUTO_INCREMENT 리셋
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE question_skills;
TRUNCATE TABLE question_categories;
TRUNCATE TABLE questions;
SET FOREIGN_KEY_CHECKS = 1;
ALTER TABLE questions AUTO_INCREMENT = 1;

-- Questions 더미 데이터 삽입
-- Frontend (category_id = 1)
-- Easy 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('React에서 컴포넌트란 무엇인가요?', 'React 컴포넌트는 UI를 독립적이고 재사용 가능한 부분으로 나눈 것입니다. 함수형 컴포넌트와 클래스형 컴포넌트가 있으며, props를 통해 데이터를 받아 렌더링합니다.', 'EASY', NOW()),
('useState Hook의 역할은 무엇인가요?', 'useState는 함수형 컴포넌트에서 상태를 관리하기 위한 React Hook입니다. 상태 값과 그 값을 업데이트하는 함수를 반환합니다.', 'EASY', NOW()),
('JSX란 무엇인가요?', 'JSX는 JavaScript XML의 약자로, React에서 HTML과 유사한 문법을 사용하여 UI를 작성할 수 있게 해주는 JavaScript 확장 문법입니다.', 'EASY', NOW()),
('Props와 State의 차이점은 무엇인가요?', 'Props는 부모 컴포넌트에서 자식 컴포넌트로 전달되는 읽기 전용 데이터이고, State는 컴포넌트 내부에서 관리되는 변경 가능한 데이터입니다.', 'EASY', NOW()),
('React에서 이벤트 핸들링은 어떻게 하나요?', 'React에서는 camelCase로 이벤트를 명명하고, 함수를 이벤트 핸들러로 전달합니다. 예: onClick={handleClick}', 'EASY', NOW()),
('컴포넌트 생명주기란 무엇인가요?', '컴포넌트 생명주기는 컴포넌트가 생성, 업데이트, 제거되는 과정을 의미합니다. useEffect Hook을 사용하여 생명주기 메서드를 구현할 수 있습니다.', 'EASY', NOW()),
('React에서 리스트를 렌더링할 때 key를 사용하는 이유는?', 'key는 React가 어떤 항목이 변경, 추가, 제거되었는지 식별하는 데 도움을 줍니다. 각 항목의 고유성을 보장하여 효율적인 렌더링을 가능하게 합니다.', 'EASY', NOW()),
('useEffect Hook은 언제 사용하나요?', 'useEffect는 컴포넌트가 렌더링된 후에 실행되는 부수 효과(side effect)를 처리하기 위해 사용됩니다. 데이터 fetching, 구독 설정, DOM 조작 등에 활용됩니다.', 'EASY', NOW()),
('React에서 조건부 렌더링은 어떻게 하나요?', '조건부 렌더링은 if문, 삼항 연산자(?:), 논리 연산자(&&) 등을 사용하여 조건에 따라 다른 컴포넌트나 요소를 렌더링합니다.', 'EASY', NOW()),
('Virtual DOM이란 무엇인가요?', 'Virtual DOM은 실제 DOM의 가상 표현으로, React가 변경사항을 효율적으로 처리하기 위해 사용하는 개념입니다. 변경사항을 비교하여 최소한의 DOM 조작만 수행합니다.', 'EASY', NOW());

-- Frontend Medium 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('React의 성능 최적화 방법에는 어떤 것들이 있나요?', 'React.memo, useMemo, useCallback을 사용하여 불필요한 리렌더링을 방지하고, 코드 스플리팅, lazy loading을 통해 번들 크기를 줄일 수 있습니다.', 'MEDIUM', NOW()),
('상태 관리 라이브러리(Redux, Zustand 등)를 언제 사용해야 하나요?', '프로젝트가 커지고 여러 컴포넌트 간 상태 공유가 복잡해질 때, 또는 전역 상태 관리가 필요할 때 상태 관리 라이브러리를 사용합니다.', 'MEDIUM', NOW()),
('React에서 커스텀 Hook을 만드는 이유와 방법은?', '커스텀 Hook은 로직을 재사용하기 위해 만듭니다. "use"로 시작하는 함수를 만들고, 다른 Hook들을 조합하여 사용합니다.', 'MEDIUM', NOW()),
('Context API의 장단점은 무엇인가요?', '장점: props drilling을 피할 수 있고, 전역 상태 관리가 간단합니다. 단점: 모든 하위 컴포넌트가 리렌더링될 수 있어 성능 이슈가 발생할 수 있습니다.', 'MEDIUM', NOW()),
('React에서 메모이제이션을 언제 사용해야 하나요?', '비용이 큰 계산이나 렌더링, 또는 자주 변경되지 않는 데이터를 다룰 때 메모이제이션을 사용하여 성능을 최적화합니다.', 'MEDIUM', NOW()),
('Server-Side Rendering(SSR)과 Client-Side Rendering(CSR)의 차이는?', 'SSR은 서버에서 HTML을 생성하여 전달하고, CSR은 클라이언트에서 JavaScript로 렌더링합니다. SSR은 초기 로딩이 빠르고 SEO에 유리합니다.', 'MEDIUM', NOW()),
('React Router를 사용한 라우팅 구현 방법은?', 'BrowserRouter로 앱을 감싸고, Routes와 Route 컴포넌트를 사용하여 경로를 정의합니다. useNavigate, useParams 등의 Hook을 활용합니다.', 'MEDIUM', NOW()),
('React에서 폼 처리와 유효성 검사는 어떻게 하나요?', '제어 컴포넌트(controlled component)를 사용하여 state로 폼 값을 관리하고, onChange 이벤트로 업데이트합니다. 유효성 검사는 조건문이나 라이브러리(react-hook-form 등)를 사용합니다.', 'MEDIUM', NOW()),
('Error Boundary의 역할과 구현 방법은?', 'Error Boundary는 하위 컴포넌트 트리의 JavaScript 오류를 포착하고, 오류 UI를 표시합니다. componentDidCatch와 getDerivedStateFromError를 사용하여 구현합니다.', 'MEDIUM', NOW()),
('React의 렌더링 최적화 전략에는 무엇이 있나요?', '불필요한 리렌더링 방지(React.memo), 코드 스플리팅, 가상화(virtualization), 이미지 최적화, 번들 최적화 등을 통해 렌더링 성능을 개선할 수 있습니다.', 'MEDIUM', NOW());

-- Frontend Hard 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('React의 Fiber 아키텍처는 어떻게 동작하나요?', 'Fiber는 React 16에서 도입된 재조정(reconciliation) 알고리즘입니다. 작업을 작은 단위로 나누고 우선순위를 부여하여 비동기적으로 처리할 수 있게 합니다.', 'HARD', NOW()),
('React의 Concurrent Mode와 Suspense는 무엇인가요?', 'Concurrent Mode는 React가 여러 작업을 동시에 처리할 수 있게 해주는 기능이고, Suspense는 비동기 작업이 완료될 때까지 대체 UI를 표시하는 컴포넌트입니다.', 'HARD', NOW()),
('React에서 메모리 누수를 방지하는 방법은?', 'useEffect의 cleanup 함수를 사용하여 구독 해제, 이벤트 리스너 제거, 타이머 클리어 등을 수행하고, 불필요한 참조를 제거합니다.', 'HARD', NOW()),
('React의 렌더링 파이프라인을 설명해주세요.', 'React는 렌더링 단계와 커밋 단계로 나뉩니다. 렌더링 단계에서는 Virtual DOM을 생성하고, 커밋 단계에서는 실제 DOM을 업데이트합니다.', 'HARD', NOW()),
('상태 관리 패턴의 선택 기준은 무엇인가요?', '프로젝트 규모, 상태의 복잡도, 팀의 경험, 성능 요구사항 등을 고려하여 Context API, Redux, Zustand, Jotai 등의 상태 관리 솔루션을 선택합니다.', 'HARD', NOW()),
('React에서 대규모 애플리케이션의 아키텍처 설계 방법은?', '도메인별로 폴더 구조를 나누고, 컴포넌트 계층화, 상태 관리 전략 수립, 코드 스플리팅, 마이크로 프론트엔드 등을 고려하여 설계합니다.', 'HARD', NOW()),
('React의 배치 업데이트(Batch Update)는 어떻게 동작하나요?', 'React는 여러 상태 업데이트를 배치로 묶어서 한 번에 처리합니다. React 18부터는 자동 배칭이 모든 이벤트 핸들러에서 동작합니다.', 'HARD', NOW()),
('React에서 성능 프로파일링과 디버깅 방법은?', 'React DevTools Profiler를 사용하여 컴포넌트 렌더링 시간을 측정하고, React.memo, useMemo, useCallback의 효과를 분석합니다. 또한 Chrome DevTools의 Performance 탭을 활용합니다.', 'HARD', NOW()),
('React의 렌더링 최적화를 위한 고급 기법은?', '가상화(virtualization), 웹 워커 활용, 스케줄러 우선순위 조정, 컴포넌트 지연 로딩, 메모이제이션 전략 수립 등을 통해 최적화할 수 있습니다.', 'HARD', NOW()),
('React 애플리케이션의 보안 취약점과 대응 방법은?', 'XSS 공격 방지를 위해 dangerouslySetInnerHTML 사용 최소화, 입력값 검증, CSP 설정, 의존성 취약점 점검 등을 수행해야 합니다.', 'HARD', NOW());

-- Backend (category_id = 2)
-- Easy 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('RESTful API란 무엇인가요?', 'RESTful API는 REST 아키텍처 스타일을 따르는 API입니다. HTTP 메서드(GET, POST, PUT, DELETE)를 사용하여 리소스를 조작하고, 상태 없는 통신을 지향합니다.', 'EASY', NOW()),
('HTTP 메서드의 역할을 설명해주세요.', 'GET: 리소스 조회, POST: 리소스 생성, PUT: 리소스 전체 수정, PATCH: 리소스 부분 수정, DELETE: 리소스 삭제', 'EASY', NOW()),
('데이터베이스 인덱스란 무엇인가요?', '인덱스는 데이터베이스에서 데이터를 빠르게 검색하기 위한 자료구조입니다. 책의 목차와 같은 역할을 하며, 특정 컬럼에 대한 검색 속도를 향상시킵니다.', 'EASY', NOW()),
('트랜잭션이란 무엇인가요?', '트랜잭션은 데이터베이스에서 하나의 논리적 작업 단위입니다. ACID 속성(원자성, 일관성, 격리성, 지속성)을 만족해야 합니다.', 'EASY', NOW()),
('JWT(JSON Web Token)란 무엇인가요?', 'JWT는 JSON 객체를 안전하게 전송하기 위한 토큰 기반 인증 방식입니다. Header, Payload, Signature로 구성되어 있으며, 무상태(stateless) 인증에 사용됩니다.', 'EASY', NOW()),
('ORM(Object-Relational Mapping)이란?', 'ORM은 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑해주는 기술입니다. SQL 쿼리 대신 객체 지향적인 코드로 데이터베이스를 조작할 수 있게 해줍니다.', 'EASY', NOW()),
('API 버전 관리 방법은?', 'URL 경로에 버전 포함(/api/v1/users), 헤더에 버전 지정, 쿼리 파라미터 사용 등의 방법이 있습니다. 가장 일반적인 방법은 URL 경로에 버전을 포함하는 것입니다.', 'EASY', NOW()),
('캐싱이란 무엇이고 왜 사용하나요?', '캐싱은 자주 사용되는 데이터를 임시 저장소에 저장하여 빠르게 접근할 수 있게 하는 기술입니다. 데이터베이스 부하를 줄이고 응답 속도를 향상시킵니다.', 'EASY', NOW()),
('동기와 비동기의 차이는?', '동기는 작업이 순차적으로 실행되어 이전 작업이 끝나야 다음 작업을 시작하고, 비동기는 작업이 병렬로 실행되어 여러 작업을 동시에 처리할 수 있습니다.', 'EASY', NOW()),
('REST API의 상태 코드는 무엇을 의미하나요?', '200: 성공, 201: 생성됨, 400: 잘못된 요청, 401: 인증 필요, 403: 권한 없음, 404: 찾을 수 없음, 500: 서버 오류 등이 있습니다.', 'EASY', NOW());

-- Backend Medium 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('데이터베이스 정규화의 목적과 과정을 설명해주세요.', '정규화는 데이터 중복을 제거하고 무결성을 보장하기 위한 과정입니다. 1NF부터 5NF까지 있으며, 각 단계마다 특정 이상 현상을 제거합니다.', 'MEDIUM', NOW()),
('분산 시스템에서 CAP 이론이란?', 'CAP 이론은 분산 시스템이 Consistency(일관성), Availability(가용성), Partition tolerance(파티션 허용) 중 최대 2가지만 동시에 만족할 수 있다는 이론입니다.', 'MEDIUM', NOW()),
('마이크로서비스 아키텍처의 장단점은?', '장점: 독립적 배포, 기술 스택 다양성, 확장성. 단점: 복잡한 통신, 분산 트랜잭션 관리, 모니터링 어려움', 'MEDIUM', NOW()),
('데이터베이스 복제(Replication)와 샤딩(Sharding)의 차이는?', '복제는 동일한 데이터를 여러 서버에 복사하는 것이고, 샤딩은 데이터를 여러 서버에 분산 저장하는 것입니다. 복제는 읽기 성능 향상, 샤딩은 쓰기 성능 향상에 유리합니다.', 'MEDIUM', NOW()),
('Rate Limiting을 구현하는 방법은?', '토큰 버킷 알고리즘, 슬라이딩 윈도우, 고정 윈도우 등의 방법을 사용합니다. Redis나 메모리 기반 저장소를 활용하여 구현할 수 있습니다.', 'MEDIUM', NOW()),
('데이터베이스 락(Lock)의 종류와 사용 시나리오는?', '공유 락(Shared Lock): 읽기 작업, 배타 락(Exclusive Lock): 쓰기 작업. 데드락을 방지하기 위해 락 순서를 일관되게 유지해야 합니다.', 'MEDIUM', NOW()),
('메시지 큐(Message Queue)를 사용하는 이유는?', '비동기 처리, 시스템 간 결합도 감소, 부하 분산, 장애 격리 등을 위해 사용합니다. RabbitMQ, Kafka, Redis 등이 대표적인 메시지 큐입니다.', 'MEDIUM', NOW()),
('API 설계 시 고려사항은 무엇인가요?', 'RESTful 원칙 준수, 일관된 네이밍, 적절한 상태 코드 사용, 페이징 처리, 필터링/정렬 지원, 버전 관리, 보안(인증/인가), 문서화 등을 고려해야 합니다.', 'MEDIUM', NOW()),
('데이터베이스 커넥션 풀(Connection Pool)이란?', '커넥션 풀은 데이터베이스 연결을 미리 생성하여 풀에 보관하고, 필요할 때 재사용하는 기법입니다. 연결 생성 비용을 줄이고 성능을 향상시킵니다.', 'MEDIUM', NOW()),
('인증(Authentication)과 인가(Authorization)의 차이는?', '인증은 사용자가 누구인지 확인하는 것이고, 인가는 사용자가 특정 리소스에 접근할 권한이 있는지 확인하는 것입니다.', 'MEDIUM', NOW());

-- Backend Hard 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('분산 시스템에서 일관성을 보장하는 방법은?', '2PC(Two-Phase Commit), 3PC, Paxos, Raft 알고리즘 등을 사용하여 분산 환경에서 일관성을 보장합니다. 각 알고리즘은 트레이드오프가 있습니다.', 'HARD', NOW()),
('이벤트 소싱(Event Sourcing)과 CQRS 패턴을 설명해주세요.', '이벤트 소싱은 상태 변경을 이벤트로 저장하고, CQRS는 명령(Command)과 조회(Query)를 분리하는 패턴입니다. 두 패턴을 함께 사용하면 확장성과 유연성을 높일 수 있습니다.', 'HARD', NOW()),
('데이터베이스 트랜잭션 격리 수준(Isolation Level)을 설명해주세요.', 'READ UNCOMMITTED, READ COMMITTED, REPEATABLE READ, SERIALIZABLE이 있으며, 각 수준마다 허용하는 동시성과 일관성의 트레이드오프가 다릅니다.', 'HARD', NOW()),
('분산 트랜잭션을 처리하는 방법은?', 'Saga 패턴, TCC(Try-Confirm-Cancel), 2PC, 이벤트 기반 보상 트랜잭션 등을 사용하여 분산 환경에서 트랜잭션을 처리합니다.', 'HARD', NOW()),
('서킷 브레이커(Circuit Breaker) 패턴은 무엇인가요?', '서킷 브레이커는 연속된 실패가 발생하면 요청을 차단하여 시스템을 보호하는 패턴입니다. Closed, Open, Half-Open 상태를 가집니다.', 'HARD', NOW()),
('데이터베이스 최적화 전략에는 무엇이 있나요?', '인덱스 최적화, 쿼리 튜닝, 파티셔닝, 정규화/비정규화, 커넥션 풀 설정, 캐싱 전략, 읽기 복제본 활용 등을 통해 데이터베이스를 최적화합니다.', 'HARD', NOW()),
('마이크로서비스 간 통신 패턴은?', '동기 통신(REST, gRPC), 비동기 통신(메시지 큐, 이벤트 버스), API 게이트웨이 패턴 등을 사용하여 서비스 간 통신을 구현합니다.', 'HARD', NOW()),
('분산 추적(Distributed Tracing)이란?', '분산 시스템에서 요청이 여러 서비스를 거치는 경로를 추적하여 성능 병목과 장애 지점을 파악하는 기술입니다. Zipkin, Jaeger 등이 대표적인 도구입니다.', 'HARD', NOW()),
('데이터 일관성과 가용성의 트레이드오프를 어떻게 관리하나요?', '최종 일관성(Eventual Consistency)을 허용하거나, 읽기/쓰기 정책을 조정하여 일관성과 가용성 사이의 균형을 맞춥니다. 비즈니스 요구사항에 따라 선택합니다.', 'HARD', NOW()),
('대규모 트래픽을 처리하기 위한 아키텍처 설계 방법은?', '로드 밸런싱, 수평 확장, 캐싱 전략, CDN 활용, 데이터베이스 샤딩, 읽기 복제본, 비동기 처리, 큐 시스템 등을 조합하여 설계합니다.', 'HARD', NOW());

-- DevOps (category_id = 5)
-- Easy 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('Docker란 무엇인가요?', 'Docker는 컨테이너 기반 가상화 플랫폼입니다. 애플리케이션을 컨테이너로 패키징하여 어느 환경에서든 동일하게 실행할 수 있게 해줍니다.', 'EASY', NOW()),
('컨테이너와 가상머신의 차이는?', '가상머신은 하이퍼바이저를 통해 전체 OS를 가상화하지만, 컨테이너는 호스트 OS의 커널을 공유하여 더 가볍고 빠르게 실행됩니다.', 'EASY', NOW()),
('CI/CD란 무엇인가요?', 'CI(Continuous Integration)는 코드 변경을 자동으로 통합하고 테스트하는 것이고, CD(Continuous Deployment/Delivery)는 자동으로 배포하는 것입니다.', 'EASY', NOW()),
('Kubernetes란 무엇인가요?', 'Kubernetes는 컨테이너 오케스트레이션 플랫폼입니다. 컨테이너의 배포, 스케일링, 관리를 자동화합니다.', 'EASY', NOW()),
('Git의 주요 명령어는 무엇인가요?', 'git add: 스테이징, git commit: 커밋, git push: 원격 저장소에 업로드, git pull: 원격 저장소에서 가져오기, git branch: 브랜치 관리', 'EASY', NOW()),
('로드 밸런서의 역할은?', '로드 밸런서는 여러 서버에 트래픽을 분산시켜 부하를 균등하게 분배하고, 서버 장애 시 자동으로 다른 서버로 트래픽을 전환합니다.', 'EASY', NOW()),
('인프라스트럭처 as Code(IaC)란?', 'IaC는 인프라를 코드로 정의하고 관리하는 방식입니다. Terraform, Ansible, CloudFormation 등의 도구를 사용하여 인프라를 자동화합니다.', 'EASY', NOW()),
('모니터링의 목적은 무엇인가요?', '모니터링은 시스템의 상태, 성능, 오류를 실시간으로 추적하여 문제를 조기에 발견하고 대응하기 위한 것입니다.', 'EASY', NOW()),
('환경 변수(Environment Variable)를 사용하는 이유는?', '환경 변수는 설정값을 코드와 분리하여 보안을 강화하고, 환경별로 다른 설정을 쉽게 적용할 수 있게 해줍니다.', 'EASY', NOW()),
('레지스트리(Registry)란 무엇인가요?', '레지스트리는 Docker 이미지를 저장하고 공유하는 저장소입니다. Docker Hub, AWS ECR, Google Container Registry 등이 있습니다.', 'EASY', NOW());

-- DevOps Medium 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('Kubernetes의 주요 구성 요소를 설명해주세요.', 'Master Node(API Server, etcd, Scheduler, Controller Manager), Worker Node(kubelet, kube-proxy, Container Runtime), Pod, Service, Deployment 등이 있습니다.', 'MEDIUM', NOW()),
('Blue-Green 배포와 Canary 배포의 차이는?', 'Blue-Green은 전체 트래픽을 한 번에 새 버전으로 전환하고, Canary는 점진적으로 일부 트래픽만 새 버전으로 전환하여 검증합니다.', 'MEDIUM', NOW()),
('Docker Compose의 역할은?', 'Docker Compose는 여러 컨테이너로 구성된 애플리케이션을 정의하고 실행하는 도구입니다. YAML 파일로 서비스, 네트워크, 볼륨을 관리합니다.', 'MEDIUM', NOW()),
('CI/CD 파이프라인을 설계할 때 고려사항은?', '테스트 자동화, 빌드 최적화, 배포 전략, 롤백 계획, 보안 스캔, 환경 분리, 알림 설정 등을 고려해야 합니다.', 'MEDIUM', NOW()),
('Kubernetes에서 스케일링은 어떻게 하나요?', 'HPA(Horizontal Pod Autoscaler)를 사용하여 CPU/메모리 사용량에 따라 자동으로 Pod 수를 조정하거나, 수동으로 replica 수를 변경할 수 있습니다.', 'MEDIUM', NOW()),
('인프라 모니터링 도구에는 무엇이 있나요?', 'Prometheus, Grafana, ELK Stack, Datadog, New Relic 등이 있으며, 각각 메트릭 수집, 시각화, 로그 분석 등의 기능을 제공합니다.', 'MEDIUM', NOW()),
('Secret 관리 방법은?', 'Kubernetes Secrets, HashiCorp Vault, AWS Secrets Manager, 환경 변수 등을 사용하여 민감한 정보를 안전하게 관리합니다.', 'MEDIUM', NOW()),
('컨테이너 보안을 위한 베스트 프랙티스는?', '최소 권한 원칙 적용, 베이스 이미지 검증, 취약점 스캔, 네트워크 정책 설정, 리소스 제한, 정기적인 업데이트 등을 수행합니다.', 'MEDIUM', NOW()),
('Git 워크플로우 전략은?', 'Git Flow, GitHub Flow, GitLab Flow 등이 있으며, 브랜치 전략과 릴리스 프로세스를 정의합니다. 팀 규모와 프로젝트 특성에 맞게 선택합니다.', 'MEDIUM', NOW()),
('인프라 비용 최적화 방법은?', '리소스 사용량 모니터링, 오토스케일링 설정, 스팟 인스턴스 활용, 불필요한 리소스 제거, 예약 인스턴스 구매, 컨테이너 최적화 등을 통해 비용을 절감합니다.', 'MEDIUM', NOW());

-- DevOps Hard 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('Kubernetes 네트워크 모델과 CNI를 설명해주세요.', 'Kubernetes는 플랫넷 네트워크 모델을 사용하며, CNI(Container Network Interface) 플러그인을 통해 네트워크를 구성합니다. Calico, Flannel, Weave 등이 있습니다.', 'HARD', NOW()),
('서비스 메시(Service Mesh)의 역할과 구현 방법은?', '서비스 메시는 마이크로서비스 간 통신을 관리하는 인프라 레이어입니다. Istio, Linkerd 등을 사용하여 트래픽 관리, 보안, 관찰성을 제공합니다.', 'HARD', NOW()),
('멀티 클라우드 전략의 장단점과 구현 방법은?', '장점: 벤더 종속성 감소, 재해 복구, 비용 최적화. 단점: 복잡도 증가, 관리 어려움. Kubernetes, Terraform 등을 활용하여 구현합니다.', 'HARD', NOW()),
('GitOps의 개념과 구현 방법은?', 'GitOps는 Git을 단일 소스로 사용하여 인프라와 애플리케이션을 관리하는 방법입니다. ArgoCD, Flux 등을 사용하여 Git 변경사항을 자동으로 클러스터에 반영합니다.', 'HARD', NOW()),
('Kubernetes에서 고가용성을 보장하는 방법은?', '다중 마스터 노드 구성, etcd 클러스터링, Pod Disruption Budget 설정, 리소스 요청/제한 설정, 헬스체크 구성 등을 통해 고가용성을 보장합니다.', 'HARD', NOW()),
('컨테이너 오케스트레이션에서 리소스 스케줄링 알고리즘은?', 'Kubernetes는 우선순위, 선호도, 리소스 요청량 등을 고려하여 Pod를 노드에 스케줄링합니다. 커스텀 스케줄러를 구현할 수도 있습니다.', 'HARD', NOW()),
('인프라 자동화를 위한 도구 비교와 선택 기준은?', 'Terraform(선언적, 멱등성), Ansible(명령형, 에이전트리스), Pulumi(코드 기반) 등을 비교하여 프로젝트 요구사항에 맞게 선택합니다.', 'HARD', NOW()),
('분산 시스템의 관찰성(Observability)을 구현하는 방법은?', '메트릭(Prometheus), 로그(ELK), 트레이싱(Jaeger)을 통합하여 3대 기둥을 구축하고, OpenTelemetry 표준을 활용하여 구현합니다.', 'HARD', NOW()),
('Kubernetes 보안 강화 전략은?', 'RBAC 설정, Network Policy, Pod Security Policy, 이미지 스캔, 시크릿 암호화, API 서버 인증 강화, 노드 보안 강화 등을 통해 보안을 강화합니다.', 'HARD', NOW()),
('대규모 인프라의 비용 관리와 최적화 전략은?', '리소스 태깅, 비용 할당, 사용량 분석, 오토스케일링 튜닝, 스팟 인스턴스 활용, 예약 인스턴스 전략, FinOps 도구 활용 등을 통해 비용을 관리합니다.', 'HARD', NOW());

-- Android (category_id = 3)
-- Easy 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('Android의 4대 컴포넌트는 무엇인가요?', 'Activity(화면), Service(백그라운드 작업), Broadcast Receiver(시스템 이벤트 수신), Content Provider(데이터 공유)가 Android의 4대 컴포넌트입니다.', 'EASY', NOW()),
('Activity 생명주기를 설명해주세요.', 'onCreate → onStart → onResume → (실행 중) → onPause → onStop → onDestroy. onResume과 onPause 사이에 Activity가 사용자와 상호작용합니다.', 'EASY', NOW()),
('Intent란 무엇인가요?', 'Intent는 컴포넌트 간 통신을 위한 메시지 객체입니다. 명시적 Intent(특정 컴포넌트 지정)와 암시적 Intent(액션 기반)로 나뉩니다.', 'EASY', NOW()),
('Fragment란 무엇인가요?', 'Fragment는 Activity 내에서 재사용 가능한 UI 컴포넌트입니다. 여러 Fragment를 하나의 Activity에서 조합하여 사용할 수 있습니다.', 'EASY', NOW()),
('RecyclerView와 ListView의 차이는?', 'RecyclerView는 ViewHolder 패턴을 내장하고 있어 더 효율적이며, 레이아웃 매니저를 통해 다양한 레이아웃을 지원합니다. ListView보다 성능이 우수합니다.', 'EASY', NOW()),
('SharedPreferences란?', 'SharedPreferences는 간단한 키-값 쌍 데이터를 저장하는 Android의 저장소입니다. 설정값이나 사용자 선호도를 저장하는 데 사용됩니다.', 'EASY', NOW()),
('AndroidManifest.xml의 역할은?', 'AndroidManifest.xml은 앱의 구성 정보를 정의하는 파일입니다. 컴포넌트 선언, 권한 요청, 앱 메타데이터 등을 포함합니다.', 'EASY', NOW()),
('Gradle이란 무엇인가요?', 'Gradle은 Android 프로젝트의 빌드 자동화 도구입니다. 의존성 관리, 빌드 설정, 태스크 실행 등을 담당합니다.', 'EASY', NOW()),
('안드로이드에서 스레드와 비동기 처리는?', '메인 스레드에서 네트워크나 무거운 작업을 하면 ANR이 발생하므로, AsyncTask, Handler, Thread, 또는 코루틴을 사용하여 백그라운드에서 처리해야 합니다.', 'EASY', NOW()),
('View와 ViewGroup의 차이는?', 'View는 단일 UI 요소이고, ViewGroup은 여러 View를 포함할 수 있는 컨테이너입니다. 레이아웃은 ViewGroup의 일종입니다.', 'EASY', NOW());

-- Android Medium 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('MVVM 아키텍처 패턴을 Android에서 어떻게 구현하나요?', 'Model(데이터), View(UI), ViewModel(로직)로 분리합니다. LiveData, DataBinding, ViewModel을 사용하여 구현하며, 관찰 가능한 데이터로 UI를 업데이트합니다.', 'MEDIUM', NOW()),
('Room 데이터베이스를 사용하는 방법은?', 'Entity, DAO, Database 클래스를 정의하고, @Entity, @Dao, @Database 어노테이션을 사용합니다. 컴파일 타임에 SQL 쿼리를 검증합니다.', 'MEDIUM', NOW()),
('Retrofit을 사용한 네트워크 통신 방법은?', '인터페이스에 @GET, @POST 등의 어노테이션을 사용하여 API를 정의하고, Retrofit 인스턴스를 생성하여 네트워크 요청을 수행합니다.', 'MEDIUM', NOW()),
('Dependency Injection의 장점과 Dagger/Hilt 사용법은?', '의존성 주입은 코드 재사용성과 테스트 용이성을 높입니다. Hilt는 Dagger를 기반으로 한 Android용 DI 라이브러리로, @HiltAndroidApp, @AndroidEntryPoint 등을 사용합니다.', 'MEDIUM', NOW()),
('Android에서 메모리 누수를 방지하는 방법은?', '정적 참조 피하기, Context 사용 주의, 리스너 해제, WeakReference 사용, LeakCanary로 모니터링 등을 통해 메모리 누수를 방지합니다.', 'MEDIUM', NOW()),
('Jetpack Compose란 무엇인가요?', 'Jetpack Compose는 선언적 UI 프레임워크입니다. Kotlin으로 UI를 작성하고, 상태 변화에 따라 자동으로 리컴포지션됩니다.', 'MEDIUM', NOW()),
('Coroutine을 사용한 비동기 처리 방법은?', 'suspend 함수를 사용하고, CoroutineScope에서 launch나 async로 코루틴을 시작합니다. Dispatchers로 스레드를 지정하고, Flow로 스트림 데이터를 처리합니다.', 'MEDIUM', NOW()),
('Android의 권한 시스템은 어떻게 동작하나요?', 'Android 6.0부터 런타임 권한이 도입되었습니다. 위험한 권한은 사용자에게 요청하고, 일반 권한은 자동으로 부여됩니다.', 'MEDIUM', NOW()),
('ProGuard와 R8의 역할은?', 'ProGuard와 R8은 코드 난독화와 최적화를 수행하는 도구입니다. 앱 크기를 줄이고, 리버스 엔지니어링을 어렵게 만듭니다.', 'MEDIUM', NOW()),
('Android의 백그라운드 작업 제한은?', 'Android 8.0부터 백그라운드 서비스 제한이 강화되었습니다. WorkManager, JobScheduler, Foreground Service 등을 사용하여 백그라운드 작업을 처리합니다.', 'MEDIUM', NOW());

-- Android Hard 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('Android의 빌드 시스템과 빌드 최적화 방법은?', 'Gradle 빌드 캐시, 증분 빌드, 병렬 빌드, 빌드 변형(variant) 관리 등을 통해 빌드 시간을 단축하고, 모듈화를 통해 빌드 성능을 최적화합니다.', 'HARD', NOW()),
('Android의 렌더링 파이프라인과 성능 최적화는?', 'Measure → Layout → Draw 단계를 최적화하고, 오버드로우 방지, 계층 구조 단순화, 하드웨어 가속 활용 등을 통해 렌더링 성능을 개선합니다.', 'HARD', NOW()),
('멀티모듈 아키텍처 설계 방법은?', '도메인별로 모듈을 분리하고, 의존성 방향을 명확히 하며, 공통 모듈과 앱 모듈을 구분합니다. Clean Architecture 원칙을 적용합니다.', 'HARD', NOW()),
('Android의 메모리 관리와 GC 동작 방식은?', 'Dalvik/ART 가비지 컬렉터는 세대별 수집, 동시 마킹 등을 사용합니다. 메모리 누수 방지, 객체 풀링, 이미지 최적화 등을 통해 메모리를 관리합니다.', 'HARD', NOW()),
('Android 보안 강화 방법은?', '코드 난독화, SSL Pinning, 루팅 감지, 디버깅 방지, 데이터 암호화, ProGuard/R8 사용 등을 통해 앱 보안을 강화합니다.', 'HARD', NOW()),
('Jetpack Compose의 성능 최적화는?', '리컴포지션 범위 최소화, remember 사용, key 사용, LazyColumn 최적화, 파생 상태 관리 등을 통해 Compose 성능을 최적화합니다.', 'HARD', NOW()),
('Android의 테스트 전략과 테스트 피라미드는?', '단위 테스트, 통합 테스트, UI 테스트로 구성된 테스트 피라미드를 구축하고, JUnit, Mockito, Espresso, UI Automator 등을 활용합니다.', 'HARD', NOW()),
('Android의 프로세스와 스레드 모델은?', '각 앱은 별도의 프로세스에서 실행되며, 메인 스레드(UI 스레드)와 워커 스레드로 구분됩니다. 프로세스 간 통신은 Binder를 통해 이루어집니다.', 'HARD', NOW()),
('대규모 Android 앱의 아키텍처 설계는?', 'Clean Architecture, MVI/MVVM 패턴, 모듈화, 의존성 주입, 리액티브 프로그래밍 등을 조합하여 확장 가능한 아키텍처를 설계합니다.', 'HARD', NOW()),
('Android의 배터리 최적화와 Doze 모드는?', 'Doze 모드와 App Standby는 배터리를 절약하기 위한 기능입니다. WorkManager, 알람 매니저, 포그라운드 서비스를 적절히 사용하여 배터리 최적화를 고려합니다.', 'HARD', NOW());

-- iOS (category_id = 4)
-- Easy 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('iOS의 MVC 아키텍처 패턴을 설명해주세요.', 'Model(데이터), View(UI), Controller(로직)로 구성됩니다. Controller가 Model과 View를 연결하지만, View와 Model은 서로 직접 통신하지 않습니다.', 'EASY', NOW()),
('View Controller 생명주기는?', 'viewDidLoad → viewWillAppear → viewDidAppear → (화면 표시) → viewWillDisappear → viewDidDisappear → viewWillUnload → viewDidUnload', 'EASY', NOW()),
('Delegate 패턴이란?', 'Delegate 패턴은 한 객체가 다른 객체의 작업을 대신 처리하도록 위임하는 디자인 패턴입니다. 프로토콜을 사용하여 구현합니다.', 'EASY', NOW()),
('ARC(Automatic Reference Counting)란?', 'ARC는 Swift의 메모리 관리 시스템입니다. 컴파일러가 자동으로 참조 카운트를 관리하여 메모리 누수를 방지합니다.', 'EASY', NOW()),
('옵셔널(Optional)이란?', '옵셔널은 값이 있을 수도 있고 없을 수도 있는(nil) 타입입니다. ?로 선언하고, 옵셔널 바인딩이나 옵셔널 체이닝으로 안전하게 접근합니다.', 'EASY', NOW()),
('Storyboard와 Code로 UI를 만드는 차이는?', 'Storyboard는 시각적으로 UI를 구성하지만 협업 시 충돌이 발생하기 쉽고, Code는 버전 관리가 용이하고 재사용성이 높습니다.', 'EASY', NOW()),
('UITableView와 UICollectionView의 차이는?', 'UITableView는 단일 열의 리스트를 표시하고, UICollectionView는 그리드나 커스텀 레이아웃으로 다양한 형태의 컬렉션을 표시할 수 있습니다.', 'EASY', NOW()),
('UserDefaults란?', 'UserDefaults는 간단한 데이터를 영구 저장하는 iOS의 저장소입니다. 키-값 쌍으로 설정값이나 사용자 선호도를 저장합니다.', 'EASY', NOW()),
('프로토콜(Protocol)이란?', '프로토콜은 메서드, 프로퍼티, 요구사항을 정의하는 청사진입니다. 클래스, 구조체, 열거형이 프로토콜을 채택하여 구현할 수 있습니다.', 'EASY', NOW()),
('IBOutlet과 IBAction의 역할은?', 'IBOutlet은 Storyboard의 UI 요소를 코드와 연결하고, IBAction은 UI 이벤트를 처리하는 메서드를 연결합니다.', 'EASY', NOW());

-- iOS Medium 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('MVVM 패턴을 iOS에서 어떻게 구현하나요?', 'Model(데이터), View(UI), ViewModel(로직)로 분리합니다. Combine 프레임워크나 RxSwift를 사용하여 데이터 바인딩을 구현합니다.', 'MEDIUM', NOW()),
('Core Data를 사용하는 방법은?', 'NSManagedObjectModel, NSPersistentContainer를 설정하고, NSManagedObjectContext를 통해 데이터를 저장, 조회, 수정합니다. 관계와 마이그레이션을 관리합니다.', 'MEDIUM', NOW()),
('URLSession을 사용한 네트워크 통신 방법은?', 'URLSession.shared.dataTask를 사용하거나, URLSessionConfiguration을 커스터마이징하여 네트워크 요청을 수행합니다. async/await를 활용할 수도 있습니다.', 'MEDIUM', NOW()),
('Dependency Injection의 구현 방법은?', '생성자 주입, 프로퍼티 주입, 프로토콜 기반 주입 등을 사용합니다. Swinject 같은 DI 프레임워크를 활용할 수도 있습니다.', 'MEDIUM', NOW()),
('SwiftUI의 특징과 사용법은?', 'SwiftUI는 선언적 UI 프레임워크입니다. @State, @Binding, @ObservedObject 등을 사용하여 상태를 관리하고, View 프로토콜을 채택하여 UI를 구성합니다.', 'MEDIUM', NOW()),
('Combine 프레임워크의 역할은?', 'Combine은 리액티브 프로그래밍을 위한 프레임워크입니다. Publisher와 Subscriber를 사용하여 비동기 이벤트를 처리합니다.', 'MEDIUM', NOW()),
('메모리 누수를 방지하는 방법은?', '강한 참조 순환을 weak나 unowned로 해결하고, 클로저에서 self 캡처 시 주의하며, Instruments의 Leaks 도구로 메모리 누수를 확인합니다.', 'MEDIUM', NOW()),
('iOS의 권한 시스템은 어떻게 동작하나요?', 'Info.plist에 권한 사용 설명을 추가하고, CLLocationManager, PHPhotoLibrary 등 권한 관리자를 통해 사용자에게 권한을 요청합니다.', 'MEDIUM', NOW()),
('앱 생명주기(App Lifecycle)를 설명해주세요.', 'Not Running → Inactive → Active → Background → Suspended 상태로 전환됩니다. SceneDelegate와 AppDelegate에서 생명주기 이벤트를 처리합니다.', 'MEDIUM', NOW()),
('GCD(Grand Central Dispatch)와 Operation Queue의 차이는?', 'GCD는 저수준 API로 간단한 작업에 적합하고, Operation Queue는 고수준 API로 복잡한 작업 의존성과 취소 기능을 제공합니다.', 'MEDIUM', NOW());

-- iOS Hard 10개
INSERT INTO questions (question, answer, difficulty, created_at) VALUES
('Swift의 메모리 안전성과 동시성 모델은?', 'Swift는 값 타입, ARC, 접근 제어를 통해 메모리 안전성을 보장합니다. Swift 5.5부터 async/await와 Actor를 통해 동시성을 안전하게 처리합니다.', 'HARD', NOW()),
('iOS의 렌더링 파이프라인과 성능 최적화는?', '레이어 합성, 오프스크린 렌더링 최소화, 이미지 최적화, 뷰 계층 구조 단순화 등을 통해 렌더링 성능을 개선합니다. Instruments로 프로파일링합니다.', 'HARD', NOW()),
('멀티모듈 아키텍처 설계 방법은?', '도메인별로 모듈을 분리하고, 의존성 방향을 명확히 하며, 공통 모듈과 앱 모듈을 구분합니다. SPM이나 CocoaPods로 의존성을 관리합니다.', 'HARD', NOW()),
('Core Data의 성능 최적화 방법은?', '배치 처리, 프리치(fetch) 최적화, 관계 지연 로딩, 백그라운드 컨텍스트 활용, 인덱싱 등을 통해 Core Data 성능을 최적화합니다.', 'HARD', NOW()),
('iOS 보안 강화 방법은?', '코드 난독화, SSL Pinning, Jailbreak 감지, 디버깅 방지, Keychain 사용, 데이터 암호화, App Transport Security 설정 등을 통해 보안을 강화합니다.', 'HARD', NOW()),
('SwiftUI의 성능 최적화는?', '뷰 업데이트 최소화, @State와 @ObservedObject 적절한 사용, LazyView 활용, 뷰 계층 구조 단순화 등을 통해 SwiftUI 성능을 최적화합니다.', 'HARD', NOW()),
('iOS의 테스트 전략과 테스트 피라미드는?', '단위 테스트, 통합 테스트, UI 테스트로 구성된 테스트 피라미드를 구축하고, XCTest, Quick/Nimble, Snapshot Testing 등을 활용합니다.', 'HARD', NOW()),
('대규모 iOS 앱의 아키텍처 설계는?', 'Clean Architecture, MVVM/MVI 패턴, 모듈화, 의존성 주입, 리액티브 프로그래밍(Combine/RxSwift) 등을 조합하여 확장 가능한 아키텍처를 설계합니다.', 'HARD', NOW()),
('iOS의 백그라운드 작업 처리 방법은?', 'Background Tasks, Background Fetch, Silent Push, Background URL Session 등을 사용하여 백그라운드에서 작업을 처리합니다. 시스템 제한을 고려해야 합니다.', 'HARD', NOW()),
('앱 크기 최적화 방법은?', '에셋 최적화, 코드 스플리팅, 불필요한 프레임워크 제거, 비트코드 활성화, 압축 등을 통해 앱 크기를 줄입니다. App Thinning을 활용합니다.', 'HARD', NOW());

-- Question-Category 매핑 데이터
-- 실제 삽입 순서: Frontend(1~30), Backend(31~60), DevOps(61~90), Android(91~120), iOS(121~150)
-- Frontend (category_id = 1) - question_id 1~30
INSERT INTO question_categories (question_id, category_id) VALUES
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1), (9, 1), (10, 1),
(11, 1), (12, 1), (13, 1), (14, 1), (15, 1), (16, 1), (17, 1), (18, 1), (19, 1), (20, 1),
(21, 1), (22, 1), (23, 1), (24, 1), (25, 1), (26, 1), (27, 1), (28, 1), (29, 1), (30, 1);

-- Backend (category_id = 2) - question_id 31~60
INSERT INTO question_categories (question_id, category_id) VALUES
(31, 2), (32, 2), (33, 2), (34, 2), (35, 2), (36, 2), (37, 2), (38, 2), (39, 2), (40, 2),
(41, 2), (42, 2), (43, 2), (44, 2), (45, 2), (46, 2), (47, 2), (48, 2), (49, 2), (50, 2),
(51, 2), (52, 2), (53, 2), (54, 2), (55, 2), (56, 2), (57, 2), (58, 2), (59, 2), (60, 2);

-- DevOps (category_id = 5) - question_id 61~90
INSERT INTO question_categories (question_id, category_id) VALUES
(61, 5), (62, 5), (63, 5), (64, 5), (65, 5), (66, 5), (67, 5), (68, 5), (69, 5), (70, 5),
(71, 5), (72, 5), (73, 5), (74, 5), (75, 5), (76, 5), (77, 5), (78, 5), (79, 5), (80, 5),
(81, 5), (82, 5), (83, 5), (84, 5), (85, 5), (86, 5), (87, 5), (88, 5), (89, 5), (90, 5);

-- Android (category_id = 3) - question_id 91~120
INSERT INTO question_categories (question_id, category_id) VALUES
(91, 3), (92, 3), (93, 3), (94, 3), (95, 3), (96, 3), (97, 3), (98, 3), (99, 3), (100, 3),
(101, 3), (102, 3), (103, 3), (104, 3), (105, 3), (106, 3), (107, 3), (108, 3), (109, 3), (110, 3),
(111, 3), (112, 3), (113, 3), (114, 3), (115, 3), (116, 3), (117, 3), (118, 3), (119, 3), (120, 3);

-- iOS (category_id = 4) - question_id 121~150
INSERT INTO question_categories (question_id, category_id) VALUES
(121, 4), (122, 4), (123, 4), (124, 4), (125, 4), (126, 4), (127, 4), (128, 4), (129, 4), (130, 4),
(131, 4), (132, 4), (133, 4), (134, 4), (135, 4), (136, 4), (137, 4), (138, 4), (139, 4), (140, 4),
(141, 4), (142, 4), (143, 4), (144, 4), (145, 4), (146, 4), (147, 4), (148, 4), (149, 4), (150, 4);

-- Question-Skill 매핑 데이터
-- Frontend 질문들에 React, JavaScript, TypeScript, Vue.js, Angular, Next.js, CSS, HTML 등 매핑
-- Easy (1-10): React, JavaScript, HTML, CSS
INSERT INTO question_skills (question_id, skill_id) VALUES
(1, 1), (1, 4), (1, 11), (2, 1), (2, 4), (3, 1), (3, 4), (3, 11), (4, 1), (4, 4),
(5, 1), (5, 4), (6, 1), (6, 4), (7, 1), (7, 4), (8, 1), (8, 4), (9, 1), (9, 4), (9, 11),
(10, 1), (10, 4), (10, 11);

-- Frontend Medium (11-20): React, JavaScript, TypeScript, Next.js, Vue.js
INSERT INTO question_skills (question_id, skill_id) VALUES
(11, 1), (11, 4), (11, 9), (12, 1), (12, 2), (12, 4), (13, 1), (13, 4), (13, 9), (14, 1), (14, 4),
(15, 1), (15, 4), (15, 9), (16, 1), (16, 12), (17, 1), (17, 4), (18, 1), (18, 4), (19, 1), (19, 4),
(20, 1), (20, 4), (20, 9);

-- Frontend Hard (21-30): React, TypeScript, Next.js, Vue.js, Angular
INSERT INTO question_skills (question_id, skill_id) VALUES
(21, 1), (21, 9), (22, 1), (22, 9), (23, 1), (23, 4), (24, 1), (24, 4), (25, 1), (25, 2), (25, 4),
(26, 1), (26, 12), (27, 1), (27, 9), (28, 1), (28, 9), (29, 1), (29, 9), (30, 1), (30, 4);

-- Backend 질문들에 Java, Spring Boot, Python, Node.js, Go, MySQL, PostgreSQL, MongoDB, Redis 등 매핑
-- Easy (31-40): Java, Spring Boot, MySQL, Python
INSERT INTO question_skills (question_id, skill_id) VALUES
(31, 14), (31, 13), (32, 14), (33, 14), (33, 21), (34, 14), (35, 14), (36, 14), (36, 17), (37, 14), (37, 21),
(38, 14), (38, 21), (39, 14), (39, 17), (40, 14), (40, 21);

-- Backend Medium (41-50): Spring Boot, Java, Python, Node.js, PostgreSQL, MongoDB
INSERT INTO question_skills (question_id, skill_id) VALUES
(41, 13), (41, 14), (42, 14), (42, 24), (43, 13), (43, 14), (44, 13), (44, 14), (44, 21), (45, 14), (45, 21),
(46, 14), (46, 21), (46, 24), (47, 16), (48, 14), (48, 21), (49, 13), (49, 14), (50, 14), (50, 21);

-- Backend Hard (51-60): Spring Boot, Java, Go, PostgreSQL, MongoDB, Redis
INSERT INTO question_skills (question_id, skill_id) VALUES
(51, 13), (51, 14), (52, 13), (52, 14), (53, 13), (53, 14), (54, 13), (54, 14), (55, 13), (55, 14),
(56, 13), (56, 14), (56, 21), (57, 13), (57, 16), (58, 14), (58, 24), (59, 13), (59, 14), (59, 24),
(60, 13), (60, 14), (60, 24), (60, 25), (60, 26);

-- DevOps 질문들에 Docker, Kubernetes, AWS, Jenkins, GitHub Actions, Terraform, Ansible 등 매핑
-- Easy (61-70): Docker, Kubernetes, AWS, Git
INSERT INTO question_skills (question_id, skill_id) VALUES
(61, 34), (62, 34), (63, 34), (64, 35), (65, 34), (66, 35), (67, 34), (68, 35),
(69, 34), (70, 34);

-- DevOps Medium (71-80): Kubernetes, Docker, AWS, Jenkins, GitHub Actions
INSERT INTO question_skills (question_id, skill_id) VALUES
(71, 35), (71, 34), (72, 35), (73, 35), (74, 35), (75, 35), (76, 35), (77, 35),
(78, 34), (79, 34), (80, 35), (80, 36), (72, 39), (75, 40);

-- DevOps Hard (81-90): Kubernetes, Docker, Terraform, Ansible, Istio, Argo CD
INSERT INTO question_skills (question_id, skill_id) VALUES
(81, 35), (81, 48), (82, 35), (82, 48), (83, 35), (84, 35), (85, 35), (86, 35),
(87, 49), (88, 35), (89, 35), (90, 35), (84, 44), (87, 50);

-- Android 질문들에 Kotlin, Java, Flutter, React-Native 등 매핑
-- Easy (91-100): Kotlin, Java
INSERT INTO question_skills (question_id, skill_id) VALUES
(91, 15), (91, 14), (92, 15), (92, 14), (93, 15), (93, 14), (94, 15), (94, 14), (95, 15),
(96, 15), (96, 14), (97, 15), (97, 14), (98, 15), (98, 14), (99, 15), (99, 14), (100, 15), (100, 14);

-- Android Medium (101-110): Kotlin, Java, Flutter, React-Native
INSERT INTO question_skills (question_id, skill_id) VALUES
(101, 15), (102, 15), (103, 15), (104, 15), (105, 15), (106, 15), (107, 15), (108, 15), (109, 15), (110, 15),
(106, 28), (107, 29), (108, 14);

-- Android Hard (111-120): Kotlin, Java, Flutter
INSERT INTO question_skills (question_id, skill_id) VALUES
(111, 15), (112, 15), (113, 15), (114, 15), (115, 15), (116, 15), (117, 15), (118, 15), (119, 15), (120, 15),
(116, 28), (119, 28);

-- iOS 질문들에 Swift, Objective-C, SwiftUI, UIKit 등 매핑
-- Easy (121-130): Swift, Objective-C
INSERT INTO question_skills (question_id, skill_id) VALUES
(121, 30), (121, 31), (122, 30), (122, 31), (123, 30), (123, 31), (124, 30), (125, 30), (126, 30),
(127, 30), (128, 30), (129, 30), (130, 30), (130, 31);

-- iOS Medium (131-140): Swift, SwiftUI, UIKit
INSERT INTO question_skills (question_id, skill_id) VALUES
(131, 30), (131, 32), (132, 30), (133, 30), (134, 30), (135, 30), (135, 32), (136, 30),
(137, 30), (138, 30), (139, 30), (140, 30);

-- iOS Hard (141-150): Swift, SwiftUI, Core Data
INSERT INTO question_skills (question_id, skill_id) VALUES
(141, 30), (141, 32), (142, 30), (143, 30), (144, 30), (145, 30), (146, 30), (146, 32),
(147, 30), (148, 30), (149, 30), (150, 30), (144, 33), (149, 33);

