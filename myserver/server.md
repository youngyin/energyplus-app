## 파일 구조
```
C:.
|   .gitignore
|   database.json
|   main.js
|   main.text
|   package-lock.json
|   package.json
|   
\---node_modules
```
## database.json
```
{
    "host": "rds주소.rds.amazonaws.com",
    "user": "user",
    "password": "password",
    "database": "mydb" //패키지 이름
  }
  ```
  # run server
  ```
  npm init
  ```
  package.json이 생성되고 이 파일에서 패키지의 의존성을 관리한다.

  ```
  npm install express
  ```
  node_modules에 expressjs가 설치된다.
  ```
  node main.js  
  ```
  파일을 실행한다.
