package com.rptools.util;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by DZL on 2/15/14.
 */
public class Logger {
    public static Map<String, java.util.logging.Logger> loggers = Maps.newHashMap();
    java.util.logging.Logger log;

    protected Logger(String name, String bundle) {
        log = java.util.logging.Logger.getLogger(name, bundle);
    }

    public static Logger getLogger(Class entityClass) {
        return new Logger(entityClass.getName(), null);
    }

    public static Logger getLogger(Class entityClass, String bundleName) {
        return new Logger(entityClass.getName(), bundleName);
    }

    public void addThrow(Object[] args, Throwable thr){
        if(args.length == 0){ return; }
        args[args.length] = thr.getMessage();
        args[args.length] = thr.getStackTrace();
    }

    public void info(String formatString, Throwable thr, Object... args){
        addThrow(args, thr);
        log.info(String.format(formatString.concat("\n%s\n%s"), args));
    }

    public void warning(String formatString, Throwable thr, Object... args){
        addThrow(args, thr);
        log.warning(String.format(formatString.concat("\n%s\n%s"), args));
    }

    public void error(String formatString, Throwable thr, Object... args){
        addThrow(args, thr);
        if(args.length == 0){ log.severe(formatString); }
        else { log.severe(String.format(formatString.concat("\n%s\n%s"), args)); }
    }

    public void info(String formatString, Object... args) {
        log.info(String.format(formatString, args));
    }

    public void warning(String formatString, Object... args) {
        log.warning(String.format(formatString, args));
    }

    public void error(String formatString, Object... args) {
        log.severe(String.format(formatString, args));
    }
}
