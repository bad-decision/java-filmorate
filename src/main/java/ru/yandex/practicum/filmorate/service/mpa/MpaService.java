package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;
import ru.yandex.practicum.filmorate.service.BaseService;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

@Service
public class MpaService extends BaseService<Mpa> {
    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        super(mpaStorage);
    }
}