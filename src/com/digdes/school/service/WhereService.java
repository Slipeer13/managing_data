package com.digdes.school.service;

import com.digdes.school.context.Context;
import com.digdes.school.entity.Entity;
import com.digdes.school.repository.UserRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class WhereService {
    private static final UserRepository userRepository = Context.getUserRepository();

    public static List<Entity> findUsers(String request) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        request = request.split("(?i)where")[1].replaceAll("([<>!=]=|[<=>])", " $1 ");
        List<Entity> resultUserList = new ArrayList<>();
        for (Entity entity :
                userRepository.getENTITIES()) {
            if (checkEntity(entity, request)) resultUserList.add(entity);
        }
        return resultUserList;
    }

    public static boolean checkEntity(Entity entity, String request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        StringTokenizer stringTokenizer = new StringTokenizer(request, "'‘’ ");
        List<Boolean> booleanList = new ArrayList<>();
        List<String> operands = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String nameField = stringTokenizer.nextToken().toLowerCase(Locale.ROOT);
            if(nameField.equalsIgnoreCase("or") || nameField.equalsIgnoreCase("and")) {
                operands.add(nameField);
            } else {
                Field[] fields = entity.getClass().getDeclaredFields();
                Field resultField = null;
                for (Field field : fields) {
                    if (field.getName().equalsIgnoreCase(nameField)) {
                        resultField = field;
                        resultField.setAccessible(true);
                        break;
                    }
                }
                Class<?> fieldType = resultField.getType();
                Object result = resultField.get(entity);
                String operator = stringTokenizer.nextToken().toLowerCase(Locale.ROOT);
                if (Long.class.isAssignableFrom(fieldType)) {
                    long longValue = Long.parseLong(stringTokenizer.nextToken());
                    switch (operator) {
                        case "<" -> booleanList.add((Long) result < longValue);
                        case ">" -> booleanList.add((Long) result > longValue);
                        case "=" -> booleanList.add((Long) result == longValue);
                        case "!=" -> booleanList.add((Long) result != longValue);
                        case "<=" -> booleanList.add((Long) result <= longValue);
                        case ">=" -> booleanList.add((Long) result >= longValue);
                        default -> throw new NoSuchElementException(String.format("There is no such operator : %s", operator));
                    }
                }
                if (String.class.isAssignableFrom(fieldType)) {
                    String stringValue = stringTokenizer.nextToken();
                    switch (operator) {
                        case "=" -> booleanList.add(operator.equals(stringValue));
                        case "!=" -> booleanList.add(!operator.equals(stringValue));
                        case "ilike" -> {
                            if (stringValue.startsWith("%") && stringValue.endsWith("%")) {
                                stringValue = stringValue.substring(1, stringValue.length() - 1).toLowerCase(Locale.ROOT);
                                booleanList.add(((String) result).toLowerCase(Locale.ROOT).contains(stringValue));
                            } else {
                                if (stringValue.startsWith("%")) {
                                    stringValue = stringValue.substring(1).toLowerCase(Locale.ROOT);
                                    booleanList.add(((String) result).toLowerCase(Locale.ROOT).endsWith(stringValue));
                                } else if (stringValue.endsWith("%")) {
                                    stringValue = stringValue.substring(0, stringValue.length() - 1).toLowerCase(Locale.ROOT);
                                    booleanList.add(((String) result).toLowerCase(Locale.ROOT).startsWith(stringValue));
                                } else {
                                    booleanList.add(operator.equalsIgnoreCase(stringValue));
                                }
                            }
                        }
                        case "like" -> {
                            if (stringValue.startsWith("%") && stringValue.endsWith("%")) {
                                stringValue = stringValue.substring(1, stringValue.length() - 1);
                                booleanList.add(((String) result).contains(stringValue));
                            } else {
                                if (stringValue.startsWith("%")) {
                                    stringValue = stringValue.substring(1);
                                    booleanList.add(((String) result).endsWith(stringValue));
                                } else if (stringValue.endsWith("%")) {
                                    stringValue = stringValue.substring(0, stringValue.length() - 1);
                                    booleanList.add(((String) result).startsWith(stringValue));
                                } else {
                                    booleanList.add(operator.equals(stringValue));
                                }
                            }
                        }
                        default -> throw new NoSuchElementException(String.format("There is no such operator : %s", operator));
                    }
                }
                if (Double.class.isAssignableFrom(fieldType)) {
                    double doubleValue = Double.parseDouble(stringTokenizer.nextToken());
                    switch (operator) {
                        case "<" -> booleanList.add((Double) result < doubleValue);
                        case ">" -> booleanList.add((Double) result > doubleValue);
                        case "=" -> booleanList.add((Double) result == doubleValue);
                        case "!=" -> booleanList.add((Double) result != doubleValue);
                        case "<=" -> booleanList.add((Double) result <= doubleValue);
                        case ">=" -> booleanList.add((Double) result >= doubleValue);
                        default -> throw new NoSuchElementException(String.format("There is no such operator : %s", operator));
                    }
                }
                if (Boolean.class.isAssignableFrom(fieldType)) {
                    Boolean booleanValue = Boolean.parseBoolean(stringTokenizer.nextToken());
                    switch (operator) {
                        case "=" -> booleanList.add(result == booleanValue);
                        case "!=" -> booleanList.add(result != booleanValue);
                        default -> throw new NoSuchElementException(String.format("There is no such operator : %s", booleanValue));
                    }
                }
            }
        }
        boolean resultRequest = false;
        if (!booleanList.isEmpty()) {
            resultRequest = booleanList.get(0);
            booleanList.remove(0);
        }
        while (!booleanList.isEmpty()) {
            if (operands.size() > 0) {
                String s = operands.get(0);
                operands.remove(0);
                switch (s) {
                    case "and" -> {
                        resultRequest &= booleanList.get(0);
                        booleanList.remove(0);
                    }
                    case "or" -> {
                        resultRequest |= booleanList.get(0);
                        booleanList.remove(0);
                    }
                }
            }
        }
        return resultRequest;
    }
}
