package shogi.gui;


import shogi.board.GameBoard;
import shogi.board.Position;
import shogi.piece.ChessMen;
import shogi.piece.Pawn;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * @author sina
 * @version 1.0
 *          Created by sina on 4/5/16.
 */
public class GameMap extends JPanel {
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    private GameBoard gameBoard;
    private ChessMen[][] table = new ChessMen[9][9];
    static ArrayList<Cell> cells = new ArrayList<>();
    private ChessMen chessManMover;
    private Position position;
    private IncomingChessMen whiteBase;
    private IncomingChessMen blacBase;
    private  int whiteSize =0 ;
    private int blackSize =0;
    Border bevelBorder = BorderFactory.createRaisedBevelBorder();
    Border lineBorder = LineBorder.createBlackLineBorder();

    public Position getPosition() {
        return position;
    }


    public GameMap(GameBoard gm, IncomingChessMen whiteBase, IncomingChessMen blackBase) {
        this.setLayout(null);
        this.setSize(630, 630);
        this.whiteBase = whiteBase;
        this.blacBase = blackBase;
        gameBoard = gm;
        this.setLocation((1000 - 640) / 2, 200);
        this.table = gameBoard.getTable();
        this.addMouseListener(new myMouselistener(this));
        this.mapInitializer();


    }

    int r, c;

    private void mapInitializer() {
        int i = 0;
        for (r = 0; r < 9; r++) {
            for (c = 0; c < 9; c++) {
                Cell a = new Cell(new Position(r, c), gameBoard);
                this.add(a);
                cells.add(a);

            }
        }
    }

    //TODO
    public void paintingCells(ArrayList<Position> positions) {
        for (int i = 0; i < positions.size(); i++) {
            int n = positions.get(i).getRow() * 9 + positions.get(i).getCol();
            Cell c = cells.get(n);
            c.setBackground(c.getCorrectColor());

        }
    }

    class myMouselistener implements MouseListener {
        private GameMap gameMap;

        public myMouselistener(GameMap gameMap) {
            this.gameMap = gameMap;
        }

        @Override

        public void mouseClicked(MouseEvent e) {
            boolean hasUpgrade = false;
            if (chessManMover == null && (whiteBase.getAddChesman() == null && blacBase.getAddChesman() == null)) {
                if (table[e.getY() / 70][e.getX() / 70] != null) {
                    if (gameBoard.canSelect(table[e.getY() / 70][e.getX() / 70].getPosition())) {
                        ChessMen chessMen = table[e.getY() / 70][e.getX() / 70];
                        if( gameBoard.mustUpgrade(chessMen)){
                            MyMessages myMessages = new MyMessages(2);
                            gameBoard.doUpgrade(chessMen.getPosition());
                            hasUpgrade = true;
                        }

                        if( gameBoard.canUpgrade(chessMen) && chessMen.getNormal() == true&& !hasUpgrade){
                            MyMessages myMessages = new MyMessages(1);
                            if( myMessages.getResult()== 0 ){
                                gameBoard.doUpgrade(chessMen.getPosition());
                                hasUpgrade = true;
                            }
                        }
                        if( !hasUpgrade) {
                            this.showAvailableMoves(e, chessMen);
                            int n = e.getY() / 70 * 9 + e.getX() / 70;
                            Cell c = cells.get(n);
                            c.setBackground(Color.ORANGE);
                        }else{
                            hasUpgrade = false;
                        }
                    }
                }
            } else if( chessManMover ==null && (whiteBase.getAddChesman()!= null | blacBase.getAddChesman() != null)){
                if( whiteBase.getAddChesman()!=null) {
                    if (gameBoard.canPut(new Position(e.getY() / 70, e.getX() / 70), whiteBase.getAddChesman())) {
                        gameBoard.puttingNewChessManInMap(new Position(e.getY() / 70, e.getX() / 70), whiteBase.getAddChesman());
                        int n = e.getY() / 70 * 9 + e.getX() / 70;
                        ChessMen ch;
                        Cell c = cells.get(n);
                        c.repaint();
                        whiteBase.setAddChesman(null);
                        int index = whiteBase.findingIndex(whiteBase.getTempE());
                        whiteBase.getPieces().remove(index);
                        whiteBase.repaint();

                    }
                }else  if( blacBase.getAddChesman() !=null){
                    gameBoard.puttingNewChessManInMap(new Position(e.getY()/70,e.getX()/70),blacBase.getAddChesman());
                    int n = e.getY() / 70 * 9 + e.getX() / 70;
                    ChessMen ch ;
                    Cell c = cells.get(n);
                    blacBase.setAddChesman(null);
                    c.repaint();
                    int index = blacBase.findingIndex(blacBase.getTempE());
                    blacBase.getPieces().remove(index);
                    blacBase.repaint();



                }
            }
            else {
                    this.chessManMove(e);

            }

        }


