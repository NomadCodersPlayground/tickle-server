package com.techblogfinder.api.common.converter;

import com.opencsv.bean.AbstractBeanField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringToListConverter extends AbstractBeanField<List<String>, String> {
    @Override
    protected Object convert(String value) {
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

