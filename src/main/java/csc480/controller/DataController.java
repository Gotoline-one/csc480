package csc480.controller;

import csc480.model.Activity;
import csc480.model.Award;
import csc480.model.Badge;
import csc480.model.Scout;
import csc480.service.ActivityService;
import csc480.service.AwardService;
import csc480.service.BadgeService;
import csc480.service.ScoutService;
import javafx.concurrent.Task;
import org.bson.BsonValue;

import java.util.ArrayList;
import java.util.Map;

public class DataController {
    MainController mainController;
    ScoutService scoutService;
    BadgeService badgeService;
    AwardService awardService;
    ActivityService activityService;
    DataController(){
        this.mainController = VistaNavigator.getMainController();
        this.scoutService = new ScoutService();
        this.badgeService = new BadgeService();
        this.awardService = new AwardService();
        this.activityService = new ActivityService();
    }

    public void  createScout(Scout newScout) {

        Task<Map<Integer, BsonValue>> task = new Task<>() {
            @Override

            protected  Map<Integer, BsonValue> call() throws Exception {

                return scoutService.createScout(newScout) ;
            }
        };

        task.setOnSucceeded(event -> {
            // Update the UI after the scout is created
            MainController mc = VistaNavigator.getMainController();
            if(mc !=null){
                Map<Integer, BsonValue> scoutIDMap = task.getValue();
                //mc.currentScoutSelected.setId( task.getValue());
            }
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
    public void refreshScoutList() {
        if(VistaNavigator.getMainController() !=null){
            VistaNavigator.getMainController().scoutList.refresh();
            System.out.println("updated GUI from Task Thread ");

        }else{
            System.out.println("Could not update GUI from Task Thread ");
        }
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

    public void saveAll(ArrayList<Scout> updateScouts,
                        ArrayList<Award> updateAwards,
                        ArrayList<Badge> updateBadge,
                        ArrayList<Activity> updateActivity ) {

/*      Go through all the objects and check for ids
        If any do not have Ids make a New_ list to call add instead of update
        - or could do that on the lower level inside the for loop of adding them. s
 */
        scoutService.updateScouts(updateScouts);
        awardService.updateAwards(updateAwards);
        badgeService.updateBadges(updateBadge);
        activityService.updateActivities(updateActivity);

    }

    public void saveAll(ArrayList<Scout> es) {
        scoutService.updateScouts(es);
    }
}
