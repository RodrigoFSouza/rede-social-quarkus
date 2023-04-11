package br.com.cronos.redesocial.api.dto.mapper;

import br.com.cronos.redesocial.api.dto.AddressResponse;
import br.com.cronos.redesocial.api.dto.CreateAddressRequest;
import br.com.cronos.redesocial.api.dto.UpdateAddressRequest;
import br.com.cronos.redesocial.domain.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creation", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "user.id", ignore = true)
    @Mapping(target = "street", source = "nameOfStreet")
    Address toAddress(CreateAddressRequest createAddressRequest);

    @Mapping(target = "street", source = "nameOfStreet")
    void toAddress(UpdateAddressRequest updateAddressRequest, @MappingTarget Address address);

    @Mapping(target = "creation", source = "creation", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "updated", source = "updated", dateFormat = "dd/MM/yyyy HH:mm:ss")
    AddressResponse toResponse(Address address);
}
