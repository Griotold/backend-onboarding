package com.griotold.backend_onboarding.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.griotold.backend_onboarding.application.service.AuthService;
import com.griotold.backend_onboarding.infra.config.security.AuthConfig;
import com.griotold.backend_onboarding.infra.config.security.CustomAccessDeniedHandler;
import com.griotold.backend_onboarding.infra.config.security.CustomAuthenticationEntryPoint;
import com.griotold.backend_onboarding.presentation.controller.AuthController;
import com.griotold.backend_onboarding.presentation.dto.UserSigninRequest;
import com.griotold.backend_onboarding.presentation.dto.UserSignupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @DisplayName("signup - 성공")
    @Test
    void signup_success() throws Exception {
        // given
        String username = "testuser";
        String password = "Pass1234!";
        String nickname = "mentos";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("signup - username이 없을 때")
    @Test
    void signup_whenUsernameIsNull() throws Exception {
        // given
        String username = null;
        String password = "Pass1234!";
        String nickname = "mentos";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signup - password가 없을 때")
    @Test
    void signup_whenPasswordIsNull() throws Exception {
        // given
        String username = "testuser";
        String password = null;
        String nickname = "mentos";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signup - nickname이 없을 때")
    @Test
    void signup_whenNicknameIsNull() throws Exception {
        // given
        String username = "testuser";
        String password = "Pass1234!";
        String nickname = null;
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signup - username 길이 검증")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "abcdefghijk"}) // 3자, 11자
    void signup_whenUsernameInvalidLength(String username) throws Exception {
        // given
        String password = "Pass1234!";
        String nickname = "mentos";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signup - username이 영어 소문자가 아닐 때")
    @Test
    void signup_whenUsernameNotLowerCase() throws Exception {
        // given
        String username = "TestUser123";
        String password = "Pass1234!";
        String nickname = "mentos";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signup - password 길이 검증")
    @ParameterizedTest
    @ValueSource(strings = {"Pass1!", "Pass1234!Pass1234!"}) // 6자, 16자
    void signup_whenPasswordInvalidLength(String password) throws Exception {
        // given
        String username = "testuser";
        String nickname = "mentos";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signup - password가 특수문자를 포함하지 않을 때")
    @Test
    void signup_whenPasswordNoSpecialChar() throws Exception {
        // given
        String username = "testuser";
        String password = "Password123";
        String nickname = "mentos";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signup - nickname 길이 검증")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "abcdefghijk"}) // 3자, 11자
    void signup_whenNicknameInvalidLength(String nickname) throws Exception {
        // given
        String username = "testuser";
        String password = "Pass1234!";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signup - nickname이 영어 소문자가 아닐 때")
    @Test
    void signup_whenNicknameNotLowerCase() throws Exception {
        // given
        String username = "testuser";
        String password = "Pass1234!";
        String nickname = "Mentos123";
        UserSignupRequest userSignupRequest = new UserSignupRequest(username, password, nickname);

        // when & then
        mockMvc.perform(post("/signup")
                        .content(objectMapper.writeValueAsString(userSignupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signin - 성공")
    @Test
    void signin_success() throws Exception {
        // given
        String username = "testuser";
        String password = "Pass1234!";
        UserSigninRequest userSigninRequest = new UserSigninRequest(username, password);

        // when & then
        mockMvc.perform(post("/signin")
                        .content(objectMapper.writeValueAsString(userSigninRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("signin - username이 없을 때")
    @Test
    void signin_whenUsernameIsNull() throws Exception {
        // given
        String username = null;
        String password = "Pass1234!";
        UserSigninRequest userSigninRequest = new UserSigninRequest(username, password);

        // when & then
        mockMvc.perform(post("/signin")
                        .content(objectMapper.writeValueAsString(userSigninRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("signin - password가 없을 때")
    @Test
    void signin_whenPasswordIsNull() throws Exception {
        // given
        String username = "testuser";
        String password = null;
        UserSigninRequest userSigninRequest = new UserSigninRequest(username, password);

        // when & then
        mockMvc.perform(post("/signin")
                        .content(objectMapper.writeValueAsString(userSigninRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}