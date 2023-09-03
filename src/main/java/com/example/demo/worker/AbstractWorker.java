package com.example.demo.worker;


import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractWorker <T> {

    public List<T> findNewItems(List<T> db, List<T> api) {
        return api.stream()
                .filter(apiItem -> db.stream().noneMatch(dbItem -> areEqual(apiItem, dbItem)))
                .collect(Collectors.toList());
    }


    public List<T> findDisabledItems(List<T> db, List<T> api) {
        return db.stream()
                .filter(dbItem -> api.stream().noneMatch(apiItem -> areEqual(dbItem, apiItem)) && isActive(dbItem))
                .peek(this::setNegativeStatus)
                .collect(Collectors.toList());
    }

    protected abstract boolean isActive(T item);

    protected abstract void setPositiveStatus(T item);
    protected abstract void setNegativeStatus(T item);

    protected abstract boolean areEqual(T item1, T item2);

}
