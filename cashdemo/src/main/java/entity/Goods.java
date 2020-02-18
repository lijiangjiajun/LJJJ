package entity;

public class Goods {
    private Integer id;
    private String name;
    private String introduce;
    private Integer stock;
    private String unit;
    private Integer price;//存入数据库为整数
    private Integer discount;
    private Integer buyGoodsNum;//记录需要购买当前商品的数量

    public Integer getBuyGoodsNum() {
        return buyGoodsNum;
    }

    public void setBuyGoodsNum(Integer buyGoodsNum) {
        this.buyGoodsNum = buyGoodsNum;
    }

    public Double getprice(){
        return price * 1.0 /100;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public Integer getStock() {
        return stock;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getPriceInt() {
        return price;
    }

    public Integer getDiscount() {
        return discount;
    }
}
