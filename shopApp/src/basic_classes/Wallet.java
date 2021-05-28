package basic_classes;

public class Wallet {

    private double currentMoney;

    public Wallet(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    void addMoney(double money){
        this.currentMoney += money;
    }
}
