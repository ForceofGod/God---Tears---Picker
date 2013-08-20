package forceofGod.divinetears;
 
public class GuiSettings {
 
        public static ScriptSetting COLLECTION_TARGET = ScriptSetting.RANDOM;
        public static boolean DEPOSIT_TEARS = false;
        public static int TEARS_DEPOSIT_GOAL = 0;
 
        public enum ScriptSetting {
                RANDOM,
                CHOP,
                MINE,
                FISH,
        }
 
}
