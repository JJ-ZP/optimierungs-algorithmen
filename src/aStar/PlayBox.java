package aStar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import aStar.City.PaintMode;
import aStar.Logger.Level;
import aStar.julian.CsvReader;
import aStar.julian.GuiFrame;

public class PlayBox extends JPanel {

	/**
	 * Create the panel.
	 */
	public PlayBox(GuiFrame guiFrame) {
		
		JButton btnSimulate = new JButton("calculate");
		btnSimulate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(City.startCity != null && City.targetCity != null) {
					AStarAlgorithm aStar = new AStarAlgorithm(City.startCity, City.targetCity);
					City.solution = aStar.solveAll();
					if (City.solution != null) {
						City.solution.mark();
						City.targetCity.setPaintMode(PaintMode.GLOW);
						Logger.log(Level.DEBUG, "Final Track: " + City.solution.toString());
					}
					else {
						JOptionPane.showMessageDialog(PlayBox.this.getParent().getParent(),
								"Es gibt keinen Weg von Stadt " + City.startCity + " nach Stadt " + City.targetCity);
					}
				}else
					JOptionPane.showMessageDialog(PlayBox.this.getParent().getParent(),
							"Bitte wähle zuerst 2 Städte aus");
			}
		});
		
		JSeparator separator = new JSeparator();
		
		JButton btnPlay_1 = new JButton("play");
		
		JButton btnPlay = new JButton("pause");
		
		JButton btnNext = new JButton("next");
		
		JButton btnReset = new JButton("reset");
		
		JButton btnPrev = new JButton("prev");
		
		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setFileFilter(new MyFilter(".csv"));
			    chooser.setAcceptAllFileFilterUsed(false);
			    int returnVal = chooser.showSaveDialog(guiFrame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	try {
						CsvWriter.write(chooser.getSelectedFile().getPath(), guiFrame);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(guiFrame, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
			    }
			}
		});
		btnSave.setMnemonic('S');
		
		JButton btnOpen = new JButton("OPEN");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnOpen.setMnemonic('O');
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnSimulate, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnPlay_1, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnPlay, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnNext, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnPrev, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
							.addGap(13)
							.addComponent(btnOpen, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnSimulate)
					.addGap(9)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPlay_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPlay)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNext)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnReset)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPrev)
					.addPreferredGap(ComponentPlacement.RELATED, 267, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnOpen))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
}
