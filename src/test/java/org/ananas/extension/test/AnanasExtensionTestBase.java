package org.ananas.extension.test;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;
import org.junit.contrib.java.lang.system.SystemOutRule;


public class AnanasExtensionTestBase {
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final ProvideSystemProperty threadFactory =
            new ProvideSystemProperty(
                    "java.util.concurrent.ForkJoinPool.common.threadFactory",
                    AcceptanceForkJoinThreadFactory.class.getName());
}
