package jp.sagalab;
import java.util.*;

public class Vote {

  final private FuzzyPoint m_point1;
  final private FuzzyPoint m_point2;

  public static final int NUM_OF_DIVISION_RHO = 1600;
  public static final int NUM_OF_DIVISION_THETA = 360;

  public static final double R = Math.sqrt(Math.pow(800,2) + Math.pow(800,2));

  public Vote(FuzzyPoint _p1, FuzzyPoint _p2){
    m_point1 = _p1;
    m_point2 = _p2;
  }

  public static Vote create(FuzzyPoint _p1, FuzzyPoint _p2){
    return new Vote(_p1,_p2);
  }


//  public double[] voteGradeTheta(){
//    double[] vote;
//    vote = new double[NUM_OF_DIVISION_THETA];
//    Pair p = Pair.create(m_point1,m_point2);
//
//    for(int i = 0; i < NUM_OF_DIVISION_THETA; i++){
//      vote[i] = p.getGradeOfAngle3(getBeginTheta(i), getEndTheta(i));
//    }
//
//    return vote;
//  }

//  public double[] voteGradeRho(){
//    double[] vote;
//    vote = new double[NUM_OF_DIVISION_RHO];
//    Pair p = Pair.create(m_point1,m_point2);
//
//    for(int i= -NUM_OF_DIVISION_RHO/2; i<NUM_OF_DIVISION_RHO/2; i++){
//      vote[i + NUM_OF_DIVISION_RHO/2] = p.getGradeOfDistance(getBeginRho(i), getEndRho(i));
//    }
//
//    return vote;
//  }

  public double[][] voteGrade(){
    double[][] vote;
    vote = new double[NUM_OF_DIVISION_THETA][NUM_OF_DIVISION_RHO];
    Pair p = Pair.create(m_point1,m_point2);

    for(int i=0; i<NUM_OF_DIVISION_THETA; i++){
      for(int j= -NUM_OF_DIVISION_RHO/2; j<NUM_OF_DIVISION_RHO/2; j++){
        vote[i][j + NUM_OF_DIVISION_RHO/2] = Math.min(p.getGradeOfAngle3(getBeginTheta(i), getEndTheta(i)),p.getGradeOfDistance(i * 2 * Math.PI / Vote.NUM_OF_DIVISION_THETA,getBeginRho(i), getEndRho(i)));
      }
    }
    return vote;
  }


	// グレード0以上のビンを返すメソッド. (リストで返す)
  public List<Bin> getSymmetricBin(){
    List<Bin> bin = new ArrayList<>();
    double grade;
    double[][] vote = voteGrade();

    for(int i=0; i<NUM_OF_DIVISION_THETA; i++){
      for(int j=-NUM_OF_DIVISION_RHO/2; j<NUM_OF_DIVISION_RHO/2; j++){
        grade = vote[i][j + NUM_OF_DIVISION_RHO/2];
        if(grade > 0){
          bin.add(Bin.create(i,j,grade));
        }
      }
    }
    return bin;
  }

  public static Bin getMostSymmetricBin(List<Bin> bin){
    Bin mostSymmetricBin = bin.get(0);

    for(int i = 1; i < bin.size() - 1; i++){
      if(mostSymmetricBin.getGrade() < bin.get(i).getGrade()){
        mostSymmetricBin = bin.get(i);
      }
    }
    return mostSymmetricBin;
  }

  private double normalizeAngle2(double _theta){
    if(_theta > 2 * Math.PI){
      return _theta - 2 * Math.PI;
    }else if(_theta < 0){
      return _theta + 2 * Math.PI;
    }else{
      return _theta;
    }
  }

  public double getBeginTheta(int _num){
    double span = 2 * Math.PI / Vote.NUM_OF_DIVISION_THETA;
    return _num * span - span / 2;
  }

  public double getEndTheta(int _num){
    double span = 2 * Math.PI / Vote.NUM_OF_DIVISION_THETA;
    return _num * span + span / 2;
  }

  public double getBeginRho(int _num){
    double span = Vote.R / Vote.NUM_OF_DIVISION_RHO;
    return _num * span - span / 2;
  }

  public double getEndRho(int _num){
    double span = Vote.R / Vote.NUM_OF_DIVISION_RHO;
    return _num * span + span / 2;
  }

}
