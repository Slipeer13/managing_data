package com.digdes.school.service;

import com.digdes.school.context.Context;
import com.digdes.school.entity.Entity;
import com.digdes.school.repository.UserRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DeleteService {
    private static final UserRepository userRepository = Context.getUserRepository();

    public static List<Map<String, Object>> delete(String request) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Entity> usersForDelete;
        if (request.toLowerCase(Locale.ROOT).contains("where")) {
            usersForDelete = WhereService.findUsers(request);
            deleteValues(usersForDelete);
        } else {
            usersForDelete = userRepository.getENTITIES();
            userRepository.getENTITIES().clear();
            userRepository.getMapsEntities().clear();
        }
        return userRepository.getMapFromEntity(usersForDelete);
    }

    private static void deleteValues(List<Entity> entities) {
        entities.forEach(entity -> {
            try {
                userRepository.delete(entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
