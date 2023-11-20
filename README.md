# Finding_Shelter   
- 🔍 주소 정보를 입력하면 빠르게 가까운 대피소 3곳을 알려주는 서비스
  

## 소프트웨어의 기능 

  - 해당 서비스로 주소 정보를 입력하여 요청하면 위치 기준에서 가까운 대피소 3곳을 추출한다.
  - 주소는 도로명 주소 또는 지번을 입력하여 요청 받는다.
    - 정확한 주소를 입력 받기 위해 [Kakao 우편번호 서비스](https://postcode.map.daum.net/guide) 사용   
  - 주소는 정확한 상세 주소(동, 호수)를 제외한 주소 정보를 이용하여 추천 한다.   
    - ex) 서울 광운로 20 
  - 입력 받은 주소를 위도, 경도로 변환 하여 기존 대피소 데이터와 비교 및 가까운 대피소를 찾는다.   
    - 지구는 평면이 아니기 때문에, 구면에서 두 점 사이의 최단 거리 구하는 공식이 필요    
    - 두 위 경도 좌표 사이의 거리를 [haversine formula](https://en.wikipedia.org/wiki/Haversine_formula)로 계산  
    - 지구가 완전한 구형이 아니 므로 아주 조금의 오차가 있다.   
  - 입력한 주소 정보에서 정해진 반경(10km) 내에 있는 대피소만 추천한다.   
  - 추출한 대피소 데이터는 길안내 URL 및 로드뷰 URL로 제공한다.   
    - ex) 길안내 URL : https://map.kakao.com/link/map/회사,37.402056,127.108212    
          로드뷰 URL : https://map.kakao.com/link/roadview/37.402056,127.108212    

  - 길안내 URL은 고객에게 제공 되기 때문에 가독성을 위해 shorten url로 제공한다.
  - shorten url에 사용되는 key값은 인코딩하여 제공한다.
    - ex) http://localhost:8080/dir/3Ta
    - base62를 통한 인코딩    
  - shorten url의 유효 기간은 30일로 제한한다.

  - [외부 API(카카오 주소 검색 API](https://developers.kakao.com/docs/latest/ko/local/dev-guide))와 [공공 데이터(대피소 현황 정보)를 활용헀다.
추천된 대피소 길 안내는 [카카오 지도 및 로드뷰 바로가기 URL](https://apis.map.kakao.com/web/guide/#routeurl)로 제공된다. 


## 설치 방법


## 사용 방법 


## Shelter Recommendation Process   

<img width="484" alt="2023-11-15 174420" src="https://github.com/KwangWoonUnivOpenSource/Finding_Shelter-BE/assets/105183327/81c7c613-0e9a-4a97-b8ee-e1d950100d2b">

## Direction Shorten Url Process

<img width="457" alt="2023-11-15 174344" src="https://github.com/KwangWoonUnivOpenSource/Finding_Shelter-BE/assets/105183327/c6c949cb-9eb0-4935-b2f1-87e03ca12a5f">


## Feature List   

- Spring Data JPA를 이용한 CRUD 메서드 구현하기      
- Spock를 이용한 테스트 코드 작성하기     
- 카카오 주소검색 API 연동하여 주소를 위도, 경도로 변환하기   
- 추천 결과를 카카오 지도 URL로 연동하여 제공하기   
- 공공 데이터를 활용하여 개발하기 (대피소 현황 데이터)    
- base62를 이용한 shorten url 개발하기 (길안내 URL)    


## Tech Stack   

- JDK 17
- Spring Boot 3.1.5
- Spring Data JPA
- Gradle
- Lombok
- Github
- MariaDB
- Spock    


## License 
이 프로젝트는 MIT License를 따른다.

이 소프트웨어를 누구라도 무상으로 제한없이 취급해도 좋다. 단, 저작권 표시 및 이 허가 표시를 소프트웨어의 모든 복제물 또는 중요한 부분에 기재해야 한다.

저자 또는 저작권자는 소프트웨어에 관해서 아무런 책임을 지지 않는다.
