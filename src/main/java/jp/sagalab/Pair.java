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

  public double getGradeOfDistanceLeft(double _theta, double _rho){
    FuzzyPoint m = m_point1.midPoint(m_point2);
    return (1 / m.getR() * _rho) + 1 - 1 * ((m.getX() * Math.cos(_theta) + m.getY() * Math.sin(_theta)) / m.getR());
  }

  public double getGradeOfDistanceRight(double _theta, double _rho){
    FuzzyPoint m = m_point1.midPoint(m_point2);
    return (-1 / m.getR() * _rho) + 1 + ((m.getX() * Math.cos(_theta) + m.getY() * Math.sin(_theta)) / m.getR());
  }


  /**
   * θ,ρ地点での対象軸の距離のグレードを返す.
   * @return grade
   */
  public double getGradeOfDistance(double _theta, double _left, double _right){
    double grade,gradeLeft,gradeRight;

    gradeLeft = Math.max(getGradeOfDistanceLeft(_theta,_left), getGradeOfDistanceLeft(_theta,_right));
    gradeRight = Math.max(getGradeOfDistanceRight(_theta,_left), getGradeOfDistanceRight(_theta,_right));
    grade = Math.min(gradeLeft,gradeRight);

    if(grade > 1.0){
      return 1.0;
    }else if(grade < 0){
      return 0;
    }else{
      return grade;
    }

  }



  public double getGradeOfAngleLeft(double _theta){
    double left = 2 * m_point1.getL(m_point2) / (Math.PI * (m_point1.getR() + m_point2.getR())) * _theta + (Math.PI * (m_point1.getR() + m_point2.getR()) - 2 * m_point1.getL(m_point2) * m_point1.getRadian(m_point2)) / (Math.PI * (m_point1.getR() + m_point2.getR()));
    return left;
  }

  public double getGradeOfAngleRight(double _theta){
    double right = -2 * m_point1.getL(m_point2) / (Math.PI * (m_point1.getR() + m_point2.getR())) * _theta + (Math.PI * (m_point1.getR() + m_point2.getR()) + 2 * m_point1.getL(m_point2) * m_point1.getRadian(m_point2)) / (Math.PI * (m_point1.getR() + m_point2.getR()));
    return right;
  }

  public double getGradeOfAngle(double _left, double _right){
    double gradeLeft = Math.max(getGradeOfAngleLeft(_left),getGradeOfAngleLeft(_right));
    double gradeRight = Math.max(getGradeOfAngleRight(_left),getGradeOfAngleRight(_right));
    double grade = Math.min(gradeLeft,gradeRight);

    if(grade > 1.0){
      return 1.0;
    }else if(grade < 0){
      return 0;
    }else{
      return grade;
    }

  }

  /**
   * θ,ρ地点での対象軸の角度のグレードを,π平行移動したものとの論理和を取ったものを返す.
   * @param _left , _right
   * @return
   */
  public double getGradeOfAngle2(double _left,double _right ) {
    double grade1 = getGradeOfAngle(_left, _right);
    double grade2 = getGradeOfAngle(_left - Math.PI, _right - Math.PI);
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
   * @param _left , _right
   * @return
   */
  public double getGradeOfAngle3(double _left, double _right){
    double grade1 = getGradeOfAngle2(_left - 2 * Math.PI, _right - 2 * Math.PI);
    double grade2 = getGradeOfAngle2(_left, _right);
    double grade3 = getGradeOfAngle2(_left + 2 * Math.PI, _right + 2 * Math.PI);

    double grade;
    grade = Math.max(grade1, grade2);
    grade = Math.max(grade, grade3);

    return grade;
  }



}