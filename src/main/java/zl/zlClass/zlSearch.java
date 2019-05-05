package zl.zlClass;

import edu.princeton.cs.algs4.StdRandom;

/*
 * @Description: 查找单元
 * @Param:
 * @Author: zl
 * @Date: 2019/4/4 22:29
 */
public class zlSearch {
    public static boolean BinarySearch(int key,int [] iArr){
        int lo = 0;
        int hi=iArr.length;
        int mid = 0;
        while (lo<hi){
             mid = lo +(hi -lo)/2;
             if(iArr[mid]<key) {
                 lo=mid+1;
             }
             else if(iArr[mid]>key) {
                 hi=mid - 1;
             }
             else  {
                 return true;
             }
        }
        return false;
    }
    public static void main(String[] args) {
        int len = StdRandom.uniform(6);
        int a[] = new int[100];
        for(int i =0;i<100;i++)
            a[i] = i;
        System.out.println(BinarySearch(99,a));

    }
}
