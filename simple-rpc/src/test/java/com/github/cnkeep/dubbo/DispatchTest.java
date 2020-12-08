package com.github.cnkeep.dubbo;

public class DispatchTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Base b = null; //此处如果 换成 Sub b=null，则b.x=40;不再是20
        b = new Sub();
        System.out.println(b.x);
        Sub s = (Sub) b;
        System.out.println(s.x);
    }

    static class Base {

        int x = 10;

        public Base() {
            this.printMessage();
            x = 20;
        }

        {
            System.out.println("Base block");
        }

        static {
            System.out.println("Base static block");
        }


        public void printMessage() {
            System.out.println("Base.x=" + x);
        }

    }

    static class Sub extends Base {

        int x = 30;

        public Sub() {
            super();//此处的super()；去掉也可以，默认执行super(); 而且只能在构造函数的第一行，如果放在其他行，编译错误，
            this.printMessage();
            x = 40;

        }

        public void printMessage() {
            System.out.println("Sub.x=" + x);
        }
    }

}