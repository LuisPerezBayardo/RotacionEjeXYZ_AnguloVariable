package Rotacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;


public class RotacionXYZ extends JFrame implements ActionListener, MouseListener{

    
    final private BufferedImage buffer, image;
    private Image dbImage;
    private Graphics dbg;
    JLabel lbl=new JLabel();
    JTextArea TA_angulo=new JTextArea();
    JButton restart=new JButton(), rotar=new JButton();
    JCheckBox rotacionX=new JCheckBox(), rotacionY=new JCheckBox(), rotacionZ=new JCheckBox();
    final int LARGO=200, ALTO=100, ANCHO=100;
//    private final int X_INICIO_X0=250;
//    private final int Y_INICIO_Y0=500;
//    private final int Z_INICIO_Z0=0;
    private final int X_INICIO_X0=500-(LARGO/2);
    private final int Y_INICIO_Y0=400-(ALTO/2);
    private final int Z_INICIO_Z0=0;
    int destinoX=X_INICIO_X0, destinoY=Y_INICIO_Y0, destinoZ=Z_INICIO_Z0;
    static int[] P=new int[6]; //x0,y0,z0,x1,y1,z1
    boolean trasladar=false, recursividad=false;
    int xCoordinate=X_INICIO_X0, yCoordinate=Y_INICIO_Y0, zCoordinate=Z_INICIO_Z0;
    private final int X_CENTRO=500;
    private final int Y_CENTRO=400;
    double angulo=0.01;
    private final int COUNTER=10000;
    int counter=COUNTER;
    
