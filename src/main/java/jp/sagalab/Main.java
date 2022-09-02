package jp.sagalab;
import javax.swing.*;


public class Main extends JFrame{

    public static  void main(String[] args){
        new Main();
    }

    private Main(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyJPanel myJPanel = MyJPanel.create(800,800);
        JPanel panel = new JPanel();
        panel.add(myJPanel);
        getContentPane().add(panel);
        setTitle("Axis_of_symmetry_extraction");
        pack();
        setVisible(true);
    }


}
