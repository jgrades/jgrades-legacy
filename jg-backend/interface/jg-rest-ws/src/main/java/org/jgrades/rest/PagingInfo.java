package org.jgrades.rest;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class PagingInfo {
    private int pageNumber;

    private int pageSize;

    public Pageable toPageable() {
        return new PageRequest(pageNumber, pageSize);
    }
}
