package vendingmachine;

public class Application {
    public static void startApplication(Controller controller) {
        controller.setMoney();


    }

    public static void main(String[] args) {
        View view = new View();
        VendingMachine vendingMachine = new VendingMachine();
        Controller controller = new Controller(vendingMachine, view);

        startApplication(controller);
    }
}
