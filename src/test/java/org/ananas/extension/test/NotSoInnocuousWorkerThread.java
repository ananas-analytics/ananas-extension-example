package org.ananas.extension.test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class NotSoInnocuousWorkerThread extends ForkJoinWorkerThread {
    protected NotSoInnocuousWorkerThread(ForkJoinPool pool) {
        super(pool);
    }
}

