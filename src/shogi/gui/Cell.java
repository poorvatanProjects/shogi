package shogi.gui;


import shogi.board.GameBoard;
import shogi.board.Position;
import shogi.piece.ChessMen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.util.ArrayList;

/**
 * @author sina
 * @version 1.0
 * Created by sina on 4/5/16.
 */
public class Cell  extends  JPanel  {
    private int width = 70;

    public Position getPosition() {
        return position;
    }

    private Position position;

    private Color wrongColor = new Color(255,0,0);
    private Color correctColor = new Color(0,255,0);
    private Color emptyColorWhite = new Color(255,255,255);
    private Color getEmptyColorBlack =  new Color(0,0,0);
    private GameBoard gameBoard ;
    private ChessMen[][] table;
    private Character  gettingSignOfChessMen(){
    Character ch ;
    if( table[position.getCol()][position.getRow()].getClass().getSimpleName().compareTo("SilverGeneral")==0){
        ch = 's';
        return ch;
    }
        else if (table[position.getCol()][position.getRow()].getClass().getSimpleName().compareTo("Knight")==0 ) {
        ch = 'N';
        return ch;
    }
        else {
            ch = table[position.getCol()][position.getRow()].getClass().getSimpleName().charAt(0);
        }
            return ch;
        }


    @Override
    public int getWidth() {

        return width;
    }

    public Cell(Position pos, GameBoard gm) {
        this.position = pos;
        this.gameBoard = gm;
        this.table = gameBoard.getTable();
        this.setLocation(this.position.getRow()*this.width, (this.position.getCol()*this.width));
        this.setSize(70,70);
        System.out.println(this.getSize());
        if((position.getRow()+position.getCol())%2 ==0){
            System.out.println("here1");

               this.setBackground(new Color(139,69,19));
         //   this.set
        }
        else {
           this.setBackground(new Color(210,180,140));
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
//        if((position.getRow()+position.getCol())%2 ==0){
//            g.setColor(new Color(139,69,19));
//        }
//        else {
//            g.setColor(new Color(210,180,140));
//        }
//        this.setLocation(this.position.getRow()*this.width, (this.position.getCol()*this.width));
//        this.setSize(this.width, this.width);
//      //  g.fillRoundRect(0,0,this.width,this.width,4 ,4);
        if( table[position.getCol()][position.getRow()]!=null) {

            if(table[position.getCol()][position.getRow()].getPlayerRole()== ChessMen.roles.PLAYER_WHITE_ROLE ){
                g.setColor(Color.white);
                int []x = new int [5];
                int []y= new int [5];

                x[0]=35;
                y[0] = 5;
                x[1]=60;
                y[1] = 20;
                x[2]=60;
                y[2] = 60;
                x[3]=10;
                y[3] =60;
                x[4]=10;
                y[4] = 20;

                g.fillPolygon(x,y,5);
                g.setColor(Color.BLACK);
                int style = Font.BOLD |  Font.PLAIN;
                Font font = new Font("TimesRoman", style, 20);
                g.setFont(font);
                g.drawString(this.gettingSignOfChessMen().toString().toUpperCase(),30,36);



            }
            else if ( table[position.getCol()][position.getRow()].getPlayerRole() == ChessMen.roles.PLAYER_BLACK_ROLE)
                {
                    g.setColor(Color.black);
                    int []x = new int [5];
                    int []y= new int [5];

                    x[0]=35;
                    y[0] = getHeight()-5;
                    x[1]=60;
                    y[1] = getHeight() - 20;
                    x[2]=60;
                    y[2] = getHeight() - 60;
                    x[3]=10;
                    y[3] = getHeight() -60;
                    x[4]=10;
                    y[4] =getHeight() - 20;
                    g.fillPolygon(x,y,5);

                    g.setColor(Color.white);
                    int style = Font.BOLD |  Font.PLAIN;
                    Font font = new Font("TimesRoman", style, 20);
                    g.setFont(font);
                    g.drawString(this.gettingSignOfChessMen().toString().toUpperCase(),30,36);


                }
           // g.fillOval(13, 13, this.width / 2, this.width / 2);
        }
      //  this.setLocation(this.position.getRow()*this.width+35, (this.position.getCol()*this.width+35));
//        g.fillRoundRect(35,35,this.width,this.width);
    }


}
