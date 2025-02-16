# 배포 AWS EC2, Docker
![배포_swagger](https://github.com/user-attachments/assets/44638d3f-6801-44c6-bc2a-1a1b2df0f6ec)
![배포_ec2](https://github.com/user-attachments/assets/71e77637-f4cc-4fb3-b883-fa2528bc93d2)
- dockerhub에 이미지를 올린 후 Docker로 배포

# Github Actions로 Pull Request 이벤트 발생시 테스트 동작
- /.github/workflows/run-test.yml
```
name: Run Test

on:
  push:
    branches: [ develop, feat/*,]
  pull_request:
    branches:
      - develop


jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          distribution: 'adopt'
          java-version: '17'
      - name: Run All Tests
        run: |
          chmod +x ./gradlew
          ./gradlew clean test
```
![image](https://github.com/user-attachments/assets/c704e168-ef9b-455f-9ebb-3b4fd1d4421d)

# 테스트 전략
## Controller 테스트 전략
```
@ActiveProfiles("test")
@Import({AuthConfig.class, CustomAuthenticationEntryPoint.class, CustomAccessDeniedHandler.class})
@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;
```

- `@WebMvcTest` 어노테이션을 활용해서 컨트롤러 계층 단위 테스트
- 입력 유효성 검사와 상태 코드를 검증

## Service 테스트
```
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
```

- `@SpringBootTest` 어노테이션을 활용해서 통합 테스트
- 서비스 계층은 핵심 로직이 들어가는 곳이고, 객체들의 협력 관계를 테스트하기 위해서는 통합테스트가 맞다고 생각합니다.
- Classicist vs Mockist 에서 Classicist 입장입니다.
- 웬만하면 스프링 환경을 띄워서 통합 테스트를 진행하고, 외부 컴포넌트만 Mocking 하는 편입니다. ex) PG사, 이메일, 슬랙 등등

## Repository 테스트
```
@ActiveProfiles("test")
@Transactional
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
```

- Spring Data JPA가 제공하는 테스트는 테스트하지 않습니다.
- 쿼리 메서드 기능, QueryDSL로 직접 만든 메서드만 테스트 합니다.
