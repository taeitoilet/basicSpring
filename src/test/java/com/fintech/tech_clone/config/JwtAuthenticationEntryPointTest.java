package com.fintech.tech_clone.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.tech_clone.dto.response.ApiResponse;
import com.fintech.tech_clone.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import java.io.PrintWriter;
@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthenticationEntryPointTest {
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
    @Test
    void commence_ShouldReturnUnauthenticatedResponse() throws Exception {
        // Mock HttpServletRequest và HttpServletResponse
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        // Mock PrintWriter cho response.getWriter()
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        // Gọi phương thức commence
        jwtAuthenticationEntryPoint.commence(request, response, new AuthenticationException("Unauthenticated") {
        });
        // Xác minh rằng mã trạng thái được đặt đúng
        Mockito.verify(response).setStatus(ErrorCode.UNAUTHENTICATED.getStatusCode().value());
        // Xác minh rằng Content-Type được đặt thành application/json
        Mockito.verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Tạo ApiResponse mong đợi
        ApiResponse<?> expectedResponse = new ApiResponse<>();
        expectedResponse.setCode(ErrorCode.UNAUTHENTICATED.getCode());
        expectedResponse.setMessage(ErrorCode.UNAUTHENTICATED.getMessage());
        String expectedJson = new ObjectMapper().writeValueAsString(expectedResponse);
        // Xác minh rằng phản hồi JSON được ghi đúng
        Mockito.verify(writer).write(expectedJson);
        // Xác minh rằng response.flushBuffer() được gọi
        Mockito.verify(response).flushBuffer();
    }
}
