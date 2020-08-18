import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {

        greet();
    }

    public static void greet() {

        String str;
        String indent = "    ";

        System.out.println(indent + "------------");
        System.out.println(indent + "Hello! I'm Duke \n    What can I do for you?");
        System.out.println(indent + "------------");

        Scanner in = new Scanner(System.in);
        str = in.nextLine();

        while (!str.equals("bye")) {

            System.out.println(indent + "------------");
            System.out.println(indent + "added: " + str);
            System.out.println(indent + "------------");
            str = in.nextLine();
        }

        System.out.println(indent + "------------");
        System.out.println(indent + "Bye! Hope to see you again soon!");
        System.out.println(indent + "------------");
    }
}
