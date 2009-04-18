package org.twdata.trader.command

import java.lang.annotation.Target
import java.lang.annotation.ElementType

/**
 * 
 */
@Target([ElementType.FIELD, ElementType.PARAMETER])
public @interface NotNull {
}