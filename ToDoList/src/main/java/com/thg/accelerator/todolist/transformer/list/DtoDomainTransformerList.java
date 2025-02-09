package com.thg.accelerator.todolist.transformer.list;

import com.thg.accelerator.todolist.controllers.dto.ListDto;
import com.thg.accelerator.todolist.controllers.dto.TaskItemDto;
import com.thg.accelerator.todolist.domain.listDomain.ListDomain;
import org.springframework.stereotype.Component;

@Component
public class DtoDomainTransformerList {

    public DtoDomainTransformerList() {
    }

    public ListDomain transformDtoToDomain(ListDto listDto) {
        return new ListDomain(listDto.getListId(),
                listDto.getListType());
    }

    public ListDto transformDomainToDto(ListDomain listDomain) {
        return new ListDto(listDomain.getListId(),
                listDomain.getListType());
    }
}
