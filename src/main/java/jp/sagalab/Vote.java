package jp.sagalab;
import java.util.*;

public class Vote {

  final private FuzzyPoint m_point1;
  final private FuzzyPoint m_point2;

  private final int NUM_OF_DIVISION_RHO = 1600;
  private final int NUM_OF_DIVISION_THETA = 360;

  final private double R = Math.sqrt(Math.pow(800,2) + Math.pow(800,2));

  public Vote(FuzzyPoint _p1, FuzzyPoint _p2){
    m_point1 = _p1;
    m_point2 = _p2;
  }

  public static Vote create(FuzzyPoint _p1, FuzzyPoint _p2){
    return new Vote(_p1,_p2);
  }


	// ファジィ角度とファジィ距離のうち小さい方を投票する. (角度のメンバシップ関数, 距離のメンバシップ関数)
  public double[][] voteGrade(){
    double[][] vote;
    vote = new double[NUM_OF_DIVISION_THETA][NUM_OF_DIVISION_RHO];
    Pair p = Pair.create(m_point1,m_point2);

    for(int i=0; i<NUM_OF_DIVISION_THETA; i++){

      for(int j= -NUM_OF_DIVISION_RHO/2; j<NUM_OF_DIVISION_RHO/2; j++){
        vote[i][j + NUM_OF_DIVISION_RHO/2] = Math.min(p.getGradeOfAngle3(i * 2 * Math.PI / NUM_OF_DIVISION_THETA),p.getGradeOfDistance(i * 2 * Math.PI / NUM_OF_DIVISION_THETA,j * R / (NUM_OF_DIVISION_RHO/2.0)));
      }
    }
    return vote;
  }

  public double[] voteGradeTheta(){
    double[] vote;
    vote = new double[NUM_OF_DIVISION_THETA];
    Pair p = Pair.create(m_point1,m_point2);

    //binの始点と終点のグレードを比較し、大きい方をbinのグレードとする。
    //binの内部にグレード最高の地点が含まれる場合は、binのグレードを1.0とする。
    for(int i=0; i<NUM_OF_DIVISION_THETA; i++){
      if(p.getGradeOfAngle3(getBeginTheta(i)) > p.getGradeOfAngle3(getEndTheta(i))) {
        if (p.getGradeOfAngle3(getBeginTheta(i + 1)) > p.getGradeOfAngle3(getBeginTheta(i))) {
          vote[i] = 1.0;
        }
      }else{
        if (p.getGradeOfAngle3(getBeginTheta(i - 1)) > p.getGradeOfAngle3(getBeginTheta(i))){
          vote[i] = 1.0;
        }
      }
    }
    return vote;
  }

	// その地点の投票結果を返すメソッド. (グレードで返す)
  public double getGrade(int _thetaNum, int _rhoNum){
    double[][] vote = voteGrade();
    return vote[_thetaNum][_rhoNum];
  }

	// グレード0以上の軸を返すメソッド. (リストで返す)
  public List<Axis> getSymmetricAxis(){
    List<Axis> axis = new ArrayList<>();
    double grade;
    double[][] vote = voteGrade();

    for(int i=0; i<NUM_OF_DIVISION_THETA; i++){
      for(int j=-NUM_OF_DIVISION_RHO/2; j<NUM_OF_DIVISION_RHO/2; j++){
        grade = vote[i][j + NUM_OF_DIVISION_RHO/2];
        if(grade > 0){
          axis.add(Axis.create(j * R / (NUM_OF_DIVISION_RHO/2.0),i * 2 * Math.PI / NUM_OF_DIVISION_THETA,grade));
        }
      }
    }
    return axis;
  }

  public static Axis getMostSymmetricAxis(List<Axis> axis){
    Axis mostSymmetricAxis = axis.get(0);

    for(int i = 1; i < axis.size() - 1; i++){
      if(mostSymmetricAxis.getGrade() < axis.get(i).getGrade()){
        mostSymmetricAxis = axis.get(i);
      }
    }
    return mostSymmetricAxis;
  }

  public double getBeginTheta(int _num){
    double span = 2 * Math.PI / NUM_OF_DIVISION_THETA;
    return _num * span - span / 2;
  }

  public double getEndTheta(int _num){
    double span = 2 * Math.PI / NUM_OF_DIVISION_THETA;
    return _num * span + span / 2;
  }

  public double getBeginRho(int _num){
    double span = R / NUM_OF_DIVISION_RHO;
    return _num * span - span / 2;
  }

  public double getEndRho(int _num){
    double span = R / NUM_OF_DIVISION_RHO;
    return _num * span + span / 2;
  }

}
