package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestSummary extends ExternalResource {

    public static class TestInfo {
        private final String methodName;
        private final String displayName;
        private final long time;

        public TestInfo(String methodName, String displayName, long time) {
            this.methodName = methodName;
            this.displayName = displayName;
            this.time = time;
        }

        @Override
        public String toString() {
            return "TestInfo{" +
                    "methodName='" + methodName + '\'' +
                    "displayName='" + displayName + '\'' +
                    ", time=" + time + "ms" +
                    '}';
        }
    }

    private static final Logger log = LoggerFactory.getLogger(TestSummary.class);
    private final List<TestInfo> testInfoList = new ArrayList();
    private long timeStart;

    public void add(TestInfo testInfo) {
        testInfoList.add(testInfo);
    }

    @Override
    protected void before() throws Throwable {
        timeStart = System.currentTimeMillis();
    }

    @Override
    protected void after() {
        log.info("+++ After all tests ------------");
        for (TestInfo testInfo : testInfoList) {
            log.info(testInfo.methodName + " - " + testInfo.time + "ms");
        }
        log.info("+++ ------------");
        log.info("+++ total time: " + (System.currentTimeMillis() - timeStart) + "ms");
    }
}
