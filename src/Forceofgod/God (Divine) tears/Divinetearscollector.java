package Forceofgod.divinetears;
 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;
 
import javax.imageio.ImageIO;
 
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Hud;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Item;
 
import Forceofgod.divinetears.events.DepositTask;
import Forceofgod.divinetears.events.FishingTask;
import Forceofgod.divinetears.events.MiningTask;
import Forceofgod.divinetears.events.WoodcuttingTask;
import Forceofgod.divinetears.handlers.PaintHandler;
import Forceofgod.divinetears.tasks.Task;
import Forceofgod.divinetears.tasks.TaskManager;
 
@Manifest (authors = { "ForceofGod" }, description = "Picks the Divine Tears from the World Event",
                        name = "Divine Tears Picker")
public class DivineTearsCollector extends PollingScript implements PaintListener {
 
        private final Image DIVINE_CURSOR = getImage("https://dl.dropboxusercontent.com/u/44164076/divine_tears.png");
 
        private Gui gui = null;;
        private TaskManager manager = null;
 
        public static String status = "Idle";
        public static String antibanStatus = "Idle";
 
        private int starting_tears_count;
        public static int tears_collected;
        public static int tears_per_hour;
        private Timer timer = null;
 
        public DivineTearsCollector() {
                getExecQueue(State.START).add(new Runnable() {
                        public void run() {
 
                                if(manager == null)
                                        manager = new TaskManager(new Task[] {
                                                                        new FishingTask(getContext(), "FISH"),
                                                                                new MiningTask(getContext(), "MINE"),
                                                                                        new WoodcuttingTask(getContext(), "CHOP"),
                                                                                                new DepositTask(getContext(), "DEPOSIT")
                                        });
 
                                if(gui == null)
                                        gui = new Gui();
 
                                if(timer == null)
                                        timer = new Timer(0);
 
                                for(Item item : ctx.backpack.getAllItems())
                                        if(item.getId() == 28811)
                                                starting_tears_count = item.getStackSize();
 
                                tears_collected = 0;
                                tears_per_hour = 0;
                        }
                });
 
        }
 
        public static Logger getLog() {
                return Logger.getLogger("ForceofGod.divinetears.DivineTearsCollector");
        }
 
        @Override
        public int poll() {
 
                while(gui.isVisible()) {
                        log.info("Waiting for GUI input.");
                        return 2000;
                }
 
                while(ctx.game.getClientState() != 11)
                        return 2000;
 
                calculateTearsCollected();
                calculateTearsPerHour();
 
                if(!ctx.hud.isOpen(Hud.Window.BACKPACK))
                        ctx.hud.open(Hud.Window.BACKPACK);
                else
                        ctx.hud.view(Hud.Window.BACKPACK);
 
                if(GuiSettings.DEPOSIT_TEARS && hasTearsTarget()) {
                        final Task depositTask = manager.getTaskByTag("DEPOSIT");
                        if(!depositTask.active()) {
                                depositTask.setActive(true);
                                depositTask.activate();
                        } else {
                                depositTask.execute();
                                return depositTask.delay();
                        }
                }
 
                final Task task = manager.getCurrentTask();
                if(task.active()) {
                        task.execute();
                        return task.delay();
                }
                return 250;
        }
 
        private boolean hasTearsTarget() {
                for(Item item : ctx.backpack.getAllItems())
                        if(item.getId() == 28811 && item.getStackSize() >= GuiSettings.TEARS_DEPOSIT_GOAL)
                                return true;
                return false;
        }
 
        private void calculateTearsCollected() {
                for(Item item : ctx.backpack.getAllItems())
                        if(item.getId() == 28811 && item.getStackSize() > starting_tears_count)
                                        tears_collected = item.getStackSize() - starting_tears_count;
        }
 
        private void calculateTearsPerHour() {
                tears_per_hour = (int) (tears_collected * 3600000D / timer.getElapsed());
        }
 
        @Override
        public void repaint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.drawImage(DIVINE_CURSOR, ctx.mouse.getLocation().x - 12, ctx.mouse.getLocation().y - 12, 32, 32, null);
                PaintHandler.drawBackground(g2);
                PaintHandler.drawStats(g2);
        }
 
        private Image getImage(String url) {
                try {
                        return ImageIO.read(new URL(url));
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return null;
        }
 
}