        @Override
        public void mousePressed(MouseEvent e) {
            for (Cell cell : cells) {
                if ((cell.getPosition().getCol() + cell.getPosition().getRow()) % 2 == 0)
                    cell.setBackground(cell.getEvenBackground());
                else cell.setBackground(cell.getOddBackgrand());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        private void showAvailableMoves(MouseEvent e , ChessMen chessMen) {
            {
                ArrayList<Position> positions = new ArrayList<>();
                chessManMover = chessMen;
                if (chessMen.getPlayerRole() == gameBoard.getTurn())
                   positions = chessMen.calculatingMoves();
              paintingCells(positions);
            }

        }

//        private void ChessManMove(MouseEvent e) {
//            System.out.println("Here1");
//            int flag = 0;
//            int n = e.getY() / 70 * 9 + e.getX() / 70;
//            Cell c = cells.get(n);
////
//            for (int i = 0; i < chessManMover.calculatingMoves().size(); i++) {
//                if (chessManMover.calculatingMoves().get(i).getCol() == c.getPosition().getCol() && chessManMover.calculatingMoves().get(i).getRow() == c.getPosition().getRow()) {
//                    flag = 1;
//                    break;
//                }
//
//            }
//            if (flag == 1) {
//                ChessMen tempChessMan = table[e.getY() / 70][e.getX() / 70];
//                if (tempChessMan != null) {
//                    if (tempChessMan.getPlayerRole() == ChessMen.roles.PLAYER_BLACK_ROLE)
//                        whiteBase.push(tempChessMan);
//                    else blacBase.push(tempChessMan);
//                }
//                table[e.getY() / 70][e.getX() / 70] = chessManMover;
//                c.addChessMan(chessManMover);
//                n = chessManMover.getPosition().getRow() * 9 + chessManMover.getPosition().getCol();
//                c = cells.get(n);
//                c.remove(chessManMover);
//                chessManMover.setPosition(new Position(e.getY() / 70, e.getX() / 70));
//
//                flag = 0;
//            }
//
//            chessManMover = null;
//
//
//        }
   // }
        private void chessManMove( MouseEvent e){
            int n = e.getY() / 70 * 9 + e.getX() / 70;
            ChessMen ch ;
            Cell c = cells.get(n);
            Position target = new Position(e.getY()/70, e.getX()/70);


            if ( gameBoard.canMove( chessManMover.getPosition(), target)){
                gameBoard.move(chessManMover.getPosition(), target);
                c.addChessMan(chessManMover);
                if ( gameBoard.getBlackKickedPieces().size() != blackSize){
                    blackSize++;
                    ch = gameBoard.getBlackKickedPieceIndex(gameBoard.getBlackKickedPieces().size()-1);
               //     gameBoard.setBlackKickedPiceIndex(ch);
                        whiteBase.push(ch);


//  TODO  :
//  TODO  :gameBoard.getWhiteKickedPieces().size() !=0
                }
                if( gameBoard.getWhiteKickedPieces().size() != whiteSize){
                    whiteSize++;
                    ch = gameBoard.getWhiteKickedPieceIndex(gameBoard.getWhiteKickedPieces().size()-1);
               //     gameBoard.setWhiteKickedPieceIndex(ch);
                        blacBase.push(ch);


                }

                    n = chessManMover.getPosition().getRow() * 9 + chessManMover.getPosition().getCol();
                c = cells.get(n);
                c.remove(chessManMover);
                //chessManMover.setPosition(new Position(e.getY() / 70, e.getX() / 70));


            }
            chessManMover = null;

        }
    }
//    1. handleing "kish" in calculating canGO ( for showing green places)
    // 2. bad az kish shodan ...

}

