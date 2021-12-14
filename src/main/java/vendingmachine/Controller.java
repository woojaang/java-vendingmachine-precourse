package vendingmachine;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static List<Coin> coins;
    private View view;
    private static VendingMachine vendingMachine;

    public Controller(VendingMachine vendingMachine, View view) {
        this.view = view;
        this.vendingMachine = vendingMachine;
        this.coins = makeCoins();
    }

    public static List<Coin> makeCoins() {
        List<Coin> coins = new ArrayList<>();
        coins.add(Coin.COIN_500);
        coins.add(Coin.COIN_100);
        coins.add(Coin.COIN_50);
        coins.add(Coin.COIN_10);
        return coins;
    }

    public List<Coin> getCoins() {
        return this.coins;
    }

    public void setMoney() {
        vendingMachine.setMoney(view.setMoney());
    }

    public void printCoins() {
        System.out.println();
        changeCoins();
        view.printCoins(this.coins);
        System.out.println();
    }

    public static void changeCoins() {
        int money = vendingMachine.getMoney();
        int indexOfCOIN_10 = 3;
        for (int i=0; i<3; i++) {
            List<Integer> randomList = getRandomList(money, coins.get(i));
            int randomNumber = Randoms.pickNumberInList(randomList);
            coins.get(i).setNumberOfCoins(randomNumber);
            money -= randomNumber*coins.get(i).getAmount();
        }
        coins.get(indexOfCOIN_10).setNumberOfCoins(money/10);
    }

    private static List<Integer> getRandomList(int money, Coin coin) {
        List<Integer> randomList = new ArrayList<Integer>();
        for (int i=0; i<=money/coin.getAmount(); i++) {
            randomList.add(i);
        }
        return randomList;
    }

    public void inputProduct() {
        while (true) {
            if (vendingMachine.createProducts(view.inputProduct())) {
                break;
            }
            System.out.println("[ERROR] 금액은 숫자여야 합니다.");
        }
        System.out.println();
    }

    public void setSpentMoney() {
        vendingMachine.setSpentMoney((view.inputMoney()));
        System.out.println();
    }

    public void order() {
        while (vendingMachine.getSpentMoney() > vendingMachine.getMinPrice()) {
            String menu = view.askProduct(vendingMachine.getSpentMoney());
            vendingMachine.buyProduct(menu);
            System.out.println();
        }
    }

    public void backChange() {
        int change = vendingMachine.getSpentMoney();
        int[] numberOfCoins = new int[4];
        for (int i=0; i<4; i++) {
            if (coins.get(i).getAmount()*coins.get(i).getNumberOfCoin()<change) {
                numberOfCoins[i] = coins.get(i).getNumberOfCoin();
                change -= numberOfCoins[i]*coins.get(i).getAmount();
                continue;
            }
            if (coins.get(i).getNumberOfCoin()>0) {
                numberOfCoins[i] = change/coins.get(i).getAmount();
                change -= numberOfCoins[i]*coins.get(i).getAmount();
            }
        }
        view.printChange(vendingMachine.getSpentMoney(), numberOfCoins);
    }
}
