package shadowdev.quests;

public class Quest {

	int step = 0;
	QuestTask[] tasks;
	
	public Quest(QuestTask...tasks) {
		this.tasks = tasks;
	}
	
	public void nextStep() {
		step++;
	}
	
	public QuestTask getCurrentTask() {
		return tasks[step];
	}
	
}
