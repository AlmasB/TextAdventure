package com.almasb.ta;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class TextAdventureApp extends Application {

    private TextArea output = new TextArea();
    private TextField input = new TextField();

    private Map<String, Command> commands = new HashMap<>();

    private Room[][] rooms = new Room[10][10];

    private int currentX = 5;
    private int currentY = 4;

    private Parent createContent() {
        output.setPrefHeight(600 - 80);
        output.setFont(Font.font(24));
        output.setEditable(false);
        output.setFocusTraversable(false);

        input.setOnAction(e -> {
            String inputText = input.getText();
            input.clear();

            onInput(inputText);
        });

        VBox root = new VBox(15, output, input);
        root.setPadding(new Insets(15));
        root.setPrefSize(800, 600);

        initGame();

        return root;
    }

    private void initGame() {
        println("Welcome to Text Adventure v 0.1");
        initCommands();
        initRooms();
    }

    private void initCommands() {
        commands.put("exit", new Command("exit", "Exit the game", Platform::exit));

        commands.put("help", new Command("help", "Print all commands", this::runHelp));

        commands.put("go west", new Command("go west", "Move west", () -> runGo(-1, 0)));
        commands.put("go east", new Command("go east", "Move east", () -> runGo(1, 0)));
        commands.put("go north", new Command("go north", "Move north", () -> runGo(0, -1)));
        commands.put("go south", new Command("go south", "Move south", () -> runGo(0, 1)));

        commands.put("attack", new Command("attack", "Kill monsters in the room", this::runAttack));
    }

    private void initRooms() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                rooms[x][y] = new Room(x, y);

                if (Math.random() < 0.2) {
                    rooms[x][y].spawnMonsters();
                }
            }
        }
    }

    private Room getCurrentRoom() {
        return rooms[currentX][currentY];
    }

    private void println(String line) {
        output.appendText(line + "\n");
    }

    private void onInput(String line) {
        if (!commands.containsKey(line)) {
            println("Command " + line + " not found");
            return;
        }

        commands.get(line).execute();
    }

    private void runHelp() {
        commands.forEach((name, cmd) -> {
            println(name + "\t" + cmd.getDescription());
        });
    }

    private void runGo(int dx, int dy) {
        if (getCurrentRoom().hasMonsters()) {
            println("The room still has monsters");
            return;
        }

        currentX += dx;
        currentY += dy;

        println("You are now in room: " + currentX + "," + currentY);
    }

    private void runAttack() {
        getCurrentRoom().killMonsters();

        println("All monsters in the room have been killed");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static class Launcher {
        public static void main(String[] args) {
            Application.launch(TextAdventureApp.class, args);
        }
    }
}
