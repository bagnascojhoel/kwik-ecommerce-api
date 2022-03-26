package br.com.kwikecommerce.api.application.exceptionhandling;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Schema(hidden = true)
public class FieldValidationResponseDto {

    private List<FieldValidation> validations;

    @Builder
    @Data
    public static class FieldValidation {

        private String field;
        private String message;
        private String value;

    }

}
