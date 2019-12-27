package com.rachel.neteaselearning2019.过滤数据;

class book {
    private int id; // ID
    private String bookName; // 书名
    private int price; // 单价

    public book() {
    }

    public book(int id, String bookName, int price) {
        this.id = id;
        this.bookName = bookName;
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "book [id=" + id + ", bookName=" + bookName + ", price=" + price + "]";
    }
}

