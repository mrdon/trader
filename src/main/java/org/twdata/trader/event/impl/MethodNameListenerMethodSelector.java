package org.twdata.trader.event.impl;

import java.lang.reflect.Method;

/**
 * Listener method selector that makes its determination by matching the method name
 */
public class MethodNameListenerMethodSelector implements ListenerMethodSelector
{
    private final String methodName;

    public MethodNameListenerMethodSelector()
    {
        this("channel");
    }

    public MethodNameListenerMethodSelector(String s)
    {
        this.methodName = s;
    }


    /**
     * Determines if the listener method has the name as the one configured
     * @param method The method to test
     * @return True if the method name matches the configured method name, false otherwise
     */
    public boolean isListenerMethod(Method method)
    {
        if (method == null)
            throw new IllegalArgumentException("Method cannot be null");

        return methodName.equals(method.getName());
    }
}
