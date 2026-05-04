class Solution {
    public boolean findRotation(int[][] mat, int[][] target) {
       for (int k = 0; k < 4; k++) {
            if (areEqual(mat, target)) {
                return true;
            }
            rotate(mat); // rotate 90 degrees
        }
        return false;
       }
    
    public void rotate(int[][] mat){
         int n= mat.length;
        for(int i =0;i<n;i++){
            for(int j=i+1;j<n;j++){
                int temp = mat[i][j];
                mat[i][j]=mat[j][i];
                mat[j][i]=temp;
            }
        }
        for(int i = 0;i<n;i++){
            int left = 0;
            int right = n-1;
            while(left<right){
                int temp = mat[i][left];
                mat[i][left]=mat[i][right];
                mat[i][right]=temp;
                left++;
                right--;
            }
        }
    }
    public boolean areEqual(int[][] mat,int[][] target){
        if(mat.length != target.length || mat[0].length != target[0].length){
            return false;
        }
        for(int i =0;i<mat.length;i++){
            for(int j=0;j<mat[0].length;j++){
                if(mat[i][j] != target[i][j]){
                    return false;
                }
            }
        }
        return true;

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        int[][] mat = new int[n][n];

        System.out.println("Enter mat:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = sc.nextInt();
            }
        }
        int[][] target = new int[n][n];

        System.out.println("Enter target:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                target[i][j] = sc.nextInt();
            }
        }
        Solution obj = new Solution();
        boolean result = obj.findRotation(mat,target);
        System.out.println(result);
    }
 
}
