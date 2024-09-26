package csc480.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import csc480.model.*;
import csc480.repository.mongo.Connection;
import csc480.service.BadgeService;
import csc480.service.ScoutService;
import javafx.concurrent.Task;
import org.bson.BsonValue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataController {
    MainController mainController;
    ScoutService scoutService;
    BadgeService badgeService;
    Connection dbConn;

    LoadFakeData lfd;


    DataController(MainController mc){
        this.mainController = mc;
        this.scoutService =     new ScoutService();
        this.badgeService =     new BadgeService();

        dbConn = new Connection();
        lfd = new LoadFakeData();


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

    public ArrayList<Badge> getAllBadges(){
        try {
            return lfd.loadBadges();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }



    /*      Go through all the objects and check for ids
            If any do not have Ids make a New_ list to call add instead of update
            - or could do that on the lower level inside the for loop of adding them. s
     */
    public void saveAll(ArrayList<Scout> updateScouts,ArrayList<Badge> updateBadge){
        scoutService.updateScouts(updateScouts);
        badgeService.updateBadges(updateBadge);
    }

    public void saveAll(ArrayList<Scout> es) {
        scoutService.updateScouts(es);
    }
}
//Badge2 badge = objectMapper.readValue(new File("./src/main/resources/csc480/TroopManagementApp.MeritBadge.json"), Badge2.class);

//
//

class LoadFakeData {

    ArrayList<Badge> loadBadges() throws IOException {

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON file and map it to a List of Badge2 objects
            List<Badge2> badge2List = objectMapper.readValue(new File("./src/main/resources/csc480/TroopManagementApp.MeritBadge.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Badge2.class));
            ArrayList<Badge> badgeArrayList= new ArrayList<>();

            for (Badge2 dbBadge : badge2List) {

                ArrayList<Requirement> badgeReqs = new ArrayList<>();

                badgeReqs.addAll(requirementParser( dbBadge.getRequirements() ));

                Boolean badgeComplete;

                if(dbBadge.getComplete() == null)
                    badgeComplete = false;
                else
                    badgeComplete = dbBadge.getComplete();


                Badge b = new Badge(dbBadge.getId(),
                    dbBadge.getName(),
                    "Place Holder Text",//dbBadge.getDescription(),
                    badgeComplete,
                    dbBadge.getRequirements(),
                    badgeReqs
                );
                badgeArrayList.add(b);



                b.setBadgeRequirementsList(badgeReqs);
            }
            return badgeArrayList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Requirement> requirementParser(String mainString) throws IOException {
        Scanner scanner = new Scanner(mainString);
        String line;

        List<Requirement> mainRequirements = new ArrayList<>();
        Requirement currentMainRequirement = null;
        Requirement currentSubRequirement = null;

        Pattern mainReqPattern      =   Pattern.compile("^(\\d+)\\.\\s+(.*)$");
        Pattern subReqPattern       =   Pattern.compile("^\\s+\\(\\s*([a-z])\\s*\\)\\s+(.*)$");
        Pattern nestedSubReqPattern =   Pattern.compile("^\\s+\\(\\s*(\\d+)\\s*\\)\\s+(.*)$");
        Pattern multiTabPattern     =   Pattern.compile("^\\t{2,}(.*)");
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            line = line.trim();
            line = line.replaceFirst("^\\\\","");
            if (line.isEmpty()) continue;

            Matcher mainMatcher = mainReqPattern.matcher(line);
            Matcher subMatcher = subReqPattern.matcher(line);
            Matcher nestedMatcher = nestedSubReqPattern.matcher(line);
            Matcher multiTabMatcher = multiTabPattern.matcher(line);

            if (mainMatcher.find()) {
                String id = mainMatcher.group(1);
                String desc = mainMatcher.group(2);

                Requirement req = new Requirement(id, desc, true, false);
                mainRequirements.add(req);

                currentMainRequirement= req;
            } else if (subMatcher.find()) {
                String id = subMatcher.group(1);
                String desc = subMatcher.group(2);
                Requirement req = new Requirement(id, desc, true, false);

                if (currentMainRequirement != null) {
                    currentSubRequirement = req;
                    currentMainRequirement.addSubRequirement(req);
                }else{
                    System.err.println("Sub-requirement without a main requirement.");
                }
            } else if (nestedMatcher.find()) {
                String id = nestedMatcher.group(1);
                String desc = nestedMatcher.group(2);
                Requirement req = new Requirement(id, desc, true, false);

                if (currentSubRequirement != null) {
                    // In case there is no sub-requirement level
                    currentSubRequirement.addSubRequirement(req);
                }
                else if(currentMainRequirement !=null){
                    currentMainRequirement.addSubRequirement(req);
                }
                else {
                    System.err.println("Nested sub-requirement without a parent.");
                }

            } else if (multiTabMatcher.matches()) {
                // Line starts with multiple tabs
                String content = multiTabMatcher.group(1);
                // Decide how to handle this content
                if (currentSubRequirement != null) {
                    // Append to the current sub-requirement
                    currentSubRequirement.appendDescription("\n\t-" + content);
                } else if (currentMainRequirement != null) {
                    // Append to the current main requirement
                    currentMainRequirement.appendDescription("\n" + content);
                }
            } else {
                // Continuation of the previous description
                if (currentSubRequirement != null) {
                    currentSubRequirement.appendDescription(" " + line);
                } else if (currentMainRequirement != null) {
                    currentMainRequirement.appendDescription(" " + line);
                } else {
                    System.err.println("Text outside of any requirement: " + line);
                }
            }
        }

        scanner.close();

        return mainRequirements;
    }

        private static void printRequirement(Requirement req, int level) {
            String indent = "  ".repeat(level);
            System.out.println(indent+req.getDisplayID() + ". " + req.getDescription());

            for (NodeData subReq : req.getSubRequirements()) {
                printRequirement((Requirement) subReq, level + 1);

            }
        }


    }
