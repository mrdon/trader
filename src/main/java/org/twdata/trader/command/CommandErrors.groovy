package org.twdata.trader.command
/**
 * 
 */

public class CommandErrors {
    Set<String> globalErrors = [];
    Map<String,Set<String>> fieldErrors = [:];

    def add(String error) {
        globalErrors.add(error);
    }

    def add(String field, String error) {
        fieldErrors[field] = error;
    }

    def boolean isEmpty() {
        return globalErrors.isEmpty() && fieldErrors.isEmpty();
    }

    public String toString()
    {
        String err = "Errors - global[";
        globalErrors.each{
            err += "${it}, ";
        }
        err += "] field[";
        fieldErrors.each { k,v ->
            err += "${k}:${v}, ";
        }
        err += "]";
        return err;
    }


}