## Icon



<img src="myapp\app\src\main\res\drawable\biofuel.png" width="20%" />

<div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

<img src="myapp\app\src\main\res\drawable\leaf.png" width="20%" />

<div>아이콘 제작자 <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/kr/" title="Flaticon">www.flaticon.com</a></div>



## 프로젝트 구조

<img src="document\프로젝트 구조.png" width="50%" />



## DEMO

<img src="document\demo.gif" width="40%" />



## 특이사항

- SharedPreference

사용자가 입력한 로그인 정보를 기억

- LiveData

서버와의 통신에 시간이 걸리기에 AsyncTask을 이용해 백그라운드에서 실행

서버에서 받아온 정보를 LiveData로 처리 

oberver을 이용하여 값을 받아와서 값이 변경되면 그 정보를 화면에 띄워줌

- ViewBinding

뷰와 상호작용하는 코드를 쉽게 작성



## 아쉬운 점

- Server post

post 방식으로 통신을 시도해 보았으나  여러 오류가 발생하였고 get을 사용하여 통신을 처리