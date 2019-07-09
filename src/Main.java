import controller.MazeController;
import model.MazeModel;
import view.MazeView;

public class Main {

	/**
	 * Feel free to increse your VM memory by using the VM arguments -Xss200m
	 * @param args
	 */
	public static void main(String[] args) {
		MazeModel m = new MazeModel(50,5);
		MazeView v = new MazeView(m);
		MazeController c = new MazeController(m,v);
				
	}
}
