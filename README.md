# GraphQL
**Netflix DGS**를 이용해서 GraphQL을 지원하는 Supergraph Architecture를 구현한 프로젝트입니다.

# Rover CLI 설치

1. PowerShell에서 아래 코드를 입력한다
```Shell
iwr 'https://rover.apollo.dev/win/latest' | iex
```

2. 전체 루트에 supergraph.yaml 작성
```yaml
federation_version: =2.3.0

subgraphs:
  users:
    routing_url: http://localhost:8083/graphql
    schema:
      subgraph_url: http://localhost:8083/graphql
  content:
    routing_url: http://localhost:8081/graphql
    schema:
      subgraph_url: http://localhost:8081/graphql
  reviews:
    routing_url: http://localhost:8082/graphql
    schema:
      subgraph_url: http://localhost:8082/graphql
```

3. 명령어 실행
```Shell
rover supergraph compose --config supergraph.yaml --elv2-license=accept | Out-File -Encoding utf8 supergraph.graphql
```

4. supergraph.graphql 생성
자동으로 생성된다.

# Apollo Router 설정
1. Apollo Router 다운로드
https://github.com/apollographql/router/releases/tag/v2.10.1에서 windows 버전을 다운로드 한다.

2. 라우터 실행
압축을 해제하고 폴더 내의 router.exe를 전체 루트로 옮긴다.
그리고 아래 명령어를 실행한다.
```Shell
.\router.exe --supergraph supergraph.graphql
```

3. lh:4000/ 동작
http://localhost:4000/로 POST 요청을 보낸다. 이 때 body는 graphql로 보낸다.