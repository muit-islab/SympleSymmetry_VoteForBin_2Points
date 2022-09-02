package jp.sagalab;

public class Axis {

	public static Axis create(double _distance, double _degree, double _grade){
		return new Axis(_distance, _degree, _grade);
	}

	// 角度と距離を求める. (角度のメンバシップ関数を返すメソッドと距離のメンバシップ関数を返すメソッド)

	public double getGrade(){
		return m_grade;
	}

	public double getDistance(){
		return m_distance;
	}

	public double getDegree(){
		return m_degree;
	}
	public Axis(double _distance, double _degree, double _grade){
		m_distance = _distance;
		m_degree = _degree;
		m_grade = _grade;
	}

	private final double m_distance;
	private final double m_degree;
	private final double m_grade;
}
