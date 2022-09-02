package jp.sagalab;

public class Pair {

  final private FuzzyPoint m_point1;
  final private FuzzyPoint m_point2;

  public Pair(FuzzyPoint _p1, FuzzyPoint _p2){
    m_point1 = _p1;
    m_point2 = _p2;
  }

  public static Pair create(FuzzyPoint _p1, FuzzyPoint _p2){
    return new Pair(_p1,_p2);
  }

  /**
   * θ,ρ地点での対象軸の距離のグレードを返す.
   * @return grade
   */
  public double getGradeOfDistance(double _theta, double _rho){
    double grade,left,right;
    FuzzyPoint m = m_point1.midPoint(m_point2);

    left = (1 / m.getR() * _rho) + 1 - 1 * ((m.getX() * Math.cos(_theta) + m.getY() * Math.sin(_theta)) / m.getR());
    right = (-1 / m.getR() * _rho) + 1 + ((m.getX() * Math.cos(_theta) + m.getY() * Math.sin(_theta)) / m.getR());


    if (left > right) {
      grade = right;
    } else {
      grade = left;
    }

    if(grade < 0){
      return 0;
    }
    return grade;
  }

  /**
   * θ,ρ地点での対象軸の角度のグレードを返す.
   * @param _theta
   * @return
   */
  public double getGradeOfAngle(double _theta){
    double left,right;

    left = 2 * m_point1.getL(m_point2) / (Math.PI * (m_point1.getR() + m_point2.getR())) * _theta + (Math.PI * (m_point1.getR() + m_point2.getR()) - 2 * m_point1.getL(m_point2) * m_point1.getRadian(m_point2)) / (Math.PI * (m_point1.getR() + m_point2.getR()));
    right = -2 * m_point1.getL(m_point2) / (Math.PI * (m_point1.getR() + m_point2.getR())) * _theta + (Math.PI * (m_point1.getR() + m_point2.getR()) + 2 * m_point1.getL(m_point2) * m_point1.getRadian(m_point2)) / (Math.PI * (m_point1.getR() + m_point2.getR()));

    return Math.min(left,right);

  }

  /**
   * θ,ρ地点での対象軸の角度のグレードを,π平行移動したものとの論理和を取ったものを返す.
   * @param _theta
   * @return
   */
  public double getGradeOfAngle2(double _theta) {
    double grade1 = getGradeOfAngle(_theta);
    double grade2 = getGradeOfAngle(_theta - Math.PI);
    double grade3 = (m_point1.getR() + m_point2.getR() - m_point1.getL(m_point2)) / (m_point1.getR() + m_point2.getR());

    double grade;
    grade = Math.max(grade1, grade2);
    grade = Math.max(grade, grade3);

    if (grade < 0) {
      return 0;
    } else {
      return grade;
    }
  }

  /**
   * θ,ρ地点での対象軸の角度のグレードを 0 <= θ < 2π の範囲に正規化したものを返す.
   * @param _theta
   * @return
   */
  public double getGradeOfAngle3(double _theta){
    double grade1 = getGradeOfAngle2(_theta - 2 * Math.PI);
    double grade2 = getGradeOfAngle2(_theta);
    double grade3 = getGradeOfAngle2(_theta + 2 * Math.PI);

    double grade;
    grade = Math.max(grade1, grade2);
    grade = Math.max(grade, grade3);

    return grade;
  }



}