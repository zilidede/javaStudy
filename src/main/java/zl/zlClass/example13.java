package zl.zlClass;

import com.jimmysun.algorithms.chapter1_3.Queue;
import com.jimmysun.algorithms.chapter1_3.Stack;

/*
 * @Description: 背包队列栈
 * @Param:
 * @Author: zl
 * @Date: 2019-03-23 19:03
 */
public class example13 {
    public  static  Boolean IsInteger(Character c ){
        int iASCII= (int)c;
        if (iASCII>=48 && iASCII<=57 )
            return true;
        else
            return false;
    }
    public  static  Boolean IsOperator(Character c ){

        int iASCII= (int)c;
        if (c.equals('+')||(c.equals('-')))
            return true;
        else
            return false;
    }
    public static Double getArithmeticExpress(String sExpression){
        double dR =0;
        Stack <Integer> numStk= new Stack<Integer>();
        Stack <Character> operateStk= new Stack<Character>();
        for(int i=0;i<sExpression.length();i++){
            Character t=sExpression.charAt(i);
            if (IsInteger(t)){
                numStk.push(Integer.valueOf(String.valueOf(t)));
            }
            else if(IsOperator(t))
            {
                operateStk.push(t);
            }
            else if(t.equals('(')){
                continue;
            }
            else if(t.equals(')')){
                int dSum = 0;
                Character c= operateStk.pop();
                Integer ic= numStk.pop();
                if (c.equals('+')){

                    dSum= ic + numStk.pop()  ;

                }
                else if(c.equals('-')){
                     dSum=numStk.pop() - ic  ;

                }
                numStk.push(dSum);
            }
        }
        while (!operateStk.isEmpty()){
            Character c= operateStk.pop();
            Integer ic= numStk.pop();
            int dSum = 0;

            if (c.equals('+')){

                dSum= ic + numStk.pop()  ;

            }
            else if(c.equals('-')){
                dSum=numStk.pop() - ic  ;

            }
            numStk.push(dSum);
        }
        dR = numStk.pop();
        return dR;
    }
    public static String examle139(String [] args){
        Stack<String> ops = new Stack<>();
        Stack<String> vals = new Stack<>();
        for (String s : args) {
            if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")){
                ops.push(s);
            }else if(s.equals(")")){
                String firstString=vals.pop();
                String secondString=vals.pop();
                String newString ="("+secondString+ops.pop()+firstString+")";
                vals.push(newString);
            }else{
                vals.push(s);
            }
        }
        return vals.pop();
    }
    //操作符笔记 return-ture op1<=op2  false>
    public static boolean compareOfOperator(String op1,String op2){
        if (op1.equals("")||op2.equals(""))
            return false;
        if ((op1.equals("*")||op1.equals("/"))&&(op2.equals("+")||op2.equals("-"))){
            return true;
        }
        else
            return false;
    }
    public static String InfixToPosfix(String[] Infix){
        Stack<String> ops = new Stack<>();
        Queue<String> ouq= new Queue<>();
        String tOps="";
        for(String s : Infix){
            if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")) {

                while (true)
                {
                    if(ops.isEmpty())
                        break;
                    else
                        tOps= ops.peek();
                    if (tOps.equals("("))
                        break;
                    if (compareOfOperator(s,tOps)){
                        break;
                    }
                    else
                    {
                        tOps= ops.pop();
                        ouq.enqueue(tOps);
                    }
                }
                ops.push(s);
            }
            else if(s.equals("(")){
                ops.push(s);
            }
            else if (s.equals(")")){
                while (!ops.isEmpty()){

                    String  tOp=ops.pop();
                    if (tOp.equals("("))
                            break;
                    else
                        ouq.enqueue(tOp);
                }
            }



            else {
                ouq.enqueue(s);
            }

        }
        while (!ops.isEmpty()){
            ouq.enqueue(ops.pop());
        }
        String rs="";
        while (!ouq.isEmpty()){
            rs=rs+ouq.dequeue();
        }
        return rs;

    }

   public static class myStack <Item> extends Stack implements Cloneable  {
       public myStack<Item> copy(myStack<Item> im) {
            myStack<Item> r = null;
            try {
                 r =(myStack<Item>) this.clone();
            }
            catch(CloneNotSupportedException e){
                e.printStackTrace();
           }
           return r ;

       }

       /* @Override
       protected Object clone() throws CloneNotSupportedException {
           return super.clone();
       }
       */

   }
    public  static  void  test(){
        String [] args ={"1","+","2",")","*","3","-","4",")","*","5","-","6",")",")",")"};
        String [] args1 ={"2","*","3","/","(","2","-","1",")","+","3","*","(","4","-","1",")"};
        String s1="";
        for(String s:args1){
            s1 = s1+s;
        }
       // System.out.printf(s1);
       // System.out.println(InfixToPosfix(args1));
        myStack <String>  ms= new myStack<String>();
        ms.push("1");
        ms.push("2");

        myStack <String>  ms1= (myStack <String>) ms.copy(ms);
        System.out.println(ms.pop());
        System.out.println(ms1.pop());

    }
    public static void main(String[] args) {
        test();
    }
}
