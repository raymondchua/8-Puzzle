/*
 *  Compilation:  javac Board.java
 *
 *  
 */

import java.util.*;

public class Board {

	private final int N;
	private final int[][] board;

	// construct a board from an N-by-N array of tiles
   	public Board(int[][] tiles){

   		N = tiles.length;
   		board = new int[N][N];

   		for (int x = 0; x < N; x++){
   			for (int y = 0; y < N; y++){
   				board[x][y] = tiles[x][y];
   			}
   		}



   	}        

   	// return number of blocks out of place
   	public int hamming(){


   		//get the total number of tiles in the board
   		int boardSize = N * N;

   		//create an array to store he boolean value if the tile is out of place.
   		boolean[] isHamming = new boolean[boardSize];

   		int numOfHammingBlocks = 0;

   		//transfer the content in the board to a 1D array
   		int[] boardArray = get1DArray(board);


   		//check if blocks are out of place. Set true if it is out of place. Ignoring empty space, which is 0.
   		for (int i = 1; i < boardArray.length; i++){
   			if(boardArray[i] != i){
   				isHamming[i] = true;
   				numOfHammingBlocks++;
   			}
   		}


   		return numOfHammingBlocks;

   	}               

   	// return sum of Manhattan distances between blocks and goal
   	public int manhattan(){

   

   		int manhattanDist = 0;

   		for (int x = 0; x < N; x++){
   			for (int y = 0; y < N; y++){
   				int value = board[x][y];

   				//skip 0 value, which is the empty tile
   				if (value != 0){
   					int goalX = value/N;
   					int goalY = value%N;

   					int dx = x - goalX;
   					int dy = y - goalY;

   					manhattanDist += Math.abs(dx) + Math.abs(dy);
   				}

   			}
   		}

   		return manhattanDist;
   		

   	}             

   	// does this board position equal y
   	public boolean equals(Object y){

   		Board newBoard = (Board)y;

   		int[] boardArray = get1DArray(board);
   		int[] goalArray = get1DArray(newBoard.getBoardValue());

   		boolean same = true;

   		for (int i = 0; i< boardArray.length; i++){
   			if( boardArray[i] != goalArray[i]){
   				same = false;
   			}
   		}

   		return same;


   	}    

   	
   	// return an Iterable of all neighboring board positions
   	public Iterable<Board> neighbors(){

   		Queue<Board> neighborsBoard = new LinkedList<Board>();
   		Board temp;

   		int N = board[0].length;

   		//check for location of the empty tile: 0
   		int emptyX = 0;
   		int emptyY = 0;

   		for (int x = 0; x < N; x++){
   			for( int y =0; y < N; y++){
   				if(board[x][y] == 0){
   					emptyX = x;
   					emptyY = y;

   					break;
   				}
   			}
   		}

   		

   		//create a tempBoard to hold the resulting board after movement

   		int[][] tempTiles = new int[N][N];

   		Board tempBoard = new Board(tempTiles);

   		int[][] tempBoardData = tempBoard.getBoardValue();

   		for (int x = 0; x < N; x++){
   			for(int y = 0; y < N; y++){
   				tempBoardData[x][y] = board[x][y];
   			}
   		}


   		/*if empty tile is in the middle. For example: 
   		* 4 | 1 | 2
   		* 3 | 0 | 5
   		* 6 | 7 | 8
   		*/

   		if ((emptyX > 0) && (emptyX < (N-1)) && (emptyY > 0) && (emptyY < (N-1))){

   			//4 possible moves
   			for (int i = 0; i < 4; i++){

   				/*swap with tile on top
   				* 4 | 0	| 2
   				* 3 | 1 | 5
   				* 6 | 7 | 8
   				*/

   				if( i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX-1][emptyY];
   					tempBoardData[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX-1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;

   				}

   				/*swap with tile on bottom
   				* 4 | 1	| 2
   				* 3 | 7 | 5
   				* 6 | 0 | 8
   				*/

   				else if ( i == 1){

   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX+1][emptyY];
   					tempBoardData[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX+1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;

   				}

   				/*swap with tile on left
   				* 4 | 1	| 2
   				* 0 | 3 | 5
   				* 6 | 7 | 8
   				*/

   				else if ( i == 2){

   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY-1];
   					tempBoardData[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY-1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;

   				}

   				/*swap with tile on right
   				* 4 | 1	| 2
   				* 3 | 5 | 0
   				* 6 | 7 | 8
   				*/

   				else if ( i == 3){

   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY+1];
   					tempBoardData[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY+1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;

   				}




   			}
   		}

   	

   		/* empty tile on the top left corner
   		* 0 | 1 | 2
   		* 3 | 4 | 5
   		* 6 | 7 | 8
   		*/
   		else if (emptyX == 0 && emptyY == 0){

   			//2 possible moves
   			for (int i = 0; i < 2; i++){

   				/*swap with tile on right
   				* 1 | 0 | 2
   				* 3 | 4 | 5
   				* 6 | 7 | 8
   				*/

   				if (i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY+1];
   					tempBoardData[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY+1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 3 | 1 | 2
   				* 0 | 4 | 5
   				* 6 | 7 | 8
   				*/

   				else if (i == 1) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX+1][emptyY];
   					tempBoardData[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX+1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}
   			}

   		}

   	

   		/* empty tile on the top right corner
   		* 1 | 2 | 0
   		* 3 | 4 | 5
   		* 6 | 7 | 8
   		*/ 
   		else if ((emptyX == 0) && (emptyY == N - 1)){

   			

   			//2 possible moves
   			for (int i = 0; i < 2; i++){

   				/*swap with tile on left
   				* 1 | 0 | 2
   				* 3 | 4 | 5
   				* 6 | 7 | 8
   				*/

   				if (i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY-1];
   					tempBoardData[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY-1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 1 | 2 | 5
   				* 3 | 4 | 0
   				* 6 | 7 | 8
   				*/

   				else if (i == 1) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX+1][emptyY];
   					tempBoardData[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX+1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}
   			}

   		}

   	

   		/* empty tile on the bottom left corner
   		* 1 | 2 | 3
   		* 4 | 5 | 6
   		* 0 | 7 | 8
   		*/ 
   		else if ((emptyX == N-1) && (emptyY == 0)){

   			
   			//2 possible moves
   			for (int i = 0; i < 2; i++){

   				/*swap with tile on top
   				* 1 | 2 | 3
   				* 0 | 5 | 6
				* 4 | 7 | 8
   				*/

   				if (i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX-1][emptyY];
   					tempBoardData[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX-1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on right
   				* 1 | 2 | 3
   				* 4 | 5 | 6
				* 7 | 0 | 8
   				*/

   				else if (i == 1) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY+1];
   					tempBoardData[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY+1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}
   			}

   		}

   	

   		/* empty tile on the bottom right corner
   		* 1 | 2 | 3
   		* 4 | 5 | 6
   		* 7 | 8 | 0
   		*/ 
   		else if ((emptyX == N-1) && (emptyY == N-1)){

   			
   			//2 possible moves
   			for (int i = 0; i < 2; i++){

   				/*swap with tile on top
   				* 1 | 2 | 3
   				* 4 | 5 | 0
   				* 7 | 8 | 6
   				*/

   				if (i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX-1][emptyY];
   					tempBoardData[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX-1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}


   				/*swap with tile on left
   				* 1 | 2 | 3
   				* 4 | 5 | 6
				* 7 | 0 | 8
   				*/

   				else if (i == 1) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY-1];
   					tempBoardData[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY-1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}
   			}

   		}


