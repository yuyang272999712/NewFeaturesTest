package com.yuyang.fitsystemwindowstestdrawer.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yuyang.fitsystemwindowstestdrawer.json.baseBean.Result;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Gson使用小技巧
 * https://www.jianshu.com/p/c88260adaf5e
 */

public class GsonTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Book book1 = new Book("android开发艺术", 35.5f, new Date());
        Book book2 = new Book("java内从管理", 20.1f, formatter.parse("2018-02-13 23:12:23"));
        Book book3 = new Book("git入门", 100.4f, formatter.parse("2007-12-30 12:23:43"));

        Result<Book> result1 = new Result<>();
        result1.setData(book1);
        result1.setCode(200);
        result1.setMessage("请求成功");

        Result<List<Book>> result2 = new Result<>();
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        result2.setData(books);
        result2.setCode(200);
        result2.setMessage("请求成功");

        Gson gson1 = new Gson();
        String resultStr1 = gson1.toJson(result1);
        String resultStr2 = gson1.toJson(result2);

        System.out.println("result1序列化结果："+resultStr1);
        System.out.println("result2序列化结果："+resultStr2);

        System.out.println();

        /*-----反序列化------------------------------------------------------------*/

        Gson gson2 = new GsonBuilder()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting()
                .create();

        resultStr1 = "{\"code\":200,\"message\":\"请求成功\",\"data\":{\"title\":\"android开发艺术\",\"price\":35.5,\"date\":\"1989-04-17 12:23:49\"}}";
        resultStr2 = "{\"code\":200,\"message\":\"请求成功\",\"data\":[{\"title\":\"android开发艺术\",\"price\":35.5,\"date\":\"2018-04-17 12:23:49\"},{\"title\":\"java内从管理\",\"price\":20.1,\"date\":\"2018-04-17 12:23:49\"},{\"title\":\"git入门\",\"price\":100.4,\"date\":\"2018-04-17 12:23:49\"}]}";

        //ZHU yuyang 使用TypeToken实现范型机制 不再重复定义Result类
        Type bookType = new TypeToken<Result<Book>>(){}.getType();
        Result<Book> userResult = gson2.fromJson(resultStr1, bookType);
        Book book = userResult.getData();

        Type bookListType = new TypeToken<Result<List<Book>>>(){}.getType();
        Result<List<Book>> userListResult = gson2.fromJson(resultStr2, bookListType);
        List<Book> bookList = userListResult.getData();

        System.out.println("resultStr1反序列化结果："+book);
        System.out.println("resultStr2反序列化结果："+bookList);
    }
}
