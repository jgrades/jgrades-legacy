package org.jgrades.admin.api.common;


public interface CrudPagingService<T, ID> extends CrudService<T, ID>, PagingSelector<T, ID> {
}
