public class Cat {
    public String name;
    public int age;
    public String noise;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
        noise = "Meow!";
    }

    public void greet() {System.out.println("Cat" + name + "says: " + this.noise);}

    public void play(String expr) {System.out.println("Woo it is so much fun being a cat!" + expr)}

    public static void main(String[] args) {
        Cat c = new Cat("Garfield", 6);
        c.greet(15);
    }
}
