package zl.zlClass;

/*
 * @Description: 计算三整数数量为0
 * @Param:
 * @Author: zl
 * @Date: 2019/3/28 21:27
 */

import edu.princeton.cs.algs4.StdRandom ;

public class ThreeSum {
    public static int count(int[] a) {
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    public static  double timeTrial(int N){
        int max = 10000;
        int []a=new int[N];
        for(int i = 0;i<N;i++){
            a[i]= StdRandom.uniform(-max,max);
        }
        long statTime= System.currentTimeMillis();
        count(a);
        double cost= (System.currentTimeMillis()-statTime)/1000;
        return  cost;

    }
    public static void testMain1(){
        for(int i=250;i<100000;i=i*2){

            String logs= String.format("%d任务,耗时%f秒",i, timeTrial(i));
            System.out.println(logs);

        }
    }
    public static void main(String[] args) {

        testMain1();
        System.exit(0);
        zlFileIo f = new zlFileIo();
        String content =f.ReadFile("./data/4Kints.txt","utf-8");
        String[] fileContentArr = new String(content).split("\n");
        long statTime= System.currentTimeMillis();
        int [] iArr=zlTypeConvern.strArrTointArr(fileContentArr);
        count(iArr);
        double cost= (System.currentTimeMillis()-statTime)/1000;
        String logs= String.format("%s任务,耗时%f秒","统计4Kints.txt文件三个数相加为0的次数",cost);

        System.out.println(logs);
    }
}
