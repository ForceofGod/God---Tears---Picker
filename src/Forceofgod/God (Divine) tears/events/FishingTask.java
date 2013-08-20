package forceofgod.divinetears.events;
 
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.Npc;
 
import forceofgod.divinetears.DivineTearsCollector;
import forceofgod.divinetears.GuiSettings;
import forceofgod.divinetears.tasks.Task;
 
public class FishingTask extends Task {
 
        private final int FISH_POOL_ID = 17792;
        private Npc poolNpc = ctx.npcs.getNil();
 
        public FishingTask(MethodContext ctx, String tag) {
                super(ctx, tag);
        }
 
        public boolean activate() {
                if(poolNpc == null || !poolNpc.isValid()) {
                        for(Npc pool : ctx.npcs.select().id(FISH_POOL_ID).nearest().first()) {
                                if(pool.isValid())
                                        poolNpc = pool;
                                if(playerNextToPool()) {
                                        poolNpc.interact("Net"); // TEMP fix for script going idle if player starts script next to formation.
                                        DivineTearsCollector.status = "Fishing";
                                }
                        }
                }
                return poolNpc.isValid();
        }
 
        public void execute() {
                if(poolNpc.getId() == FISH_POOL_ID) {
                        if(!poolNpc.isOnScreen()) {
                                DivineTearsCollector.status = "Turning camera";
                                ctx.camera.turnTo(poolNpc);
                                Delay.sleep(300, 700);
                        }
                        if(!playerNextToPool()) {
                                DivineTearsCollector.status = "Traversing";
                                ctx.movement.stepTowards(poolNpc);
                                poolNpc.interact("Net");
                                DivineTearsCollector.status = "Fishing";
                        }
                        if(player.getAnimation() == -1 && playerNextToPool()) {
                                poolNpc.interact("Net");
                                Delay.sleep(800, 2000);
                        }
                        antiban();
                } else
                        if(GuiSettings.COLLECTION_TARGET == GuiSettings.ScriptSetting.FISH)
                                getNewPool();
                        else
                                setActive(false);
        }
 
        private boolean playerNextToPool() {
                return player.getLocation().distanceTo(poolNpc) == 1;
        }
 
        private void getNewPool() {
                DivineTearsCollector.status = "Searching";
                if(!poolNpc.isValid()) {
                        for(Npc pool : ctx.npcs.select().id(FISH_POOL_ID).nearest().first()) {
                                if(pool.isValid())
                                        poolNpc = pool;
                        }
                }
        }
 
}
