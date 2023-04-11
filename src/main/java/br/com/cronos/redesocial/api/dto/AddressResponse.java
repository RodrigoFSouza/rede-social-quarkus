package br.com.cronos.redesocial.api.dto;

import lombok.Data;

@Data
public class AddressResponse {
    private String street;
    private String state;
    private String city;
    private String number;
    private String zipcode;
    private String district;
    private String complement;
    private String creation;
    private String updated;
}
