import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {

        greet();
    }

    public static void greet() {

        String str;
        String indent = "    ";
        String[] tasks = new String[100];
        int i = 0;

        System.out.println(indent + "------------");
        System.out.println(indent + "Hello! I'm Duke \n    What can I do for you?");
        System.out.println(indent + "------------");

        Scanner in = new Scanner(System.in);
        str = in.nextLine();

        while (!str.equals("bye")) {

            if (str.equals("list")) {

                System.out.println(indent + "------------");
                for (int j = 0; j < i; j++) {

                    System.out.println(indent + Integer.toString(j + 1) + ". " + tasks[j]);
                }
                System.out.println(indent + "------------");
                str = in.nextLine();
            }
            else {

                tasks[i] = str;
                System.out.println(indent + "------------");
                System.out.println(indent + "added: " + str);
                System.out.println(indent + "------------");
                str = in.nextLine();
                i = i + 1;
            }
        }
        System.out.println(indent + "------------");
        System.out.println(indent + "Bye! Hope to see you again soon!");
        System.out.println(indent + "------------");
    }

    public static class Task {

        protected String description;
        protected boolean isDone;

        public Task(String description) {

            this.description = description;
            this.isDone = false;
        }

        public String getStatusIcon() {

            return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
        }

        public void markAsDone() {

            this.isDone = true;
        }
    }
}
