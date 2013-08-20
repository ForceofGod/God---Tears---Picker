package forceofgod.divinetears.handlers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import forceofgod.divinetears.DivineTearsCollector;

public class PaintHandler {

	public static void drawBackground(Graphics2D g) {
		g.setColor(new Color(220, 220, 220, 210));
		g.fillRect(10, 12, 300, 122);
		g.setFont(new Font("Impact", Font.BOLD, 24));
		g.setColor(Color.green);
		g.drawString("> Divine Tears Collector <", 22, 36);
		g.setFont(new Font("Verdana", Font.ITALIC, 16));
		g.setColor(Color.blue);
		g.drawString("By Forceofgod", 170, 54);
	}

	public static void drawStats(Graphics2D g) {
		g.setFont(new Font("Verdana", Font.PLAIN, 16));
		g.setColor(Color.black);
		g.drawString("Tears collected: " + DivineTearsCollector.tears_collected, 16, 74);
		g.drawString("Tears P/H: " + DivineTearsCollector.tears_per_hour, 16, 92);
		g.drawString("Status: " + DivineTearsCollector.status, 16, 110);
		g.drawString("Antiban: " + DivineTearsCollector.antibanStatus, 16, 128);
	}

}
