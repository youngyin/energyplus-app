const fs = require('fs');
const express = require('express');
const app = express();

app.use(express.urlencoded({extended: true}));
app.use(express.json()); 

const data = fs.readFileSync('./database.json');
const conf = JSON.parse(data);
var mysql = require("mysql"); // mysql 모듈을 불러옵니다.

// View engine 을 html로 설정
app.set('view engine', 'html');
app.engine('html', require('ejs').renderFile);

//파일 위치 동적화
app.use(express.static(__dirname + '/views'));

// 포트 번호 설정
var port = normalizePort(process.env.PORT || '3000');
app.set('port', port);

// define db name
const userDB = 'user';
const recordDB = 'record';
const collectorDB = 'collector';

// 커넥션을 정의합니다.
// RDS Console 에서 본인이 설정한 값을 입력해주세요.
const connection = mysql.createConnection({
    host: conf.host,
    user: conf.user,
    password: conf.password,
    port: conf.port,
    database: conf.database // SCHEMAS
});

// RDS에 접속합니다.
connection.connect(function(err) {
  if (err) {
    throw err; // 접속에 실패하면 에러를 throw 합니다.
  } else {
    console.log("connect RDS!");
  }
});

// 1. 로그인
app.get('/api/login/:user_id/:user_pw/:phone', (req, res, next)=>{
  const mquery = 
  `Select count(*) from ${userDB} 
  where 
  user_id = "${req.params.user_id}" and
  user_pw = "${req.params.user_pw}" and
  phone = "${req.params.phone}"`

  connection.query(mquery, (err, rows, fields) => {
    res.send(rows); 
  });
})

// 2. 사용자 아이디로 기록 검색하기
app.get('/api/searchrecordbyuser/:user_id', (req, res, next)=>{
  const mquery = `select volume, discharge_date from record where user_id = "${req.params.user_id}";`;
  connection.query(mquery, (err, rows, fields) => {
    res.send(rows); // 결과를 출력합니다!
  });
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}!`);
});