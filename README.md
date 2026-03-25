# GraphQL
**Netflix DGS**를 이용해서 GraphQL을 지원하는 Supergraph Architecture를 구현한 프로젝트입니다.

# Rover CLI 설치

PowerShell에서 아래 코드를 입력한다
```Shell
iwr 'https://rover.apollo.dev/win/latest' | iex
```

# Apollo GraphOS 설정
1. Apollo Studio에 가입하고 새로운 Graph를 만들어서 APOLLO_KEY를 발급받는다.

2. PowerShell에서 아래 코드를 입력한다.
```Shell
$env:APOLLO_KEY="service:your-graph-name:YOUR_APOLLO_KEY"

rover subgraph publish your-graph-name@current --name users --schema ./user_service/src/main/resources/schema/schema.graphqls --routing-url http://localhost:8083/graphql

rover subgraph publish your-graph-name@current --name content --schema ./content_service/src/main/resources/schema/schema.graphqls --routing-url http://localhost:8081/graphql

rover subgraph publish your-graph-name@current --name reviews --schema ./review_service/src/main/resources/schema/schema.graphqls --routing-url http://localhost:8082/graphql
```

# Apollo Router 설정
1. Apollo Router 다운로드
https://github.com/apollographql/router/releases/tag/v2.10.1에서 windows 버전을 다운로드 한다.

2. 라우터 실행
압축을 해제하고 폴더 내의 router.exe를 루트로 옮긴다.
그리고 아래 명령어를 실행한다.
```Shell
$env:APOLLO_GRAPH_REF="your-graph-name@current"

.\router.exe --dev
```

3. lh:4000/ 동작
http://localhost:4000/로 POST 요청을 보낸다. 이 때 body는 graphql로 보낸다.