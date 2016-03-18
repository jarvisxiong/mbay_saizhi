package cn.mbpartner.mbay.activity.check.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import cn.mbpartner.mbay.activity.check.service.UserService;

public class ActivityExpiredCheck {

    private ThreadPoolTaskExecutor taskExecutor;

    private UserService userService;

    private TaskList list = new TaskList();

    @Autowired
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
	this.taskExecutor = taskExecutor;
    }

    @Autowired
    public void setUserService(UserService userService) {
	this.userService = userService;
    }

    public void doCheck() {
	List<String> usernumbers = userService.findAllUsernumber();
	Task task = null;
	for (String usernumber : usernumbers) {
	    task = new Task(usernumber);
	    list.addTask(task);
	}
	for (int i = 0; i < (this.taskExecutor.getMaxPoolSize() / 2) + 5; i++) {
	    this.taskExecutor.execute(new ActivityExperdCheckThread(list));
	}
    }
}
