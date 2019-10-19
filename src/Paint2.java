import javax.swing.*;

import java.awt.*;
import  java.awt.geom.*;
import java.util.*;
import java.text.DecimalFormat;
import java.awt.Graphics2D;
import java.awt.Graphics;

@SuppressWarnings("serial")


public class Paint2 extends JFrame {
    JButton brushbutton,linebut,ellipsebut,rectbut,strokebut,fillbut;
    JSlider transSlider;
    JLabel translabel;
    DecimalFormat dec=new DecimalFormat("#.##");
    Graphics2D graphSettings;

    int currentaction=1;

    public static void main(String [] args){
       new  Paint2();
    }
    public Paint2(){
        this.setSize(1050,550);

    }

}
