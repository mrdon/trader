package org.twdata.trader.event.impl;

import groovy.lang.Closure;

import java.lang.reflect.Method;

/**
 * Listener method selector that makes its determination by matching the method name
 */
public class ClosureListenerMethodSelector implements ListenerMethodSelector
{
    /**
     * Determines if the listener method has the name as the one configured
     * @param method The method to test
     * @return True if the method name matches the configured method name, false otherwise
     */
    public boolean isListenerMethod(Method method)
    {
        if (method == null)
            throw new IllegalArgumentException("Method cannot be null");

        return "call".equals(method.getName()) && method.getParameterTypes().length == 1 &&
                method.getParameterTypes()[0] == Object.class && method.getDeclaringClass().isAssignableFrom(Closure.class);
    }
}
