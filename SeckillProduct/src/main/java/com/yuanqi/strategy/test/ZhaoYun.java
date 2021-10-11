package com.yuanqi.strategy.test;

/**
 * 使用策略模式
 * 优点：
 * 1、算法可以自由切换。
 * 2、使用策略模式可以避免使用多重条件(if-else)语句。
 * 3、扩展性良好。增加新策略，只需实现接口的具体逻辑即可。当旧策略不需要时，直接剔除就行。
 * 4、良好的封装性。策略的入口封装在Context封装类中，客户端只要知道使用哪种策略对象就可以了。
 * 缺点：
 * 1、客户端必须知道具体的策略，并且决定使用哪一个策略，也就是说各个策略需要暴露给客户端。
 * 2、如果策略增多，策略类的数量就会增加。对比上面的ifelse会发现增加了很多类。
 */
public class ZhaoYun {
    public static void main(String[] args) {
        Context context;

        /**刚到吴国拆第一个**/
        context = new Context(new Strategy1());     //拿到妙计
        context.operate();  //拆开执行

        /**拆第二个**/
        context = new Context(new Strategy2());     //拿到妙计
        context.operate();  //拆开执行

        /**拆第三个**/
        context = new Context(new Strategy3());     //拿到妙计
        context.operate();  //拆开执行
    }
}
