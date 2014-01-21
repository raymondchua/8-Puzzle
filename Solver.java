public class Solver {
   public Solver(Board initial)        // find a solution to the initial board
   public boolean isSolvable()         // is the initial board solvable?
   public int moves()                  // return min number of moves to solve initial board; -1 if no solution
   public Iterable<Board> solution()   // return an Iterable of board positions in solution

   	
   
   	public static void main(String[] args) {
   		
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
	}
}
