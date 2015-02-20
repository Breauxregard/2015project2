import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JFrame;


public class CarFormation extends JApplet
	implements MouseListener, MouseMotionListener {
	
	private ArrayList<MyCar> cars; //ArrayList of car objects
	private ArrayList<MyCar> duplicates;//cas actually moved
	
	private MyCar carToBeMoved;
	
	private int deltaX;
	private int deltaY;
	/**
	 * @param args
	 */
	public CarFormation() { //Constructor
		cars = new ArrayList<MyCar>();
		cars.add(new MyCar(Color.red, 50, 50, 100, 50, 33));
		cars.add(new MyCar(Color.green, 200, 50, 100, 50, 33));
		cars.add(new MyCar(Color.blue, 350, 50, 100, 50, 33));
		cars.add(new MyCar(Color.magenta,  500, 50, 100, 50, 33));
		cars.add(new MyCar(Color.cyan, 650, 50, 100, 50, 33));
		cars.add(new MyCar(Color.yellow, 800, 50, 100, 50, 33));


		duplicates = new ArrayList<MyCar>();
		
		carToBeMoved = null;
		
		// Handle mouse and mouse motion events
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		
		
	}
	
	public void paint( Graphics g) {
		//create the back buffer
		Image backBuffer = createImage(getSize().width, getSize().height);
		Graphics gBackBuffer = backBuffer.getGraphics();
		//clear the back buffer
		gBackBuffer.setColor(Color.white);
		gBackBuffer.clearRect(0, 0, getSize().width, getSize().height);
		//draw the cars to the back bugger
		for (int i=0; i<cars.size();i++) { //draw originals
			cars.get(i).draw(gBackBuffer);
		}
		for (int i=0; i<duplicates.size();i++) { //draw duplicates
			duplicates.get(i).draw(gBackBuffer);
		}
		//copy image from back buffer to front
		g.drawImage(backBuffer, 0, 0, null);
	} //end method paintComponent
	
	public static void main(String[] args) {
		JFrame application = new JFrame( "CarFormation" );
		
		CarFormation panel = new CarFormation();
		application.add(panel);
		
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setSize(950,700);
		application.setVisible( true );
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.isMetaDown()) return; //ignore right button
		if (carToBeMoved != null) {
			carToBeMoved.move(e.getX() - deltaX, e.getY() - deltaY);
			deltaX = e.getX();
			deltaY = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.isMetaDown()) return; //ignore right button
		for (int i=cars.size()-1;i>=0;i--) {
			MyCar p = cars.get(i);
			if (p.hitTest(e.getX(), e.getY())) {
				cars.get(i);
				MyCar duplicate = new MyCar(p);
				duplicates.add(duplicate);
				repaint();
				break;
			}
		}
		for (int i=duplicates.size()-1;i>=0;i--) {
			MyCar p = duplicates.get(i);
			if (p.hitTest(e.getX(), e.getY())) {
				duplicates.remove(i);
				duplicates.add(p);
				carToBeMoved=p;
				deltaX = e.getX();
				deltaY = e.getY();
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isMetaDown()) {
			for (int i=duplicates.size()-1;i>=0;i--) { //hitTest passes on a duplicate
				MyCar p = duplicates.get(i);
				if (p.hitTest(e.getX(), e.getY())) {
					duplicates.remove(i); //delete the duplicate
					repaint();
					break;
				}
			}
			return; //ignore right button
		}
		for (int i=cars.size()-1;i>=0;i--) {
			MyCar p = cars.get(i);
			if (p.hitTest(e.getX(), e.getY())) { //hitTest passes on original
				cars.get(i);
				MyCar duplicate = new MyCar(p);
				duplicates.add(duplicate);
				carToBeMoved=duplicate;
				deltaX = e.getX();
				deltaY = e.getY();
				repaint();
				break;
			}
		}
		for (int i=duplicates.size()-1;i>=0;i--) { //hitTest passes on a duplicate
			MyCar p = duplicates.get(i);
			if (p.hitTest(e.getX(), e.getY())) {
				duplicates.remove(i);
				duplicates.add(p);
				carToBeMoved=p;
				deltaX = e.getX();
				deltaY = e.getY();
				repaint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		carToBeMoved=null;
	}
}
