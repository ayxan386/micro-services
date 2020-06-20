package org.aykhan.dataprovider.service;

public interface CrudService<REQ, RES> {
    RES get(REQ request);

    RES add(REQ request);

    RES update(REQ request);

    String delete(REQ request);
}
