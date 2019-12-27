package com.rachel.neteaselearning2019.过滤数据;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤数据
 * https://www.cnblogs.com/wanghao1874/p/10115553.html
 * 请问一个比较小白的问题，list<book> listbook只知道book的bookname,怎么找出该书名的位置，我用listbook.indexof(bookname)返回一直是-1
 * 详情查看对话记录
 */
public class Test {
    public static void main(String[] args) {
        // 需要过滤出排除姓名为张三的数据
        List<book> bookList = new ArrayList<>();
        bookList.add(new book(99, "大帝姬", 18));
        bookList.add(new book(4, "剑来", 11));
        bookList.add(new book(1, "大道朝天", 18));
        bookList.add(new book(3, "雪中悍刀行", 18));
        bookList.add(new book(2, "剑来", 16));
        bookList.add(new book(8, "剑来", 12));
        System.out.println("所有数据：" + bookList);
        // 主要过滤运用了8中Lambda表达式和filter这个方法
        System.out.println("过滤后的数据：" + bookList.stream().filter(u -> u.getBookName() == "剑来").collect(Collectors.toList()));
        List<book> books = bookList.stream().filter(u -> u.getBookName() == "剑来").collect(Collectors.toList());
        if (books.size() != 0) {
            int index = bookList.indexOf(books.get(0));
            System.out.println("位置：" + index);
        }
    }
}
