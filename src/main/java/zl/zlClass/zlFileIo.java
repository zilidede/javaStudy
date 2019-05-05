package zl.zlClass;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/*
 * @Description: 文件io
 * @Param:
 * @Author: zl
 * @Date: 2019-03-17 19:06
 */
public class zlFileIo {
    private Runtime r = Runtime.getRuntime(); // 性能消耗内存
    private Long cost;
    // file
    public static boolean CreateFile(String filePath ){
           File f = new File(filePath) ;
           String dir= f.getPath().replace(f.getName(),"");
           File d= new File(dir);
           if (!d.isDirectory())
               CreateDir(dir);

           if (!f.exists()) {
               try {
                   f.createNewFile();
               }
               catch(IOException e){
                   e.printStackTrace();
                   return false;
               }

           }
           return  true;
    }
    public static boolean DeteleFile(String filePath){
        File f = new File(filePath) ;
        if ( f.exists() ){
            f.delete();
        }
        return  true;

    }
    public static  boolean RemoveFile(String oFilePath,String nFilePath){
        File f = new File(oFilePath) ;
        String dir= nFilePath.replace(f.getName(),"");
        CreateDir(dir);
        File f1 = new File(nFilePath);
        if (! f.exists() ){
            return false;
        }
        else {
            f.renameTo(f1);
        }
        return  true;
    }
    //java7
    public static  boolean CopyFile(String oFilePath,String nFilePath){
        File f = new File(oFilePath) ;
        String dir= nFilePath.replace(f.getName(),"");
        CreateDir(dir);
        File f1 = new File(nFilePath);
        if (! f.exists() ){
            return false;
        }
        else {

            try {
                Files.copy(f.toPath(),f1.toPath(),StandardCopyOption.REPLACE_EXISTING);
            }
            catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
        return  true;
    }
    //dir
    public static  boolean CreateDir(String dirPath){
        File dir = new File(dirPath) ;
        if (!dir.exists()){
            if (dir.mkdirs())
                    return true;
            else
                return false;
        }
        else
            return true;
    }
    public static  boolean DeleteDir(String dirPath){
        File dir = new File(dirPath) ;
        if (!dir.exists())
            return true;
        else{
            if (dir.isFile())
                  dir.delete();
            else
                for (File f:dir.listFiles())
                    DeleteDir(f.getPath());

            }
        return dir.delete();
    }
    public static  boolean RemoveDir(String srcDirPath,String desDirPath){
        File sDir = new File(srcDirPath) ;
        File dDir = new File(desDirPath);
        String tDirPath =  srcDirPath;
        if (!sDir.exists())
            return  true;
        if (sDir.isFile()){
            RemoveFile(sDir.getPath(),dDir.getPath());
        }
        else{
            desDirPath = dDir.getPath()+srcDirPath.replace(tDirPath,"");
            File dir = new File(desDirPath);
            if (!dir.exists())
                CreateDir(dir.getPath());
            for (File f:sDir.listFiles()) {
                String desDirPath1 =desDirPath +f.separator+ f.getName();
                RemoveDir(f.getPath(), desDirPath1);
            }
            DeleteDir(srcDirPath);

        }
        return false ;

    }
    public static  boolean CopyDir(String srcDirPath,String desDirPath){
        File sDir = new File(srcDirPath) ;
        File dDir = new File(desDirPath);
        String tDirPath =  srcDirPath;
        if (!sDir.exists())
            return  true;
        if (sDir.isFile()){
            CopyFile(sDir.getPath(),dDir.getPath());
        }
        else {
            desDirPath = dDir.getPath()+srcDirPath.replace(tDirPath,"");
            File dir = new File(desDirPath);
            if (!dir.exists())
                CreateDir(dir.getPath());
            for (File f:sDir.listFiles()) {
                String desDirPath1 =desDirPath +f.separator+ f.getName();
                CopyDir(f.getPath(), desDirPath1);
            }
            //DeleteDir(srcDirPath);

        }
        return true ;

    }
    public static  String ReadFile(String filePath,String fileCoding){
        /*
        * @Description: 获取文件所有内容并返回固定编码格式的字符串。
        * @Param: [filePath, fileCoding]
        * @return: java.lang.String
        * @Author: zl
        * @Date: 2019/3/28
        */
        File f =new File(filePath);
        Long fl=f.length();
        byte [] fileContent = new byte[fl.intValue()];
        try {
            FileInputStream fin = new FileInputStream(f);
            fin.read(fileContent);
            fin.close();
        }
        catch (FileNotFoundException fe){
            fe.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //编码式转换
        String  rs=null;
        try{
             rs= new String(fileContent,fileCoding);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }

        return  rs;
    }
    public static boolean WriteFile(String filePath,String contents,String fileCoding){
        //
        OutputStreamWriter wR = null;
        try {
            wR =new OutputStreamWriter(new FileOutputStream(filePath,true),fileCoding);
            wR.write(contents);
            wR.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (wR != null) {
                    wR.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public static boolean byteArrToFile(byte [] byArr ,String filePath){
        if (CreateFile(filePath)){

            ByteArrayOutputStream outBuffer =new ByteArrayOutputStream(byArr.length);
            try(FileOutputStream outF =new FileOutputStream(filePath)){
                outF.write(byArr);
                outF.flush();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            return true;
        }
        else
            return false;

    }
    public static String ReadBigFile(String filePath,String fileCoding,Integer readFilesize){
        //filesize<2g;
        byte [] fileContent = new byte[readFilesize];
        File f =new File(filePath);
        Long fl=f.length();
        final int BUFFER_SIZE=0x300000;
        long start=System.currentTimeMillis();
        try {
            MappedByteBuffer inputBuffer = new RandomAccessFile(f,"r").getChannel().map(FileChannel.MapMode.READ_ONLY,0,fl);
            byte [] dst = new byte[BUFFER_SIZE]; //每次读出3m
            for(int offset=0;offset<fl;offset=offset+BUFFER_SIZE){
                if(fl-offset>=BUFFER_SIZE){
                    for(int i=0;i<BUFFER_SIZE;i++){
                        dst[i] = inputBuffer.get(offset+i);
                    }
                }
                else{
                    for(int i=0;i<fl-offset;i++){
                        dst[i] = inputBuffer.get(offset+i);
                    }
                }
               /* String s= null;
                try{
                    s= new String(dst,fileCoding);
                    System.out.println(s);
                }
                catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                    return null;
                }
                */
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        long cost =System.currentTimeMillis() -start;
        //System.out.println("NIO 内存映射读大文件，总共耗时："+(cost)+"ms");
        //编码式转换
        String  rs=null;
        try{
            rs= new String(fileContent,fileCoding);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }

        return  rs;
    }

    public static void test(){
        String oFilePath ="E:\\work\\it\\project\\javaBase\\data\\zlFile.txt";
        String nFilePaht="E:\\work\\it\\project\\javaBase\\data\\back\\zlFile.txt";
        //file opearator
        DeteleFile(oFilePath);
        CreateFile(oFilePath);
        CopyFile(oFilePath,nFilePaht);
        RemoveFile(oFilePath,nFilePaht);
        // write read;
        WriteFile(nFilePaht,"12323413","utf-8");
        System.out.println(ReadFile(nFilePaht,"utf-8"));
    }
    public static  void testBigFileRead(){
        //大于2g文件读写
        String filePath="E:\\work\\it\\project\\javaBase\\data\\zllog.txt";
        String oFilePath ="E:\\work\\it\\project\\javaBase\\data\\zlFile.txt";
        String contents= ReadBigFile(filePath,"utf-8",1024*6);
        WriteFile(oFilePath,contents,"utf-8");
    }
    public static void main(String[] args) {
        testBigFileRead();
    }
}
