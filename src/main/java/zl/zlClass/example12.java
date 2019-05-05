package zl.zlClass;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * @Description: 算法习题1.2
 * @Param:
 * @Author: zl
 * @Date: 2019-03-21 18:08
 */
public class example12 {
   static class smartDate extends zlDate{
       private final int month;
       private final int day;
       private final int year;
        public smartDate(String date) {
            String[] fields = date.split("/");

            month = Integer.parseInt(fields[0]);
            day = Integer.parseInt(fields[1]);
            year = Integer.parseInt(fields[2]);


        }
        public String dayofWeek(){
            int m =month;
            int d=day;
            int y= year;
            if(m==1) {
                m=13;
                y--;
            }
            if(m==2){
                m=14;
                y--;
            }
            int week=(d+2*m+3*(m+1)/5+y+y/4-y/100+y/400)%7;
            String weekstr="";
            switch(week)
            {
                case 0: weekstr="星期一"; break;
                case 1: weekstr="星期二"; break;
                case 2: weekstr="星期三"; break;
                case 3: weekstr="星期四"; break;
                case 4: weekstr="星期五"; break;
                case 5: weekstr="星期六"; break;
                case 6: weekstr="星期日"; break;
            }
            return weekstr;
        }
    }
    public static  void example1211(){
        smartDate tSD = new smartDate("03/23/2019");
        System.out.println(tSD.dayofWeek());
    }
    public  static  void example121(){
        int n=100;
        double Distance ;
        double minDistance=4455;
        double m=2.0;
        Point2D[] points=new Point2D[n] ;
        for (int i=0;i<n;i++){
            points[i] =new Point2D(Math.random(),Math.random());
            points[i].draw();
        }
        for (int i=0;i<n;i++){
            for(int j=i+1;j<n;j++) {
                double iXValue= points[i].x() -points[j].x();
                double iYValue =points[i].y() -points[j].y();
                Distance = Math.sqrt(Math.pow(iXValue,m)+Math.pow(iYValue,m));
                if (minDistance>Distance)
                    minDistance=Distance;
            }
        }
        System.out.println("Min distance: " + minDistance);


        if (n > 1) {
            double min = points[0].distanceTo(points[1]);
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (points[i].distanceTo(points[j]) < min) {
                        min = points[i].distanceTo(points[j]);
                    }
                }
            }
            System.out.println("Min distance: " + min);
        }
    }


    public static  void BinarySearch(int [] a,int key,count icount){
        int lo=0;
        int hi=a.length ;
        while (lo<hi){
            icount.Inc();
            int mid= (hi-lo)/2+lo;
            if(a[mid]<key){
                lo=mid+1;
            }
            else if (a[mid]>key) {
                hi= mid - 1;
            }
            else
                break;

        }
        System.out.println(icount.toString());
    }
    public static  void example129(){
        int [] a={1,2,3,4,5,6,7,8,9,10};
        int key=1;
        count icount = new count("");
        BinarySearch(a,key,icount);
    }
    public static  void example126(){
        int N=50;
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); //第一行为单词个数N
        ArrayList<String> list = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < N; i ++) {
            String s = sc.next(); //输入并存储每行的单词。此处必须调用next（）方法
            if (!list.contains(s)) {
                count ++;
                list.add(s);
                for (int j = 0; j < s.length() - 1; j ++) {
                    //以下注释是另一种方法，把可能的循环单词加入list，
                    //思路：把要测试的单词后再重复下这个单词，如：word ，变成 wordword
                    String str= new String();
                    str=s.concat(s);
/*                    str.append(s);
                      sb.insert(sb.length(), x) 都可实现,用StringBuffer类即可实现
                   str.append(s); sb.insert(sb.length(), x) */
                    String b= str.substring(j, s.length() + j);
                    list.add(b);
                }


            }
        }
        System.out.println(count);

    }
    public static  String mystery(String s){
        int N=s.length();
        if(N<=1)return s;
        String a=s.substring(0,N/2);
        String b=s.substring(N/2,N);
        return mystery(a)+mystery(b);

    }
    public static  void example127(){
        System.out.println(mystery("923456"));
    }
    public static  void example124_5(){
        String s1="hwo";
        String s="你好,world";
        String s2=s1;
        s1="123";
        int [] arr1={1,2,3,5,6};
        int [] arr2=arr1;
        arr1[3]=8;
        s= s.toUpperCase();
        s=s.toLowerCase();

        for(int i=0;i<s.length();i++){
            System.out.println(s.charAt(i));
        }
        s=s.substring(0,2);
        System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println( arr1[3]);
        System.out.println( arr2[3]);

    }
    public static  void example122(){
        int N =4;
        for (int i=0;i<N;i++){
           // double d= StdIn.readDouble();
        }
        Interval1D[] intervals = new Interval1D[N];

        for (int i = 0; i < N; i++) {
            intervals[i] = new Interval1D(StdIn.readDouble(), StdIn.readDouble());
        }
        if (N > 2) {
            for (int i = 0; i < N - 1; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (intervals[i].intersects(intervals[j])) {
                        System.out.println(intervals[i] + " intersects " + intervals[j]);
                    }
                }
            }
        }
    }
    public static  void example123(){
        int N = 10;
        double min = 0;
        double max = 9;
        StdDraw.setXscale(min, max);
        StdDraw.setYscale(min, max);
        Point2D[] leftTopPoints = new Point2D[N];
        Point2D[] rightBottomPoints = new Point2D[N];
        Interval2D[] intervals = new Interval2D[N];
        for (int i = 0; i < N; i++) {
            double a = StdRandom.uniform(min, max);
            double b = StdRandom.uniform(min, max);
            double left, right, top, bottom;
            Interval1D x, y;
            if (a < b) {
                left = a;
                right = b;
            } else {
                left = b;
                right = a;
            }
            x = new Interval1D(left, right);
            a = StdRandom.uniform(min, max);
            b = StdRandom.uniform(min, max);
            if (a < b) {
                top = a;
                bottom = b;
            } else {
                top = b;
                bottom = a;
            }
            y = new Interval1D(top, bottom);
            leftTopPoints[i] = new Point2D(left, top);
            rightBottomPoints[i] = new Point2D(right, bottom);
            intervals[i] = new Interval2D(x, y);
            intervals[i].draw();
        }
        int containNum = 0, intervalNum = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < N; j++) {
                if (j > i && intervals[i].intersects(intervals[j])) {
                    intervalNum++;
                }
                if (j != i && intervals[i].contains(leftTopPoints[j]) && intervals[i].contains(rightBottomPoints[j])) {
                    containNum++;
                }
            }
        }
        System.out.println("Interval count: " + intervalNum);
        System.out.println("Contain count: " + containNum);
    }
    public static  void example1215(){
        String s="123"+"\n"+"321"+"\n"+"321"+"\n"+"321";
        String [] fileds= s.split("\n");
        System.out.println(fileds[0]);
    }
    public static  void example120(){
        System.out.println("");
    }

    public static void main(String[] args) {

        example1215();

    }
}
