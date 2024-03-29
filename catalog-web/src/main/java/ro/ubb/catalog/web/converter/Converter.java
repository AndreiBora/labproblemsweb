package ro.ubb.catalog.web.converter;

import ro.ubb.catalog.core.model.BaseEntity;
import ro.ubb.catalog.web.dto.BaseDto;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto<Long>> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

