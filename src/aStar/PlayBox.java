package aStar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

import aStar.City.PaintMode;

public class PlayBox extends JPanel {

	/**
	 * Create the panel.
	 */
	public PlayBox() {
		
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
		
		JButton btnPlay_1 = new JButton("play");
		
		JButton btnPlay = new JButton("pause");
		
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
						.addComponent(btnPlay_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(btnPlay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
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
					.addComponent(btnPlay_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPlay)
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
}
