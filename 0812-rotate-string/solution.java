import java.util.*;
class Solution {
    public static boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) return false;
        return (s + s).contains(goal);   
    }
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        System.out.println("enter string : ");
        String s = sc.next();
        System.out.println("enter goal : ");
        String goal = sc.next();
        rotateString(s,goal);
    }


}
