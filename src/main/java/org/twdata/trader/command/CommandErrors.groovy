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
}