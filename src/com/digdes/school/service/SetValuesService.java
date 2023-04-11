package com.digdes.school.service;

import com.digdes.school.entity.Entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class SetValuesService {

    public static Entity setValuesFromRequest(String request, Entity entity) {
        StringTokenizer stringTokenizer = new StringTokenizer(request, ",='‘’ ");
        List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken().toLowerCase(Locale.ROOT);
            fields.forEach(field -> {
                field.setAccessible(true);
                String nameField = field.getName().toLowerCase(Locale.ROOT);
                if (nameField.equals(token)) {
                    String typeField = field.getType().getSimpleName();
                    /*String methodName = "set" + nameField.substring(0, 1).toUpperCase() + nameField.substring(1);
                    Method method;*/
                    try {
                        switch (typeField) {
                            case "Long", "long" -> {
                                field.set(entity, Long.parseLong(stringTokenizer.nextToken()));
                                /*method = entity.getClass().getMethod(methodName, Long.class);
                                method.invoke(entity, Long.parseLong(stringTokenizer.nextToken()));*/
                            }
                            case "int", "Integer" -> {
                                field.set(entity, Integer.parseInt(stringTokenizer.nextToken()));
                               /* method = entity.getClass().getMethod(methodName, Integer.class);
                                method.invoke(entity, Integer.parseInt(stringTokenizer.nextToken()));*/
                            }
                            case "String" -> {
                                field.set(entity, stringTokenizer.nextToken());
                                /*method = entity.getClass().getMethod(methodName, String.class);
                                method.invoke(entity, stringTokenizer.nextToken());*/
                            }
                            case "double", "Double" -> {
                                field.set(entity, Double.valueOf(stringTokenizer.nextToken()));
                                /*method = entity.getClass().getMethod(methodName, Double.class);
                                method.invoke(entity, Double.valueOf(stringTokenizer.nextToken()));*/
                            }
                            case "boolean", "Boolean" -> {
                                field.set(entity, Boolean.parseBoolean(stringTokenizer.nextToken()));
                                /*method = entity.getClass().getMethod(methodName, Boolean.class);
                                method.invoke(entity, Boolean.parseBoolean(stringTokenizer.nextToken()));*/
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return entity;
    }
}
