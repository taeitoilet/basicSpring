package com.fintech.tech_clone.exception;
import com.fintech.tech_clone.dto.response.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.nio.file.AccessDeniedException;
class GlobalExceptionTest {
    private final GlobalException exceptionHandler = new GlobalException();
    @Test
    void testHandlingAccessDeniedException() {
        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handlingAccessDeniedException();
        Assertions.assertEquals(403, response.getStatusCodeValue());
        ApiResponse<Void> apiResponse = response.getBody();
        Assertions.assertEquals("You dont have permission", apiResponse.getMessage());
        Assertions.assertEquals(null, apiResponse.getResult());
    }
    @Test
    void testHandlingAppException(){
        ErrorCode errorCode = ErrorCode.PHONE_EXIST;
        AppException exception = new AppException(errorCode);
        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handlingAppException(exception);
        Assertions.assertEquals(errorCode.getStatusCode().value(), response.getStatusCodeValue()); // HTTP status code
        ApiResponse<Void> apiResponse = response.getBody();
        Assertions.assertEquals(errorCode.getCode(), apiResponse.getCode()); // Check error code
        Assertions.assertEquals(errorCode.getMessage(), apiResponse.getMessage()); // Check error message
        Assertions.assertEquals(null, apiResponse.getResult()); // Ensure no result
    }
}