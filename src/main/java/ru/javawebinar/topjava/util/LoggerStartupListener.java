package ru.javawebinar.topjava.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

/**
 * https://stackoverflow.com/questions/1975939/read-environment-variables-from-logback-configuration-file
 */

public class LoggerStartupListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {
    private static final String LOGGER_PATH_VAR_NAME = "TOPJAVA_ROOTZ";
    private volatile boolean isStarted = false;

    @Override
    public void start() {
        if (isStarted) {
            return;
        }
//        if (!System.getenv().containsKey(LOGGER_PATH_VAR_NAME)) {
//            Path tempDir = Files.createTempDirectory(LOGGER_PATH_VAR_NAME);
//        }
        isStarted = true;
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public boolean isResetResistant() {
        return true;
    }

    @Override
    public void onStart(LoggerContext loggerContext) {

    }

    @Override
    public void onReset(LoggerContext loggerContext) {

    }

    @Override
    public void onStop(LoggerContext loggerContext) {

    }

    @Override
    public void onLevelChange(Logger logger, Level level) {

    }

}
