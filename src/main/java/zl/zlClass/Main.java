package zl.zlClass;

public class Main {



    public  static  void getI(String s){
        String s1="1312321321";
        s=s1;
    }
    public static  void getaRR(int [] iArr){
        for(int i=0;i<iArr.length ;i++){
            iArr[i] = 12;
        }
    }
    public static void main(String[] args) {
        String s ="1111";
        int [] iArr={1,2,3,4};
        for(int i=0;i<iArr.length ;i++){
            System.out.println(iArr[i]);
        }
        System.out.println(s);
        getaRR(iArr);
        for(int i=0;i<iArr.length ;i++){
            System.out.println(iArr[i]);
        }
        System.out.println(s);

    }
}
