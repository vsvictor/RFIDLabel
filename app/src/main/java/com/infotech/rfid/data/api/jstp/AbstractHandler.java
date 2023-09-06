package com.infotech.rfid.data.api.jstp;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractHandler<T>
{
    protected final Class<T> persistentClass;

    public AbstractHandler()
    {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
