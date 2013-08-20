package forceofgod.divinetears.handlers;

import java.awt.Point;

import org.powerbot.script.methods.Hud;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Delay;
import org.powerbot.script.util.Random;

import forceofgod.divinetears.DivineTearsCollector;

public class AntiBanHandler {

	public static void afk() {
		DivineTearsCollector.antibanStatus = "AFK";
		Delay.sleep(6500, 24300);
		DivineTearsCollector.antibanStatus = "Idle";
	}

	public static void moveCamera(MethodContext ctx) {
		DivineTearsCollector.antibanStatus = "Moving camera";
		int newYaw = Random.nextInt(0, 360);
		ctx.camera.setYaw(newYaw);
		Delay.sleep(865, 1740);
		newYaw = Random.nextInt(0, 360);
		ctx.camera.setYaw(newYaw);
		Delay.sleep(350, 775);
		DivineTearsCollector.antibanStatus = "Idle";
	}

	public static void checkSkill(MethodContext ctx) {
		DivineTearsCollector.antibanStatus = "Checking skills";
		if(ctx.hud.isOpen(Hud.Window.SKILLS)) {
			ctx.hud.view(Hud.Window.SKILLS);
			//TODO: Add random skill hovering.
		} else
			ctx.hud.open(Hud.Window.SKILLS);
		Delay.sleep(800, 1200);
		if(ctx.hud.isOpen(Hud.Window.BACKPACK)) {
			ctx.hud.view(Hud.Window.BACKPACK);
		} else
			ctx.hud.open(Hud.Window.BACKPACK);
		DivineTearsCollector.antibanStatus = "Idle";
	}

	public static void moveMouse(MethodContext ctx) {
		DivineTearsCollector.antibanStatus = "Moving mouse";
		final Point newPoint = new Point(Random.nextInt(10, ctx.game.getDimensions().width - 40), 
												Random.nextInt(10, ctx.game.getDimensions().height - 40));
		ctx.mouse.move(newPoint);
		DivineTearsCollector.antibanStatus = "Idle";
	}
