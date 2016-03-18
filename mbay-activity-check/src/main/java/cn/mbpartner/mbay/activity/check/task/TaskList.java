package cn.mbpartner.mbay.activity.check.task;

import java.util.LinkedList;

public class TaskList {
    private LinkedList<Task> linktask = new LinkedList<Task>();

    public synchronized void addTask(Task task) {
	this.linktask.add(task);
    }

    public synchronized Task getTask() {

	if (this.linktask.size() <= 0)
	    try {
		wait();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	return this.linktask.poll();

    }

    public synchronized int getCount() {

	return this.linktask.size();

    }

    public synchronized void removeAll() {

	this.linktask.clear();
    }
}
