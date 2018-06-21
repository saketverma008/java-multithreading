package com.programming.multithreading;

class EvenPrinter implements Runnable {
	private int number = 2;
	private final Object mutex;                                       /*what is object mutex and how is it defined*/

	public EvenPrinter(Object mutex) {
		this.mutex = mutex;
	}

	public void run() {
		while (number < 20) {
			synchronized (mutex) {
				System.out.println("Even number = " + number);
				number = number + 2;
				mutex.notify();
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class OddPrinter implements Runnable {
	int oddNumber = 1;
	private final Object mutex;

	public OddPrinter(Object mutex) {
		this.mutex = mutex;
	}

	public void run() {
		while (oddNumber < 20) {
			synchronized (mutex) {
				System.out.println("Odd number = " + oddNumber);
				oddNumber = oddNumber + 2;
				mutex.notify();
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

public class DigitPrinter {

	public static void main(String[] args) {
		Object mutex = new Object();
		EvenPrinter evenRunnable = new EvenPrinter(mutex);
		OddPrinter oddRunnable = new OddPrinter(mutex);
		Thread oddThread = new Thread(oddRunnable, "Odd Printer Thread");
		Thread evenThread = new Thread(evenRunnable, "Even Printer Thread");
		oddThread.start();
		evenThread.start();
	}
}