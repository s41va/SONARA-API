package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String message;
}
