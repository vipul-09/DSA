class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for(int i =0;i<n;i++){
            for(int j =i+1;j<n;j++){
                int temp = matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=temp;
            }
        }

        for(int i=0;i<n;i++){
            int left =0;
            int right=n-1;
            while(left<right){
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right]=temp;
                left++;
                right--;
            }
        }
    }
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Take size input
        System.out.print("Enter n (size of matrix): ");
        int n = sc.nextInt();

        int[][] matrix = new int[n][n];

        // Take matrix input
        System.out.println("Enter the matrix elements:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        Solution obj = new Solution();

        System.out.println("\nOriginal Matrix:");
        printMatrix(matrix);

        obj.rotate(matrix);

        System.out.println("\nRotated Matrix:");
        printMatrix(matrix);

        sc.close();

    }
}
