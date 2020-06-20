package org.aykhan.springdataprovidermongo.service;

public interface CrudService<Req, Res> {
    Res get(Req request);

    Res add(Req request);

    Res update(Req request);

    String delete(Req request);
}
