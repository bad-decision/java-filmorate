package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class BaseService<T extends Entity<Long>> {

    private final Storage<T, Long> storage;
    private final static String ITEM_IS_NULL = "Item is null";

    protected BaseService(Storage<T, Long> storage) {
        this.storage = storage;
    }

    public T findById(Long id) {
        Optional<T> item = storage.findById(id);

        if (item.isEmpty())
            throw new NotFoundException("Item not found, id=" + id);

        return item.get();
    }

    public List<T> findAll() {
        return storage.findAll();
    }

    public T create(T item) {
        if (item == null) {
            log.error(ITEM_IS_NULL);
            throw new IllegalArgumentException(ITEM_IS_NULL);
        }

        return storage.add(item);
    }

    public T update(T item) {
        if (item == null) {
            log.error(ITEM_IS_NULL);
            throw new IllegalArgumentException(ITEM_IS_NULL);
        }

        if (!storage.existsById(item.getId()))
            throw new NotFoundException("Item not found, id=" + item.getId());

        return storage.update(item);
    }
}
