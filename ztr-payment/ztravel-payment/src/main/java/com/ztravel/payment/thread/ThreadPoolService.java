package com.ztravel.payment.thread;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zuoning.shen
 *
 */
public class ThreadPoolService {

	private ThreadPoolExecutor threadPoolExecutor = null;
	private static ThreadPoolService threadPoolService = new ThreadPoolService();

	private ThreadPoolService() {
		this.threadPoolExecutor = new ThreadPoolExecutor(20, 30, 500, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public static ThreadPoolService getInstance() {
		return ThreadPoolService.threadPoolService;
	}

	/**
	 * 提交任务
	 * 
	 * @param pCallable
	 * @return
	 */
	public Future<Object> submit(Callable<Object> pCallable) {
		return this.threadPoolExecutor.submit(pCallable);
	}

	@SuppressWarnings("unchecked")
	public Future<Object> submit(Runnable pRunnable) {
		return this.threadPoolExecutor.submit((Callable<Object>) pRunnable);
	}

	public void shutdown() {
		this.threadPoolExecutor.shutdown();
	}

	public Collection<Runnable> shutdownNow() {
		return this.threadPoolExecutor.shutdownNow();
	}
}
