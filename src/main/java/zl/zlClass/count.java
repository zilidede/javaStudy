package zl.zlClass;

/*
 * @Description: 计数器
 * @Param:
 * @Author: zl
 * @Date: 2019-03-20 15:16
 */

public class count implements  Comparable <count>{
    //private
    private String fs="";
    private int iCount;
    public  count(String s){
        fs =s;
        iCount = 0;
    }
    public  void Inc(){
        iCount =iCount + 1;
    }
    public  int GetCount(){
        return iCount;
    }
    public  String toString(){
        return iCount+fs;
    }
    @Override
    public int compareTo(count that){
        if      (this.iCount < that.iCount) return -1;
        else if (this.iCount > that.iCount) return +1;
        else                              return  0;
    }

    public static void main(String[] args) {

        count ec= new count("one");
        ec.Inc();
        count ec1= ec;
        ec1.Inc();
        System.out.println(ec.toString());


    }



}
