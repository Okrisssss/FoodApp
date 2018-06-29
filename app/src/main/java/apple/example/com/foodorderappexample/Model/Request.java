package apple.example.com.foodorderappexample.Model;

import java.util.List;

public class Request {

    private String phone;
    private String adress;
    private String name;
    private String total;
    private String status;
    private List<Order> foods;

    public Request() {
    }

    public Request(String phone, String adress, String name, String total, List<Order> foods) {
        this.phone = phone;
        this.adress = adress;
        this.name = name;
        this.total = total;
        this.foods = foods;
        this.status = "0";  //Default is 0;  0 - placed, 1 - SHipping, 2 - Shipped.

    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
