package org.twdata.trader.event.impl;

import org.twdata.trader.event.TraderEventListener;

import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

/**
 * Listener method selector that looks for a specific marker annotation
 */
public class AnnotationListenerMethodSelector implements ListenerMethodSelector
{
    private final Class<? extends Annotation> markerAnnotation;

    public AnnotationListenerMethodSelector()
    {
        this(TraderEventListener.class);
    }

    public AnnotationListenerMethodSelector(Class<? extends Annotation> markerAnnotation)
    {
        this.markerAnnotation = markerAnnotation;
    }
    public boolean isListenerMethod(Method method)
    {
        return (method.getAnnotation(markerAnnotation) != null);
    }
}
