import java.util.Queue();

public class Board {

	int[][] board;

	// construct a board from an N-by-N array of tiles
   	public Board(int[][] tiles){

   		board = tiles;

   	}        

   	// return number of blocks out of place
   	public int hamming(){

   		int N = board[0].length;


   		//get the total number of tiles in the board
   		int boardSize = N * N;

   		//create an array to store he boolean value if the tile is out of place.
   		boolean[] isHamming = new boolean[boardSize];

   		int numOfHammingBlocks = 0;

   		//transfer the content in the board to a 1D array
   		int[] boardArray = get1DArray();


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

   		int N = board[0].length;

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

   		Board newBoard = Board(y);

   		int [] boardArray = get1DArray(board);
   		int [] goalArray = get1DArray(newBoard);

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

   		Queue<Board> neighbors = new Queue<Board>();
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

   		tempBoard = new Board[N][N];

   		for (int x = 0; x < N; x++){
   			for(int y = 0; y < N; y++){
   				tempBoard[x][y] = board[x][y];
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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX-1][emptyY];
   					tempBoard[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[empty-1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;

   				}

   				/*swap with tile on bottom
   				* 4 | 1	| 2
   				* 3 | 7 | 5
   				* 6 | 0 | 8
   				*/

   				else if ( i == 1){

   					tempBoard[emptyX][emptyY] = tempBoard[emptyX+1][emptyY];
   					tempBoard[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX+1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;

   				}

   				/*swap with tile on left
   				* 4 | 1	| 2
   				* 0 | 3 | 5
   				* 6 | 7 | 8
   				*/

   				else if ( i == 2){

   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY-1];
   					tempBoard[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY-1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;

   				}

   				/*swap with tile on right
   				* 4 | 1	| 2
   				* 3 | 5 | 0
   				* 6 | 7 | 8
   				*/

   				else if ( i == 3){

   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY+1];
   					tempBoard[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY+1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;

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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY+1];
   					tempBoard[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY+1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 3 | 1 | 2
   				* 0 | 4 | 5
   				* 6 | 7 | 8
   				*/

   				else if (i == 1) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX+1][emptyY];
   					tempBoard[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX+1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY-1];
   					tempBoard[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY-1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 1 | 2 | 5
   				* 3 | 4 | 0
   				* 6 | 7 | 8
   				*/

   				else if (i == 1) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX+1][emptyY];
   					tempBoard[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX+1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX-1][emptyY];
   					tempBoard[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX-1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on right
   				* 1 | 2 | 3
   				* 4 | 5 | 6
				* 7 | 0 | 8
   				*/

   				else if (i == 1) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY+1];
   					tempBoard[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY+1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX-1][emptyY];
   					tempBoard[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX-1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on left
   				* 1 | 2 | 3
   				* 4 | 5 | 6
				* 7 | 0 | 8
   				*/

   				else if (i == 1) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY-1];
   					tempBoard[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY-1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY-1];
   					tempBoard[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY-1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on right
   				* 1 | 3 | 0
   				* 2 | 4 | 5
   				* 6 | 7 | 8
   				*/

   				else if (i == 1) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY+1];
   					tempBoard[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY+1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 1 | 4 | 3
   				* 2 | 0 | 5
   				* 6 | 7 | 8
   				*/

   				else if (i == 2) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX+1][emptyY];
   					tempBoard[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX+1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX-1][emptyY];
   					tempBoard[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX-1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on right
   				* 1 | 2 | 3
   				* 4 | 0 | 5
   				* 6 | 7 | 8
   				*/
   				*/

   				else if (i == 1) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX+1][emptyY+1];
   					tempBoard[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY+1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 1 | 2 | 3
   				* 6 | 4 | 5
   				* 0 | 7 | 8
   				*/

   				else if (i == 2) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX+1][emptyY];
   					tempBoard[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX+1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX-1][emptyY];
   					tempBoard[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX-1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on right
   				* 1 | 2 | 3
   				* 7 | 4 | 5
   				* 6 | 8 | 0
   				*/
   				*/

   				else if (i == 1) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX+1][emptyY+1];
   					tempBoard[emptyX][emptyY+1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY+1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on left
   				* 1 | 2 | 3
   				* 7 | 4 | 5
   				* 0 | 6 | 8
   				*/

   				else if (i == 2) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY-1];
   					tempBoard[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY-1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
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
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX-1][emptyY];
   					tempBoard[emptyX-1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX-1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on bottom
   				* 1 | 2 | 3
   				* 7 | 4 | 8
   				* 6 | 5 | 0
   				*/
   				*/

   				else if (i == 1) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX+1][emptyY];
   					tempBoard[emptyX+1][emptyY] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX+1][emptyY] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}

   				/*swap with tile on left
   				* 1 | 2 | 3
   				* 7 | 0 | 4
   				* 6 | 5 | 8
   				*/

   				else if (i == 2) {
   					tempBoard[emptyX][emptyY] = tempBoard[emptyX][emptyY-1];
   					tempBoard[emptyX][emptyY-1] = 0;
   					temp = new Board(tempBoard);
   					neighbors.enqueue(temp);

   					//undo the tile movement
   					tempBoard[emptyX][emptyY-1] = tempBoard[emptyX][emptyY];
   					tempBoard[emptyX][emptyY] = 0;
   				}
   			}
   		}


   	return neighbors;



   	} 

   	// return a string representation of the board
   	public String toString(){

   		String content1D = "1D Array: [ ";
   		String content2D = "";
   		int numOfColumn = board[0].length;

   		for (int i = 0; i < numOfColumn; i++){
   			for (int j = 0; j < numOfColumn; j++){
   				content2D += board[i][j] + " ";
   			}

   			content2D += "/n";
   		}

   		for (int i = 0; i < numOfColumn; i++){
   			for (int j = 0; j < numOfColumn; j++){
   				content1D += board[i][j] + ", "
   			}
   		}

   		content1D += "]";

   		return content2D + content1D;

   	}

   	//transfer the content in the board to a 1D array
   	private int[] get1DArray(Board input){

   		//let N be the dimension value
   		int N = input[0].length;

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


}