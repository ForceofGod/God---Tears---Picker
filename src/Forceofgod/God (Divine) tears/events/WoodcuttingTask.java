package forceofgod.divinetears.events;
 
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.GameObject;
 
import forceofgod.divinetears.DivineTearsCollector;
import forceofgod.divinetears.GuiSettings;
import forceofgod.divinetears.tasks.Task;
 
public class WoodcuttingTask extends Task {
 
        private final int FORMATION_ID = 85894;
        private GameObject formationObj = ctx.objects.getNil();
 
        public WoodcuttingTask(MethodContext ctx, String tag) {
                super(ctx, tag);
        }
 
        public boolean activate() {
                if(formationObj == null || !formationObj.isValid()) {
                        for(GameObject formation : ctx.objects.select().id(FORMATION_ID).nearest().first()) {
                                if(formation.isValid())
                                        formationObj = formation;
                                if(playerNextToFormation()) {
                                        formationObj.interact("Chop"); // TEMP fix for script going idle if player starts script next to formation.
                                        DivineTearsCollector.status = "Chopping formation";
                                }
                        }
                }
                return formationObj.isValid();
        }
 
        public void execute() {
                if(formationObj.isValid()) {
                        if(!formationObj.isOnScreen()) {
                                DivineTearsCollector.status = "Turning camera";
                                ctx.camera.turnTo(formationObj);
                                Delay.sleep(300, 700);
                        }
                        if(!playerNextToFormation()) {
                                DivineTearsCollector.status = "Traversing";
                                ctx.movement.stepTowards(formationObj);
                                Delay.sleep(1000, 2000);
                                formationObj.interact("Chop");
                                DivineTearsCollector.status = "Chopping Formation";
                        }
                        antiban();
                } else
                        if(GuiSettings.COLLECTION_TARGET == GuiSettings.ScriptSetting.CHOP)
                                getNewFormation();
                        else
                                setActive(false);
        }
 
        private boolean playerNextToFormation() {
                return player.getLocation().distanceTo(formationObj) == 1;
        }
 
        private void getNewFormation() {
                DivineTearsCollector.status = "Searching";
                if(!formationObj.isValid()) {
                        for(GameObject formation : ctx.objects.select().id(FORMATION_ID).nearest().first()) {
                                if(formation.isValid())
                                        formationObj = formation;
                        }
                }
        }
}