   		/* empty tile on the top center
   		* 1 | 0 | 3
   		* 2 | 4 | 5
   		* 6 | 7 | 8
   		*/

   		else if ((emptyX == 0) && (emptyY > 0) && (emptyY < N-1)){

   			//3 possible moves
   			for (int i = 0; i < 3; i++){

   				/*swap with tile on left
   				* 0 | 1 | 3
   				* 2 | 4 | 5
   				* 6 | 7 | 8
   				*/

   				if (i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY-1];
   					tempBoardData[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY-1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on right
   				* 1 | 3 | 0
   				* 2 | 4 | 5
   				* 6 | 7 | 8
   				*/

   				else if (i == 1) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY+1];
   					tempBoardData[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY+1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 1 | 4 | 3
   				* 2 | 0 | 5
   				* 6 | 7 | 8
   				*/

   				else if (i == 2) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX+1][emptyY];
   					tempBoardData[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX+1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}
   			}
   		}



   		/* empty tile on the left center
   		* 1 | 2 | 3
   		* 0 | 4 | 5
   		* 6 | 7 | 8
   		*/

   		else if ((emptyY == 0) && (emptyX > 0) && (emptyX < N-1)){

   			//3 possible moves
   			for (int i = 0; i < 3; i++){

   				/*swap with tile on top
   				* 0 | 2 | 3
   				* 1 | 4 | 5
   				* 6 | 7 | 8
   				*/

   				if (i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX-1][emptyY];
   					tempBoardData[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX-1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on right
   				* 1 | 2 | 3
   				* 4 | 0 | 5
   				* 6 | 7 | 8
   				*/
   				

   				else if (i == 1) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY+1];
   					tempBoardData[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY+1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 1 | 2 | 3
   				* 6 | 4 | 5
   				* 0 | 7 | 8
   				*/

   				else if (i == 2) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX+1][emptyY];
   					tempBoardData[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX+1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}
   			}
   		}


