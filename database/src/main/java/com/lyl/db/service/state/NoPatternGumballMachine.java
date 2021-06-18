package com.lyl.db.service.state;

public class NoPatternGumballMachine{
    /*
     * 四个状态
     */
    /**没有硬币状态*/
    private final static int NO_QUARTER = 0;
    /**投币状态*/
    private final static int HAS_QUARTER = 1;
    /**出售糖果状态*/
    private final static int SOLD = 2;
    /**糖果售尽状态*/
    private final static int SOLD_OUT = 3;

    private int state = SOLD_OUT;
    private int candyCount = 0;

    public NoPatternGumballMachine(int count) {
        this.candyCount = count;
        if(candyCount > 0)
            state = NO_QUARTER;
    }

    /*
     * 四个动作
     */

    /**
     * 投币
     */
    public void insertQuarter() {
        if(NO_QUARTER == state){
            System.out.println("投币");
            state = HAS_QUARTER;
        }
        else if(HAS_QUARTER == state){
            System.out.println("请不要重复投币！");
            returnQuarter();
        }
        else if(SOLD == state){
            System.out.println("已投币，请等待糖果");
            returnQuarter();
        }else if(SOLD_OUT == state){
            System.out.println("糖果已经售尽");
            returnQuarter();
        }
    }

    /**
     * 退币
     */
    public void ejectQuarter() {
        if(NO_QUARTER == state){
            System.out.println("没有硬币，无法弹出");
        }
        else if(HAS_QUARTER == state){
            returnQuarter();
            state = NO_QUARTER;
        }
        else if(SOLD == state){
            System.out.println("无法退币，正在发放糖果，请等待");
        }else if(SOLD_OUT == state){
            System.out.println("没有投币，无法退币");
        }
    }

    /**
     * 转动出糖曲轴
     */
    public void turnCrank() {
        if(NO_QUARTER == state){
            System.out.println("请先投币");
        }
        else if(HAS_QUARTER == state){
            System.out.println("转动曲轴，准备发糖");
            state = SOLD;
        }
        else if(SOLD == state){
            System.out.println("已按过曲轴，请等待");
        }else if(SOLD_OUT == state){
            System.out.println("糖果已经售尽");
        }
    }

    /**
     * 发糖
     */
    public void dispense() {
        if(NO_QUARTER == state){
            System.out.println("没有投币，无法发放糖果");
        }
        else if(HAS_QUARTER == state){
            System.out.println("this method don't support");
        }
        else if(SOLD == state){
            if(candyCount > 0){
                System.out.println("分发一颗糖果");
                candyCount --;
                state = NO_QUARTER;
            }
            else{
                System.out.println("抱歉，糖果已售尽");
                state = SOLD_OUT;
            }
        }else if(SOLD_OUT == state){
            System.out.println("抱歉，糖果已售尽");
        }
    }

    /**
     * 退还硬币
     */
    protected void returnQuarter() {
        System.out.println("退币……");
    }

}