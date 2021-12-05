import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
public class Reversi extends JFrame {
    
    private int size,square_size,player,wCount=0,bCount=0,wpass=0,bpass=0;
    private int[][] grid;
    private drawBoard board;  
    private JFrame frame;
    private JLabel playerturn,bcount,wcount,valid;
    private int[][] newgrid;
    private MouseListener listener;
    public String player1name="Player1",player2name="Player2";
    private JTextField field1,field2, newgamesizefield;
    private JLabel p1name,p2name,newgamesize;
    private JButton startnewgame,createnewgame;
    public static void main(String[] args) {
        Reversi newGame = new Reversi(8);
    }
    
    private int getPlayer() {
        return player;
    }
    private void setPlayer(int newPlayer) {
        player = newPlayer;
    }
    private int[][] getGrid() {
        return grid;
    }
    private int getsize() {
        return size;
    }
    
    
    public Reversi(int size) {
        this.size = size;
        //Creating the jframe and setting the layout
        frame = new JFrame();        
        frame.setLayout(new BorderLayout());
        //Intialising the grid using the given size
        grid = new int[size+1][size+1];
        
        //1 is black, 2 is white, 0 is blank
        
        //Making every element of the grid a 0
        for (int a=0;a<grid.length;a++) {
            for (int b=0;b<grid[a].length;b++) {
                grid[a][b] = 0;
            }
        }
        //Defining the starting pieces (4)
        grid[size/2][size/2] = 1;
        grid[size/2+1][size/2+1] = 1;
        grid[size/2+1][size/2] = 2;
        grid[size/2][size/2+1] = 2;
        newgrid=grid;
        //Drawing the board by creating a drawBoard object then adding it to the jframe
        board = new drawBoard(size,grid);
        //The size of each square in the grid is defined as 60 (pixels)
        square_size = 60;
        //Calling the menubar function to add the menubar
        frame.setJMenuBar(menuBar());
        
        //Starting player
        player = 1;//black

        //Initialise the info panel which will contain all information and buttons to alter the game
        //Set the layout to gridbag
        JPanel info = new JPanel(new GridBagLayout());
        //Define the constraints of the layout with the insets to define the spacing between elements
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,0,10,20);
        //Defining the label to say whether the move was valid or not
        //Asigning the constrains defined before
        //Adding the label to the info panel
        valid = new JLabel("Valid");
        gbc.gridy = 0;
        info.add(valid,gbc);
        
        //Defining the label to say whos turn it is
        //Asigning the constrains defined before
        //Adding the label to the info panel
        playerturn = new JLabel("Player: Black");
        gbc.gridy = 1;
        info.add(playerturn,gbc);
        
        //Defining the labels to show the number of counters each player has
        //Asigning the constrains defined before
        //Adding the labels to the info panel
        bcount = new JLabel(player1name + ": " + String.valueOf(bCount)+" (Black)");
        wcount = new JLabel(player2name + ": " + String.valueOf(wCount)+" (White)");
        gbc.gridy = 3;        
        info.add(bcount,gbc);
        gbc.gridy = 5;
        info.add(wcount,gbc);
        
        //Defining the pass button to allow users to skip their turn
        //Asigning the constrains defined before
        //Adding the label to the info panel
        JButton pass = new JButton("Pass");
        pass.addActionListener(e -> pass());
        gbc.gridy = 7;
        info.add(pass,gbc);
        //Defining the options for creating a new game
        p1name = new JLabel("Player 1 Name: ");
        field1 = new JTextField(1);
        p2name = new JLabel("Player 2 Name: ");
        field2 = new JTextField(1);
        newgamesize = new JLabel("Size: ");
        newgamesizefield = new JTextField(1);        
        newgamesizefield.setText("8");
        startnewgame = new JButton("Start");
        startnewgame.addActionListener(e -> start());
        createnewgame = new JButton("New Game");
        createnewgame.addActionListener(e -> newGame());        
        
        gbc.gridy = 9;
        info.add(p1name,gbc);
        gbc.gridy = 11;
        gbc.gridwidth=2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        info.add(field1,gbc);
        gbc.gridy = 13;
        info.add(p2name,gbc);
        gbc.gridy = 15;
        info.add(field2,gbc);
        gbc.gridy = 17;
        info.add(newgamesize,gbc);
        gbc.gridy = 19;
        info.add(newgamesizefield,gbc);
        gbc.gridy = 21;
        info.add(startnewgame,gbc);
        gbc.gridy = 23;
        info.add(createnewgame,gbc);
        createnewgame.setVisible(false);
        frame.getContentPane().add(board);
        frame.add(info,BorderLayout.EAST);
        
