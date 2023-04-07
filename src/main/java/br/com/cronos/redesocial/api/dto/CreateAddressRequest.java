package br.com.cronos.redesocial.api.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Data
public class CreateAddressRequest {
    @NotBlank
    @Schema(description = "Nome da rua", example = "Av. Brasil")
    private String street;
    @NotBlank
    @Schema(description = "Nome do estado", example = "PR")
    private String state;
    @NotBlank
    @Schema(description = "Nome da Cidade", example = "Maringá")
    private String city;
    @NotBlank
    @Schema(description = "Numero do imóvel", example = "99")
    private String number;
    @NotBlank
    @Schema(description = "Cep da rua", example = "87084-090")
    private String zipcode;
    @NotBlank
    @Schema(description = "Nome do bairro", example = "Centro")
    private String district;
}
