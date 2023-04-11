package com.digdes.school.service;

import com.digdes.school.context.Context;
import com.digdes.school.entity.Entity;
import com.digdes.school.repository.UserRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SelectService {
    private static final UserRepository userRepository = Context.getUserRepository();

    public static List<Map<String, Object>> select(String request) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Entity> usersForSelect;
        if (request.toLowerCase(Locale.ROOT).contains("where")) {
            usersForSelect = WhereService.findUsers(request);
        } else {
            usersForSelect = userRepository.getENTITIES();
        }
        return userRepository.getMapFromEntity(usersForSelect);
    }
}
