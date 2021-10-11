package com.yuanqi.strategy.test;

/**
 * 这是锦囊类，里面包含很多的策略
 *
 * 环境(Context)角色：持有Strategy的引用，通常会封装一些获取实现类的方法，或策略类的调用。相对来说定义比较灵活。
 * 抽象策略(Strategy)角色：抽象角色，通常是一个接口或抽象类。所有的具体策略类都需要实现该接口。如果是抽象类，可实现一些公共的方法。
 * 具体策略(ConcreteStrategy)角色：具体算法或行为的实现，也就是抽象策略类中定义的方法的不同实现。
 */
public class Context {
    private IStrategy strategy;

    // 通过构造方法注入，也可以通过其他方式注入
    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    //使用计谋了！
    public void operate() {
        strategy.operate();
    }
}
