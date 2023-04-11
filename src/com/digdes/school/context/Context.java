package com.digdes.school.context;

import com.digdes.school.controller.Controller;
import com.digdes.school.controller.ControllerImpl;
import com.digdes.school.entity.User;
import com.digdes.school.repository.UserRepository;
import com.digdes.school.repository.UserRepositoryImpl;
import com.digdes.school.service.Service;
import com.digdes.school.service.ServiceImpl;

public class Context {
    private static final Service SERVICE = new ServiceImpl();

    private static final Controller controller = new ControllerImpl<>(User.class);

    public static Controller getController() {
        return controller;
    }

    private static final UserRepository userRepository = new UserRepositoryImpl();

    public static Service getUserService() {
        return SERVICE;
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }
}
