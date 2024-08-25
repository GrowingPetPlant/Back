# GrowingPetPlant
반려 식물을 키우는 ‘식집사'들이 늘어나는 요즈음, 식물을 간편하게 기를 수 있는 어플을 제작하고자 하였습니다.</br>
Mqtt 프로토콜을 활용해 아두이노와의 통신을 기반으로 한 IoT 연계 어플입니다.</br>
GrowingPetPlant를 통해 식집사들이 부담을 덜고, 식물 가꾸기를 더욱 즐길 수 있기를 기대합니다.</br>
</br>

## 🪴 시연 영상
[![Video Label](http://img.youtube.com/vi/ZTcdPNmSgsk/0.jpg)](https://www.youtube.com/watch?v=ZTcdPNmSgsk)
</br>
</br>

## 🛠️ Tech Stack
| **Category** | **Technologies**                                                                                                                                                                                                                             |
|--------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Backend**  | <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/> |
| **Database** | <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"/>                            |
| **Cloud**    | <img src="https://img.shields.io/badge/google%20cloud-4285F4?style=for-the-badge&logo=google%20cloud&logoColor=white">
| **Tool**     | <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/> |
</br>

## ERD
![gpp_uml](https://github.com/user-attachments/assets/5bc5a6dd-98c4-4251-9986-36c2de552f86)
</br>
</br>

## 시스템 구조도
<img width="894" alt="GPP_시스템구조도" src="https://github.com/user-attachments/assets/cfa73bdb-683f-4cd8-b157-e106841b4630">
</br>

## 주요 기능
- 식물 온실 온도, 대기 습도, 토양 습도 실시간 모니터링
- 아두이노를 통해 원격 물 주기, 조명 on/off, 팬 on/off
- 식물 온실 자동화
- 식물 온실 환경에 대한 데이터를 그래프로 시각화
- 수분 공급 일자 캘린더에 표시
</br>

## 상세 화면
#### 홈 화면 - 낮/밤
<img width="500" alt="Home 낮/밤" src="https://github.com/user-attachments/assets/b761f6c6-9d7f-419c-9fb3-a289bf7f2727">

#### 상태바
<img width="200" alt="Home 상태바" src="https://github.com/user-attachments/assets/11e4d18a-f45e-4a7f-a739-88507aa8f586">

#### 독 바
<img width="300" alt="Home 독 바" src="https://github.com/user-attachments/assets/98b93178-2b79-47f4-b705-808349918709">

#### 홈 화면 - 스와이프시 식물 추가
<img width="500" alt="Home Plant Add" src="https://github.com/user-attachments/assets/3ad739da-0423-4624-b68c-6c34d3d68082">

#### 캘린더
<img width="250" alt="Calendar" src="https://github.com/user-attachments/assets/1f394459-a11f-45ce-aa16-419b75902268">

#### 그래프
<img width="250" alt="Graph" src="https://github.com/user-attachments/assets/e3dfb575-1c8c-4f70-a988-d9f9799e7464">
</br>

## Mqtt 통신
#### Topic 발행
<img width="532" alt="Screenshot 2024-08-24 at 10 05 15 PM" src="https://github.com/user-attachments/assets/7048a342-827f-456e-a47b-241655d1d2cf">

Server는 유저가 독 바의 버튼을 눌러 기능을 실행하면 해당 기능에 맞는 Topic의 message를 발행합니다.</br>
예를 들어, 유저가 조명 버튼을 누르면 "TOPIC_LIGHT" 토픽에 message를 발행합니다.</br>
발행하는 message는 현재 아두이노의 조명의 작동 여부를 확인하고, 이와 반대 동작을 수행하도록 합니다.</br>
</br>

#### 실시간 모니터링
<img width="538" alt="Screenshot 2024-08-24 at 10 05 31 PM" src="https://github.com/user-attachments/assets/dd877e5d-97a8-47cc-b18c-2476cae32813">

Server는 아두이노가 10초마다 특정 Topic에 발행하는 message를 수신해 해당 유저-식물 번호의 데이터를 저장하고, 실시간 정보를 업데이트합니다.</br>
이때, 여러 식물의 실시간 정보를 받기 위해 아두이노가 발행하는 토픽을 "userPlant/+/temperature"와 같이 지정합니다.</br>
와일드카드 처리를 통해 유저-식물 번호를 구분해 정보를 업데이트할 수 있습니다.</br>
</br>

#### 온실 자동화
@Scheduled 어노테이션을 통해 매일 자정 온실 자동화 함수를 실행합니다.</br>
유저의 자동화 설정 여부를 확인해 설정한 유저의 유저-식물의 상태를 모니터링합니다.</br>
만약, 유저-식물의 상태가 식물DB에 저장되어있는 최적 상태를 벗어나있다면 수분 공급, 조명 작동, 팬 작동을 통해 식물 온실을 최적 상태로 유도합니다.</br>
