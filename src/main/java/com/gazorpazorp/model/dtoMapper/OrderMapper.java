package com.gazorpazorp.model.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.gazorpazorp.model.Order;
import com.gazorpazorp.model.dto.OrderMinimalDto;


@Mapper(componentModel = "spring")
public interface OrderMapper {
	
	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
	
	@Mapping(target="id")
	@Mapping(target="total")
	@Mapping(target="createdAt")
	@Mapping(target="status")
	OrderMinimalDto orderToOrderMinimalDto(Order order);
}
