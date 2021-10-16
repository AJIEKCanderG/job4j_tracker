package ru.job4j.tracker;

import ru.job4j.tracker.action.ExitAction;
import ru.job4j.tracker.action.UserAction;
import ru.job4j.tracker.action.CreateAction;
import ru.job4j.tracker.action.DeleteAction;
import ru.job4j.tracker.action.FindAllAction;
import ru.job4j.tracker.action.FindByIdAction;
import ru.job4j.tracker.action.FindByNameAction;
import ru.job4j.tracker.action.ReplaceAction;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;
import ru.job4j.tracker.io.ConsoleInput;
import ru.job4j.tracker.io.ConsoleOutput;
import ru.job4j.tracker.io.ValidateInput;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StartUI {
    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Store tracker, List<UserAction> actions) {
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
        var tracker = new MemTracker();
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