   		/* empty tile on the bottom center
   		* 1 | 2 | 3
   		* 7 | 4 | 5
   		* 6 | 0 | 8
   		*/

   		else if ((emptyX == N-1) && (emptyY > 0) && (emptyY < N-1)){

   			//3 possible moves
   			for (int i = 0; i < 3; i++){

   				/*swap with tile on top
   				* 1 | 2 | 3
   				* 7 | 4 | 5
   				* 6 | 0 | 8
   				*/

   				if (i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX-1][emptyY];
   					tempBoardData[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX-1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on right
   				* 1 | 2 | 3
   				* 7 | 4 | 5
   				* 6 | 8 | 0
   				*/
   				

   				else if (i == 1) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY+1];
   					tempBoardData[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY+1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on left
   				* 1 | 2 | 3
   				* 7 | 4 | 5
   				* 0 | 6 | 8
   				*/

   				else if (i == 2) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY-1];
   					tempBoardData[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY-1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}
   			}
   		}

   	

   		/* empty tile on the center right
   		* 1 | 2 | 3
   		* 7 | 4 | 0
   		* 6 | 5 | 8
   		*/

   		else if ((emptyY == N-1) && (emptyX > 0) && (emptyX < N-1)){

   			//3 possible moves
   			for (int i = 0; i < 3; i++){

   				/*swap with tile on top
   				* 1 | 2 | 0
   				* 7 | 4 | 3
   				* 6 | 5 | 8
   				*/

   				if (i == 0){
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX-1][emptyY];
   					tempBoardData[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX-1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 1 | 2 | 3
   				* 7 | 4 | 8
   				* 6 | 5 | 0
   				*/
   				

   				else if (i == 1) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX+1][emptyY];
   					tempBoardData[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX+1][emptyY] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on left
   				* 1 | 2 | 3
   				* 7 | 0 | 4
   				* 6 | 5 | 8
   				*/

   				else if (i == 2) {
   					tempBoardData[emptyX][emptyY] = tempBoardData[emptyX][emptyY-1];
   					tempBoardData[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoardData);
   					neighborsBoard.add(temp);

   					//undo the tile movement
   					tempBoardData[emptyX][emptyY-1] = tempBoardData[emptyX][emptyY];
   					tempBoardData[emptyX][emptyY] = 0;
   				}
   			}
   		}


   	return neighborsBoard;



   	}



   	// return a string representation of the board
   	public String toString(){

   		String content1D = "1D Array: [ ";
   		String content2D = "";
   		int numOfColumn = N;

   		for (int i = 0; i < numOfColumn; i++){
   			for (int j = 0; j < numOfColumn; j++){
   				
   				//if tile is 0, print empty space
   				if (board[i][j] == 0){
   					content2D +="  ";
   				}

   				else{
   					content2D += board[i][j] + " ";	
   				}
   				
   			}

   			content2D += "\n";
   		}

   		for (int i = 0; i < numOfColumn; i++){
   			for (int j = 0; j < numOfColumn; j++){
   				content1D += board[i][j] + ", ";
   			}
   		}

   		content1D += "]";

   		return content2D + content1D;
  

   	}

   	//transfer the content in the board to a 1D array
   	private int[] get1DArray(int[][] input){


   		//get the total number of tiles in the board
   		int boardSize = N * N;

   		//transfer the content in the board to a 1D array
   		int[] boardArray = new int[boardSize];

   		int index = 0;

   		for (int i = 0; i < N; i++){
   			for (int j = 0; j < N; j++){
   				boardArray[index] = input[i][j];
   				index++;
   			}
   		}

   		return boardArray;


   	}

   	public int[][] getBoardValue(){
   		return board;
   	}

   	public Board getTwin(){

   		int[][] temp = new int[N][N];

   		//copy board values to a new int[][]
   		for(int x = 0; x < N; x++){
   			System.arraycopy(this.board[x],0,temp[x],0,N);
   		}

   		boolean doNotUseRowOne = false;

   		//check if first row has any tile with 0 value
   		for(int i = 0; i < N; i++){
   			if(temp[0][i] == 0){
   				doNotUseRowOne = true;
   			}
   		}

   		if(doNotUseRowOne){
   			int tempVal = temp[1][0];
   			temp[1][0] = temp[1][1];
   			temp[1][1] = tempVal;
   		}

   		else{
   			int tempVal = temp[0][0];
   			temp[0][0] = temp[0][1];
   			temp[0][1] = tempVal;
   		}


   		return new Board(temp);


   	}    


   	public boolean isGoal(){

   		int[] temp = get1DArray(board);

   		for(int i = 0; i < N*N; i++){
   			if (temp[i] != i){
   				return false;
   			}
   		}

   		return true;


   	}       


}