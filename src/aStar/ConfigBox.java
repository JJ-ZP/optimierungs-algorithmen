package aStar;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.Color;
import java.awt.event.ItemEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ConfigBox extends JPanel {

	JLabel label;
	JSpinner spinner;
	JCheckBox checkBox;
	JLabel label_1;
	JSpinner spinner_1;
	JCheckBox checkBox_1;
	GroupLayout groupLayout;
	SpinnerNumberModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE,1);
	SpinnerNumberModel model2 = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE,1);
	
	/**
	 * Create the panel.
	 */
	public ConfigBox() {
		
		label = new JLabel("Wert:");
		
		spinner = new JSpinner();
		spinner.setModel(model);
		
		checkBox = new JCheckBox("A   <---   B");
		checkBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spinner.setEnabled(true);
					label.setEnabled(true);
					checkBox.setForeground(Color.BLACK);
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spinner.setEnabled(false);
					label.setEnabled(false);
					checkBox.setForeground(new Color(128, 128, 128));
				}
			}
		});
		
		label_1 = new JLabel("Wert:");
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(model2);
		
		checkBox_1 = new JCheckBox("A   --->   B");
		checkBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spinner_1.setEnabled(true);
					label_1.setEnabled(true);
					checkBox_1.setForeground(Color.BLACK);
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spinner_1.setEnabled(false);
					label_1.setEnabled(false);
					checkBox_1.setForeground(new Color(128, 128, 128));
				}
			}
		});
		
		spinner.setEnabled(false);
		label.setEnabled(false);
		checkBox.setForeground(new Color(128, 128, 128));
		spinner_1.setEnabled(false);
		label_1.setEnabled(false);
		checkBox_1.setForeground(new Color(128, 128, 128));
		
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 89, Short.MAX_VALUE))
						.addComponent(checkBox, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(spinner, 0, 0, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(checkBox_1)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(checkBox)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(label))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(8)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
