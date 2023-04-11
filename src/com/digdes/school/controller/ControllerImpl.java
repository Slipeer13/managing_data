package com.digdes.school.controller;

import com.digdes.school.context.Context;
import com.digdes.school.entity.Entity;
import com.digdes.school.service.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ControllerImpl<T extends Entity> implements Controller{

    private static final Service SERVICE = Context.getUserService();

    private Class<T> type;

    public ControllerImpl(Class<T> type) {
        this.type = type;
    }

    public T createInstance() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return type.getDeclaredConstructor().newInstance();
    }

    public List<Map<String,Object>> execute(String request) throws Exception {
        String[] requestArr = request.trim().split(" ");
        String command = requestArr[0].toLowerCase(Locale.ROOT);
        request = request.replaceFirst(requestArr[0], "");
        List<Map<String,Object>> result;
        Entity entity = createInstance();
        switch (command) {
            case "insert" -> result = SERVICE.insert(request, entity);
            case "delete" -> result = SERVICE.delete(request);
            case "update" -> result = SERVICE.update(request, entity);
            case "select" -> result = SERVICE.select(request);
            default -> throw new NoSuchMethodException(String.format("There is no such Method : %s", command));
        }
        return result;
    }
}
