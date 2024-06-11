package cn.yzh.module.excel;


import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;


import java.nio.file.Path;
import java.util.Iterator;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {

        readLagerExcel();


    }
    /**
     * 大批量数据读取 十万级以上
     * 思路：采用分段缓存加载数据，防止出现OOM的情况
     *
     *
     * @throws Exception
     */
    public static void readLagerExcel() throws Exception {
        //获取当前所在目录
        System.out.println("开始读取excel");
        InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(Path.of("test.xlsx")));

        long start = System.currentTimeMillis();
        try (Workbook workbook = StreamingReader.builder()
                .rowCacheSize(10 * 20)  //缓存到内存中的行数，默认是10
                .bufferSize(1024 * 8)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(inputStream)) { //打开资源，可以是InputStream或者是File，注意：只能打开.xlsx格式的文件

            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()){
                Sheet sheet = sheetIterator.next();
                System.out.println("开始读取excel，耗时：{}毫秒，"+ (System.currentTimeMillis() - start));
                //遍历所有的行
                for (Row row : sheet) {
                    System.out.println("开始遍历第" + row.getRowNum() + "行数据：");
                }
                //总数
                System.out.println("读取结束行数：" + sheet.getLastRowNum());
                System.out.println("读取excel完毕，耗时：{}毫秒，"+ (System.currentTimeMillis() - start));
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//    public static void readbigExcel(String FileName){
//
//        long start = System.currentTimeMillis();
//        EasyExcel.read(FileName,new PageReadListener(dataList -> {
//            System.out.println(dataList);
//        },10000)).readCache(new MapCache()).doReadAll();
//        System.out.println("读取excel完毕，耗时：{}毫秒，"+ (System.currentTimeMillis() - start));
//
//
//    }
//    public static List<Carexcel> data(int index) {
//        //生成插入excel的数据index为1的时候循环到1048575，为2的时候从1048575到1048575*2以此类推
//        int dataindex=1048575;
//
//
//        List<Carexcel> list = ListUtils.newArrayList();
//        for (int i = dataindex*(index-1)==0?1:(index-1)*dataindex; i <dataindex*index ; i++) {
//            Carexcel data = new Carexcel();
//            data.setFrameNumber("字符串" + i);
//            data.setSerialNumber(String.valueOf(i));
//            data.setTheWeightOfTheCar(String.valueOf(i));
//            list.add(data);
//        }
//        return list;
//    }
//    public static void write(String fileName) {
//
//            try(ExcelWriter excelWriter = EasyExcel.write(fileName, Carexcel.class).build()) {
//                for (int i = 1; i < 2; i++) {
//                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
//                List<Carexcel> data = data(i);
//                excelWriter.write(data, writeSheet);
//            }
//        }
//
//        System.out.println("写入完成");
//
//    }
}

