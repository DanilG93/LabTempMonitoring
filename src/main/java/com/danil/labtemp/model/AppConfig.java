package com.danil.labtemp.model;

import java.util.List;

public record AppConfig(
        String companyName,
        String companyAddress,
        List<Device> devices
) {
}