   public RotacionXYZ(){
       restart.setBounds(50, 100, 130, 25);
       restart.setText("Restart");
       restart.setOpaque(true);
       restart.setVisible(true);
       restart.addActionListener(this);
       this.add(restart);
       rotar.setBounds(50, 200, 130, 25);
       rotar.setText("Rotar");
       rotar.setOpaque(true);
       rotar.setVisible(true);
       rotar.addActionListener(this);
       this.add(rotar);
       rotacionX.setBounds(500, 600, 100, 25);
       rotacionX.setText("Rotación X");
       rotacionX.setOpaque(true);
       rotacionX.setVisible(true);
       rotacionX.setSelected(true);
       this.add(rotacionX);
       rotacionY.setBounds(630, 600, 100, 25);
       rotacionY.setText("Rotación Y");
       rotacionY.setOpaque(true);
       rotacionY.setVisible(true);
       rotacionY.setSelected(true);
       this.add(rotacionY);
       rotacionZ.setBounds(760, 600, 100, 25);
       rotacionZ.setText("Rotación Z");
       rotacionZ.setOpaque(true);
       rotacionZ.setVisible(true);
       rotacionZ.setSelected(true);
       this.add(rotacionZ);
       lbl.setBounds(30, 480, 120, 20);
       lbl.setText("Ángulo");
       lbl.setOpaque(true);
       lbl.setVisible(true);
       this.add(lbl);
       TA_angulo=new JTextArea();
       TA_angulo.setVisible(true);
       TA_angulo.setOpaque(true);
       TA_angulo.setText(String.valueOf(angulo));
       TA_angulo.setBounds(30, 500, 130, 30);
       this.add(TA_angulo);
       buffer=new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
       image=new BufferedImage(X_CENTRO*2,Y_CENTRO*2,BufferedImage.TYPE_INT_RGB);
       addMouseListener(this);
       P[0]=destinoX; P[1]=destinoY; P[2]=destinoZ; P[3]=destinoX+200; P[4]=destinoY+100; P[5]=destinoZ+100;
       this.setBounds(0, 0, X_CENTRO*2, Y_CENTRO*2);
       this.setResizable(false);
       this.setLayout(null);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   
   
   public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable(){
                public void run(){
                    new RotacionXYZ().setVisible(true);
                }
        });
    }
   
   
   
   double mXY=1, mXZ=1, mYZ=1, dxPequeña=1, dyPequeña=1, dzPequeña=1;
   double[] T=new double[6];
   double[] PPrima=new double[6];
   
   @Override
    public void paint(Graphics g){
        g=image.getGraphics();
        super.paint(g);
        if(trasladar){
            if(!recursividad){
                for(int i=0; i<6; i++) PPrima[i]=(double)P[i];
                recursividad=true;
            }
            if(counter>0){
                if(rotacionX.isSelected()){
                    double x0=PPrima[0]-X_CENTRO, y0=PPrima[1]-Y_CENTRO, z0=PPrima[2],
                            x1=PPrima[3]-X_CENTRO, y1=PPrima[4]-Y_CENTRO, z1=PPrima[5];
                    PPrima[1]=y0*Math.cos(angulo)-z0*Math.sin(angulo)+Y_CENTRO;      // y0'
                    PPrima[4]=y1*Math.cos(angulo)-z1*Math.sin(angulo)+Y_CENTRO;      // y1'
                    PPrima[2]=y0*Math.sin(angulo)+z0*Math.cos(angulo);      // z0'
                    PPrima[5]=y1*Math.sin(angulo)+z1*Math.cos(angulo);      // z1'
                }
                if(rotacionY.isSelected()){
                    double x0=PPrima[0]-X_CENTRO, y0=PPrima[1]-Y_CENTRO, z0=PPrima[2],
                            x1=PPrima[3]-X_CENTRO, y1=PPrima[4]-Y_CENTRO, z1=PPrima[5];
                    PPrima[0]=x0*Math.cos(angulo)+z0*Math.sin(angulo)+X_CENTRO;      // x0'
                    PPrima[3]=x1*Math.cos(angulo)+z1*Math.sin(angulo)+X_CENTRO;      // x1'
                    PPrima[2]=-x0*Math.sin(angulo)+z0*Math.cos(angulo);     // z0'
                    PPrima[5]=-x1*Math.sin(angulo)+z1*Math.cos(angulo);     // z1'
                }
                if(rotacionZ.isSelected()){
                    double x0=PPrima[0]-X_CENTRO, y0=PPrima[1]-Y_CENTRO, z0=PPrima[2],
                            x1=PPrima[3]-X_CENTRO, y1=PPrima[4]-Y_CENTRO, z1=PPrima[5];
                    PPrima[0]=x0*Math.cos(angulo)-y0*Math.sin(angulo)+X_CENTRO;      // x0'
                    PPrima[3]=x1*Math.cos(angulo)-y1*Math.sin(angulo)+X_CENTRO;      // x1'
                    PPrima[1]=x0*Math.sin(angulo)+y0*Math.cos(angulo)+Y_CENTRO;      // y0'
                    PPrima[4]=x1*Math.sin(angulo)+y1*Math.cos(angulo)+Y_CENTRO;      // y1'
                }
                for(int i=0; i<6; i++) P[i]=(int)PPrima[i];
                rectangulo3D(P);
                //System.out.println(P[0] + ", " + P[1] + ", " + P[2] + "    ......    " + P[3] + ", " + P[4] + ", " + P[5]);
                counter--;
                repaint();
            }
//            if(!(xCoordinate==PPrima[0] || yCoordinate==PPrima[1] || zCoordinate==PPrima[2])){
//                for(int i=0; i<6; i++){
//                    PPrima[i]+=T[i];
//                    P[i]=(int)PPrima[i];
//                }
//                rectangulo3D(P);
//                repaint();
//            }
//            else{
//                for(int i=0; i<6; i++) P[i]=(int)PPrima[i];
//            }
        }
        else{
            rectangulo3D(P);
            trasladar=true;
        }
        this.getGraphics().drawImage(image, 0, 0, this);
    }
    
    
    
    public void rectangulo3D(int[] PP){   //x0,y0,z0,x1,y1,z1
        int x0=PP[0], y0=PP[1], z0=PP[2], x1=PP[3], y1=PP[4], z1=PP[5];
        if(x0>x1){
            int temp=x0;
            x0=x1;
            x1=temp;
        }
        if(y0>y1){
            int temp=y0;
            y0=y1;
            y1=temp;
        }
        if(z0>z1){
            int temp=z0;
            z0=z1;
            z1=temp;
        }
        int[][] figura=new int[8][2];
        figura[0]=calculo3Da2D(x0,y0,z0);
        figura[1]=calculo3Da2D(x1,y0,z0);
        figura[2]=calculo3Da2D(x0,y1,z0);
        figura[3]=calculo3Da2D(x1,y1,z0);
        figura[4]=calculo3Da2D(x0,y0,z1);
        figura[5]=calculo3Da2D(x1,y0,z1);
        figura[6]=calculo3Da2D(x0,y1,z1);
        figura[7]=calculo3Da2D(x1,y1,z1);
//        for(int i=0; i<=x1-x0; i++){
//            putPixel(x0+i, y0, Color.blue);
//            putPixel(x0+i, y1, Color.blue);
//        }
//        for(int i=0; i<=y1-y0; i++){
//            putPixel(x0, y0+i, Color.blue);
//            putPixel(x1, y0+i, Color.blue);
//        }
        drawLine(figura[0][0], figura[0][1], figura[1][0], figura[1][1], Color.red);
        drawLine(figura[1][0], figura[1][1], figura[3][0], figura[3][1], Color.red);
        drawLine(figura[0][0], figura[0][1], figura[2][0], figura[2][1], Color.red);
        drawLine(figura[2][0], figura[2][1], figura[3][0], figura[3][1], Color.red);
        drawLine(figura[0][0], figura[0][1], figura[4][0], figura[4][1], Color.green);
        drawLine(figura[1][0], figura[1][1], figura[5][0], figura[5][1], Color.green);
        drawLine(figura[2][0], figura[2][1], figura[6][0], figura[6][1], Color.green);
        drawLine(figura[3][0], figura[3][1], figura[7][0], figura[7][1], Color.green);
        drawLine(figura[4][0], figura[4][1], figura[5][0], figura[5][1], Color.blue);
        drawLine(figura[5][0], figura[5][1], figura[7][0], figura[7][1], Color.blue);
        drawLine(figura[4][0], figura[4][1], figura[6][0], figura[6][1], Color.blue);
        drawLine(figura[6][0], figura[6][1], figura[7][0], figura[7][1], Color.blue);
    }
    
    public int[] calculo3Da2D(double x_, double y_, double z_){
        int[] result=new int[2];
        if(z_==0 && (X_CENTRO-x_==0 || Y_CENTRO-y_==0)){
            result[0]=(int)x_;
            result[1]=(int)y_;
        }
        else{
            double signox,signoy,dx2,dy2;
            signox=1;
            if(X_CENTRO>x_)signox=-1;
            signoy=1;
            if(Y_CENTRO>y_)signoy=-1;
            dx2=(x_-((double)X_CENTRO))*(x_-((double)X_CENTRO));
            dy2=(y_-((double)Y_CENTRO))*(y_-((double)Y_CENTRO));
            result[0]=(int)( X_CENTRO + (   signox*dx2   /   (Math.sqrt(dx2+(z_*z_)))) );
            result[1]=(int)( Y_CENTRO + (   signoy*dy2   /   (Math.sqrt(dy2+(z_*z_)))) );
        }
//        else{
//            result[0]=(int)x_;
//            result[1]=(int)y_;
//        }
//        else{
//            result[0]=(int)x_+(int)z_/10;
//            result[1]=(int)y_-(int)z_/10;
//        }
        return result;
    }
    
    
    
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        image.getGraphics().drawImage(buffer, x, y, this);
    }
    
    

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==restart){
            P[0]=destinoX; P[1]=destinoY; P[2]=destinoZ; P[3]=destinoX+200; P[4]=destinoY+100; P[5]=destinoZ+100;
            trasladar=false;
            recursividad=false;
            counter=COUNTER;
            repaint();
        }
        else if(e.getSource()==rotar){
            try{
                angulo=Double.valueOf(TA_angulo.getText());
            }catch(NumberFormatException ex){TA_angulo.setText("Error");}
            trasladar=true;
            repaint();
        }
    }
    
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        xCoordinate=e.getX();
        yCoordinate=e.getY();
//        try{
//            zCoordinate=Integer.valueOf(TA_zCoordenada.getText());
//        }catch(NumberFormatException ex){TA_zCoordenada.setText("Error");}
        recursividad=false;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
    
    
    
    
   
    
    
    private void drawLine(int x0, int y0, int x1, int y1, Color c) {
        double dx, dy, xinc, yinc, x, y;
        int pasos;
        dx = x1 - x0;
        dy = y1 - y0;
        if (Math.abs(dx) > Math.abs(dy)) pasos = (int) Math.abs(dx);
        else pasos = (int) Math.abs(dy);
        xinc = dx / pasos;
        yinc = dy / pasos;
        x = x0;
        y = y0;
        for (int i = 1; i <= pasos; i++) {
            x = x + xinc;
            y = y + yinc;
            putPixel((int) Math.round(x), (int) Math.round(y), c);
        }
    }
    
    
}
