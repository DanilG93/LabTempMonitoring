package com.danil.labtemp.model;

import java.util.ArrayList;
import java.util.List;

public record AppConfig(
        String companyName,
        String companyAddress,
        List<Device> devices,
        List<User> users
) {
    public AppConfig {
        if (devices == null) devices = new ArrayList<>();
        if (users == null) users = new ArrayList<>();
    }
}
