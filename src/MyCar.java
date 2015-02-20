import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


public class MyCar {
	private Color color;
	int left; //x coordinate of top left of rectangle
	int top; //y coordinate of top left of rectangle
	int width; //width of rectangle
	int height; //height of rectangle
	int rad; //radius of the wheel
	
	public MyCar(MyCar source){
		left = source.left;
		color = source.color;
		top = source.top;
		width = source.width;
		height = source.height;
		rad = source.rad;
	}
	
	public MyCar(Color nColor, int nLeft, int nTop, int nWidth, int nHeight, int nRad) {
		left = nLeft;
		color = nColor;
		top = nTop;
		width = nWidth;
		height = nHeight;
		rad = nRad;
	}
	
	public void draw(Graphics g) {
		Polygon rect = new Polygon();
		int semiRad = width / 3; //radius of the top semicircle
		
		//set up the rectangle
		rect.addPoint(left, top);
		rect.addPoint(left + width, top);
		rect.addPoint(left + width, top + height);
		rect.addPoint(left, top + height);
		
		g.setColor(color);
		g.fillPolygon(rect);
		
		//draw up the hood
		g.fillOval(left + semiRad, top - semiRad, semiRad*2, semiRad*2);
		
		//draw up wheels
		g.setColor(Color.black);
		g.fillOval(left, top+height-(rad/2), rad, rad);		
		g.fillOval(left + width - semiRad, top+height-(rad/2), rad, rad);
	}
	
	public boolean hitTest(int x, int y) {
		//used in the delete function or the move function
		if (x > left && y > top && x < left + width && y < top + height) {
			return true; //checks within the rectangle
		}
		/*if (Math.sqrt(((x-(left+width-(rad/6)))*(x-left+width-(rad/6)) +
				(y -(top+height -(rad/2))*(y-(top+height - (rad/2)))) ))<rad) {
			System.out.println("hit circle");
			
		}*/
		return false;
	}
	
	public void move(int dx, int dy) {
		left += dx;
		top += dy;
	}
	
}
