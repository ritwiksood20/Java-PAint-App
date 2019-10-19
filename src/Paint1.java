import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.JSlider.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeEvent.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeListener.*;
import javax.swing.plaf.SliderUI;
import  java.awt.RenderingHints.*;
import java.text.DecimalFormat.*;
import static java.awt.RenderingHints.*;




public class Paint1 extends JFrame{
    JButton brushbutton,linebut,ellipsebut,rectbut,strokebut,fillbut;
    int currentaction=1;
    float transparentval=1.0f;
    JSlider transSlider;
    JLabel translabel;
    DecimalFormat dec=new DecimalFormat("##.##");
    Graphics2D graphSettings;
    Color strokecolor=Color.BLACK, fillColor=Color.BLACK;
    public static void  main(String[] args){
        new Paint1();
    }
    public Paint1(){
        // Define the defaults for the JFrame

        this.setSize(800,550);
        this.setTitle("Just Paint");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonpanel=new JPanel();
        // Swing box that will hold all the buttons

        Box thebox=Box.createHorizontalBox();
      JButton  brushbutton,linebut,ellipsebut,rectbut,strokebut,fillbut;
        brushbutton=makemebuttons("/home/ritwiksood/Java Paint APp/src/sample/Brush.png",1);
        brushbutton.setSize(10,10);
        linebut=makemebuttons("/home/ritwiksood/Java Paint APp/src/sample/Line.png",2);
        ellipsebut=makemebuttons("/home/ritwiksood/Java Paint APp/src/sample/Ellipse.png",3);
        rectbut=makemebuttons("/home/ritwiksood/Java Paint APp/src/sample/Rectangle.png",4);
        strokebut=makemeColorbuttons("/home/ritwiksood/Java Paint APp/src/sample/Stroke.png",5,true);
        fillbut=makemeColorbuttons("/home/ritwiksood/Java Paint APp/src/sample/Fill.png",6,false);
        linebut.setSize(1,1);
        ellipsebut.setSize(1,1);
        rectbut.setSize(1,1);
        strokebut.setSize(1,1);
        fillbut.setSize(1,1);
        thebox.add(brushbutton);
        thebox.add(linebut);
        thebox.add(ellipsebut);
        thebox.add(rectbut);
        thebox.add(strokebut);
        thebox.add(fillbut);
        buttonpanel.add(thebox);
        translabel=new JLabel("Transparenncy :");
        transSlider=new JSlider(10,100,99);
        ListenforSlider lForslider = new ListenforSlider();

        transSlider.addChangeListener(lForslider);
        thebox.add(translabel);
        thebox.add(transSlider);


        this.add(buttonpanel,BorderLayout.SOUTH);
        this.add(new DrawingBoard(),BorderLayout.CENTER);
        this.setVisible(true);



    }
    public JButton makemebuttons(String iconfile,final int actionNum){
        JButton theBut=new JButton();
        Icon butICon=new ImageIcon(iconfile);
        theBut.setIcon(butICon);
        theBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               currentaction=actionNum;
            }
        });
        return theBut;



    }
    public JButton makemeColorbuttons(String iconfile,final int actionNum,final boolean stroke){
        JButton theBut=new JButton();
        Icon butICon=new ImageIcon(iconfile);
        theBut.setIcon(butICon);
        theBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stroke){
                    // JColorChooser is a popup that lets you pick a color

                    strokecolor=JColorChooser.showDialog(null,"Pick a Color", Color.BLACK);
                }
                else{
                    strokecolor=JColorChooser.showDialog(null,"Pick a Color",Color.BLACK);
                }

            }
        });
        return theBut;



    }
    private class DrawingBoard extends JComponent{
        // ArrayLists that contain each shape drawn along with
        // that shapes stroke and fill

        ArrayList<Shape> shapes=new ArrayList<>();
        ArrayList<Color> shapesfill=new ArrayList<>();
        ArrayList<Color> shapesstroke=new ArrayList<>();
        ArrayList<Float> transpercent=new ArrayList<>();

        Point drawStart,drawEnd;
        public DrawingBoard(){
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e){
                    if(currentaction!=1) {

                        drawStart = new Point(e.getX(), e.getY());
                        drawEnd = drawStart;
                        repaint();
                    }
                }
                public void mouseReleased(MouseEvent e){
                    Shape ashape = null;
                    if(currentaction!=1) {
                         ashape=null;
                        if(currentaction==2){
                            ashape=drawline(drawStart.x,drawStart.y,e.getX(),e.getY());


                        }
                        else if(currentaction==3){
                            ashape=drawellipse(drawStart.x,drawStart.y,e.getX(),e.getY());
                        }
                        else if(currentaction==4){
                             ashape = drawRectangle(drawStart.x, drawStart.y, e.getX(), e.getY());
                        }
                    }



                        shapes.add(ashape);
                        shapesfill.add(fillColor);
                        shapesstroke.add(strokecolor);
                        transpercent.add(transparentval);
                        drawEnd = null;
                        drawStart = null;
                        repaint();


                }
            }); //END OF MOUSE LISTENER
            this.addMouseListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if(currentaction==1){
                        int x=e.getX();
                        int y=e.getY();
                        Shape ashape=null;
                        strokecolor=fillColor;
                        ashape=drawbrush(x,y,5,5);
                        shapes.add(ashape);
                        shapesfill.add(fillColor);
                        shapesstroke.add(strokecolor);
                        transpercent.add(transparentval);

                    }
                    drawEnd = new Point(e.getX(), e.getY());
                    repaint();
                }
            });   //END OF MOTIONLISTENER

        }

        private void addMouseListener(MouseMotionAdapter mouseMotionAdapter) {
        }

        public void paint(Graphics g){
            Graphics2D graphSettings=(Graphics2D)g;
            graphSettings.setStroke(new BasicStroke(2));
            Iterator<Color> strokecounters=shapesstroke.iterator();
            Iterator<Color> fillcounter = shapesstroke.iterator();
            Iterator<Float> transCounter =transpercent.iterator();


            for(Shape s:shapes){
                graphSettings.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
                graphSettings.setPaint(strokecounters.next());
                graphSettings.draw(s);
                graphSettings.setPaint(fillcounter.next());
                graphSettings.fill(s);
            }
            if(drawStart!=null && drawEnd!=null){

                graphSettings.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,3.0F));
                graphSettings.setPaint(Color.GRAY);
                Shape ashape=null;
                if(currentaction==2){
                    ashape=drawline(drawStart.x,drawStart.y,drawEnd.x,drawEnd.y);

                }
                else if(currentaction==3){
                    ashape=drawellipse(drawStart.x,drawStart.y,drawEnd.x,drawEnd.y);


                }
                else if(currentaction==4){
                    ashape=drawRectangle(drawStart.x,drawStart.y,drawEnd.x,drawEnd.y);

                }
                graphSettings.draw(ashape);


            }




     }
     private Rectangle2D.Float drawRectangle(int x1,int y1,int x2,int y2){
            int x=Math.min(x1,x2);
            int y=Math.min(y1,y2);
            int width=Math.abs(x1-x2);
            int height=Math.abs(y1-y2);
            return new Rectangle2D.Float(x,y,width,height);
     }
     private Ellipse2D.Float drawellipse(int x1,int x2,int y1,int y2){
         int x=Math.min(x1,x2);
         int y=Math.min(y1,y2);
         int width=Math.abs(x1-x2);
         int height=Math.abs(y1-y2);
         return new Ellipse2D.Float(x,y,width,height);
     }
        private Line2D.Float drawline(int x1,int x2,int y1,int y2){
            int x=Math.min(x1,x2);
            int y=Math.min(y1,y2);
            int width=Math.abs(x1-x2);
            int height=Math.abs(y1-y2);
            return new Line2D.Float(x,y,width,height);
        }
        private Ellipse2D.Float drawbrush(int x1,int y1,int brusstrokewidth,int brushstrokeheight){

            return new Ellipse2D.Float(x1,y1,brusstrokewidth,brushstrokeheight);
        }
    }
    private class ListenforSlider implements ChangeListener{
        public void stateChanged(ChangeEvent e){
            if(e.getSource()==transSlider){
                translabel.setText("Transparent : "+dec.format(transSlider.getValue()*0.01) );
                transparentval=(float)(transSlider.getValue()*0.01);

            }

        }
    }

}
