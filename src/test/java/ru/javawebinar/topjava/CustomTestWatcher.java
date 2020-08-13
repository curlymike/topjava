package ru.javawebinar.topjava;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomTestWatcher extends TestWatcher {
    private static final Logger log = LoggerFactory.getLogger(CustomTestWatcher.class);
    private final TestSummary testSummary;
    private long startTime;

    public CustomTestWatcher(TestSummary testSummary) {
        this.testSummary = testSummary;
    }

    @Override
    protected void starting(Description description) {
        startTime = System.currentTimeMillis();
        super.starting(description);
    }

    @Override
    protected void finished(Description description) {
        testSummary.add(
                new TestSummary.TestInfo(
                        description.getMethodName(),
                        description.getDisplayName(),
                        System.currentTimeMillis() - startTime
                )
        );
        super.finished(description);
    }



}
