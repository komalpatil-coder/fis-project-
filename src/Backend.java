
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;


public class Backend {

    //<editor-fold defaultstate="collapsed" desc="Dealing with files">
    public static boolean isFileType(File fileName, String extention) {
        return fileName.getName().endsWith(extention);
    }
    public static void startBackend(List<Catalogue> catList) throws IOException, ClassNotFoundException {
        createMainDir();
        readAllCats(catList);
    }

   
    public static boolean fileExists(File fileName) {
        return new File("files/" + fileName.getName()).exists();
    }

   
    private static boolean createMainDir() {
        File mainDir = new File("Catalogues");
        return mainDir.mkdir();
    }

   
    private static void readAllCats(List<Catalogue> catList) throws IOException, ClassNotFoundException {
        File dir = new File("Catalogues");

        String[] myFiles = dir.list(new FilenameFilter() { //read all files in directory ending with .ser

            @Override
            public boolean accept(File dir, String string) {
                return string.endsWith(".ser");
            }
        });
        //add serialized files to list of catalogues
        for (String s : myFiles) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Catalogues/" + s))) {
                Catalogue cat = (Catalogue) ois.readObject();
                catList.add(cat);
            }
        }
    }

    public static void save(List<Catalogue> catList) throws FileNotFoundException, IOException {
        for (File f : new File("Catalogues").listFiles()) {//Delete all files from directory
            f.delete();
        }
        for (Catalogue c : catList) {//add all catalogues to directory
            //System.out.println(c.getCatName());
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("Catalogues/" + c.getCatName() + ".ser")))) {
                oos.writeObject(c);
                oos.flush();
            }
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Catalogue options">
    
    public static boolean renameCatalogue(Catalogue catalogue, String newName, List<Catalogue> catList) {
        for (Catalogue c : catList) {
            if (c.getCatName().equalsIgnoreCase(catalogue.getCatName())) {
                if (!isInCatList(newName, catList)) {
                    c.setCatName(newName);
                    return true;
                }
            }
        }
        return false;
    }

   
    private static int getCatIndex(String catName, List<Catalogue> catList) {
        for (Catalogue c : catList) {
            if (c.getCatName().equalsIgnoreCase(catName)) {
                return catList.indexOf(c);
            }
        }
        return -1;
    }

   
    public static boolean deleteCatalogue(String catName, List<Catalogue> catList) {
        int delInd = getCatIndex(catName, catList);
        if (delInd != -1) {
            catList.remove(delInd);
            return true;
        }
        return false;
    }

  
    private static boolean isInCatList(String catName, List<Catalogue> catList) {
        for (Catalogue c : catList) {
            if (c.getCatName().equalsIgnoreCase(catName)) {
                return true;
            }
        }
        return false;
    }

  
    public static boolean addCatalogue(Catalogue catName, List<Catalogue> catList) {
        if (!isInCatList(catName.getCatName(), catList)) {
            return catList.add(catName);
        }
        return false;
    }