        //Calling the addMouseListener function that allows the mouse clicks
        //of the user to be recorded so that pieces can be moved
        addMouseListener(frame);
        
             
        frame.setLocationByPlatform(true);
        //Set the size of the window depending on the size of the grid and the squares in the grid
        frame.setSize(size*square_size + 200, size*square_size + 200);

        frame.setVisible(true);
    }
    //Function to start the game given the information from the user
    private void start() {
        int size = Integer.valueOf(newgamesizefield.getText());
        //Ensuring the grid is an even number that is 4 or larger
        if (!((size%2)==0) || size < 4) {
            JOptionPane.showMessageDialog(null,"Please chose an even number that is 4 or larger","Alert",JOptionPane.WARNING_MESSAGE);  
        } else {
            player1name = field1.getText();
            player2name = field2.getText();
            if (player1name.equals("")) {
                player1name="Player1";
            }
            if (player2name.equals("")) {
                player2name="Player2";
            }
            bcount.setText(player1name + ": " + String.valueOf(bCount)+" (Black)");
            wcount.setText(player2name + ": " + String.valueOf(wCount)+" (White)");
            p1name.setVisible(false);
            p2name.setVisible(false);
            newgamesize.setVisible(false);
            newgamesizefield.setVisible(false);
            startnewgame.setVisible(false);
            field1.setVisible(false);
            field2.setVisible(false);
            //If the requested size of the board is not 8 change the board size
            if (!(size==8)) {
                changeBoard(size);
            }
            //Recreate the grid to accomodate this
            grid = new int[size+1][size+1];
            for (int a=0;a<grid.length;a++) {
                for (int b=0;b<grid[a].length;b++) {
                    grid[a][b] = 0;
                }
            }  
            //Add the starting pieces
            grid[size/2][size/2] = 1;
            grid[size/2+1][size/2+1] = 1;
            grid[size/2+1][size/2] = 2;
            grid[size/2][size/2+1] = 2;
            //Refresh the contents of the page
            refresh(frame);
        }
    }
    
    private void addMouseListener(JFrame currentFrame) {
        
        listener = new MouseListener() {
        public void mouseClicked(MouseEvent e) {
                //When the mouse is clicked
                //Take the x and y position and divide by 60 to get the reference of the 
                //corresponding square on the grid
                int posx = (e.getX())/60;
                int posy = ((e.getY())/60)-1;

                boolean isValidMove = false;
                //Check if the moves made is a valid move
                if (posx > 0 && posx < size+1 && posy > 0 && posy < size+1) {
                    isValidMove = validMove(true,player,posx,posy);
                }
                if (isValidMove == false) {
                    valid.setText("Invalid Move");
                } else {
                    valid.setText("Valid Move");
                    countPieces();
                    bcount.setText(player1name + " " + String.valueOf(bCount)+" (Black)");
                    wcount.setText(player2name + " " + String.valueOf(wCount)+" (White)");
                    changePlayer();
                    if (anyPosMoves(player) == false) {
                        JOptionPane.showMessageDialog(null, "No possible moves");    
                    }
                }
                if (player == 1) {
                    playerturn.setText("Player: Black");
                } else {
                    playerturn.setText("Player: White");
                }
                refresh(frame);
                board.refresh(grid);
            }
            
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        };
        //Add the mouselistener to the JFrame
        currentFrame.addMouseListener(listener);
    }
    //Function to check if any moves are possible
    //By calling the checking function on all position on the grid
    //For a given player
    private boolean anyPosMoves(int player){
        boolean validmoves = false;
        for (int a=0;a<grid.length;a++) {
            for (int b=0;b<grid[a].length;b++) {
                validmoves = validMove(false, player, b, a);
                if (validmoves==true){
                    return validmoves;
                }
            }
        }
        return validmoves;
    
    }
    //Function for a player to skip their turn and finish the game
    private void pass() {
        //Change to other player
        changePlayer();
        if (player==1 && bpass == 0) {
            bpass = 1;
        } else if (player==2 &&  wpass == 0) {
            wpass = 1;
        }
        //If both players have pressed the pass button
        //One after another, determine who has won the game
        //and display dialog message
        if (wpass >0 && bpass >0) {
            if (bCount > wCount) {
                JOptionPane.showMessageDialog(null, "Black wins!"); 
                playerturn.setText("Black wins!");
                createnewgame.setVisible(true);
            } else if (bCount < wCount){
                JOptionPane.showMessageDialog(null, "White wins!"); 
                playerturn.setText("White wins!");
                createnewgame.setVisible(true);
            } else if (bCount == wCount){
                JOptionPane.showMessageDialog(null, "Its a draw"); 
                playerturn.setText("Its a draw");
                createnewgame.setVisible(true);
            }
            
        }

        refresh(frame);
    }
    
    //Function to count how many pieces each player has
    private void countPieces() {
        int tempb =0;
        int tempw =0;
        for(int x=0;x<grid.length;x++) {
            for(int y=0;y<grid.length;y++) {
                if (grid[x][y] == 1) {
                    tempb++;
                } else if (grid[x][y] == 2) {
                    tempw++;
                }
            }
            
        }
        bCount = tempb;
        wCount = tempw;
    
    }
    public boolean validMove(boolean flag, int player,int x, int y) {
        
        
        //Define the opposite player
        int opp = 0;
        if (player == 1) {
            opp = 2;
        } else if (player == 2) {
            opp = 1;
        }
        //Define arrays for each possible direction
        ArrayList<Integer> horizontal = new ArrayList<Integer>(size+1);
        ArrayList<Integer> vertical = new ArrayList<Integer>(size+1);
        ArrayList<Integer> diag1 = new ArrayList<Integer>(size+1);
        ArrayList<Integer> diag2 = new ArrayList<Integer>(size+1);
        
        //Create copy of grid, so that it can be editted indepently
        int[][] temp_grid = new int[grid.length][grid.length];
        for (int n=0;n<grid.length;n++) {
            for (int m=0;m<grid[n].length;m++) {
                temp_grid[n][m] = grid[n][m];
            }
        }
        
        
        //Add the current move to the temporary grid
        temp_grid[x][y] = player;

        //Add horizontal elements to arraylist
        for (int h=1;h<temp_grid[x].length;h++) {
            vertical.add(temp_grid[x][h]);
        }
        //Add vertical elements to arraylist
        for (int v=1;v<temp_grid.length;v++) {
            horizontal.add(temp_grid[v][y]);
        }

        //Diagonally (bottom left to top right)
        int startx = x;
        int starty = y;
        while (startx > 1 && starty < temp_grid.length-1) {
            starty++;
            startx--;
        }
        int dy1 = starty;

        for (int dx1=startx;dx1<temp_grid.length;dx1++) {
            diag1.add(temp_grid[dx1][dy1]);
            if (dy1 > 1) {
                dy1--;
            }
        }
        
        //Diagonally (top left to bottom right)
        startx = x;
        starty = y;
        while (startx > 1 && starty > 1) {
            starty--;
            startx--;
        }

        int dy2 = starty;

        for (int dx2=startx;dx2<temp_grid.length;dx2++) {
            if (dy2 >= temp_grid.length) {
                break;
            }
            diag2.add(temp_grid[dx2][dy2]);
            if (dy2 < temp_grid.length) {
                dy2++;
            }
        }         

        boolean isvalidmove = false;
          
        //Create temporary arrays to compare changes
        ArrayList<Integer> temphoz = new ArrayList<Integer>(horizontal);
        ArrayList<Integer> tempvert = new ArrayList<Integer>(vertical);
        ArrayList<Integer> tempdiag1 = new ArrayList<Integer>(diag1);
        ArrayList<Integer> tempdiag2 = new ArrayList<Integer>(diag2);        
        //Create new array for any flipped pieces
        ArrayList<Integer> newhoz = isValid(player,temphoz);
        ArrayList<Integer> newvert = isValid(player,tempvert);   
        ArrayList<Integer> newdiag1 = isValid(player,tempdiag1);
        ArrayList<Integer> newdiag2 = isValid(player,tempdiag2);
        
        //Compare both arrays to see if any changes occurred
        if (!(newhoz.equals(horizontal))) {
            isvalidmove = true;
        }
        if (!(newvert.equals(vertical))) {
            isvalidmove = true;
        }
        
        if (!(newdiag1.equals(diag1))) {
            isvalidmove = true;
        }
        if (!(newdiag2.equals(diag2))) {
            isvalidmove = true;
        }
        //If flag is true, call the function to put the arrays back in the grid (otherwise it was just a check)
        if (flag == true) {
            backingrid(x,y,temp_grid,temphoz,tempvert,tempdiag1,tempdiag2,newhoz,newvert,newdiag1,newdiag2,horizontal,
            vertical,diag1,diag2);
        }
        
        return isvalidmove;
    }
    //Function to put arrays back into the grid
    public void backingrid(int x, int y,int[][] temp_grid,ArrayList<Integer> temphoz,ArrayList<Integer> tempvert,ArrayList<Integer> tempdiag1,
    ArrayList<Integer> tempdiag2,ArrayList<Integer> newhoz,
    ArrayList<Integer> newvert,ArrayList<Integer> newdiag1,ArrayList<Integer> newdiag2,ArrayList<Integer> horizontal,
    ArrayList<Integer> vertical,ArrayList<Integer> diag1,ArrayList<Integer> diag2
    ) {
        int startx,starty;
        boolean isvalidmove = false;
        if (!(newhoz.equals(horizontal))) {
            isvalidmove = true;
            grid = temp_grid;
            //Horizontal
            for (int i=0;i<newhoz.size();i++) {
                grid[i+1][y] = newhoz.get(i);
            }
        }
        if (!(newvert.equals(vertical))) {
            isvalidmove = true;
            grid = temp_grid;
            //Vertical
            for (int i=0;i<newvert.size();i++) {
                grid[x][i+1] = newvert.get(i);
            }
        }        
        if (!(newdiag1.equals(diag1))) {
            isvalidmove = true;
            grid = temp_grid;
            // //Diagonal 1 (bottom left to top right)
            startx = x;
            starty = y;
            
            while (startx > 1 && starty < temp_grid.length-1) {
                 starty++;
                 startx--;
            }
            int dy3 = starty;
            int c = 0; 
            for (int dx3=startx;dx3<temp_grid.length;dx3++) {
                 grid[dx3][dy3] = newdiag1.get(c);
                 if (dy3 > 1) {
                     dy3--;
                 }
                 c++;
            }
                
        }        
        if (!(newdiag2.equals(diag2))) {
            grid = temp_grid;
            isvalidmove = true;
            startx = x;
            starty = y;
            
            while (startx > 1 && starty < temp_grid.length-1) {
                 starty--;
                 startx--;
            }
            int dy4 = starty;
            int c = 0; 
            for (int dx3=startx;dx3<temp_grid.length;dx3++) {
                 grid[dx3][dy4] = newdiag2.get(c);
                 if (dy4 < grid.length) {
                     dy4++;
                 }
                 c++;
            }
        }
    }
    //Function to flip pieces that can be flipped and return the array after making the changes
    public ArrayList<Integer> isValid(int player,ArrayList<Integer> array) {
        ArrayList<Integer> arraylist = array;
        boolean valid = false;
        int opp = 0;
        if (player == 2) {
            opp = 1;
        } else {
            opp = 2;
        }
        
        int counter=0;
        
        //Flip counters
        
        ArrayList<Integer> locations = new ArrayList<Integer>();
        
        for (int a=0;a<arraylist.size();a++) {
            if (arraylist.get(a) == player) {
                locations.add(a);
            }
        }
        
        for (int b=0;b<locations.size();b++) {
            if (locations.size() > 1) {
                if (b+1 >= locations.size()) {
                    break;
                }
                for (int c=locations.get(b)+1;c<locations.get(b+1);c++) {
                    if (arraylist.get(c) == opp) {                  
                        arraylist.set(c,player);

                    } else if (arraylist.get(c) == 0){
                        break;
                    } else {
                        break;
                    }
                }
            }
        
        }

        return arraylist;
    }
    //Function to create the menu bar
    private JMenuBar menuBar() {
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        //Create menu bar
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        menu = new JMenu("Game");
        menubar.add(menu);
        
        //Create all menu bar items with listeners to call the corresponding function, also defined
        //is the keyboard shortcuts to also call the functions
        
        item = new JMenuItem("New Game...");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
            item.addActionListener(e -> newGame());
        menu.add(item);
        
        item = new JMenuItem("Open Game...");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
            item.addActionListener(e -> openGame());
        menu.add(item);

        item = new JMenuItem("Save Game...");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, SHORTCUT_MASK));
            item.addActionListener(e -> saveGame());
        menu.add(item);
        menu.addSeparator();
        
        item = new JMenuItem("Quit");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
            item.addActionListener(e -> quit());
        menu.add(item);
        //Return the menu bar that has been created
        return menubar;
    }
    //Function to change the current player
    private void changePlayer() {
        if (getPlayer() == 1) {
            setPlayer(2);
            playerturn.setText("Player: White");
        } else {
            setPlayer(1);
            playerturn.setText("Player: Black");
        }
        refresh(frame);
    }
    //Function to quit the game
    private static void quit() {
        System.exit(0);
    }        
    //Function to save the current game to a text file
    private void saveGame() {
        //Dialog box to ask for the name of the file
        String filename = JOptionPane.showInputDialog("Choose a file name: ");
        String line = "";
        try{
            //Create the new file
            FileWriter writer = new FileWriter(new File(".//game_saves//", filename+".txt"),false);
            //Write the size of the grid to the file
            writer.write(String.valueOf(grid.length) + "\n");
            //Write the current grid to the file with
            //commas seperating each item
            for (int y=0;y<grid.length-1;y++) {
                line = "";
                for (int x=0;x<grid[x].length-1;x++) {
                    line += grid[y+1][x+1] + ",";
                }
                System.out.println(line);
                writer.write(line + "\n");
            }
            //Write the current player to the grid and names of players
            writer.write(String.valueOf(player) + "\n");
            writer.write(player1name + "\n");
            writer.write(player2name); 
            writer.close();
            JOptionPane.showMessageDialog(null, "Game Saved");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error Writing Game To File");
        }
    }
    private void openGame() {
        //Create the file chooser so that the user can pick which game they want to open
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./game_saves/"));
        int dimension;
        //The chosen file name is stored
        int result = fileChooser.showOpenDialog(frame);
        String[] data;
        String line;
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                Scanner myReader = new Scanner(selectedFile);
                dimension = Integer.valueOf(myReader.nextLine()) - 1;
                //The size of the grid is read and a temporary grid is formed to store the data
                int[][] newtempgrid = new int[dimension+1][dimension+1];
                for (int i=0;i<dimension;i++) {
                    line = myReader.nextLine();
                    System.out.println(line);
                    data = line.split(",");
                    for (int j=0;j<data.length-1;j++) {
                        //Every item from the file is added to the grid
                        newtempgrid[i+1][j+1] = Integer.valueOf(data[j]);
                    }
                }
                //The current player and the player names are assigned
                player = Integer.valueOf(myReader.nextLine());
                player1name = myReader.nextLine();
                player2name = myReader.nextLine();
                if (player == 1) {
                    playerturn.setText("Player: Black");
                } else if (player == 2){
                    playerturn.setText("Player: White");
                }
                //The counters are updated and the grid assigned to the temporary grid
                bCount = 0;
                wCount = 0;
                myReader.close();
                grid = newtempgrid;
                countPieces();
                changeBoard(dimension);
                bcount.setText(player1name + ": " + String.valueOf(bCount)+" (Black)");
                wcount.setText(player2name + ": " + String.valueOf(wCount)+" (White)");
                p1name.setVisible(false);
                p2name.setVisible(false);
                newgamesize.setVisible(false);
                newgamesizefield.setVisible(false);
                startnewgame.setVisible(false);
                field1.setVisible(false);
                field2.setVisible(false);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error opening file");
            }
        }
        
    }
    //Function to change the size of the board and update the gui to accomodate this
    public void changeBoard(int size) {
        frame.getContentPane().remove(board);
        //frame.removeMouseListener(listener);
        board = new drawBoard(size,grid);
        frame.getContentPane().add(board);
        //addMouseListener(frame);
        frame.setSize(size*square_size + 200, size*square_size + 200);
        refresh(frame);
    }
    //Function to refresh the contents of the gui
    public void refresh(JFrame frame) {

        board.refresh(this.getGrid());
        board.revalidate();
        frame.revalidate();
        frame.repaint();
        board.repaint();
    }
    //Function to initiate a new game
    public void newGame() {
        //Warning message to confirm the users wants to destroy their current game and start a new one
        int n = JOptionPane.showConfirmDialog(null,"Are you sure you want to create a new game","Reversi",JOptionPane.YES_NO_OPTION);
        if (true) {
            frame.setVisible(false);
            Reversi g = new Reversi(size);           
        }
    }
    
}