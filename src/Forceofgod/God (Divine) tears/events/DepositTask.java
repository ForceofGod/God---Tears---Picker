package forceofgod.divinetears.events;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;

import forceofgod.divinetears.DivineTearsCollector;
import forceofgod.divinetears.tasks.Task;

public class DepositTask extends Task {

	public DepositTask(MethodContext ctx, String tag) {
		super(ctx, tag);
	}

	public int delay() {
		return Random.nextInt(1200, 2100);
	}

	public boolean activate() {
		DivineTearsCollector.status = "Depositing Tears";
		return false;
	}

	public void execute() {

	}

}
