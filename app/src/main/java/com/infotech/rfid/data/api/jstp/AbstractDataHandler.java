package com.infotech.rfid.data.api.jstp;

import java.lang.reflect.ParameterizedType;

public class AbstractDataHandler<T> {
    protected Class<?> clazz = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
}
