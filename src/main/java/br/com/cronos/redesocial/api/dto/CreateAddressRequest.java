package br.com.cronos.redesocial.api.dto;

import br.com.cronos.redesocial.utils.Dto;
import br.com.cronos.redesocial.utils.ValidDto;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ValidDto
public class CreateAddressRequest implements Dto {
    @NotBlank
    @Size(min = 3, max = 240)
    @Schema(description = "Nome da rua", example = "Av. Brasil")
    private String nameOfStreet;
    @NotBlank
    @Size(min = 2, max = 2, message = "O estado deve ter {max} caracteres")
    @Schema(description = "Nome do estado", example = "PR")
    private String state;
    @NotBlank
    @Size(min = 3, max = 240)
    @Schema(description = "Nome da Cidade", example = "Maringá")
    private String city;
    @NotBlank
    @Schema(description = "Numero do imóvel", example = "99")
    private String number;
    @NotBlank
    @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$")
    @Size(min = 9, max = 9, message = "O Cep é inválido.")
    @Schema(description = "Cep da rua", example = "87084-090")
    private String zipcode;
    @NotBlank
    @Size(min = 3, max = 240)
    @Schema(description = "Nome do bairro", example = "Centro")
    private String district;
    @NotBlank
    @Size(min = 3, max = 120)
    @Schema(description = "Complemento", example = "Ap. 101 bloco 3")
    private String complement;
}
