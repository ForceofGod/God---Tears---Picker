package forceofgod.divinetears.tasks;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.util.Random;

import forceofgod.divinetears.GuiSettings;


public class TaskManager {

	public List<Task> taskList = new ArrayList<Task>();

	public TaskManager(Task[] tasks) {
		addTask(tasks);
	}

	public void clear() {
		taskList.clear();
	}

	public void addTask(Task... tasks) {
		for(Task task : tasks) {
			if(!taskList.contains(task)) 
				taskList.add(task);
		}
	}

	public void removeTask(Task task) {
		if(taskList.contains(task))
			taskList.remove(task);
	}

	private Task getActiveTask() {
		for(Task task : taskList) {
			if(task.active())
				return task;
		}
		return getRandomTask();
	}

	private boolean noActiveTask() {
		for(Task task : taskList) {
			if(task.active())
				return false;
		}
		return true;
	}

	/**
	 * Gets a random task from the task list if there is no task already active.
	 * @return random task from task list.
	 */
	private Task getRandomTask() {
		Task result = null;
		result = taskList.get(Random.nextInt(0, taskList.size() - 1));
		result.setActive(true);
		result.activate();
		return result;
	}

	/**
	 * Gets a task by its tag.
	 * @param tag Tag of the task.
	 * @return task with the given tag.
	 */
	public Task getTaskByTag(String tag) {
		for (Task task : taskList) {
			if(task.getTag().equals(tag))
				return task;
		}
		return null;
	}

	/**
	 * Gets current active task that meets settings, or a random task if no active task is present.
	 * @return currently active task.
	 */
	public Task getCurrentTask() {
		if(GuiSettings.COLLECTION_TARGET == GuiSettings.ScriptSetting.RANDOM) {
			if(noActiveTask()) {
				return getRandomTask();
			} else {
				return getActiveTask();
			}
		}
		else {
			for(Task task : taskList) {
				if(task.getTag().equals(GuiSettings.COLLECTION_TARGET.toString()))  {
					if(!task.active()) {
						task.setActive(true);
						task.activate();
					}
					return task;
				}
			}
		}
		return null;
	}

}
