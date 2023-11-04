package tn.moatez.project.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import tn.moatez.project.model.Address;

@Data
public class AddressDTO {
    private Long Id;
    private String country;
    private String state;
    private String streetbulding;
    private String delegation;
    private Float longitude;
    private Float latitude;
    public static AddressDTO mapToDTO(Address entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, AddressDTO.class);
    }
}
