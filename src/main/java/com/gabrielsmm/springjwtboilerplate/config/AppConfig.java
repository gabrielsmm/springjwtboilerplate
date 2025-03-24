package com.gabrielsmm.springjwtboilerplate.config;

import com.gabrielsmm.springjwtboilerplate.entities.enums.Perfil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Perfil, Integer> perfilToIntegerConverter = new Converter<>() {
            @Override
            public Integer convert(MappingContext<Perfil, Integer> context) {
                return context.getSource() == null ? null : context.getSource().getCodigo();
            }
        };

        modelMapper.addConverter(perfilToIntegerConverter);

        return modelMapper;
    }

}
