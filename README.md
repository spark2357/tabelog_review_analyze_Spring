## 소개
타베로그 리뷰도 네이버 리뷰처럼 키워드 필터링이 가능했으면 좋겠어서 기획한 프로젝트.
해외여행을 가면 맛집을 가도 현지인들은 어떤 메뉴를 많이 먹는지, 그 가게에는 어떤 특성이 있는지 인터넷 검색만으로는 알기 힘들다.
식당의 분위기와 문화까지도 리뷰로 쉽게 알고 싶었다.
메뉴, 경험, 분위기, 인원수 4가지 카테고리로 키워드를 분류했으나, 직접 파인튜닝한 모델의 한계로 결과는 정확하지는 않다..
또한, 무단으로 크롤링하는 만큼 시간 간격을 길게 둬서 실사용에는 부족함이 많다.

- AI 학습: https://github.com/spark2357/koelectra_tabelog_review_NER
- AI API 서버: https://github.com/spark2357/tabelog_review_analyze_FastAPI
- Backend: Spring6, SpringBoot3
- DB: PostgreSQL

http://3.36.237.34/
시험적으로 이곳에서 서비스 중.
AWS EC2 2개, RDS 1개에 배포.

## 메인 화면
<img width="1919" height="1199" alt="메인-로그인 전" src="https://github.com/user-attachments/assets/169a63d4-43dc-4ec1-89d8-8b0f25cb21ff" />
<img width="1919" height="1199" alt="메인 - 로그인 후" src="https://github.com/user-attachments/assets/84df50c9-ed70-40b6-b4c3-3d21a296fb55" />

## 회원가입, 로그인
<img width="1919" height="1199" alt="회원가입" src="https://github.com/user-attachments/assets/a4c15435-c546-4f5b-8216-c0141e17c316" />
<img width="1919" height="1199" alt="로그인" src="https://github.com/user-attachments/assets/c4d5dd71-75b9-45c1-9c4a-0e5db43aeb80" />

## 분석 결과 목록
<img width="1919" height="1199" alt="목록" src="https://github.com/user-attachments/assets/e75969c3-75da-4f52-9023-459b50c3e5bb" />

## 분석 요청
<img width="1919" height="1199" alt="분석 요청" src="https://github.com/user-attachments/assets/fbd2a8fc-51b0-46a9-8d19-b1c4f0f4f7cd" />
### 대기열이 꽉 찬 경우
<img width="1259" height="872" alt="429" src="https://github.com/user-attachments/assets/f116f2b7-629f-4a6a-94bb-c0dda0a1d8d3" />

## 요청 접수
<img width="1919" height="1199" alt="요청 접수" src="https://github.com/user-attachments/assets/ca138580-8bfe-4514-817a-5c57b62e5a2e" />


## 분석 결과 - 상세
### PathVariable을  NanoId로 생성해서 추론이 불가능하게 했다.
### 잘못된 Id를 입력한 경우
<img width="1919" height="1199" alt="404" src="https://github.com/user-attachments/assets/4ecd0eac-858d-42e0-b50e-cd8f56c79f6a" />

<img width="1916" height="1118" alt="분석 결과 1" src="https://github.com/user-attachments/assets/cc339ae2-9960-47e5-98ec-8dd42c9c9ad9" />
<img width="1905" height="832" alt="분석 결과 2" src="https://github.com/user-attachments/assets/3d8bb7a2-2b9b-4822-bfd3-7b6a3bf1069f" />
<img width="1894" height="992" alt="분석 결과 3" src="https://github.com/user-attachments/assets/27bf9b27-6eed-4926-b913-f0205d1325cb" />
<img width="1904" height="1059" alt="분석 결과 4" src="https://github.com/user-attachments/assets/9031d9d6-966f-4a4d-92c4-c2f27086a50b" />
<img width="1897" height="1059" alt="분석 결과 5" src="https://github.com/user-attachments/assets/07538202-c6fe-403e-a59c-6c3fc5b3bd81" />

## 분석 결과 - 키워드 필터링
<img width="1911" height="1124" alt="분석 결과 키워드 1" src="https://github.com/user-attachments/assets/f10b9d2f-0008-45c0-b5ec-794559603375" />
<img width="1906" height="830" alt="분석 결과 키워드 2" src="https://github.com/user-attachments/assets/0cbf579d-79f0-4799-b967-63ac5739eb9b" />
<img width="1867" height="742" alt="분석 결과 키워드 3" src="https://github.com/user-attachments/assets/a6462546-31a6-47bd-887d-56121fe47222" />
<img width="1875" height="783" alt="분석 결과 키워드 4" src="https://github.com/user-attachments/assets/5b4816b4-d1e5-4455-b9ba-379f463859e8" />


### 500 에러
<img width="1867" height="1043" alt="image" src="https://github.com/user-attachments/assets/72d0031c-f9b1-483b-a94b-9af43afcd181" />
