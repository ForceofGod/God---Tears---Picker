package Forceofgod.divinetears.events;
 
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.GameObject;
 
import forceofgod.divinetears.DivineTearsCollector;
import forceofgod.divinetears.GuiSettings;
import forceofgod.divinetears.tasks.Task;
 
public class MiningTask extends Task {
 
        private final int ROCK_ID = 86545;
        private GameObject rockObj = ctx.objects.getNil();
 
        public MiningTask(MethodContext ctx, String tag) {
                super(ctx, tag);
        }
 
        public boolean activate() {
                if(rockObj == null || !rockObj.isValid()) {
                        for(GameObject rock : ctx.objects.select().id(ROCK_ID).nearest().first()) {
                                if(rock.isValid())
                                        rockObj = rock;
                                if(playerNextToRock()) {
                                        rockObj.interact("Mine"); // TEMP fix for script going idle if player starts script next to rock.
                                        DivineTearsCollector.status = "Mining";
                                }
                        }
                }
                return rockObj.isValid();
        }
 
        public void execute() {
                if(rockObj.isValid()) {
                        if(!rockObj.isOnScreen()) {
                                DivineTearsCollector.status = "Turning camera";
                                ctx.camera.turnTo(rockObj);
                                Delay.sleep(300, 700);
                        }
                        if(!playerNextToRock()) {
                                DivineTearsCollector.status = "Traversing";
                                ctx.movement.stepTowards(rockObj);
                                rockObj.interact("Mine");
                                DivineTearsCollector.status = "Mining";
                        }
                        antiban();
                } else
                        if(GuiSettings.COLLECTION_TARGET == GuiSettings.ScriptSetting.MINE)
                                getNewRock();
                        else
                                setActive(false);
        }
 
        private boolean playerNextToRock() {
                return player.getLocation().distanceTo(rockObj) == 1;
        }
 
        private void getNewRock() {
                DivineTearsCollector.status = "Searching";
                if(!rockObj.isValid()) {
                        for(GameObject rock : ctx.objects.select().id(ROCK_ID).nearest().first()) {
                                if(rock.isValid())
                                        rockObj = rock;
                        }
                }
        }
 
}
