# [3W5D] Mission

- 적절한 Log를 기록하고 **LogBack** 설정을 해서 에러는 파일로 기록되어야 한다.
- `SpringBoot Application`으로 변경한다.
- 실행가능한 `jar`파일을 생성한다.
  <br/>

## 구현

- SpringBoot로 실행되게 수정  
  ![SpringBoot로 실행](https://user-images.githubusercontent.com/60170616/132988370-03b836e5-cb02-4711-b33c-709a48acedac.png)
- File Read 확인  
  ![FIleRead 후 list 출력](https://user-images.githubusercontent.com/60170616/132988384-7bf19c33-2c18-4293-be2c-09d04a6e9eca.png)
- Voucher 생성 확인  
  ![Voucher 생성](https://user-images.githubusercontent.com/60170616/132988414-1e88f92a-e9e4-4f29-8eb5-a1a32247d22a.png)
- Application 종료 후 파일에 VoucherList 저장 확인  
  ![application 종료 후 파일 저장](https://user-images.githubusercontent.com/60170616/132988439-d8cf8ffb-9132-4eef-96e5-dd768d57ad80.png)
- maven jar 파일 생성 및 실행

> mvn clean package spring-boot:repackage  
> java -jar target/kdt-0.0.1-SNAPSHOT.jar

![jar 파일 실행](https://user-images.githubusercontent.com/60170616/132989532-f580b5d1-231a-4d10-a649-265f9b0705d5.png)
___