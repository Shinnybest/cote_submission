package com.hanteo.cote.numbertwo;

public class Coin {

    public static void main(String[] args) {
        Coin coin = new Coin();
        int[] coins = new int[]{2, 5, 3, 6};
        int result = coin.countWays(coins, 10);
        System.out.println("result = " + result);
    }

    public int countWays(int[] coins, int sum) {
        int base = 1;
        int[] ways = new int[sum + 1];
        ways[0] = base;

        for (int coin : coins) {
            for (int amount = coin; amount <= sum; amount++) {
                ways[amount] += ways[amount - coin];
            }
        }

        return ways[sum];
    }
}
