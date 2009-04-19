package org.twdata.trader.util

import org.twdata.trader.command.Command
import org.twdata.trader.model.City
import org.twdata.trader.model.Game
import org.twdata.trader.model.Commodity
import java.lang.reflect.Field
import org.twdata.trader.command.Param

/**
 * 
 */

public class CommandUtil {
    public static void eachPrivateParamField(Class cls, final Closure c) {
        cls.declaredFields.each { Field f ->
            f.setAccessible(true);
            if (f.getAnnotation(Param.class) != null) {
                c(f);
            }
        };
        if (cls.getSuperclass()) {
            eachPrivateParamField(cls.getSuperclass(), c);
        }
    }
}