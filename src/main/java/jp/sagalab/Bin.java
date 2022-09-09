package jp.sagalab;

public class Bin {

    public Bin(int _thetaNum, int _rhoNum, double _grade){
        m_thetaNum = _thetaNum;
        m_rhoNum = _rhoNum;
        m_grade = _grade;
    }

    public static Bin create(int _thetaNum, int _rhoNum, double _grade){
        return new Bin(_thetaNum, _rhoNum, _grade);
    }

    public double getBeginTheta(){
        double span = 2 * Math.PI / Vote.NUM_OF_DIVISION_THETA;
        return m_thetaNum * span - span / 2;
    }

    public double getEndTheta(){
        double span = 2 * Math.PI / Vote.NUM_OF_DIVISION_THETA;
        return m_thetaNum * span + span / 2;
    }

    public double getBeginRho(){
        double span = Vote.R / Vote.NUM_OF_DIVISION_RHO;
        return m_rhoNum * span - span / 2;
    }

    public double getEndRho(){
        double span = Vote.R / Vote.NUM_OF_DIVISION_RHO;
        return m_rhoNum * span + span / 2;
    }

    public double getGrade(){
        return m_grade;
    }

    public double getThetaNum(){
        return m_thetaNum;
    }

    public double getRhoNum(){
        return m_rhoNum;
    }


    private int m_thetaNum;
    private int m_rhoNum;
    private double m_grade;

}
