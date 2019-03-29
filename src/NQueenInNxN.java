public class NQueenInNxN {

/*    How can N queens be placed on an NxN chessboard so that no two of them attack each other?*/

   private long totali =0;
    public void startCalculation(int n) {
        Integer[][] tabela;
        tabela = new Integer[n][n];
        fillTableWithEmpty(tabela);
        runTable(tabela, 0);

    }

    private void runTable(Integer[][] tabela, int i) {

        if (i == tabela.length) {
            printTable(tabela);
            totali++;

        } else {
            for (int ii = 0; ii < tabela.length; ii++) {
                if (addIfValid(i, ii, tabela)) {
                    runTable(tabela, i + 1);
                    tabela[i][ii] = 0;
                }
            }
        }

    }


    private void printTable(Integer[][] tabela) {
       if (isTableValid(tabela)) {
            System.out.println();
            System.out.println();
            System.out.println();
            for (int i = 0; i < tabela.length; i++) {
                System.out.println();
                for (int j = 0; j < tabela.length; j++) {
                    System.out.print(tabela[i][j]);
                }
            }
        }
    }

    private boolean isTableValid(Integer[][] tabela) {
        int num = 0;
        for (int i = 0; i < tabela.length; i++) {

            for (int j = 0; j < tabela.length; j++) {
                if (tabela[i][j] == 1)
                    num++;
            }
        }
        return num == tabela.length;
    }

    private void fillTableWithEmpty(Integer[][] tabela) {
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela.length; j++) {
                tabela[i][j] = 0;
            }
        }
    }


    private boolean addIfValid(int i, int j, Integer[][] tabela) {
        //    System.out.println(i+" "+j);
        if (checkRowValid(i, j, tabela) && checkColValid(i, j, tabela) && checkDiagonallyValid(i, j, tabela)) {
            tabela[i][j] = 1;
            return true;
        }
        return false;
    }

    private boolean checkDiagonallyValid(int i, int j, Integer[][] tabela) {
        for (int col = i, rpw = j; col < tabela.length && rpw < tabela.length; col++, rpw++) {
            if (tabela[col][rpw] == 1)
                return false;
        }
        for (int col = i, rpw = j; col >= 0 && rpw >= 0; col--, rpw--) {
            if (tabela[col][rpw] == 1)
                return false;
        }

        for (int col = i, rpw = j; col < tabela.length && rpw >= 0; col++, rpw--) {
            if (tabela[col][rpw] == 1)
                return false;
        }

        for (int col = i, rpw = j; col >= 0 && rpw < tabela.length; col--, rpw++) {
            if (tabela[col][rpw] == 1)
                return false;
        }


        return true;
    }

    private boolean checkColValid(int i, int j, Integer[][] tabela) {
        for (int col = 0; col < tabela.length; col++) {
            if (tabela[col][j] == 1)
                return false;
        }
        return true;
    }

    private boolean checkRowValid(int i, int j, Integer[][] tabela) {
        for (int row = 0; row < tabela.length; row++) {
            if (tabela[i][row] == 1)
                return false;
        }
        return true;
    }
}
