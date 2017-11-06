package aStar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import aStar.City.PaintMode;

public class PlayBox extends JPanel implements Runnable {

	private static int sleepTime = 1000;
	private Thread playThread;
	private JFrame frame;
	
	/**
	 * Create the panel.
	 */
	public PlayBox(JFrame frame) {
		this.frame = frame;
		JButton btnSimulate = new JButton("calculate");
		btnSimulate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(City.startCity != null && City.targetCity != null) {
					AStarAlgorithm aStar = new AStarAlgorithm(City.startCity, City.targetCity);
					City.solution = aStar.solveAll();
					City.solution.mark();
					City.targetCity.setPaintMode(PaintMode.GLOW);
				}else
					JOptionPane.showMessageDialog(PlayBox.this.getParent().getParent(),
							"Bitte wähle zuerst 2 Städte aus");
			}
		});
		
		JSeparator separator = new JSeparator();
		
		JButton btnPlay = new JButton("play");
		btnPlay.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(playThread == null || !playThread.isAlive()) {
					playThread = new Thread(PlayBox.this);
					frame.setEnabled(false);
					playThread.start();
				}
			}
		});
		
		JButton btnPause = new JButton("pause");
		
		JButton btnNext = new JButton("next");
		
		JButton btnReset = new JButton("reset");
		
		JButton btnPrev = new JButton("prev");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(separator, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnSimulate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnPlay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnPause, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnNext, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnReset, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnPrev, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnSimulate)
					.addGap(9)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPlay)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPause)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNext)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnReset)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPrev)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	@Override
	public void run(){
		try {
			if(City.startCity != null && City.targetCity != null) {
				City.startCity.setPaintMode(PaintMode.SELECTED);
				City.targetCity.setPaintMode(PaintMode.SELECTED);
				AStarAlgorithm aStar = new AStarAlgorithm(City.startCity, City.targetCity);
				Track t;
				while( (t = aStar.next()) != null ) {
					t.mark();
					if(!t.getCurrentCity().equals(City.targetCity)) {
						Thread.sleep(sleepTime);
						t.getCurrentCity().setPaintMode(PaintMode.GLOWINGTRACKS);
						for (Connection conn : t.getCurrentCity().getConnections()) {
							conn.setPaintMode(PaintMode.SELECTED);
						}
						Thread.sleep(sleepTime);
						t.unMark();
						for (Connection conn : t.getCurrentCity().getConnections()) {
							conn.setPaintMode(PaintMode.DEFAULT);
						}
						City.startCity.setPaintMode(PaintMode.SELECTED);
						City.targetCity.setPaintMode(PaintMode.SELECTED);
					} else {
						City.solution = t;
					}
				}
				City.targetCity.setPaintMode(PaintMode.GLOW);
			}else
				JOptionPane.showMessageDialog(null, "Bitte wähle zuerst 2 Städte aus");
		} catch (InterruptedException e) {
			
		} finally {
			frame.setEnabled(true);
		}
		
	}
}
