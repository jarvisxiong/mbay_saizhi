package cn.mbpartner.mbay.activity.check.task;

public class ActivityExperdCheckThread implements Runnable {
    private TaskList list = null;

    public ActivityExperdCheckThread(TaskList list) {
	this.list = list;
    }

    @Override
    public void run() {

	while (true) {

	    Task task = list.getTask();

	    if (task != null) {
		task.work();
	    }

	}

    }
}
