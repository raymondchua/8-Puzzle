/*
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver < puzzle04.txt
 *  Dependencies: StdIn.java Board.java
 */

import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;



public class Solver {

	
	private int shortestNumOfMoves = 0;
	private int totalMoves = 0;

	private PriorityQueue<Node> queue;
	private PriorityQueue<Node> twinQueue;

	private Stack<Board> boardPath;
	private boolean isSolve = false; 


	
	// find a solution to the initial board
   	public Solver(Board initial){

   		
   		Node start = new Node(0,initial,null);
   		Node twinStart = new Node(0, initial.getTwin(),null);

   		queue = new PriorityQueue<Node>();
   		queue.add(start);

   		twinQueue = new PriorityQueue<Node>();
   		twinQueue.add(twinStart);

   		boardPath = new Stack<Board>();

   		Board parentBoard = initial;

   		Node current;
   		Node twinCurrent;

   		Queue<Board> neighborsQueue = new LinkedList<Board>();

   		astar : while ((!queue.isEmpty()) && (!twinQueue.isEmpty())){
   			current = queue.poll();
   			neighborsQueue = (Queue<Board>)current.board.neighbors();

   			//get parent's board if available
   			if (current.parent != null){
   				parentBoard = current.parent.board;
   			}


   			//add all child nodes except if child is same as parent node
   			for (Board B : neighborsQueue){
   				if (! B.equals(parentBoard)){
   					int move = current.numOfMoves + 1;
   					queue.add(new Node(move,B,current));
   				}
   			}

   			//check if current node is goal
   			if(current.board.isGoal()){
   				shortestPathQueue(current);
   				boardPath.push(initial);
   				isSolve = true;
   				break astar;
   			}

   			//do the same for twin
   			twinCurrent = twinQueue.poll();
   			neighborsQueue = (Queue<Board>)twinCurrent.board.neighbors();

   			//get twin's parent if available
   			if (twinCurrent.parent != null){
   				parentBoard = twinCurrent.parent.board;
   			}


   			for (Board B : neighborsQueue){
   				if (! B.equals(parentBoard)){
   					int move = current.numOfMoves + 1;
   					twinQueue.add(new Node(move, B, twinCurrent));
   				}
   			}

   			if(twinCurrent.board.isGoal()){
   				isSolve = false;
   				shortestNumOfMoves = -1;
   				break astar;
   			}

   		}

   	}

   	public void shortestPathQueue(Node current){

   		while(current.parent != null){
   			boardPath.push(current.board);
   			current = current.parent;
   			shortestNumOfMoves ++ ;
   		}
   	}

   	// is the initial board solvable?        
   	public boolean isSolvable(){
   		return isSolve;

   	}

   	// return min number of moves to solve initial board; -1 if no solution         
   	public int moves(){
   		return shortestNumOfMoves;
   	}

   	// return an Iterable of board positions in solution                  
   	public Iterable<Board> solution(){

   		if (shortestNumOfMoves != -1){
   			return boardPath;
   		}

   		else{

   			return null;

   		}

   	}

   	private class Node implements Comparable<Node>{

   		private int numOfMoves;
   		private Board board;
   		private Node parent;

   		public Node(int moves, Board boardVal, Node previous){

   			numOfMoves = moves;
   			board = boardVal;
   			parent = previous;

   		}

   		public int compareTo(Node other){

   			if ((this.numOfMoves + this.board.manhattan()) > (other.numOfMoves + other.board.manhattan())){
   				return 1;
   			}

   			else if ((this.numOfMoves + this.board.manhattan()) < (other.numOfMoves + other.board.manhattan())){
   				return -1;
   			}

   			else{
   				return 0;
   			}

   		}
   	}   

   	

   	public static void main(String[] args) {


   		
   		//create a board from file
   		int N = StdIn.readInt();
   		int[][] tiles = new int[N][N];
   		
   		for (int i = 0; i < N; i++){
       		for (int j = 0; j < N; j++){
          		tiles[i][j] = StdIn.readInt();
       		}
   		}

    	
    	Board initial = new Board(tiles);


    	Solver solver = new Solver(initial);


    	for (Board board : solver.solution()){
       		System.out.println(board);
    	}

    	
    	if (!solver.isSolvable()){
    		System.out.println("No solution possible");
    	} 

    	else{
    		System.out.println("Minimum number of moves = " + solver.moves());
    	} 

    	//System.out.println(initial.isGoal());

	}
}
