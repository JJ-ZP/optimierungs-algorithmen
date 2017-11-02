package aStar;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;

import aStar.julian.EditMode;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class BuildBox extends JPanel {

	JToggleButton btnMove;
	JToggleButton btnAdd;
	JToggleButton btnDel;
	JToggleButton btnConf;
	
	ButtonGroup buttonGroup;
	JSeparator separator;
	ConfigBox panel;
	GroupLayout groupLayout;
	
	/**
	 * Create the panel.
	 */
	public BuildBox() {
		
		btnMove = new JToggleButton("MOVE");
		btnMove.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED)
					EditMode.current = EditMode.MOVE;
			}
		});
		
		btnAdd = new JToggleButton("ADD");
		btnAdd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED)
					EditMode.current = EditMode.ADDCITY;
			}
		});
		
		btnDel = new JToggleButton("DEL");
		btnDel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					EditMode.current = EditMode.REMOVECITY;
			}
		});
		
		btnConf = new JToggleButton("CONF");
		btnConf.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					panel.setVisible(true);
					separator.setVisible(true);
					EditMode.current = EditMode.MODIFYCONNECTION;
				}
				else if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					panel.setVisible(false);
					separator.setVisible(false);
				}
			}
		});
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(btnMove);
		buttonGroup.add(btnAdd);
		buttonGroup.add(btnDel);
		buttonGroup.add(btnConf);
		
		separator= new JSeparator();
		
		panel = new ConfigBox();
		
		panel.setVisible(false);
		separator.setVisible(false);
		
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnMove, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(13)
									.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnDel, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
									.addGap(13)
									.addComponent(btnConf, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)))
							.addGap(12))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
							.addContainerGap())
						.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 173, Short.MAX_VALUE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnMove)
						.addComponent(btnAdd))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDel)
						.addComponent(btnConf))
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
