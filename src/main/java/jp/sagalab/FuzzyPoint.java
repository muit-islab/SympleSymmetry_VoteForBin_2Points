package jp.sagalab;

import java.awt.*;

public class FuzzyPoint {

	public static FuzzyPoint create(Double _x, Double _y, Double _r){
		return new FuzzyPoint(_x, _y, _r);
	}

	/**
	 * x座標を返す.
	 * @return
	 */
	public double getX(){
		return m_x;
	}

	/**
	 * y座標を返す.
	 * @return
	 */
	public double getY(){
		return m_y;
	}

	/**
	 * ファジネスを返す.
	 * @return
	 */
	public double getR(){
		return m_r;
	}

	/**
	 * 中点を返す.
	 * @param _p
	 * @return
	 */
	public FuzzyPoint midPoint(FuzzyPoint _p){
		return FuzzyPoint.create((_p.getX() +  m_x)/2, (_p.getY() + m_y)/2, (_p.getR() + m_r)/2);
	}

	/**
	 * 点の間の長さLを返す.
	 * @param _p
	 * @return
	 */
	public double getL(FuzzyPoint _p){
		double x = Math.abs(_p.getX() - m_x);
		double y = Math.abs(_p.getY() - m_y);

		return Math.sqrt(x*x + y*y); // 三平方の定理
	}

	/**
	 * 二点間の角度を返す.
	 * @param _p
	 * @return
	 */
	public double getRadian(FuzzyPoint _p){
		double radian = Math.atan2(_p.getY() - m_y, _p.getX() - m_x);
		return normalizeAngle(radian);
	}

	private FuzzyPoint(Double _x, Double _y, Double _r){
		m_x = _x;
		m_y = _y;
		m_r = _r;
	}

	private double normalizeAngle(double _theta){
		if(_theta > Math.PI){
			return _theta - Math.PI;
		}else if(_theta < 0){
			return _theta + Math.PI;
		}else{
			return _theta;
		}
	}

	private final Double m_x;
	private final Double m_y;
	private final Double m_r;
}