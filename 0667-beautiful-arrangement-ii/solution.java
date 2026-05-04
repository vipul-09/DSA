class Solution {
    public int[] constructArray(int n, int k) {
        int[] result = new int[n];
        int index = 0;

        int high = k+1;
        int low=1;
        while(low<=high){
            if(index%2==0){
               result[index++]=low++;
            }
            else{
               result[index++]=high--;
            }
        }

        for(int i =k+2;i<=n;i++){
             result[index++]=i;
        }
        return result;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the value of n : ");
        int n =sc.nextInt();
        System.out.println("enter the value of k : ");
        int k = sc.nextInt();
        Solution obj = new Solution();
        int[] arr = obj.constructArray(n,k);
        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i]+",");
        }

    }
}
