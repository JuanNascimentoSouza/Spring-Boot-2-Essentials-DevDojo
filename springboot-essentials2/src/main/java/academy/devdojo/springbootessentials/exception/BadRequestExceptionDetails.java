package academy.devdojo.springbootessentials.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BadRequestExceptionDetails {
    private String tittle;
    private int status;
    private String details;
    private String develiperMessage;
    private LocalDateTime timestamp;
}
