import java.util.Random;
import java.util.Scanner;
import java.util.*;
import java.util.*;
//package TicTac;
// Tie? and exit 
// ArrayList Arrays and List
// retry if position already used 
// how did you win?  (i.e. across?
// no infinite loops 
// AI
// Constants for X and O
// 

public class TicTac {

	//private static final char CX = 'X';
	//private static final char CO = 'O';
	//private static final String SX = "X";
	//private static final String SO = "O";

	private static final char 	C_LETTER_X = 'X';
	private static final char 	C_LETTER_O = 'O';
	private static final String S_LETTER_X = "X";
	private static final String S_LETTER_O = "O";	
	
	private static final char 	C_SPACE = ' ';
	private static final String S_SPACE = " ";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		
        char[] [] gameBoard = {
        		//                   *0*   	   1    *2*    3    *4*
        							{C_SPACE, '|',C_SPACE,'|',C_SPACE }, 	// Row *0*
        							{'-', '+','-','+','-' }, 				// Row  1
        							{C_SPACE, '|',C_SPACE,'|',C_SPACE }, 	// Row *2*
        							{'-', '+','-','+','-' }, 				// Row  3
        							{C_SPACE, '|',C_SPACE,'|',C_SPACE }  	// Row *4*
        						};
        String sUserXorO = S_LETTER_X;
        String sWinner ;
        String sIsPlayerHuman = "Human";
        String sPosMethod = "Keyboard";
      
        printGameBoard(gameBoard);
    
