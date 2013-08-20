package forceofgod.divinetears.tasks;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Player;

import forceofgod.divinetears.DivineTearsCollector;
import forceofgod.divinetears.handlers.AntiBanHandler;

public abstract class Task extends MethodProvider {

	public abstract boolean activate();
	public abstract void execute();

	protected String tag = "";

	protected final Player player = ctx.players.local();
	private boolean active;

	public Task(MethodContext ctx) {
		super(ctx);
		active = false;
	}

	public Task(MethodContext ctx, String tag) {
		super(ctx);
		this.tag = tag;
	}

	public int delay() {
		return Random.nextInt(800, 1100);
	}

	public String getTag() {
		return tag;
	}

	public void setActive(boolean active) {
		DivineTearsCollector.getLog().info("Task: " + toString() + " toggled: " + active);
		this.active = active;
	}

	public boolean active() {
		return active;
	}

	public void antiban() {
		int i = Random.nextInt(0, 10);
		switch(i) {
		case 0:
			AntiBanHandler.afk();
			break;

		case 1:
			AntiBanHandler.moveCamera(ctx);
			break;

		case 2:
			//AntiBanHandler.checkSkill(ctx); < needs finishing. much to obvious to include at the moment.
			break;

		case 3:
			AntiBanHandler.moveMouse(ctx);
			break;
		}
	}

}
