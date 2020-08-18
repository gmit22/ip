import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {

        greet();
    }

    private static void greet() {

        String str;
        String indent = "    ";
        Task[] tsk = new Task[100];
        String[] arrInput;
        int i = 0, j, input = 0;
        boolean flag;

        System.out.println(indent + "------------");
        System.out.println(indent + "Hello! I'm Duke \n    What can I do for you?");
        System.out.println(indent + "------------");

        Scanner in = new Scanner(System.in);
        str = in.nextLine();

        while (!str.equals("bye")) {

            flag = true;
            arrInput = str.split(" ");
            try {
                if (arrInput.length == 2) input = Integer.parseInt(arrInput[1]);
            } catch (NumberFormatException e) {
                flag = false;
            }

            if (str.equals("list")) {

                System.out.println(indent + "------------");
                System.out.println(indent + "Here are the tasks in your list:");

                for (j = 0; j < i; j++) {
                    System.out.println(indent + (j + 1) + ".[" + tsk[j].getStatusIcon() +
                            "] " + tsk[j].description);
                }

                System.out.println(indent + "------------");
                str = in.nextLine();
                continue;
            } else if (flag && arrInput[0].equals("done")) {

                tsk[input - 1].markAsDone();

                System.out.println(indent + "------------");
                System.out.println(indent + "Nice! I've marked this task as done:");
                System.out.println(indent + "  [" + tsk[input - 1].getStatusIcon() + "] "
                        + tsk[input - 1].description);
                System.out.println(indent + "------------");

                str = in.nextLine();
            } else {

                tsk[i] = new Task(str);
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
