package ru.job4j.tracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StartUI {
    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Tracker tracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong input, you can select: 0 .. " + (actions.size() - 1));
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(input, tracker);
        }
    }

    private void showMenu(List<UserAction> actions) {
        out.println("Menu:");
        for (int index = 0; index < actions.size(); index++) {
            out.println(index + ". " + actions.get(index).name());
        }
    }

    public static void main(String[] args) {
        Output out = new ConsoleOutput();
        Item item = new Item("admin", 1);
        final LocalDateTime created = item.getCreated();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-EE-yyyy HH:mm:ss");
        String createdFormat = created.format(formatter);
        out.println("Текущие дата и время: " + "\n" + createdFormat + "\n");
        Input input = new ValidateInput(out, new ConsoleInput());
        Tracker tracker = new Tracker();
        List<UserAction> actions = new ArrayList<>();
        actions.add(new CreateAction(out));
        actions.add(new FindAllAction(out));
        actions.add(new ReplaceAction(out));
        actions.add(new DeleteAction(out));
        actions.add(new FindByIdAction(out));
        actions.add(new FindByNameAction(out));
        actions.add(new ExitAction(out));
        new StartUI(out).init(input, tracker, actions);
    }
}

