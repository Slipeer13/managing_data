package com.digdes.school.service;

import com.digdes.school.context.Context;
import com.digdes.school.entity.Entity;
import com.digdes.school.repository.UserRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class UpdateService {
    private static final UserRepository userRepository = Context.getUserRepository();

    public static List<Map<String, Object>> update(String request, Entity entity) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        String[] requestArr = request.trim().split(" ");
        List<Entity> entitiesForUpdate = null;
        List<Map<String, Object>> result = null;
        if(requestArr[0].equalsIgnoreCase("values")) {
            request = request.replaceFirst(requestArr[0], "");
            StringTokenizer stringTokenizer = new StringTokenizer(request, ",='‘’ ");
            Map<Field, Object> fieldUpdate = new HashMap<>();
            List<String> deleteField = new ArrayList<>();
            while (stringTokenizer.hasMoreTokens()) {
                String nameFieldFromRequest = stringTokenizer.nextToken();
                if(nameFieldFromRequest.equalsIgnoreCase("where")) {
                    entitiesForUpdate = WhereService.findUsers(request);
                    break;
                }
                for (Field field:
                     fields) {
                    String nameField = field.getName();
                    if(nameField.equalsIgnoreCase(nameFieldFromRequest)) {
                        String value = stringTokenizer.nextToken();
                        if(value.equals("null")){
                            deleteField.add(nameFieldFromRequest);
                        } else {
                            fieldUpdate.put(field, value);
                        }
                    }
                }

            }
            if (entitiesForUpdate == null) {
                entitiesForUpdate = userRepository.getENTITIES();
            }
            result = userRepository.updateValues(entitiesForUpdate, fieldUpdate, deleteField);

        }
        return result;
    }

}
