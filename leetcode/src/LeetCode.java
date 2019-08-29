import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;
import org.omg.PortableInterceptor.INACTIVE;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

public class LeetCode {

    public int sortedSubSequence(int[] A){
        int n = A.length;
        if(n==0){
            return 0;
        }
        if(n<=2)
            return 1;
        int ret = 1;
        int flag = 0;
        for(int i=1;i<n;i++){

        }
        return ret;
    }

    public static int Shangpin(int x,int[] A){
        int[] dp = new int[x+1];
        dp[0] = 1;
        Arrays.sort(A);
        for(int i=1;i<=x;i++){
            dp[i] = 0;
            for(int j=0;j<A.length;j++){
                if(i-A[j]<0)
                    break;
                dp[i] += dp[i-A[j]];
            }
        }
        return dp[x];
    }

    public static void Biji(){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] A = new int[n];
        for(int i=0;i<n;i++){
            A[i] = in.nextInt();
        }
        int[] dp1 = new int[n+1];
        int[] dp2 = new int[n+1];
        dp1[0] = 0;
        dp2[0] = 0;
        dp1[1] = A[0];
        dp2[1] = 1;
        for(int i=2;i<=n;i++){
            dp1[i] = dp1[i-1];
            dp2[i] = dp2[i-1];
            if(dp1[i-2]+A[i-1]>dp1[i]){
                dp1[i] = dp1[i-2]+A[i-1];
                dp2[i] = dp2[i-2]+1;
            }else if(dp1[i-2]+A[i-1]==dp1[i] && dp2[i-2]+1<dp2[i]){
                dp1[i] = dp1[i-2]+A[i-1];
                dp2[i] = dp2[i-2]+1;
            }
        }
        System.out.println(dp1[n]+" "+dp2[n]);
    }

    public int attackMoWu(int N,int T,int M,int[] H){
        int i,low,high,mid;
        Arrays.sort(H);
        high = 0;
        for(i=0;i<N-M;i++){
            high += H[i];
        }
        if(high>T-M){
            return -1;
        }
        high = H[N-1];
        low = 0;
        while (low<high){
            mid = (high-low)/2+low;
            int[] remain = Arrays.copyOf(H,N);
            int s = M;
            for(i=0;i<N;i++){
                int p = remain[i]/mid;
                if(s>p){
                    s -= p;
                    remain[i] = remain[i]%mid;
                }else{
                    remain[i] = remain[i]-s*mid;
                }
            }
        }
        return 0;
    }

    public static void MoWu(){
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int T = in.nextInt();
        int M = in.nextInt();
        int[] H = new int[N];
        for(int i=0;i<N;i++){
            H[i] = in.nextInt();
        }
        Arrays.sort(H);
        int i,j,k,s =0;
        for(i=0;i<N-M;i++){
            s += H[i];
        }
        if(s>T-M){
            System.out.println(-1);
            return;//无法通关
        }
        for(i=N-M;i<N;i++){
            s += H[i];
        }
        s -= (T-M);
        int x = s/M;
        while(true){
            j = 0;
            for(i=0;i<N;i++){
                j += H[i]%x;
            }
            if(j<=T-M){
                break;
            }
            x++;
        }
        System.out.println(x);
    }


    public static void Chongdie(){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        in.nextLine();
        String t = in.nextLine();
        int i,j;
        for(i=1;i<n;i++){
            for(j=0;j+i<n;j++){
                if(t.charAt(j)!=t.charAt(i+j)){
                    break;
                }
            }
            if(j+i==n)
                break;
        }
        String ret = t;
        for(j=0;j<k-1;j++){
            ret = ret + t.substring(n-i,n);
        }
        System.out.println(ret);
    }

    public static void BeiBao(){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] w = new int[n];
        int s = 0;
        for(int i=0;i<n;i++){
            w[i] = in.nextInt();
            s += w[i];
        }
        HashSet<Integer> dp[][] = new HashSet[n+1][s/2+1];
        for(int i=0;i<=n;i++){
            for(int j=0;j<=s/2;j++){
                dp[i][j] = new HashSet<>();
            }
        }
        for(int i=1;i<=n;i++){
        }
    }

    public static int test(){
        int i = 10;
        try{
            i = i/0;
            return --i;
        }catch (Exception e){
            --i;
            return i--;
        }finally {
            --i;
            return i--;
        }
    }

    public static void HeChangdui(){
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[][] H = new int[N][2];
        for(int i=0;i<N;i++){
            H[i][0] = in.nextInt();
            H[i][1] = i;
        }
        Arrays.sort(H,(a,b)->(a[0]-b[0]));
        int count = 0;
        for(int i=0;i<N;i++){
            if(H[i][1]>=i)
                count++;
        }

//        int cur = H[0][1];
//        int max = H[0][0];
//
//
//
//
//        for(int i=0;i<N;i++){
//            if(H[i][1]>cur){
//                count++;
//                cur = H[i][1];
//                max = H[i][0];
//            }else{
//                if(H[i][0]>max){
//                    max = H[i][0];
//                    cur = Math.max(cur,H[i][1]);
//                }
//            }
//        }
        System.out.println(count);
    }

    public static void KaoChangAnPai(){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        HashMap<Integer,HashSet<Integer>> man = new HashMap<>();
        HashMap<Integer,HashSet<Integer>> woman = new HashMap<>();
        for(int i=0;i<n;i++){
            int x =in.nextInt();
            int y = in.nextInt();
            HashSet<Integer> a;
            HashSet<Integer> b;
            if(man.containsKey(x)){
                a = man.get(x);
            }else{
                a = new HashSet<>();
                man.put(x,a);
            }
            if(woman.containsKey(y)){
                b = woman.get(y);
            }else{
                b = new HashSet<>();
                woman.put(y,b);
            }
            a.add(y);
            b.add(x);
        }
        List<Integer> ret = new ArrayList<>();
        int remain = m;
        while (remain>0){
            int remove_man = 0;
            int remove_num = 0;
            for(int x:man.keySet()){
                if(man.get(x).size()>remove_num){
                    remove_man = x;
                    remove_num = man.get(x).size();
                }
            }
            int remove_w = 0;
            int w_num = 0;
            for(int x:woman.keySet()){
                if(woman.get(x).size()>w_num){
                    w_num = woman.get(x).size();
                    remove_w = x;
                }
            }
            if(w_num>remove_num){
                remain -= w_num;
                ret.add(remove_w);
                HashSet<Integer> tmp = woman.get(remove_w);
                for(int x:tmp){
                    man.get(x).remove(remove_w);
                }
                woman.remove(remove_w);
            }else{
                ret.add(remove_man);
                remain -= remove_num;
                HashSet<Integer> tmp = man.get(remove_man);
                for(int x:tmp){
                    woman.get(x).remove(remove_man);
                }
                man.remove(remove_man);
            }
        }
        Collections.sort(ret);
        System.out.println(ret.size());
        for(int x:ret){
            System.out.println(x+" ");
        }
    }

    public static void KuaiSHou1(){
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        in.nextLine();
        String[] abc = new String[m];
        for(int i=0;i<m;i++){
            abc[i] = in.nextLine();
        }
        boolean[] ret = new boolean[m];
        for(int i=0;i<m;i++){
            String str = abc[i];
            String[] version = str.split(" ");
            String s1 = version[0];
            String s2 = version[1];
            String[] v1 = s1.split("\\.");
            String[] v2 = s2.split("\\.");
            boolean flag = false;
            for(int j=0;j<v2.length;j++){
                if(j<v1.length){
                    if(Integer.valueOf(v2[j])>Integer.valueOf(v1[j])){
                        flag = true;
                        break;
                    }else if(Integer.valueOf(v2[j])<Integer.valueOf(v1[j])){
                        flag = false;
                        break;
                    }
                }else{
                    if(Integer.valueOf(v2[j])>0){
                        flag = true;
                        break;
                    }
                }
            }
            ret[i] = flag;
        }
        for(int i=0;i<m;i++)
            System.out.println(ret[i]);
    }

    public static void KuaiSHou2(){
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int[] input = new int[m];
        for(int i = 0; i<m; i++){
            input[i] = in.nextInt();
        }
        for(int i = 0; i< m; i++){
            HashSet<Integer> hasbeen = new HashSet<>();
            int temp = getnums(input[i]);
            boolean flag = true;
            while(temp != 1 ){
                if(hasbeen.contains(temp)){
                    flag = false;
                    System.out.println(flag);
                    break;
                }
                hasbeen.add(temp);
                temp = getnums(temp);
            }
            if(flag){
                System.out.println(flag);
            }
        }
    }

    public static int getnums(int n){
        int result = 0;
        while(n>0){
            result = result + (n%10)*(n%10);
            n = n/10;
        }
        return result;
    }

    public static void KuaiSHou3(){
        Scanner in = new Scanner(System.in);
        String str1 = in.nextLine();
        String str2 = in.nextLine();
        String[] ss1 = str1.split(" ");
        String[] ss2 = str2.split(" ");
        String ret = "";
        int i,j = 0;
        for(i=0;i<ss1.length;i++){
            ret = ret + ss1[i] + " ";
            if(i%4==3 && j<ss2.length){
                ret = ret + ss2[j] + " ";
                j++;
            }
        }
        for(;j<ss2.length;j++){
            ret = ret + ss2[j] + " ";
        }
        System.out.println(ret.trim());
    }


