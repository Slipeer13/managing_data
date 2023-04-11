package com.digdes.school.service;

import com.digdes.school.context.Context;
import com.digdes.school.entity.Entity;
import com.digdes.school.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class InsertService {

    private static final UserRepository userRepository = Context.getUserRepository();

    public static List<Map<String, Object>> insert(String request, Entity entity) throws Exception {
        String[] requestArr = request.trim().split(" ");
        if(requestArr[0].equalsIgnoreCase("values")) {
            request = request.replaceFirst(requestArr[0], "");
            SetValuesService.setValuesFromRequest(request, entity);
        } else {
            throw new NoSuchElementException(String.format("There is no such command : %s", requestArr[0]));
        }
        return userRepository.insert(entity);
    }
}
