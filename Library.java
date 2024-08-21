import java.io.*;
import java.util.*;

public class Library {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private double price;
    private boolean isIssued;
    private final String FILENAME = "library.txt";
    private ArrayList<Library> libraryList = new ArrayList<>();

    public Library(int id, String title, String author, String publisher, double price, boolean isIssued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.isIssued = isIssued;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public double getPrice() {
        return price;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author + ", Publisher: " + publisher + ", Price: $" + price + ", Issued: " + isIssued;
    }

    private void addBook() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the book title: ");
        String title = input.nextLine();
        System.out.println("Enter the author name: ");
        String author = input.nextLine();
        System.out.println("Enter the publisher: ");
        String publisher = input.nextLine();
        System.out.println("Enter the book price: ");
        double price = input.nextDouble();
        System.out.println("Is the book issued? (y/n): ");
        boolean isIssued = input.next().equalsIgnoreCase("y");
        Library book = new Library(libraryList.size() + 1, title, author, publisher, price, isIssued);
        libraryList.add(book);
    }

    private void displayAllBooks() {
        for (Library book : libraryList) {
            System.out.println(book);
        }
    }

    private void listBooksByAuthor(String authorName) {
        for (Library book : libraryList) {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
                System.out.println(book);
            }
        }
    }

    private void listBookTitleAndPublisher(int id) {
        for (Library book : libraryList) {
            if (book.getId() == id) {
                System.out.println("Title: " + book.getTitle() + ", Publisher: " + book.getPublisher());
                return;
            }
        }
        System.out.println("Book with ID " + id + " not found.");
    }

    private void listBookCount() {
        System.out.println("Total number of books in the library: " + libraryList.size());
    }

    private void listBooksByIdOrder() {
        Collections.sort(libraryList, Comparator.comparingInt(Library::getId));
        displayAllBooks();
    }

    private void saveLibraryToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(libraryList);
            System.out.println("Library data saved to " + FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLibraryFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            libraryList = (ArrayList<Library>) ois.readObject();
            System.out.println("Library data loaded from " + FILENAME);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Library library = new Library(0, "", "", "", 0.0, false); // Dummy initialization
        library.loadLibraryFromFile(); // Load library data from file on program start

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nLibrary Management System Menu:");
            System.out.println("1. Add book information");
            System.out.println("2. Display all book information");
            System.out.println("3. List all books of given author");
            System.out.println("4. List the title and publisher of specified book");
            System.out.println("5. List the count of books in the library");
            System.out.println("6. List books in order of ID number");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    library.addBook();
                    break;
                case 2:
                    library.displayAllBooks();
                    break;
                case 3:
                    System.out.print("Enter author name: ");
                    String authorName = scanner.nextLine();
                    library.listBooksByAuthor(authorName);
                    break;
                case 4:
                    System.out.print("Enter book ID: ");
                    int bookId = scanner.nextInt();
                    library.listBookTitleAndPublisher(bookId);
                    break;
                case 5:
                    library.listBookCount();
                    break;
                case 6:
                    library.listBooksByIdOrder();
                    break;
                case 7:
                    library.saveLibraryToFile(); // Save library data to file before exit
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 7);

        scanner.close();
    }
}