//    static int[][] map;
    static HashSet<Integer>[] map;
    static boolean[] visited;
    static int N;
    public static int DFS(int v){
        visited[v] = true;
        int ret = 1;
        for(int x:map[v]){
            if(!visited[x]){
                ret += DFS(x);
            }
        }
        return ret;
    }

    public static void KuaiSHou4(){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        N = n;
        visited = new boolean[n];
        map = new HashSet[n];
        for(int i=0;i<n;i++){
            map[i] = new HashSet<>();
        }
        for(int i=1;i<n;i++){
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            if(w==0){
                map[u-1].add(v-1);
                map[v-1].add(u-1);
            }
        }
        List<Integer> res = new ArrayList<>();
        for(int i=0;i<n;i++){
            if(!visited[i]){
                int x = DFS(i);
                res.add(x);
            }
        }
        int mod = 1000000007;
        Long ret = 1L;
        for(int i=0;i<k;i++){
            ret = (ret*n)%mod;
        }
        for(int x:res){
            Long tmp = 1L;
            for(int i=0;i<k;i++){
                tmp = (tmp*x)%mod;
            }
            if(ret>=tmp){
                ret = ret-tmp;
            }else{
                ret = ret+mod-tmp;
            }
        }
        System.out.println(ret);
    }


    public static void JiaoHuan(){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        String[] values = new String[n];
        String[] op = new String[n-1];
        String line = in.nextLine();
        String[] coms = line.split(" ");
        int i,j;
        for(i=0;i<n-1;i++){
            values[i] = coms[2*i];
            op[i] = coms[2*i+1];
        }
        values[i] = coms[2*i];
        while(true){
            i = 0;
            for(j=i;j<n-1;j++){
                if(!op[j].equals(op[i]))
                    break;
            }
            if(op[i].equals("*")){
                Arrays.sort(values,i,j+1);
            }else if(op[i].equals("/")){

            }else if(op[i].equals("+")){

            }else if(op[i].equals("-")){

            }
        }




    }

    public String removeKdigits(String num, int k) {
        if(k==0) return num;
        if(k==num.length()) return "0";
        char[] str = num.toCharArray();
        int length = num.length();
        int i;
        while (k!=0){
            int flag = 1;
            for(i=0;i<length-1;i++)
                if(str[i]>str[i+1]){
                    flag = 0;
                    break;
                }
            if(flag==0){
                for(;i<length-1;i++)
                    str[i] = str[i+1];
            }
            length--;
            k--;
        }
        for(i = 0;i<length;i++){
            if(str[i]!='0')
                break;
        }
        k = i;
        if(length-i==0)
            return "0";
        char[] tmp = new char[length-i];
        for(;i<length;i++){
            tmp[i-k] = str[i];
        }
        return String.valueOf(tmp);
    }

    class RecentCounter {

        LinkedList<Integer> queue = new LinkedList<>();
        public RecentCounter() {

        }

        public int ping(int t) {
            queue.offerLast(t);
            while(true){
                int x = queue.peekFirst();
                if(x<t-3000 && x!=t)
                    queue.pollFirst();
                else
                    break;
            }
            return queue.size();
        }

    }

    public static void main(String[] args){
//        System.out.println("1 2 ".trim());
        LeetCode test = new LeetCode();
        Scanner in = new Scanner(System.in);

        System.out.println(test.removeKdigits("102009",3));

        String s1 = new String("123");
        String s2 = new String("123");
        System.out.println(s1==s2);

        System.out.println("1.1.1.1".replace(".","[.]"));
//        KuaiSHou4();


        int[] abc = {5,9,1,4,3};
        Arrays.sort(abc,2,3);
        for(int x:abc){

        }




//        KaoChangAnPai();
//        HeChangdui();

//        while (true){
//            Chongdie();
//        }
//        MoWu();
//        Biji();
//        Scanner in = new Scanner(System.in);
    }



    class Solution {
        public String pushDominoes(String dominoes) {
            int N = dominoes.length();
            int[] indexes = new int[N+2];
            char[] symbols = new char[N+2];
            int len = 1;
            indexes[0] = -1;
            symbols[0] = 'L';

            for (int i = 0; i < N; ++i)
                if (dominoes.charAt(i) != '.') {
                    indexes[len] = i;
                    symbols[len++] = dominoes.charAt(i);
                }
            indexes[len] = N;
            symbols[len++] = 'R';
            char[] ans = dominoes.toCharArray();
            for (int index = 0; index < len - 1; ++index) {
                int i = indexes[index], j = indexes[index+1];
                char x = symbols[index], y = symbols[index+1];
                char write;
                if (x == y) {
                    for (int k = i+1; k < j; ++k)
                        ans[k] = x;
                } else if (x > y) { // RL
                    for (int k = i+1; k < j; ++k)
                        ans[k] = k-i == j-k ? '.' : k-i < j-k ? 'R' : 'L';
                }
            }
            return String.valueOf(ans);
        }
    }

}
