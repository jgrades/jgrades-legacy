package org.jgrades.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingInfo {
    private int pageNumber;

    private int pageSize;

    public Pageable toPageable() {
        return new PageRequest(pageNumber, pageSize);
    }
}
