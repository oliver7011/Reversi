import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
public class drawBoard extends JPanel {    
    private int size;
    private int square_size;
    private int[][] grid;
    private int player;
    public JPanel panel;
    public drawBoard(int size, int[][] grid) {
        this.size = size;
        this.grid = grid;
        panel = new JPanel();
        //Intial grid formation
        grid = new int[size+1][size+1];

        for (int a=0;a<grid.length;a++) {
            for (int b=0;b<grid[a].length;b++) {
                grid[a][b] = 0;
            }
        }

        square_size = 60;

    } 
    //Function to refresh just the board
    public void refresh(int grid[][]) {
        this.grid = grid;
        this.repaint();
        this.revalidate();
    }
    @Override
    
    //Function to paint the grid and the pieces
    //Depending on the required size of the grid
    //And the defined size of the squares
    public void paint(Graphics g){
        super.paint(g);
        int startx = 0;
        int starty = 0;
        for ( int x = square_size; x <= square_size*size; x += square_size ) {
            for ( int y = square_size; y <= square_size*size; y += square_size ) { 
                g.drawRect( x, y, square_size, square_size );
                
            }
        }
        
        //Black pieces are filled ovals and white pieces are regular ovals
        for (int x=1;x<grid.length;x++) {
            for (int y=1;y<grid.length;y++) {
                if (grid[x][y] == 2) {
                    g.drawOval(x*square_size,y*square_size,square_size,square_size);
                } else if (grid[x][y] == 1) {
                    g.fillOval(x*square_size,y*square_size,square_size,square_size);
                }
            }
        }
    }
}

