package com.example.demo.converter;

import java.util.List;

public interface ResponseMapper<R, O> {
    O toResponse(R request);
    List<O> toResponse(List<R> requestList);
}
