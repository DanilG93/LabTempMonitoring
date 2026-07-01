package com.danil.labtemp.model;

public record Device(
        String name,
        double minTemp,
        double maxTemp,
        String dataRegex,
        String alarmRegex
) {
}
