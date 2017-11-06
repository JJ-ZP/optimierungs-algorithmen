package aStar;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SaveOpenBox extends JPanel {

	/**
	 * Create the panel.
	 */
	public SaveOpenBox() {
		
		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.setMnemonic('S');
		
		JButton btnLoad = new JButton("OPEN");
		btnLoad.setMnemonic('O');
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnLoad, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnLoad)
					.addContainerGap(145, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
