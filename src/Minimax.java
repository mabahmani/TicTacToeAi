public class Minimax {


    final private char player = 'x', opponent = 'o';

    private static class Move{
        int row;
        int col;
    }

    private boolean isMovesLeft(char board[][]) {
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if(board[i][j] == '_')
                    return true;
            }
        }
        return false;
    }

    private static boolean isEnd(char board[][]){
        for (int row = 0; row<3; row++)
        {
            if (board[row][0]==board[row][1] &&
                    board[row][1]==board[row][2])
            {
                if(board[row][1] != '_')
                    return true;
            }
        }

        for (int col = 0; col<3; col++)
        {
            if (board[0][col]==board[1][col] &&
                    board[1][col]==board[2][col])
            {
                if(board[1][col] != '_')
                    return true;
            }
        }

        if (board[0][0]==board[1][1] && board[1][1]==board[2][2])
        {
            if(board[1][1] != '_')
                return true;
        }

        if (board[0][2]==board[1][1] && board[1][1]==board[2][0])
        {
            if(board[1][1] != '_')
                return true;
        }

        return false;
    }

    private int evaluate(char board[][], int depth) {
        for (int row = 0; row<3; row++)
        {
            if (board[row][0]==board[row][1] &&
                    board[row][1]==board[row][2])
            {
                if (board[row][0]==player)
                    return +10 - depth;
                else if (board[row][0]==opponent)
                    return -10 + depth;
            }
        }

        for (int col = 0; col<3; col++)
        {
            if (board[0][col]==board[1][col] &&
                    board[1][col]==board[2][col])
            {
                if (board[0][col]==player)
                    return +10 - depth;

                else if (board[0][col]==opponent)
                    return -10 + depth;
            }
        }

        if (board[0][0]==board[1][1] && board[1][1]==board[2][2])
        {
            if (board[0][0]==player)
                return +10 - depth;
            else if (board[0][0]==opponent)
                return -10 + depth;
        }

        if (board[0][2]==board[1][1] && board[1][1]==board[2][0])
        {
            if (board[0][2]==player)
                return +10 - depth;
            else if (board[0][2]==opponent)
                return -10 + depth;
        }

        return 0;
    }

    private int minimax(char board[][], int depth, int alpha, int beta, boolean isMax) {

        int score = evaluate(board , depth);

        if (score > 0)
            return score;

        if (score < 0)
            return score;

        if (!isMovesLeft(board))
            return 0;

        if (isMax)
        {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    if (board[i][j]=='_')
                    {
                        board[i][j] = player;

                        //print(board);

                        best = Math.max( best, minimax(board, depth+1, alpha, beta, false) );
                        alpha = Math.max(alpha,best);

                        board[i][j] = '_';

                        if(beta <= alpha)
                            break;
                    }
                }
            }
            return best;
        }

        else
        {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    if (board[i][j]=='_')
                    {
                        board[i][j] = opponent;

                        //print(board);

                        best = Math.min(best, minimax(board, depth+1, alpha, beta, true));

                        beta = Math.min(beta,best);
                        board[i][j] = '_';

                        if (beta <= alpha)
                            break;
                    }
                }
            }
            return best;
        }
    }

    private Move findBestMove(char board[][]) {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i<3; i++)
        {
            for (int j = 0; j<3; j++)
            {
                if (board[i][j]=='_')
                {
                    board[i][j] = player;

                    //print(board);

                    int moveVal = minimax(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE ,false);

                    board[i][j] = '_';

                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    private static void print(char board [][]){
        for (int row=0; row<3; row++){
            for (int col=0; col<3; col++){
                System.out.print(board[row][col] + "    ");
            }
                System.out.println();
        }

        System.out.println("*** *** ***");
    }


    public static void main(String args[]){

        char board [][] =    {
                { '_', '_', '_' },
                { '_', '_', '_' },
                { '_', '_', '_' }
        };

        Minimax test = new Minimax();
        Move move = new Move();

        for (int i=0; i<9; i++){

            move = test.findBestMove(board);

            /*print_board*/
            if(i%2 == 0)
                board[move.row][move.col] = 'x';
            else
                board[move.row][move.col] = 'o';

            if(isEnd(board)){
                print(board);
                break;
            }
            print(board);
        }
    }
}