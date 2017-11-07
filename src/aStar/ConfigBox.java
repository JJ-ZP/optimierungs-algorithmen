package aStar;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

import aStar.City.PaintMode;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * GUI zum bearbeiten der Verbindungen
 * @author Pritzi Julian und Zöschg Jonas
 *
 */

public class ConfigBox extends JPanel {
	
	private JLabel label;
	private JSpinner spinner;
	private JCheckBox checkBox;
	private JLabel label_1;
	private JSpinner spinner_1;
	private JCheckBox checkBox_1;
	private GroupLayout groupLayout;
	private SpinnerNumberModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE,1);
	private SpinnerNumberModel model2 = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE,1);
	
	private City startCity;
	private City targetCity;
	private Connection startToTarget;
	private Connection targetToStart;
	
	/**
	 * Create the panel.
	 */
	public ConfigBox() {
		
		label = new JLabel("Wert:");
		
		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (startCity != null && targetCity != null && targetToStart != null)
					targetCity.getConnectionTo(startCity).setCost((int)spinner.getValue());
			}
		});
		spinner.setModel(model);
		
		checkBox = new JCheckBox("A   <--   B");
		checkBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spinner.setEnabled(true);
					label.setEnabled(true);
					checkBox.setForeground(Color.BLACK);
					if(targetToStart == null) {
						targetToStart = new Connection(targetCity, startCity, 1);
						targetCity.addConnection(targetToStart);
						targetToStart.setPaintMode(PaintMode.SELECTED);
						spinner.setValue(1);
					} else {
						spinner.setValue(targetToStart.getCost());
					}
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spinner.setEnabled(false);
					label.setEnabled(false);
					checkBox.setForeground(new Color(128, 128, 128));
					if(targetToStart == null) {
						spinner.setValue(1);
					} else {
						targetToStart = null;
						targetCity.removeConnection(startCity);
						spinner.setValue(1);
					}
				}
			}
		});
		
		label_1 = new JLabel("Wert:");
		
		spinner_1 = new JSpinner();
		spinner_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (startCity != null && targetCity != null && startToTarget != null)
					startCity.getConnectionTo(targetCity).setCost((int)spinner_1.getValue());
			}
		});
		
		spinner_1.setModel(model2);
		
		checkBox_1 = new JCheckBox("A   -->   B");
		checkBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spinner_1.setEnabled(true);
					label_1.setEnabled(true);
					checkBox_1.setForeground(Color.BLACK);
					if(startToTarget == null) {
						startToTarget = new Connection(startCity, targetCity, 1);
						startCity.addConnection(startToTarget);
						startToTarget.setPaintMode(PaintMode.SELECTED);
						spinner_1.setValue(1);
					} else {
						spinner_1.setValue(startToTarget.getCost());
					}
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED) {
					spinner_1.setEnabled(false);
					label_1.setEnabled(false);
					checkBox_1.setForeground(new Color(128, 128, 128));
					if(startToTarget == null) {
						spinner.setValue(1);
					} else {
						startToTarget = null;
						startCity.removeConnection(targetCity);
						spinner.setValue(1);
					}
				}
			}
		});
		
		spinner.setEnabled(false);
		label.setEnabled(false);
		checkBox.setForeground(new Color(128, 128, 128));
		spinner_1.setEnabled(false);
		label_1.setEnabled(false);
		checkBox_1.setForeground(new Color(128, 128, 128));
		checkBox.setEnabled(false);
		checkBox_1.setEnabled(false);
		
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
		City.cfgBox = this;
	}

	public void removeCities() {
		if(startCity != null)
			startCity.setPaintMode(PaintMode.DEFAULT);
		if(targetCity != null)
			targetCity.setPaintMode(PaintMode.DEFAULT);
		if(startToTarget != null)
			startToTarget.setPaintMode(PaintMode.DEFAULT);
		if(targetToStart != null)
			targetToStart.setPaintMode(PaintMode.DEFAULT);
		this.startCity = null;
		this.targetCity = null;
		this.startToTarget = null;
		this.targetToStart = null;
		checkBox_1.setText("A   -->   B");
		checkBox.setText("A   <--   B");
		checkBox.setSelected(false);
		checkBox_1.setSelected(false);
		checkBox.setEnabled(false);
		checkBox_1.setEnabled(false);
		this.repaint();
	}

	public void setCities(City startCity, City targetCity) {
		this.startCity = startCity;
		this.targetCity = targetCity;
		this.checkBox_1.setText(startCity.ID + makeSpaces(startCity.ID) +
				" --> " + makeSpaces(targetCity.ID) + targetCity.ID);
		this.checkBox.setText(startCity.ID + makeSpaces(startCity.ID) +
				" <-- " + makeSpaces(targetCity.ID) + targetCity.ID);
		this.startToTarget = startCity.getConnectionTo(targetCity);
		this.targetToStart = targetCity.getConnectionTo(startCity);
		if(this.startToTarget != null) {
			spinner_1.setValue(startToTarget.getCost());
			checkBox_1.setSelected(true);
			startToTarget.setPaintMode(PaintMode.SELECTED);
		}
		
		if(this.targetToStart != null) {
			spinner.setValue(targetToStart.getCost());
			checkBox.setSelected(true);
			targetToStart.setPaintMode(PaintMode.SELECTED);
		}
		
		this.checkBox.setEnabled(true);
		this.checkBox_1.setEnabled(true);
		startCity.setPaintMode(PaintMode.SELECTED);
		targetCity.setPaintMode(PaintMode.SELECTED);
		this.repaint();
	}
	
	public void setStartCity(City startCity) {
		removeCities();
		this.startCity = startCity;
		checkBox_1.setText(startCity.ID + makeSpaces(startCity.ID) + " -->   B");
		checkBox.setText(startCity.ID + makeSpaces(startCity.ID) + " <--   B");
		startCity.setPaintMode(PaintMode.SELECTED);
		this.repaint();
	}
	
	private String makeSpaces(long iD) {
		return (iD > 9 ? (iD > 99 ? "" : " ") : "  ");
	}
}
