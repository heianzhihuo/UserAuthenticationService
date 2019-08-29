package com.gtja.user;

import java.math.BigDecimal;
import java.util.*;

public class HelloWorld {

    public static int Huffman(String str){
        HashMap<Character,Integer> table = new HashMap<>();
        for(char ch:str.toCharArray()){
            table.put(ch,table.getOrDefault(ch,0)+1);
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for(int x:table.values())
            priorityQueue.offer(x);
        int len = 0;
        while (priorityQueue.size()>1){
            int a = priorityQueue.poll();
            int b = priorityQueue.poll();
            priorityQueue.offer(a+b);
            len+=a+b;
        }
        return len;
    }

    public static void Robot(){
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()){
            int N = in.nextInt();
            int M = in.nextInt();
            HashMap<String,String> table = new HashMap<>();
            int i;
            in.nextLine();
            for(i=0;i<N;i++){
                String line = in.nextLine();
                String[] cmds = line.split(" ");
                table.put(cmds[0],cmds[1]);
            }
            for(i=0;i<M;i++){
                String cmd = in.nextLine();
                System.out.println(table.get(cmd));
            }
        }
    }

    public static void playGame(){
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int t=0;t<T;t++){
            int N = in.nextInt();
            int X = in.nextInt();
            int[]w = new int[N];
            int[]v = new int[N];
            for(int i=0;i<N;i++){
                v[i] = in.nextInt();
                w[i] = in.nextInt();
            }
            int[][] dp = new int[N][X+1];
            for(int i=0;i<=X;i++){
                dp[0][i] = w[0]<=i?v[0]:0;//初始情况
            }
            for(int i=1;i<N;i++){
                for(int j=0;j<=X;j++){
                    dp[i][j] = dp[i-1][j];
                    if(w[i]<=j){
                        dp[i][j] = Math.max(dp[i][j], v[i] + dp[i - 1][j - w[i]]);
                    }
                }
            }
            System.out.println(dp[N-1][X]);
        }
    }

    public static void eatFood(){
        Scanner in = new Scanner(System.in);
        int mod = 10000007;
        while (in.hasNextInt()){
            int V = in.nextInt();
            int N = in.nextInt();
            int[]w = new int[N];
            int min = Integer.MAX_VALUE;
            for(int i=0;i<N;i++){
                w[i] = in.nextInt();
                if(w[i]<min)
                    min = w[i];
            }
            int M = in.nextInt();
            int[] fav = new int[N];
            for(int i=0;i<M;i++){
                int x = in.nextInt();
                fav[i] = w[x-1];
            }
            int Z = V/min;
            int[][][] dp = new int[V+1][N+1][Z+1];

        }
    }


    public static int binarySearch1(int[] A,double x){
        int i = 0;
        int j = A.length-1;
        while(i<=j){
            int mid = i+(j-i)/2;
            if(A[mid]>=x){
                j = mid-1;
            }else{
                i = mid+1;
            }
        }
        return i;
    }
    public static int binarySearch2(int[] A,double x){
        int i =0;
        int j = A.length-1;
        while (i<=j){
            int mid = i+(j-i)/2;
            if(A[mid]>x){
                j = mid -1;
            }else{
                i = mid + 1;
            }
        }
        return j;
    }
    public static void JuZhong(){
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[] w = new int[N];
        for(int i=0;i<N;i++){
            w[i] = in.nextInt();
        }
        Arrays.sort(w);
        int s = 0;
        for(int i=0;i<N;i++){
            int a = binarySearch1(w,w[i]*0.9);
            int b = binarySearch2(w,w[i]/0.9);
            System.out.println(a+" "+b);
            s+=(b-a);
        }
        System.out.println(s/2);
    }

    public static int BinarySearch(int[] A,int len,int w){
        int i = 0;
        int j = len-1;
        while (i<=j){
            int mid = i+(j-i)/2;
            if(A[mid]>w){
                j = mid-1;
            }else if(A[mid]<w){
                i = mid + 1;
            }else{
                return mid;
            }
        }
        return i;
    }
    public static int LIS(int[] A,int []B){
        int len = 1;
        B[0] = A[0];
        int i,pos = 0;
        for(i=1;i<A.length;i++){
            if(A[i]>B[len-1]){
                B[len] = A[i];
                len++;
            }else{
                pos = BinarySearch(B,len,A[i]);
                B[pos] = A[i];
            }
        }
        return len;
    }

    public static void YueGuangBao(){
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[] A = new int[N];
        int[] B = new int[N];
        for(int i=0;i<N;i++){
            A[i] = in.nextInt();
        }
        System.out.println(LIS(A,B));
    }

    public static void FuZaiCeShi(){
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[] A = new int[N];
        for(int i=0;i<N;i++){
            A[i] = in.nextInt();
        }
        int[] B = new int[N];
        int[] C = new int[N];
        int cur = A[0];
        for(int i=1;i<N;i++){
            if(A[i]>cur){
                B[i] = B[i-1];
                cur = A[i];
            }else{
                B[i] = B[i-1]+cur+1-A[i];
                cur++;
            }
        }
        cur = A[N-1];
        for(int i=N-2;i>=0;i--){
            if(A[i]>cur){
                C[i] = C[i+1];
                cur = A[i];
            }else{
                C[i] = C[i+1]+cur+1-A[i];
                cur++;
            }
        }
        int min = Math.min(B[N-1],C[0]);
        for(int i=1;i<N-1;i++){
            int x = Math.max(A[0]+i,A[N-1]+N-1-i);
            min = Math.min(min,B[i-1]+C[i+1]+x-A[i]);
        }
        System.out.println(min);
    }

    public static void FangCha(){
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[] A = new int[N];
        for(int i=0;i<N;i++){
            A[i] = in.nextInt();
        }
        Arrays.sort(A);
        double min = Double.MAX_VALUE;
        for(int i=1;i<N-1;i++){
            int s = A[i-1]+A[i]+A[i+1];
            double x = s/3.0;
            double ps = (A[i-1]-x)*(A[i-1]-x)+(A[i]-x)*(A[i]-x)+(A[i+1]-x)*(A[i+1]-x);
            ps = ps/3;
            min = Math.min(ps,min);
        }
        System.out.println(String.format("%.2f",min));
    }

    public static void ZhenZhu(){
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int N = in.nextInt();
        int[] A = new int[N];
        for(int i=0;i<N;i++){
            A[i] = in.nextInt();
        }
        Arrays.sort(A);
        int maxi = 0;
        int max =L - (A[N-1]-A[0]);
        for(int i=1;i<N;i++){
            if(A[i]-A[i-1]>max){
                maxi = i;
                max = A[i]-A[i-1];
            }
        }
        int left,right,flag;
        right = maxi;
        if(maxi==0){
            left = N-1;
            flag = -1;
        }else{
            left = maxi-1;
            flag = 1;
        }
        int s = 0;
        while(true){
            if(right>left){
                s += L - (A[right]-A[left]);
            }else{
                s += A[left]-A[right];
            }
            if(left==0){
                left = N-1;
            }else{
                left--;
            }
            if(left==right)
                break;
            if(right==N-1){
                right = 0;
            }else{
                right++;
            }
            if(left==right)
                break;
        }
        if(N%2==0){
            System.out.println(s-N*N/4);
        }else{
            System.out.println(s-(N*N-1)/4);
        }


    }

    boolean[] flag;
    int[][] map;
    HashSet<Integer> cur;
    public void DFS(int v){
        flag[v] = true;
        cur.add(v);
        for(int i=0;i<map.length;i++){
            if(i!=v && map[i][v]==1 && !flag[i]){
                DFS(i);
            }
        }
    }
    public void ShangShan(){
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int M = in.nextInt();
        boolean[] visited = new boolean[N];
        boolean[] v = new boolean[N];
        flag = new boolean[N];
        map = new int[N][N];
        for(int i=0;i<M;i++){
            int x = in.nextInt()-1;
            int y = in.nextInt()-1;
            visited[x] = true;
            v[y] = true;
            map[x][y] = 1;
            map[y][x] = 1;
        }

        int s = 0;
        for(int i=0;i<N;i++){
            if(flag[i]!=true){
                cur = new HashSet<>();
                DFS(i);
                int a = 0;
                int b = 0;
                for(int x:cur){
                    System.out.println(x);
                    if(visited[x]==false){
                        a++;
                    }
                    if(v[x]==false){
                        b++;
                    }
                }
                s+=Math.max(a,b);
            }
        }
        System.out.println(s);

    }

    public static void NS(){
        Scanner in = new Scanner(System.in);
        int mod = 1000000007;
        int N = in.nextInt();
        int S = in.nextInt();
        int ret = 1;
        for(int i=1;i<=N-1;i++){
            ret = (ret*(S-i)/i)%mod;
        }
        System.out.println(ret);
    }

    public static void main(String[] args){
        HelloWorld test = new HelloWorld();
//        test.ShangShan();
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("1.00");
        System.out.println(a.equals(b));
        System.out.println(a==b);
        System.out.println(a.compareTo(b)==0);
//        ZhenZhu();
//        FangCha();
//        FuZaiCeShi();
//        YueGuangBao();
//        JuZhong();
        //Robot();
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        int k = in.nextInt();
    }

}
