package com.leyou.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @date 2019/1/14-13:34
 */
public class ThreadUtil {
	private static final ExecutorService es = Executors.newFixedThreadPool(10);

	public static void execute(Runnable runnable) {
		es.submit(runnable);
	}
}
