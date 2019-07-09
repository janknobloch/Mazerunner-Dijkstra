package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.MazeModel;
import view.MazeView;

public class MazeController implements ActionListener {

	MazeModel m;
	MazeView v;

	public MazeController(MazeModel m, MazeView v) {
		this.m = m;
		this.v = v;
		v.generateBtn.addActionListener(this);
		v.executeBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(v.generateBtn)) {

			this.m = new MazeModel(Integer.parseInt(v.sizeTxt.getText()), Integer.parseInt(v.wallTxt.getText()));

			this.v.m = this.m;
			v.initGameBoardFieldLabels();
		}
		if (e.getSource().equals(v.executeBtn)) {
			
			this.m.applyDystkra(v.wallCheckBox.isSelected(),v.moveDiagonalCheckBox.isSelected());
			v.initGameBoardFieldLabels();
		}

	}

}
