package aStar;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import aStar.City.PaintMode;
import aStar.Logger.Level;
import aStar.julian.CsvReader;
import aStar.julian.EditMode;
import aStar.julian.GuiFrame;

import java.awt.event.ItemListener;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuildBox extends JPanel {
	
	private JToggleButton btnMove;
	private JToggleButton btnAdd;
	private JToggleButton btnDel;
	private JToggleButton btnConf;
	
	private ButtonGroup buttonGroup;
	private JSeparator separator;
	public ConfigBox panel;
	private GroupLayout groupLayout;
	
	private JButton tglbtnSave;
	private JButton tglbtnOpen;
	
	/**
	 * Create the panel.
	 */
	public BuildBox(GuiFrame guiFrame) {
		
		btnMove = new JToggleButton("MOVE");
		btnMove.setMnemonic('V');
		btnMove.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED)
					EditMode.current = EditMode.MOVE;
				else if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					if(City.lastSelected != null) {
						City.lastSelected.setPaintMode(PaintMode.DEFAULT);
						City.lastSelected = null;
					}
				}
			}
		});
		btnMove.setSelected(true);
		
		btnAdd = new JToggleButton("ADD");
		btnAdd.setMnemonic('A');
		btnAdd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED)
					EditMode.current = EditMode.ADDCITY;
			}
		});
		
		btnDel = new JToggleButton("DEL");
		btnDel.setMnemonic('D');
		btnDel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					EditMode.current = EditMode.REMOVECITY;
			}
		});
		
		btnConf = new JToggleButton("CONF");
		btnConf.setMnemonic('C');
		btnConf.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					panel.setVisible(true);
					separator.setVisible(true);
					EditMode.current = EditMode.MODIFYCONNECTION;
				}
				else if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					City.lastSelected = null;
					panel.removeCities();
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
		
		tglbtnSave = new JButton("SAVE");
		tglbtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setFileFilter(new MyFilter(".csv"));
			    chooser.setAcceptAllFileFilterUsed(false);
			    int returnVal = chooser.showSaveDialog(guiFrame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	try {
						CsvWriter.write(chooser.getSelectedFile().getPath(), guiFrame);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(guiFrame, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
			    }
			}
		});
		tglbtnSave.setSelected(true);
		tglbtnSave.setMnemonic('S');
		
		tglbtnOpen = new JButton("OPEN");
		tglbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiFrame.map.removeAll();
				City.currentID = 0;
				guiFrame.map.repaint();
				JFileChooser chooser = new JFileChooser();
			    chooser.setFileFilter(new MyFilter(".csv"));
			    chooser.setAcceptAllFileFilterUsed(false);
			    int returnVal = chooser.showOpenDialog(guiFrame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	ArrayList<City> cities = null;
					try {
						cities = CsvReader.readCities(chooser.getSelectedFile().getPath() , ",");
					} catch (IOException ex) {
						Logger.log(Level.ERROR, ex.getMessage());
					}
					
					for (City city : cities) {
						guiFrame.addCity(city);

						if(Logger.getLevel() == Level.DEBUG) {
							for(Connection conn : city.getConnections()) {
								Logger.log(Level.DEBUG, conn.toString());
							}
						}
					}	
			       
			    }
			}
		});
		tglbtnOpen.setSelected(true);
		tglbtnOpen.setMnemonic('O');
		
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
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 173, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tglbtnSave, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
							.addGap(13)
							.addComponent(tglbtnOpen, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnMove)
						.addComponent(btnAdd))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDel)
						.addComponent(btnConf))
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tglbtnSave)
						.addComponent(tglbtnOpen))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
}
