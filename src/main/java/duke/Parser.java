package duke;

import java.util.Scanner;

public class Parser {
    private final static String BREAK = "    ____________________________________________________________";
    private TaskList tasks;

    public Parser(TaskList tasks) {
        this.tasks = tasks;
    }

    public void parser() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] inputArr = input.split(" ");
        String action = inputArr[0];

        while (!action.equals("bye")) {
            int number;
            Task task;
            String[] params;
            try {
                switch (action) {
                    case "list":
                        printList();
                        break;
                    case "mark":
                        if (inputArr.length > 2 || inputArr.length == 1) {
                            throw new DukeException("The format should be: mark <number>");
                        }
                        number = Integer.parseInt(inputArr[1]);
                        if (number > tasks.getSize()) {
                            throw new DukeException("The index is invalid!");
                        }
                        task = tasks.getTask(number - 1);
                        task.markAsDone();
                        System.out.println(BREAK +
                                "\n" +
                                "     Nice! I've marked this task as done:\n       " +
                                task +
                                "\n" +
                                BREAK);
                        break;
                    case "unmark":
                        if (inputArr.length > 2 || inputArr.length == 1) {
                            throw new DukeException("The format should be: unmark <number>");
                        }
                        number = Integer.parseInt(inputArr[1]);
                        if (number > tasks.getSize()) {
                            throw new DukeException("The index is invalid!");
                        }
                        task = tasks.getTask(number - 1);
                        task.markAsNotDone();
                        System.out.println(BREAK +
                                "\n" +
                                "     Nice! I've marked this task as not done yet:\n       " +
                                task +
                                "\n" +
                                BREAK);
                        break;
                    case "todo":
                        if (input.substring(4).replaceAll("\\s+", "").equals("")) {
                            throw new DukeException("The description of a todo cannot be empty.");
                        }
                        task = new Todo(input.substring(5));
                        tasks.addTask(task);
                        printTask(task);
                        break;
                    case "deadline":
                        if (input.substring(8).replaceAll("\\s+", "").equals("")) {
                            throw new DukeException("The description of a deadline cannot be empty.");
                        }
                        if (!input.contains("/by")) {
                            throw new DukeException("The timing of a deadline cannot be omitted.");
                        }
                        params = input.substring(9).split(" /by ");
                        task = new Deadline(params[0], params[1]);
                        tasks.addTask(task);
                        printTask(task);
                        break;
                    case "event":
                        if (input.substring(5).replaceAll("\\s+", "").equals("")) {
                            throw new DukeException("The description of an event cannot be empty.");
                        }
                        if (!input.contains("/at")) {
                            throw new DukeException("The timing of an event cannot be omitted.");
                        }
                        params = input.substring(6).split(" /at ");
                        task = new Event(params[0], params[1]);
                        tasks.addTask(task);
                        printTask(task);
                        break;
                    case "delete":
                        if (inputArr.length > 2 || inputArr.length == 1) {
                            throw new DukeException("The format should be: delete <number>");
                        }
                        number = Integer.parseInt(inputArr[1]);
                        if (number > tasks.getSize()) {
                            throw new DukeException("The index is invalid!");
                        }
                        task = tasks.getTask(number - 1);
                        tasks.deleteTask(number - 1);
                        System.out.println(BREAK +
                                "\n" +
                                "     Okay! I've removed this task from the list:\n       " +
                                task +
                                "\n" +
                                BREAK +
                                "\n");
                        break;
                    default:
                        throw new DukeException("I'm sorry, but I don't know what that means :-(");
                }
            } catch (DukeException err) {
                System.out.println(BREAK +
                        "\n" +
                        "     ☹ OOPS!!! " +
                        err +
                        "\n" +
                        BREAK +
                        "\n");
            }
            input = sc.nextLine();
            inputArr = input.split(" ");
            action = inputArr[0];
        }
    }

    private void printTask(Task task) {
        System.out.println(BREAK +
                "\n" +
                "     Got it. I've added this task:\n       " +
                task);
        System.out.format("     Now you have %d tasks in the list.\n" +
                        BREAK +
                        "\n",
                tasks.getSize());
    }
    private void printList() {
        if (tasks.getSize() == 0) {
            System.out.println(BREAK +
                    "\n" +
                    "     There is no pending task for you." +
                    "\n" +
                    BREAK);
        } else {
            System.out.println(BREAK +
                    "\n" +
                    "     Here are the tasks in your list:");
            for (int i = 0; i < tasks.getSize(); i++) {
                System.out.format("     %d.%s\n", i + 1, tasks.getTask(i));
            }
            System.out.println(BREAK);
        }
    }
}