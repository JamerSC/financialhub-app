package com.jamersc.springboot.financialhub.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class StringToClientAccountDtoSetConverter implements Converter<String, Set<ClientAccountDto>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Set<ClientAccountDto> convert(@NonNull String source) {
        try {
            List<Long> ids = objectMapper.readValue(source, new TypeReference<List<Long>>() {});
            Set<ClientAccountDto> dto = new HashSet<>();
            for (Long id : ids) {
                ClientAccountDto accountDto = new ClientAccountDto();
                accountDto.setClientAccountId(id);
                dto.add(accountDto);
            }
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON string to Set<ClientAccountDto>", e);
        }
    }
}