        // Play until the user wins or loses or ties
        // TODO Display Tie message
        // TODO Retry from user on collide 
        while (true) {
        	
	        int pos ; 
	        
	        do { // empty spot and computer picks an empty spot
	        	pos = pickPos(sIsPlayerHuman, sUserXorO, sPosMethod, gameBoard);
	        } while (pickAgain(gameBoard, pos));
	        
	        placePiece(gameBoard, pos, 	sUserXorO);
	        sWinner = checkWinner(gameBoard);
	        displayWinnerInfo(sWinner);
	        printGameBoard(gameBoard);
	        
	        if ( (sWinner.length() > 0) || (boardIsFull(gameBoard)) )
	        	break;
	        
	        if (sUserXorO.equals("X"))
	        	sUserXorO = "O";
	        else
	        	sUserXorO = "X";
	        if (sPosMethod.equals("Random"))
	        	sPosMethod = "Keyboard";
	        else
	        	sPosMethod = "Random";
	        if (sIsPlayerHuman.equals("Human"))
	        	sIsPlayerHuman = "Computer";
	        else
	        	sIsPlayerHuman = "Human";
	        	        
        }    
        System.out.println("End");
	}
	
	public static void printGameBoard(char[][] gameBoard)
	{
    	for (char[] row : gameBoard) {
    		for (char c : row) {
    			System.out.print(c);
    		}
    		System.out.println("");
    	}
    	System.out.println("");
	}

	public static Integer getPosFromRandom()
	{
		Integer pos;
       	Random rand = new Random();
       	
    	pos = rand.nextInt(9)+1;

    	return(pos);
	}

	public static int getCountOfChars(String gameBoard, char XorO)
	{
		int count = 0;
		 
		for (int i = 0; i < gameBoard.length(); i++) {
		    if (gameBoard.charAt(i) == XorO) {
		        count++;
		    }
		}
		
		return(count);
	}
	
	public static Integer getPosFromBlockCenterCorner(char[][] gameBoard)
	{
		Integer pos =-1;
		
		// Priority #0 ; Can I win?
		// Priority #1 ; Block First
		// Priority #2 ; Is center available?
		// Priority #3 ; Corner closest to existing opponent piece (defensive) vs opposite 
		// Priority #4 ; remaining corner
		// Priority #5 ; remaining edge 
		
		// call method/function/sub instead
		String someString = 		String.valueOf(gameBoard[0][0]) + String.valueOf(gameBoard[0][2]) + String.valueOf(gameBoard[0][4]) ;
		someString = someString + 	String.valueOf(gameBoard[2][0]) + String.valueOf(gameBoard[2][2]) + String.valueOf(gameBoard[2][4]) ;
		someString = someString +	String.valueOf(gameBoard[4][0]) + String.valueOf(gameBoard[4][2]) + String.valueOf(gameBoard[4][4]) ;
		
		char someChar = 'O';
		int count = 0;
		 
		for (int i = 0; i < someString.length(); i++) {
		    if (someString.charAt(i) == someChar) {
		        count++;
		    }
		}
		if (count > 1) 
		{
			//Could win
			
		}
		return(pos);
	};
	
	public static Integer getPosFromKeyboard()
	{
		Integer pos;
        Scanner scan = new Scanner(System.in);
 
        do {
        	System.out.println("Enter your placement (1-9):");
            while ( ( ! scan.hasNextInt())  ) {
                System.out.println("That's not a number (1-9)!");
                scan.next(); // this is important!
            }
            pos = scan.nextInt();
            
        } while (  pos <= 0 || pos>= 10 );		
        return(pos);
	}
	
	public static Integer pickPos(String playerType, String XorO, String pickingMethod, char[][] gameBoard)
	{
		Integer pos = 0;
		//if (playerType.equals("Human"))
		if (pickingMethod.equals("Keyboard"))
			pos = getPosFromKeyboard();

		if (pickingMethod.equals("Random"))
			pos = getPosFromRandom();

		if (pickingMethod.equals("Basic"))
			pos = getPosFromBlockCenterCorner(gameBoard);
		
        return(pos); 
	}
	
	public static Boolean boardIsFull(char[][] gameBoard)
	{
		if (gameBoard[0][0] == C_SPACE)
			return(false);
		if (gameBoard[0][2] == C_SPACE)
			return(false);
		if (gameBoard[0][4] == C_SPACE)
			return(false);
		
		if (gameBoard[2][0] == C_SPACE)
			return(false);
		if (gameBoard[2][2] == C_SPACE)
			return(false);
		if (gameBoard[2][4] == C_SPACE)
			return(false);
		
		if (gameBoard[4][0] == C_SPACE)
			return(false);
		if (gameBoard[4][2] == C_SPACE)
			return(false);
		if (gameBoard[4][4] == C_SPACE)
			return(false);
		System.out.println("Board is full ; game is over");
		return (true);
	}

	public static Boolean positionCollision(char[][] gameBoard, int pos)
	{
		char cExistingChar; 
		
        if (pos <= 3) {
        	cExistingChar = gameBoard[0][(pos-1)%3+((pos-1)%3)] ;
        }
        // Second/Middle Row
        else if (pos <= 6) {
        	cExistingChar = gameBoard[2][(pos-1)%3+((pos-1)%3)] ;
        }
        // Last/Third Row
        else  {
        	cExistingChar = gameBoard[4][(pos-1)%3+((pos-1)%3)] ;
        }
        if (cExistingChar == C_LETTER_X || cExistingChar == C_LETTER_O ) {
        	// System.out.println("Collision");
        	return true;
        }
		return false;
	}

	public static Boolean pickAgain(char[][] gameBoard, int pos)
	{
		if (boardIsFull(gameBoard)) 
			return(false);
		
		if (positionCollision(gameBoard, pos)) 
			return(true);
		
		return(false);
	}
	
	public static void placePiece(char[][] gameBoard, int pos, String sUser)
	{
			// Is it valid move?  (is there a piece already there?)
			char cExistingChar; 
	        if (pos <= 3) {
	        	cExistingChar = gameBoard[0][(pos-1)%3+((pos-1)%3)] ;
	        }
	        // Second/Middle Row
	        else if (pos <= 6) {
	        	cExistingChar = gameBoard[2][(pos-1)%3+((pos-1)%3)] ;
	        }
	        // Last/Third Row
	        else  {
	        	cExistingChar = gameBoard[4][(pos-1)%3+((pos-1)%3)] ;
	        }
	        if (cExistingChar == C_LETTER_X || cExistingChar == C_LETTER_O ) {
	        	System.out.println("Invalid location ; already used");
	        	// ok here but needs to be in retry logic 
	        	return;
	        }
	        // First Row
	        if (pos <= 3) {
	        	gameBoard[0][(pos-1)%3+((pos-1)%3)] = sUser.charAt(0);
	        }
	        // Second/Middle Row
	        else if (pos <= 6) {
	        	gameBoard[2][(pos-1)%3+((pos-1)%3)] = sUser.charAt(0);
	        }
	        // Last/Third Row
	        else  {
	        	gameBoard[4][(pos-1)%3+((pos-1)%3)] = sUser.charAt(0);
	        }
		
	}

	public static char[] convertGameBoardToSmallCharArray(char[][] gameBoard)
	{

		char[] cPos = new char[10];
		int iRow; 
		
		
		for (iRow=1; iRow<=3; iRow++) {
			cPos[iRow] = gameBoard[0][(iRow-1)%3+((iRow-1)%3)] ;
		}		
		for (iRow=4; iRow<=6; iRow++) {
			cPos[iRow] = gameBoard[2][(iRow-1)%3+((iRow-1)%3)] ;
		}
		for (iRow=7; iRow<=9; iRow++) {
			cPos[iRow] = gameBoard[4][(iRow-1)%3+((iRow-1)%3)] ;
		}
		
		return(cPos);
	}

	public static String convertGameBoardToString(char[][] gameBoard)
	{

		char[] cPos = new char[10];
		int iRow; 
		cPos[0] = ' ';
		
		for (iRow=1; iRow<=3; iRow++) {
			cPos[iRow] = gameBoard[0][(iRow-1)%3+((iRow-1)%3)] ;
		}		
		for (iRow=4; iRow<=6; iRow++) {
			cPos[iRow] = gameBoard[2][(iRow-1)%3+((iRow-1)%3)] ;
		}
		for (iRow=7; iRow<=9; iRow++) {
			cPos[iRow] = gameBoard[4][(iRow-1)%3+((iRow-1)%3)] ;
		}
		//String b = new String(a);
		String sReturn = new String(cPos) ; 
		
		return(sReturn);
	}

	public static Integer checkIfCloseWinningOrLosing(char[][] gameBoard, char cXorO)
	{
		// ToDo add description of type of win
		// don't have if and else combine to one (skip if == C_SPACE or != C_SPACE)
		// close is defined as one play away 
		char[] cPos = new char[10];
		String sPos;
		
		int pos; 
		int iRow; 
		
		sPos = convertGameBoardToString(gameBoard);
		String sWorkingPos ;
		sPos = sPos.substring(1);
		sWorkingPos = sPos.substring(0,1) + sPos.substring(1,2) + sPos.substring(2,3);
		// method 1 row 1 X|-|X or X|X|- or -|X|X replace X's with O's
		if ( (getCountOfChars(sWorkingPos, cXorO) == 2) && (getCountOfChars(sWorkingPos, C_SPACE) == 1) ) 
		{
			if ( cPos[0] == C_SPACE )
				return(1);
			if ( cPos[1] == C_SPACE )
				return(1);
			if ( cPos[2] == C_SPACE )
				return(3);
		}

		// method 2 row 2 X|X|X or O|O|O
		//if ( cPos[4] == cPos[5] ) {
		//	if ( cPos[5] == cPos[6] ) {
		//		if ( cPos[4] != C_SPACE) {
		//			return(cPos[4] + " ; row two ; You are the winner!");
		//		}
		//	}
		//}
		
		// method 3 row 3 X|X|X or O|O|O
		//if ( cPos[7] == cPos[8] ) {
		//	if ( cPos[8] == cPos[9] ) {
		//		if ( cPos[7] != C_SPACE) {
		//			return(cPos[7] + " ; row three ; You are the winner!");
		//		}
		//	}
		//}
		// method 4 column 1 X|X|X or O|O|O
		//if ( cPos[1] == cPos[4] ) {
		//	if ( cPos[4] == cPos[7] ) {
		//		if ( cPos[1] != C_SPACE) {
		//			return(cPos[1] + " ; column one ; You are the winner!");
		//		}
		//	}
		//}

		// method 5 column 2 X|X|X or O|O|O
		//if ( cPos[2] == cPos[5] ) {
		//	if ( cPos[5] == cPos[8] ) {
		//		if ( cPos[2] != C_SPACE) {
		//			return(cPos[2] + " ; column two ; You are the winner!");
		//		}
		//	}
		//}
		
		// method 6 column 3 X|X|X or O|O|O
		//if ( cPos[3] == cPos[6] ) {
		//	if ( cPos[6] == cPos[9] ) {
		//		if ( cPos[3] != C_SPACE) {
		//			return(cPos[3] + " ; column three ; You are the winner!");
		//		}
		//	}
		//}

		// method 7 diangle down X|X|X or O|O|O
		//if ( cPos[1] == cPos[5] ) {
		//	if ( cPos[5] == cPos[9] ) {
		//		if ( cPos[1] != C_SPACE) {
		//			return(cPos[1]  + " ; Diangle down ; You are the winner!");
		//		}
		//	}
		//}		
		
		// method 8 diangle up X|X|X or O|O|O
		//if ( cPos[3] == cPos[5] ) {
		//	if ( cPos[5] == cPos[7] ) {
		//		if ( cPos[3] != C_SPACE) {
		//			return(cPos[3] + "; Diangle up ; You are the winner!");
		//		}
		//	}
		//}		
		
		return(0);
	}
	
	public static String checkWinner(char[][] gameBoard)
	{
		//ToDo add description of type of win
		// don't have if and else combine to one (skip if == C_SPACE or != C_SPACE) 
		char[] cPos = new char[10];

		int pos; 
		int iRow; 
		
		cPos = convertGameBoardToSmallCharArray(gameBoard);
		//for (iRow=1; iRow<=3; iRow++) {
		//	cPos[iRow] = gameBoard[0][(iRow-1)%3+((iRow-1)%3)] ;
		//}		
		//for (iRow=4; iRow<=6; iRow++) {
		//	cPos[iRow] = gameBoard[2][(iRow-1)%3+((iRow-1)%3)] ;
		//}
		//for (iRow=7; iRow<=9; iRow++) {
		//	cPos[iRow] = gameBoard[4][(iRow-1)%3+((iRow-1)%3)] ;
		//}
		
		// method 1 row 1 X|X|X or O|O|O
		if ( cPos[1] == cPos[2] ) {
			if ( cPos[2] == cPos[3] ) {
				if ( cPos[1] != C_SPACE) {
					return(cPos[1] + " ; row one ; You are the winner!");
				}
			}
		}

		// method 2 row 2 X|X|X or O|O|O
		if ( cPos[4] == cPos[5] ) {
			if ( cPos[5] == cPos[6] ) {
				if ( cPos[4] != C_SPACE) {
					return(cPos[4] + " ; row two ; You are the winner!");
				}
			}
		}
		
		// method 3 row 3 X|X|X or O|O|O
		if ( cPos[7] == cPos[8] ) {
			if ( cPos[8] == cPos[9] ) {
				if ( cPos[7] != C_SPACE) {
					return(cPos[7] + " ; row three ; You are the winner!");
				}
			}
		}
		// method 4 column 1 X|X|X or O|O|O
		if ( cPos[1] == cPos[4] ) {
			if ( cPos[4] == cPos[7] ) {
				if ( cPos[1] != C_SPACE) {
					return(cPos[1] + " ; column one ; You are the winner!");
				}
			}
		}

		// method 5 column 2 X|X|X or O|O|O
		if ( cPos[2] == cPos[5] ) {
			if ( cPos[5] == cPos[8] ) {
				if ( cPos[2] != C_SPACE) {
					return(cPos[2] + " ; column two ; You are the winner!");
				}
			}
		}
		
		// method 6 column 3 X|X|X or O|O|O
		if ( cPos[3] == cPos[6] ) {
			if ( cPos[6] == cPos[9] ) {
				if ( cPos[3] != C_SPACE) {
					return(cPos[3] + " ; column three ; You are the winner!");
				}
			}
		}

		// method 7 diangle down X|X|X or O|O|O
		if ( cPos[1] == cPos[5] ) {
			if ( cPos[5] == cPos[9] ) {
				if ( cPos[1] != C_SPACE) {
					return(cPos[1]  + " ; Diangle down ; You are the winner!");
				}
			}
		}		
		
		// method 8 diangle up X|X|X or O|O|O
		if ( cPos[3] == cPos[5] ) {
			if ( cPos[5] == cPos[7] ) {
				if ( cPos[3] != C_SPACE) {
					return(cPos[3] + "; Diangle up ; You are the winner!");
				}
			}
		}		
		
		return("");
	}

	public static void displayWinnerInfo(String sWinner)
	{
		if (sWinner.length() == 0)
			return;
		
		System.out.println(sWinner);
		
	}

}