package basic_classes;

public class App {

    private String description;
    private double price;
    private String name;
    Device device;
    //image

    public App(String description, double price, String name, Device device) {
        this.description = description;
        this.price = price;
        this.name = name;
        this.device = device;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int checkRequirements(Device device1){
        if(device.getRam() == this.device.getRam() && device1.getScreenSize() == this.device.getScreenSize()){
            return 1;
        } else return 0;
    }


}




