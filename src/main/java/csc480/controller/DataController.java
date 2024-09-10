package csc480.controller;

import com.sun.tools.javac.Main;
import csc480.model.Badge;
import csc480.model.Scout;
import csc480.service.BadgeService;
import csc480.service.ScoutService;
import javafx.concurrent.Task;

import java.util.ArrayList;

public class DataController {
    MainController mainController;
    ScoutService scoutService;
    DataController(){
        this.mainController = VistaNavigator.getMainController();
        this.scoutService = new ScoutService();
    }

    public void createScout(Scout newScout) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                scoutService.createScout(newScout);
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            // Update the UI after the scout is created
            refreshScoutList();
        });

        new Thread(task).start();
    }


    public void createScouts(ArrayList<Scout> newScouts) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                scoutService.addScouts(newScouts);
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            // Update the UI after the scout is created
            refreshScoutList();
        });

        new Thread(task).start();
    }

    /**
     * TODO: Need to find way to update this when called from other thread?
     * OR leave it up to the caller ? but then it may be out of sequence .... cause threading.
     *
     * need a way to have it do the --hey javaFX do this .. eventually
     *
     * or should this sit at the next layer up and be called there?
     */
    private void refreshScoutList() {
//        mainController.scoutList.refresh();
    }

    public void updateScout(Scout updatedScout) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                scoutService.updateScout(updatedScout);
                return null;
            }
        };
        task.setOnSucceeded(event -> {
            refreshScoutList();
        });
        new Thread(task).start();
    }

    public void createBadges(ArrayList<Badge> badges) {

    }
}
