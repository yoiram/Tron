import javax.swing.JApplet;

public class TronApplet extends JApplet {

	public void init() {
		TronPanel p = new TronPanel();
		setContentPane(p);
	}

}