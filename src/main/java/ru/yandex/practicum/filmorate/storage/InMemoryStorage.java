package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Entity;

import java.util.*;

public class InMemoryStorage<T extends Entity<Long>> implements Storage<T, Long> {

    private Long id = 0L;
    private final Map<Long, T> items = new HashMap<>();

    @Override
    public T add(T item) {
        item.setId(++id);
        items.put(id, item);
        return item;
    }

    @Override
    public T update(T item) {
        items.put(id, item);
        return item;
    }

    @Override
    public Optional<T> findById(Long id) {
        if (items.containsKey(id))
            return Optional.of(items.get(id));
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return items.containsKey(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public void deleteById(Long id) {
        items.remove(id);
    }
}