//</editor-fold>

    public static void moveToDefaultDir(File fileName) throws IOException {

        File copyTo = new File("files/" + fileName.getName());
        if (new File("files").exists()) {
            if (!copyTo.exists()) {
                Files.copy(fileName.toPath(), copyTo.toPath());

            }
        } else {
            new File("files").mkdir();
            Files.copy(fileName.toPath(), copyTo.toPath());

        }

    }

    public static int generateMultimediaId(Catalogue catToUse) {
        int id = 1;
        //automatic id creation
        if (catToUse.getMultimediaList().size() > 1) {
            // if more than one Multimedia exists, find highest id and add one to that. done incase lower id has been removed
            for (Multimedia m : catToUse.getMultimediaList()) {
                if (m.getMultID() >= id) {
                    id = m.getMultID() + 1;
                }
            }
        } else {
            id = catToUse.getMultimediaList().size() + 1; //automatic id creation
        }
        return id;
    }

   
    public static boolean addMultimedia(int id, String multimediaName, File multimediaTextFileLocation, File mulimediaImgFileLocation, Catalogue catToAddTo, List<Catalogue> catList) throws IOException {
        id = generateMultimediaId(catToAddTo);
        Multimedia mToAdd = new Multimedia(id, multimediaName, Calendar.getInstance().getTime(), "files/" + multimediaTextFileLocation.getName(), "files/" + mulimediaImgFileLocation.getName());//add nultimedia to Catalogue
        for (Catalogue c : catList) {
            if (c.getCatName().equalsIgnoreCase(catToAddTo.getCatName())) {
                c.getMultimediaList().add(mToAdd);
                return true;
            }
        }
        return false;
    }

   
    private static int indexOfMultimedia(int id, List<Multimedia> multList) {
        for (Multimedia m : multList) {
            if (m.getMultID() == id) {
                return multList.indexOf(m);
            }
        }
        return -1;
    }

   
    public static boolean removeMultimedia(int multimediaToRemove, String catalogueToUse, List<Catalogue> catList) {
        for (Catalogue c : catList) {
            if (c.getCatName().equalsIgnoreCase(catalogueToUse)) {
                //System.out.println(indexOfMultimedia(multimediaToRemove, c.getMultimediaList()));
                if (indexOfMultimedia(multimediaToRemove, c.getMultimediaList()) != -1) {
                    c.getMultimediaList().remove(indexOfMultimedia(multimediaToRemove, c.getMultimediaList()));
                    return true;
                }
            }
        }
        return false;
    }

    
    public static boolean editMultimedia(int multimediaToChange, String newName, File newImageFile, File newTextFileName, String catalogueToUse, List<Catalogue> catList) {
        for (Catalogue c : catList) {
            if (c.getCatName().equalsIgnoreCase(catalogueToUse)) {
                //System.out.println(c.getMultimediaList().get(multimediaToChange).getTextFileName());
                for (Multimedia m : c.getMultimediaList()) {
                    if (m.getMultID() == multimediaToChange) {
                        m.setTextFileName("files/" + newTextFileName.getName());
                        m.setName(newName);
                        m.setImageName("files/" + newImageFile.getName());
                    }
                }
                // System.out.println(c.getMultimediaList().get(multimediaToChange).getTextFileName());
                return true;
            }
        }
        return false;
    }

