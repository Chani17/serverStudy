package inu.appcenter.study6.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import inu.appcenter.study6.domain.Member;
import inu.appcenter.study6.domain.MemberStatus;
import inu.appcenter.study6.model.MemberCreateRequest;
import inu.appcenter.study6.service.MemberService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)     // web 관련된 spring 설정 bean들만 가지고 온다
@AutoConfigureRestDocs  // rest docs 자동 설정
@ExtendWith(RestDocumentationExtension.class)      // rest docs 확장
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext wac, RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .apply(documentationConfiguration(restDocumentationContextProvider)   // mockmvc rest docs 설정
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("회원 생성 API")
    void saveMember() throws Exception {

        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setAge(23);
        memberCreateRequest.setName("김찬희");

        String body = objectMapper.writeValueAsString(memberCreateRequest);

        Member member = Member.createMember(1L, "김찬희", 23);
        given(memberService.saveMember(any()))
            .willReturn(member);

        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("김찬희"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
        .andDo(document("member/create",
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이")),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 ID"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이"),
                        fieldWithPath("status").type(JsonFieldType.STRING).description("회원 상태")
                )));

        then(memberService).should(times(1)).saveMember(any());
    }


}