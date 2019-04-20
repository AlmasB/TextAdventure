package com.almasb.ta;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class TextAdventureApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new Pane()));
        stage.show();
    }

    public static class Launcher {
        public static void main(String[] args) {
            Application.launch(TextAdventureApp.class, args);
        }
    }
}
