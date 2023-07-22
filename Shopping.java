import java.io.*;
import java.util.Scanner;

public class Shopping {

    static int total = 0; // To Store Grand Total
    static FileWriter fileWriter, databaseWriter;
    static File f;
    static Scanner Scanner = new Scanner(System.in);
    static Scanner input;
    static FileReader fr;
    static String productName;
    static int datachoice, cost;

    // Functions
    static void writeData(FileWriter fw, String str) {
        try {
            databaseWriter = new FileWriter(new File("admin/customerDB.txt"), true);
            fw.append(str);
            databaseWriter.append(str);
            databaseWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void findCost(String str, int item1) {
        try {
            Scanner input = new Scanner(new File(str));
            boolean flag = false;
            while (input.hasNext()) {
                datachoice = input.nextInt();
                productName = input.next();
                cost = input.nextInt();
                if (item1 == datachoice) {
                    flag = true;
                    total += cost;
                    writeData(fileWriter, productName + " => " + cost + " is your bill\n");
                }
            }
            if (!flag) {
                System.out.println("Please Enter Correct Value");
            }
        } catch (Exception e) {
        }
    }

    static void displayList(String str) {
        try {
            fr = new FileReader(str);
            int i;
            while ((i = fr.read()) != -1) {
                System.out.print((char) i);
            }
            i = 0;
        } catch (Exception e) {

        }
    }

    static void printHeading(String str) {
        if (str != null) {
            System.out.println("\n" + str + " Items Details");
        }
        System.out.println("----------------------");
    }

    static void choiceSelection(String str) {
        int item = 0;
        printHeading(str);
        displayList("Datasets/" + str + ".txt");
        System.out.println("\nEnter Your Choice: ");
        item = Scanner.nextInt();
        findCost("Datasets/" + str + ".txt", item);
    }

    // View Database Files
    static void viewDatabase(String str) {
        printHeading(str);
        File directory = new File(str);
        File[] files = directory.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
        System.out.println();
        System.out.print("Enter the Bill You want to see: ");
        String bill = Scanner.nextLine();
        System.out.println("\n" + bill + " Details");
        System.out.println("----------------------");
        displayList("databases/" + bill);
        printHeading(null);
    }

    // RefreshDatasets
    static void refreshDatasets(String fileDirectory) {
        try {
            File f = new File("admin/list.txt");
            FileWriter fileWriter = new FileWriter(f);
            String str = null;
            File directory = new File(fileDirectory);
            File[] files = directory.listFiles();
            int i = 1;
            for (File file : files) {
                str = i + " " + file.getName();
                fileWriter.write(str + "\n"); // write data to file
                i++;
            }
            fileWriter.close(); // close the FileWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Insert Data Function
    static void insertData() {
        try {
            refreshDatasets("Datasets");
            printHeading("Datasets");
            displayList("admin/list.txt");
            System.out.print("\nPlease Enter your Choice: ");
            int select = Scanner.nextInt();
            int listno, cost, flag = 0;
            String listname = null;
            Scanner listview = new Scanner(new File("admin/list.txt"));
            while (listview.hasNext()) {
                listno = listview.nextInt();
                listname = listview.next();
                if (listno == select) {
                    flag = 1;
                    System.out.print(listno + " " + listname);
                    break;
                }
            }
            if (flag == 1) {
                System.out.println();
                printHeading("Selected ListName:" + listname + " ");
                displayList("datasets/" + listname);

                FileWriter insert = new FileWriter("Datasets/" + listname, true);

                System.out.println("\n\nInsert Details");
                System.out.println("----------------");
                System.out.print("Serial No: ");
                listno = Scanner.nextInt();
                Scanner.nextLine();
                System.out.print("Product Details: ");
                listname = Scanner.nextLine();
                System.out.print("Product Cost: ");
                cost = Scanner.nextInt();

                String append = listno + " " + listname + " " + cost + "\n";
                insert.write(append);
                insert.flush();
                insert.close();
            } else {
                System.out.println("\nData not Found");
            }
        } catch (Exception e) {
        }
    }

    static void deleteData() {
        try {
            refreshDatasets("Datasets");
            printHeading("Datasets");
            displayList("admin/list.txt");
            System.out.print("\nPlease Enter your Choice: ");
            int select = Scanner.nextInt();
            int listno, flag = 0;
            String listname = null;
            Scanner listview = new Scanner(new File("admin/list.txt"));
            while (listview.hasNext()) {
                listno = listview.nextInt();
                listname = listview.next();
                if (listno == select) {
                    flag = 1;
                    System.out.print(listno + " " + listname);
                    break;
                }
            }
            if (flag == 1) {
                System.out.println();
                printHeading("Selected ListName:" + listname + " ");
                displayList("datasets/" + listname);
                System.out.print("Serial No: ");
                listno = Scanner.nextInt();

                FileReader delete = new FileReader("Datasets/" + listname);
                FileWriter fw = new FileWriter("datasets/temp.txt");
                BufferedReader reader = new BufferedReader(delete);
                String line;
                int lineNumber = 1;
                while ((line = reader.readLine()) != null) {
                    if (lineNumber != listno) {
                        fw.write(line + "\n");
                    }
                    lineNumber++;
                }
                fw.close();
                reader.close();
                delete.close();

                File originalFile = new File("Datasets/grocery.txt");
                File tempFile = new File("datasets/temp.txt");
                BufferedReader readerr = new BufferedReader(new FileReader(tempFile));
                BufferedWriter writerr = new BufferedWriter(new FileWriter(originalFile));
                String linee;
                while ((linee = readerr.readLine()) != null) {
                    writerr.write(linee);
                    writerr.newLine(); // Write a new line after each line
                }
                tempFile.delete();
                readerr.close();
                writerr.close();
            }

        } catch (Exception e) {
        }
    }

    static void writeInDatabase() {
        printHeading("Sales Report");
        System.out.println();
        displayList("admin/customerDB");
    }

    public static void main(String[] args) {

        displayList("Datasets/typeofuser.txt");
        System.out.print("\nPlease Enter your Choice: ");
        int user = Scanner.nextInt();
        Scanner.nextLine();

        if (user == 1) {

            int count = 3, ch = 0;
            System.out.print("\nPlease Enter Credentials");
            System.out.println("\n-----------------------");
            while (true) {
                System.out.print("Enter username: ");
                String username = Scanner.nextLine();
                System.out.print("Enter password: ");
                String password = Scanner.nextLine();
                if (username.trim().equals("abc") && password.trim().equals("123")) {
                    System.out.print("\nWelcome Admin\n");
                    break;
                } else {
                    count--;
                    if (count == 0) {
                        System.out.print("\nSorry End of Limit\n");
                        System.exit(0);
                    }
                    System.out.print("\nWrong Credentials\n");
                    System.out.print("-------------------------------\n\n");
                }
            }
            do {
                int item = 0;
                printHeading("Operations");
                displayList("admin/adminchoice.txt");
                System.out.print("\n\nEnter Your Choice: ");
                item = Scanner.nextInt();
                Scanner.nextLine();
                switch (item) {
                    case 1:
                        viewDatabase("databases");
                        break;
                    case 2:
                        insertData();
                        break;
                    case 3:
                        deleteData();
                        break;
                    case 4:
                        writeInDatabase();
                        break;
                    default:
                        System.out.println("\nPlease Enter Correct Credentials");
                }
                displayList("Datasets/continueShopping.txt");
                System.out.print("Enter Your Choice : ");
                ch = Scanner.nextInt();
                System.out.println();
            } while (ch > 0 && ch <= 1);

        } else if (user == 2)

        {
            try {
                System.out.println("\nHello Happy User");
                while (true) {
                    System.out.print("\nEnter File name You want to Store The Data: ");
                    String str = Scanner.nextLine();
                    f = new File("databases/" + str + ".txt");
                    if (!f.exists()) {
                        fileWriter = new FileWriter(f);
                        System.out.print("Enter Your Full Name:");
                        String name = Scanner.nextLine();
                        writeData(fileWriter, "Bill Owner : " + name
                                + "\n----------------------------------------------\n");
                        break;
                    } else {
                        System.out.print("\nFile already exists select different FileName!!\n");
                        System.out.print("-------------------------------\n\n");
                    }
                }
                int c, ch;
                do {
                    displayList("Datasets/maincase.txt");
                    System.out.print("\n\nEnter Your Choice: ");
                    c = Scanner.nextInt();
                    System.out.println();
                    switch (c) {
                        case 1:
                            choiceSelection("Smartphone");
                            break;
                        case 2:
                            choiceSelection("Grocery");
                            break;
                        case 3:
                            choiceSelection("Children");
                            break;
                        case 4:
                            choiceSelection("Men");
                            break;
                        case 5:
                            choiceSelection("Women");
                            break;
                        default:
                            System.out.println("you have enter wrong choice");
                    }
                    displayList("Datasets/continueShopping.txt");
                    System.out.print("Enter Your Choice : ");
                    ch = Scanner.nextInt();
                    System.out.println();
                } while (ch > 0 && ch <= 1);
                System.out.println("Grand Total: " + total + "\n");
                writeData(fileWriter, "Grand Total: " + total + "\n\n");
                fileWriter.close();
                Scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sorry User Not Found");
            System.exit(0);
        }
    }
}