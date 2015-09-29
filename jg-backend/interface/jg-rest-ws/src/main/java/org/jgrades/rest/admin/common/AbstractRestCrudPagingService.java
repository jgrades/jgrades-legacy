package org.jgrades.rest.admin.common;

import org.jgrades.admin.api.common.CrudPagingService;
import org.jgrades.rest.PagingInfo;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class AbstractRestCrudPagingService<T, ID, S extends CrudPagingService<T, ID>> extends AbstractRestCrudService<T, ID, S> {
    protected AbstractRestCrudPagingService(S crudService) {
        super(crudService);
    }

    @RequestMapping(value = "/page/{number}/{size}", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<T> getPage(@PathVariable Integer number, @PathVariable Integer size) {
        PagingInfo pagingInfo = new PagingInfo(number, size);
        return crudService.getPage(pagingInfo.toPageable());
    }
}