//</editor-fold>
   
    private static boolean isValidInput(boolean whileCondition) {
        try {
            return whileCondition;
        } catch (Exception e) {
            return false;
        }

    }

   
    private static void mainMenu(List<Catalogue> listName) throws IOException {
         System.out.println("\n ***** WELCOME TO LockedMe.COM *****");
        System.out.println("\n Developed BY komal patil");
        System.out.println("What would you like to do?(Type the appropriate slection)");
        System.out.println("(1) Add Catalogue");
        System.out.println("(2) Search Catalogue");
        System.out.println("(q) to quit");
        System.out.println("(save) to save");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (isValidInput(!"1".equals(input) && !"2".equals(input)
                && !"q".equalsIgnoreCase(input) && !"save".equalsIgnoreCase(input))) {
            System.err.println("Please enter 1, 2  or 'Q' only");
            input = kb.nextLine();
        }
        switch (input) {
            case "1":
                addCat(listName);
                break;
            case "2":
                searchCat(listName);
                break;
            case "q":
                System.out.println("thank you");
                System.exit(0);
                break;
            case "save":
                save(listName);
                break;
        }
    }

   
    private static void addCat(List<Catalogue> listName) throws IOException {
        System.out.print("Enter catalogue name: ");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (input.trim().isEmpty()) {
            System.err.println("Please enter a name");
            input = kb.nextLine();
        }
        if (addCatalogue(new Catalogue(input), listName)) {
            System.out.println("Added Successfully. going to main menu");
            mainMenu(listName);
        } else {
            System.err.println("cannot add catalogue");
            addCat(listName);
        }

    }

   
    private static void searchCat(List<Catalogue> listName) throws IOException {
        System.out.print("Enter catalogue name: ");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (input.trim().isEmpty()) {
            System.out.println("Please enter a name");
            input = kb.nextLine();
        }
        if (isInCatList(input, listName)) {
            System.out.println("Catalogue found");
            System.out.println("(1) Multimedia options");
            System.out.println("(2) Edit Catalogue");
            System.out.println("(3) Delete Catalogue");
            System.out.println("(4) View Catalogue Info");
            System.out.println("(5) main menu");
            System.out.println("(save) Save Work");
            String ask = kb.nextLine();
            while (isValidInput(!"1".equalsIgnoreCase(ask) && !"2".equalsIgnoreCase(ask) && !"3".equalsIgnoreCase(ask)
                    && !"4".equalsIgnoreCase(ask) && !"5".equalsIgnoreCase(ask))) {
                System.err.println("Please enter 1, 2, 3, 4, 5  or 'save' only");
                ask = kb.nextLine();
            }
            switch (ask) {
                case "1":
                    multMenu(listName, input);
                    break;
                case "2":
                    editCat(listName, input);
                    break;
                case "3":
                    deleteCat(listName, input);
                    break;
                case "4":
                    viewCat(listName, input);
                    break;
                case "5":
                    mainMenu(listName);
                    break;
                case "save":
                    save(listName);
                    break;
            }
        } else {
            System.out.println("Catalogue not found");
            searchCat(listName);
        }
    }

   
    private static void editCat(List<Catalogue> listName, String catName) throws IOException {
        System.out.println("Edit Catalogue");
        System.out.println("Enter New Catalogue Name: ");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (input.trim().isEmpty()) {
            System.out.println("Please enter a name");
            input = kb.nextLine();
        }
        while (isInCatList(input, listName)) {
            System.out.println("Name exists. please enter new name");
            input = kb.nextLine();
        }
        if (renameCatalogue(new Catalogue(catName), input, listName)) {
            System.out.println("Edited! returning to search menu");
            searchCat(listName);
        } else {
            System.out.println("cannot rename");
            editCat(listName, catName);
        }
    }

   
    private static void deleteCat(List<Catalogue> listName, String catName) throws IOException {
        System.out.println("Are you sure you want to delete this catalogue?(y/n)");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
            System.out.println("Please enter y or n only");
            input = kb.nextLine();
        }
        switch (input) {
            case "y":
                if (deleteCatalogue(catName, listName)) {
                    System.out.println("Successfully deleted. returning to main menu");
                    mainMenu(listName);
                } else {
                    System.out.println("Error: Could not delete.");
                    deleteCat(listName, catName);
                }
                break;
            case "n":
                System.out.println("returning to search menu.");
                searchCat(listName);
                break;
        }

    }

   
    private static void viewCat(List<Catalogue> listName, String catName) throws IOException {
        System.out.println("Catalogue details of " + catName);
        for (Catalogue c : listName) {
            if (c.getCatName().equalsIgnoreCase(catName)) {
                System.out.println("Catalogue Name: " + c.getCatName());
                System.out.println("--Multimedia--");
                for (Multimedia m : c.getMultimediaList()) {
                    System.out.println("ID: " + m.getMultID());
                    System.out.println("Name: " + m.getName());
                    System.out.println("Date Created: " + m.getCreationDate());
                    System.out.println("Name of Text file: " + m.getTextFileName());
                    System.out.println("Name of Image file: " + m.getImageName());
                }
            }
        }
        System.out.println("-------------------------");
        System.out.println("returning to search menu");
        searchCat(listName);
    }

  
    private static void multMenu(List<Catalogue> listName, String catName) throws IOException {
        System.out.println("Multimedia Options");
        System.out.println("(1) Add multimedia");
        System.out.println("(2) Search multimedia");
        System.out.println("(3) main menu");
        System.out.println("(q) Quit");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (isValidInput(!"1".equals(input) && !"2".equals(input)
                && !"3".equalsIgnoreCase(input) && !"q".equalsIgnoreCase(input))) {
            System.out.println("PLease enter 1, 2, 3 or quit");
        }
        switch (input) {
            case "1":
                addMult(listName, catName);
                break;
            case "2":
                searchMultimedia(listName, catName);
                break;
            case "3":
                mainMenu(listName);
                break;
            case "q":
                System.out.println("Thank you");
                System.exit(0);
                break;
        }
    }

   
    private static boolean isInt(String checkInt) {
        try {
            Integer.parseInt(checkInt);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

  
    private static void addMult(List<Catalogue> listName, String catName) throws IOException {
        System.out.println("Enter Name of Multimedia.");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (input.trim().isEmpty()) {
            System.out.println("Please enter name");
            input = kb.nextLine();
        }
        Catalogue x = null;
        for (Catalogue c : listName) {
            if (c.getCatName().equalsIgnoreCase(catName)) {
                x = c;
            }
        }
        if (x != null) {
            if (addMultimedia(generateMultimediaId(x), input, new File("files/despicable_me_2_2013-wallpaper-1920x1080.jpg"), new File("files/CookiesException.txt"), x, listName)) {
                //please not that files are preadded. to view this functionality please use GUI
                System.out.println("Added! returning to multimedia menu");
                multMenu(listName, catName);
            } else {
                System.out.println("Could not add multimedia");
                addMult(listName, catName);
            }
        }

    }

    
    private static void searchMultimedia(List<Catalogue> listName, String catName) throws IOException {
        System.out.println("Enter ID of multimedia to search");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (!isInt(input)) {
            System.out.println("please enter an Id");
            input = kb.nextLine();
        }
        int ind = Integer.parseInt(input);
        List<Multimedia> ml = null;
        for (Catalogue c : listName) {
            if (c.getCatName().equalsIgnoreCase(catName)) {
                ml = c.getMultimediaList();
            }
        }
        if (ml != null) {
            int index = indexOfMultimedia(ind, ml);
            if (index != -1) {
                System.out.println("ID found!");
                System.out.println("(1) Edit Multimedia Name");
                System.out.println("(2) Delete Multimedia");
                System.out.println("(3) Main Menu");
                System.out.println("(q) quit");

                String input1 = kb.nextLine();
                while (isValidInput(!"1".equals(input) && !"2".equals(input)
                        && !"3".equalsIgnoreCase(input) && !"q".equalsIgnoreCase(input))) {
                    System.out.println("Please select 1, 2, 3 or q only");
                    input1 = kb.nextLine();
                }
                switch (input1) {
                    case "1":
                        editMultName(ind, catName, listName);
                        break;
                    case "2":
                        deleteMult(ind, catName, listName);
                        break;
                    case "3":
                        mainMenu(listName);
                        break;
                }
            } else{
                System.out.println("ID not found");
                searchMultimedia(listName, catName);
            }
        }

    }

    private static void editMultName(int id, String catName, List<Catalogue> listName) throws IOException {
        System.out.println("Edit Multimedia");
        System.out.println("Enter New Multimedia Name: ");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (input.trim().isEmpty()) {
            System.out.println("Please enter a name");
            input = kb.nextLine();
        }
        //System.out.println(input);
        if (editMultimedia(id, input, new File("files/despicable_me_2_2013-wallpaper-1920x1080.jpg"), new File("files/CookiesException.txt"), catName, listName)) {
            System.out.println("Edited! returning to multimedia menu");
            multMenu(listName, catName);
        } else {
            System.out.println("cannot edit. try again");
            editMultName(id, catName, listName);
        }
    }

   
    private static void deleteMult(int id, String catName, List<Catalogue> listName) throws IOException {
        System.out.println("Delete multimedia");
        System.out.println("Are you sure you want to delete this multimedia?(y/n)");
        Scanner kb = new Scanner(System.in);
        String input = kb.nextLine();
        while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
            System.out.println("Please enter y or n only");
            input = kb.nextLine();
        }
        switch (input) {
            case "y":
                if (removeMultimedia(id, catName, listName)) {
                    System.out.println("Successfully deleted. returning to main menu");
                    mainMenu(listName);
                } else {
                    System.out.println("Error: Could not delete.");
                    multMenu(listName, catName);
                }
                break;
            case "n":
                System.out.println("returning to multimedia menu.");
                multMenu(listName, catName);
                break;
        }
    }

   
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        List<Catalogue> cats = new ArrayList<>();
        startBackend(cats);
        mainMenu(cats);

    }
}
