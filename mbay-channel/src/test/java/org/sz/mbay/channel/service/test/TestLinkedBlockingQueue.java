package org.sz.mbay.channel.service.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class TestLinkedBlockingQueue {

	class Basket {
		private BlockingQueue<String> basket = new LinkedBlockingQueue<String>(
				40);

		public void produce() {
			try {
				if (basket.remainingCapacity() != 0) {
					System.out.println(basket.remainingCapacity());
					basket.put("dfljslfjlsfd");
				} else {
					// / System.out.println("抱歉已经无法再加入了哦");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void take() {
			try {
				this.basket.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class Producer implements Runnable {
		private String instance;
		private Basket basket;

		public Producer(String instance, Basket basket) {
			this.instance = instance;
			this.basket = basket;
		}

		public void run() {
			try {
				int i = 0;
				while (i < 40) {
					// 生产苹果

					basket.produce();
					i++;
					// 休眠300ms
					// // Thread.sleep(300);
				}
			} catch (Exception ex) {
				System.out.println("Producer Interrupted");
			}
		}
	}

	// 定义苹果消费者
	class Consumer implements Runnable {
		private String instance;
		private Basket basket;

		public Consumer(String instance, Basket basket) {
			this.instance = instance;
			this.basket = basket;
		}

		public void run() {
			long ctime = System.currentTimeMillis();
			while (true) {
				if (System.currentTimeMillis() - ctime > 250) {
					// 消费苹果
					basket.take();
					System.out.println("!消费者消费苹果完毕：" + instance);
					// 休眠1000ms
					// // Thread.sleep(1000);
					ctime = System.currentTimeMillis();
				}
			}

		}
	}

	public static void main(String[] args) throws InterruptedException {
		TestLinkedBlockingQueue test = new TestLinkedBlockingQueue();  

		// 建立一个装苹果的篮子
		Basket basket = test.new Basket();

		ExecutorService service = Executors.newCachedThreadPool();
		Producer producer = test.new Producer("生产者001", basket);
		Producer producer2 = test.new Producer("生产者002", basket);
		Consumer consumer = test.new Consumer("消费者001", basket);
		service.submit(producer);
		// // service.submit(producer2);
		Thread.sleep(1000 * 5);
		System.out.println("*********************************");
		service.submit(consumer);
		// 程序运行5s后，所有任务停止
		// try {
		// Thread.sleep(1000 * 5);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// service.shutdownNow();
	}

}
