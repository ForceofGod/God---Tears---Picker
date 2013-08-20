package forceofGod.divinetears;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class Gui extends JFrame {
        private static final long serialVersionUID = 1L;
 
        private final String[] collectionOptions = { "Random", "Mine", "Chop", "Fish" };
        private final Integer[] depositOptions = { 0, 500, 750, 1000, 1250, 1500 };
 
        private JPanel cpane = new JPanel();
 
        public Gui() {
                GodTearsCollector.getLog().info("Initialising settings GUI.");
                setSize(305, 170);
                setTitle("Settings.");
                setResizable(false);
                setAlwaysOnTop(true);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setLocationRelativeTo(null);
                setVisible(true);
                prepareContentPane();
        }
 
        private void prepareContentPane() {
                setContentPane(cpane);
                cpane.setLayout(null);
                final JLabel optionLabel = new JLabel("Select a method of collection:");
                optionLabel.setBounds(10, 2, 160, 30);
                cpane.add(optionLabel);
                final JLabel depositLabel = new JLabel("Deposit after: (0 for none)");
                depositLabel.setBounds(10, 70, 230, 30);
                cpane.add(depositLabel);
                final JComboBox<String> collectionList = new JComboBox<String>(collectionOptions);
                collectionList.setBounds(10, 30, 140, 30);
                cpane.add(collectionList);
                final JComboBox<Integer> depositList = new JComboBox<Integer>(depositOptions);
                depositList.setBounds(10, 98, 140, 30);
                cpane.add(depositList);
                final JButton startButton = new JButton("Start Script");
                startButton.setBounds(180, 30, 110, 30);
                cpane.add(startButton);
 
                cpane.repaint();
 
                startButton.addActionListener(new ActionListener() {
 
                        public void actionPerformed(ActionEvent ae) {
                                if(collectionList.getSelectedItem() == collectionOptions[0])
                                        GuiSettings.COLLECTION_TARGET = GuiSettings.ScriptSetting.RANDOM;
                                else if(collectionList.getSelectedItem() == collectionOptions[1])
                                        GuiSettings.COLLECTION_TARGET = GuiSettings.ScriptSetting.MINE;
                                else if(collectionList.getSelectedItem() == collectionOptions[2])
                                        GuiSettings.COLLECTION_TARGET = GuiSettings.ScriptSetting.CHOP;
                                else if(collectionList.getSelectedItem() == collectionOptions[3])
                                        GuiSettings.COLLECTION_TARGET = GuiSettings.ScriptSetting.FISH;
 
                                if(depositList.getSelectedItem() == depositOptions[0])
                                        GuiSettings.DEPOSIT_TEARS = false;
                                else
                                        GuiSettings.DEPOSIT_TEARS = false; // change to true when deposit task is finished.
                                GuiSettings.TEARS_DEPOSIT_GOAL = (int) depositList.getSelectedItem();
 
                                divineTearsCollector.getLog().info("Script collection setting set: " + GuiSettings.COLLECTION_TARGET);
                                divineTearsCollector.getLog().info("Script deposit setting set: " + GuiSettings.DEPOSIT_TEARS);
                                dispose();
                        }
 
                });
        }
 
}
