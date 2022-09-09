package jp.sagalab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MyJPanel extends JPanel implements MouseListener, MouseMotionListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Graphics g = getGraphics();
		m_counter++;

		if(m_counter%2 == 1){
			Point point = new Point(e.getX(), e.getY());
			drawPoint(point, Color.RED,g);
			m_points.add(point);
		}else if(m_counter%2 == 0){
			drawFuzzy(m_points.get(m_counter-2), e.getPoint(), Color.BLUE,g);
			m_points.add(e.getPoint());

			if(m_fuzzyPoints.size() == 2){

				Vote a = Vote.create(m_fuzzyPoints.get(0), m_fuzzyPoints.get(1));
				b = a.getSymmetricBin();
				Bin c = Vote.getMostSymmetricBin(b);

				//System.out.println("distance:"+c.getDistance()+", degree:"+Math.toDegrees(c.getDegree()));
				System.out.println("the number of bin:"+b.size());

				repaint();

				System.out.println("grade:"+c.getGrade());



			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}



	public  static  MyJPanel create(int _width, int _height){
		return new MyJPanel(_width, _height);
	}

	private MyJPanel(int _width, int _height){
		m_width = _width;
		m_height = _height;
		setPreferredSize(new Dimension(m_width, m_height));
		addMouseListener(this);
		addMouseMotionListener(this);
		this.setBackground(Color.WHITE);
	}

	public void drawPoint(Point _p, Color _color, Graphics g) {
		g.setColor(_color);
		int diameter = 8;
		g.fillOval((int)_p.getX()-diameter/2, (int)_p.getY()-diameter/2, diameter, diameter);
	}


	public void drawFuzzy(Point _previousPoint, Point _currentPoint, Color _color, Graphics g){
		g.setColor(_color);
		double radiusX = Math.pow(_currentPoint.getX() - _previousPoint.getX(), 2);
		double radiusY = Math.pow(_currentPoint.getY() - _previousPoint.getY(), 2);
		double radius = Math.sqrt(radiusX + radiusY);

		FuzzyPoint fuzzyPoint = FuzzyPoint.create(_previousPoint.getX(), _previousPoint.getY(), radius);
		m_fuzzyPoints.add(fuzzyPoint);

		g.drawOval((int)_previousPoint.getX()-(int)radius, (int)_previousPoint.getY()-(int)radius, (int)radius*2, (int)radius*2);
	}

	public void drawAxis(Bin _bin, Color _color, Graphics g){
		double x1,x2;

		for(double i = _bin.getBeginTheta(); i < _bin.getEndTheta(); i += 0.1) {
			for (double j = _bin.getBeginRho(); j < _bin.getEndRho(); j++) {
				x1 = (j * Vote.R / Vote.NUM_OF_DIVISION_RHO) / Math.cos(i * 2 * Math.PI / Vote.NUM_OF_DIVISION_THETA);
				x2 = (j * Vote.R / Vote.NUM_OF_DIVISION_RHO) / Math.cos(i * 2 * Math.PI / Vote.NUM_OF_DIVISION_THETA) - Math.tan(i * 2 * Math.PI / Vote.NUM_OF_DIVISION_THETA) * CANVAS_SIZE_Y;

				g.setColor(_color);
				g.drawLine((int)x1,0,(int)x2,CANVAS_SIZE_Y);
			}
		}

	}

	List<Bin> b;
	private int m_counter = 0;
	private final List<Point> m_points = new ArrayList<>();
	private final List<FuzzyPoint> m_fuzzyPoints = new ArrayList<>();
	private final int CANVAS_SIZE_X = 800;
	private final int CANVAS_SIZE_Y = 800;
	private final int m_width;
	private final int m_height;

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if(m_points.size() > 0){
			for (int i = 0; i < m_points.size(); i++){
				drawPoint(m_points.get(i), Color.RED,g);
				drawFuzzy(m_points.get(i),m_points.get(i+1),Color.BLUE,g);
				i++;
			}
		}

		if(b != null) {
			for (Bin value : b) {
				Color green = new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), (int) (value.getGrade() * 30));
				drawAxis(value, green, g);

			}

			Bin bin = Vote.getMostSymmetricBin(b);
			drawAxis(bin,Color.RED,g);

		}

	}

}
