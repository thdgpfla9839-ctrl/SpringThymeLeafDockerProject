# SpringThymeLeafDockerProject
<aside>
💡

**Spring Boot + JPA** 구조를 이용해 레시피 목록(페이징)과 쉐프 목록을 보여주고, 제목/쉐프명으로 검색하고 레시피 상세보기까지 가능한 프로젝트를 **GitHub Actions와 Docker** 두 가지 방식으로 각각 **배포**함

</aside>

# 🖇️프로젝트 구조

---

```
com.sist.web
├── controller     # RecipeController (페이지 라우팅)
├── restcontroller  # RecipeRestController (검색 API, JSON 응답)
├── service        # RecipeService 인터페이스 / 구현체
├── repository     # JpaRepository (Recipe, RecipeDetail, Chef)
└── entity         # Recipe, RecipeDetail, Chef
```

# 💻소스 구성

---

<aside>

- 실행환경:
    - JDK 21
    - Spring Boot 4.1.0
    - Oracle DB
- 기술 스택:
    - Spring Boot 4.1.0 (data-jpa / jdbc / thymeleaf / webmvc / webservices)
    - JPA(Hibernate) + Oracle DB (ojdbc11)
    - Thymeleaf + thymeleaf-layout-dialect (레이아웃 공통화)
    - Lombok
- 빌드 도구: Gradle
- 주요 기능:
    - 메인 페이지: 레시피 목록 페이징 출력 (/main/main)
    - 쉐프 목록 페이징 출력 (/recipe/chef_list)
    - 레시피 제목/쉐프명 검색 (/recipe/find_vue - 비동기 리스트 API)
    - 쉐프별 레시피 조회 (/recipe/chef_recipe)
    - 레시피 상세보기 (/recipe/detail)
- 구조: Controller → Service(Impl) → JpaRepository → Oracle DB
</aside>

# 🚀배포 방식 (두가지 버전의 배포)

---

### 방식 1) GitHub Actions (jar 직접 실행) ⇒ 자동배포

---

### 방식 2) Docker 컨테이너 배포

---

<aside>

📌 War/Tomcat이 아니라 jar로 직접 실행되는 Spring Boot 내장 서버(Tomcat 내장) 방식

</aside>

<aside>

- main 브랜치에 push
- 실행 위치: self-hosted runner (우분투 서버)
- 순서:
    1. Checkout
    2. JDK 21 설치 (setup-java)
    3. gradlew 권한 부여 (chmod +x)
    4. ./gradlew clean build -x test 로 jar 빌드
    5. 8080포트 사용 중인 기존 프로세스 종료 (kill -15)
    6. nohup java -jar로 재실행
</aside>

<aside>

📌 우분투 서버에서 직접 docker 명령어를 입력해서 수동으로 진행함 (GitHub Actions와 별개 연습)

</aside>

- **이미지 빌드 및 실행**

```bash
sudo docker build -t recipe-app .
sudo docker run --name recipe -it -d -p 8080:8080 recipe-app
```

- **Docker Hub에 올리기**

```bash
sudo docker login -u 도커이름
sudo docker tag recipe-app 도커이름/recipe-app
sudo docker push 도커이름/recipe-app
```

- **다른 서버에서 이미지 받아오기**

```bash
sudo docker pull 도커이름/recipe-app
sudo docker run -d -p 8080:8080 도커이름/recipe-app
```

- **docker-compose로 관리**

```yaml
version: "3"
services:
  app:
    image: thdgpfla5659(도커이름)/recipe-app
    ports:
      - "8080:8080"
```

```bash
sudo docker compose up -d
sudo docker compose down
```

---

<aside>

💡  GitHub Actions 방식과 Docker 방식의 **차이**: 

**GitHub Actions**는 push하면 자동으로 jar를 빌드· 실행하는 반면, 

**Docker** 방식은 이미지로 패키징해서 어느 서버에서든 동일하게 돌릴 수 있게 만드는 방식

</aside>

# 🔥트러블슈팅

---

1) .yml 파일 띄어쓰기 오류
bash
[-n "$PID"] && kill -15 $PID || true

원인: 대괄호([)와 -n 사이에 띄어쓰기가 없음. bash에서 [는 "test"라는 명령어의 다른 이름이라, 띄어쓰기 없이 [-n이라고 붙여 쓰면 컴퓨터는 이걸 [-n이라는 존재하지 않는 명령어로 착각해서 에러를 냄.

해결: [ -n "$PID" ]처럼 대괄호 앞뒤로 띄어쓰기 넣기.
---

2) env: 들여쓰기 오류 → YAML 구조 깨짐
Invalid workflow file: You have an error in your yaml syntax on line 13

원인: env: 블록의 들여쓰기가 첫 번째 run 스텝(PID=...)의 하위 요소인지, 다른 위치인지 애매하게 걸쳐 있어서 steps: 시퀀스 전체의 YAML 구조가 깨짐. GitHub은 이 여파를 steps: 바로 아래 첫 주석 줄(13번째 줄)에서 에러로 표시함 (실제 문제 지점과 에러가 보고되는 줄이 다름).

해결: env: 블록을 두 번째 run 스텝(nohup java -jar) 바로 아래로 옮기고, run:과 정확히 같은 들여쓰기 칸수로 맞춤
</aside>
