package zl.zlClass;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/*
 * @Description: union-find 问题
 * @Param:
 * @Author: zl
 * @Date: 2019/4/11 15:20
 */
public class tUF {
    public  tUF(int n){
        id = new int[n];
        count =n;
        for(int i = 0;i<n;i++){
            id[i] =i;
        }
    }
    private int [] id=null;
    private int count;

    public   void union(int p,int q){
        int pRoot =find(p);
        int qRoot =find(q);
        if (pRoot ==qRoot) return;
        id[pRoot] = qRoot;
        count--;
    }
    public   int find(int p){
        while (p!=id[p]) p=id[p];
        return  p;
    }
    public   int count(){
        return count();
    }
    public   boolean connect(int p,int q){
        return find(p)==find(q);
    }
    public static void main(String[] args) {
            int N =10;
            tUF uf = new tUF(N);
            while (!StdIn.isEmpty()){
                int p = StdIn.readInt();
                int q=StdIn.readInt();
                if (uf.connect(p,q)) continue;
                uf.union(p,q);
                StdOut.println(p+" " + q);
            }
            StdOut.println(uf.count()+"components");


    }
}
