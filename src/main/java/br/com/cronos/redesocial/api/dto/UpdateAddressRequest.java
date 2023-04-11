package br.com.cronos.redesocial.api.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateAddressRequest {
    @NotBlank
    @Schema(description = "Nome da rua", example = "Av. Paraná")
    private String nameOfStreet;
    @NotBlank
    @Schema(description = "Nome do estado", example = "PR")
    private String state;
    @NotBlank
    @Schema(description = "Nome da Cidade", example = "Londrina")
    private String city;
    @NotBlank
    @Schema(description = "Numero do imóvel", example = "101")
    private String number;
    @NotBlank
    @Schema(description = "Cep da rua", example = "87084-100")
    private String zipcode;
    @NotBlank
    @Schema(description = "Nome do bairro", example = "Zona 2")
    private String district;
    @NotBlank
    @Schema(description = "Complemento", example = "Ap. 101 bloco 8")
    private String complement;
}
