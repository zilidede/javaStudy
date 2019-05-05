package zl.zlClass;

import java.io.UnsupportedEncodingException;

/*
 * @Description: 基本类型转换
 * @Param:
 * @Author: zl
 * @Date: 2019-03-17 11:47
 */
public class zlTypeConvern {
    //private

    //protected
    //public
    // 判断对象类型 该函数只能判断引用类型
    public  static  String  GetValueTypes(Object v){
        return v.getClass().toString();
    }
    // return  -1- error 0-integer
    public  static  Integer  GetValueType(Object v){
        return   -1;
       // if  (v.getClass().toString().equals("class java.lang.Integer")){
      //      return  0;
       // }
    }
    // 基本类型与引用类型互相转换 自动装包和卸包
    // 引用类型之间的转换
    public  Integer LongToInt(Long value){
       return value.intValue();
    }
    public    String IntToStr(Integer value){
        return value.toString();
    }

    public  static Integer StrToInt(String value){
        return Integer.valueOf(value);
    }// string 只能为数字
    public  static  String DoubleToStr(Double value){
        return value.toString();
    }
    public static  Double StringToDouble(String  value){
        return  Double.valueOf(value);
    }
    public static char[] StringToCharArr(String value){
        return  value.toCharArray();
    }
    public static String CharArrToString(char [] value){
        return  String.valueOf(value);
    }
    public static char byte2char(byte b) {
        char cR=(char) (b & 0xff);
        return (char) (b & 0xff);
    }

    public static  double intToDouble(int i){
        Integer I = new Integer(i);
        return I.doubleValue();
    }
    public  static int [] strArrTointArr(String[] strArr){
        int [] rIArr=new int[strArr.length];
        int i=0;
        int ir=0;
        for(String s:strArr){
            s=s.trim();
            rIArr[i] =Integer.parseInt(s);
            i++;
        }
        return rIArr;
    }
    public static String byteArrToString(byte [] bArr,String characterEncoding){
        String  rs=null;
        try{
            rs= new String(bArr,characterEncoding);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }

        return  rs;
    }
    public static void main(String[] args) {
        byte[] array = { 100, -76, -77, -78 };
        char[] code = new char[4];
        for (int i = 0; i < code.length; i++) {
            code[i] = byte2char(array[i]);
            System.out.println(code[i]);
        }
        String value1 = "356.1313";
        char [] value = {'3','5','1'};
        for (char ab:value){
            System.out.println(ab);
        }
        String sOut=String.format("当前输出的结果是%s,类型是%s, ", CharArrToString(value), GetValueTypes(CharArrToString(value)));
        System.out.println(sOut);
    }
}
