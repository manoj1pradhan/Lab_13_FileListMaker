import javax.swing.JFileChooser;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Lab_13_FileListMaker {
    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("A - Add an item to the list");
            System.out.println("D - Delete an item from the list");
            System.out.println("V - View the list on the screen");
            System.out.println("O - Open a list file from disk");
            System.out.println("S - Save the current list file to disk");
            System.out.println("C - Clear the list");
            System.out.println("Q - Quit the program");
            System.out.print("Choose an option: ");
            input = scanner.nextLine().toUpperCase();

            switch (input) {
                case "A":
                    System.out.print("Enter an item to add: ");
                    addItem(scanner.nextLine());
                    break;
                case "D":
                    System.out.print("Enter an item to delete: ");
                    deleteItem(scanner.nextLine());
                    break;
                case "V":
                    viewList();
                    break;
                case "O":
                    openList();
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    if (needsToBeSaved) {
                        System.out.print("You have unsaved changes. Would you like to save? (Y/N): ");
                        String saveInput = scanner.nextLine().toUpperCase();
                        if (saveInput.equals("Y")) {
                            saveList();
                        }
                    }
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!input.equals("Q"));
    }

    private static void addItem(String item) {
        list.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem(String item) {
        if (list.remove(item)) {
            System.out.println("Item removed.");
            needsToBeSaved = true;
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("List items:");
            for (String item : list) {
                System.out.println(item);
            }
        }
    }

    private static void openList() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                list.clear();
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }
                needsToBeSaved = false;
                System.out.println("List loaded from file.");
            } catch (IOException e) {
                System.out.println("An error occurred while opening the file.");
            }
        }
    }

    private static void saveList() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(selectedFile))) {
                for (String item : list) {
                    writer.println(item);
                }
                needsToBeSaved = false;
                System.out.println("List saved to file.");
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file.");
            }
        }
    }

    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }
}