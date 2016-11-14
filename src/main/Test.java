package main;

public class Test {
	public static void main(String[] args) {
        float pi = 3.14159265359f;
        System.out.println(pi);
        System.out.println("-----------------------------");
        System.out.println( Math.round(pi) );
        System.out.println( (double)Math.round(pi*10)/10 );
        System.out.println( (double)Math.round(pi*100)/100 );
        System.out.println( (double)Math.round(pi*1000)/1000 );
        System.out.println( (double)Math.round(pi*10000)/10000 );
        System.out.println( (double)Math.round(pi*100000)/100000 );
        System.out.println( (double)Math.round(pi*1000000)/1000000 );
        System.out.println( (double)Math.round(pi*10000000)/10000000 );
	}
}
