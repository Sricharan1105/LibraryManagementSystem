public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   📚 Library Management System v1.0   ");
        System.out.println("========================================\n");

        Library library = new Library();

        System.out.println("\n--- Display All Books ---");
        library.displayAllBooks();

        System.out.println("\n--- Display All Members ---");
        library.displayAllMembers();

        System.out.println("\n--- Borrow Books ---");
        library.borrowBook(1, 3); // Sricharan borrows Clean Code
        library.borrowBook(1, 4); // Sricharan borrows Introduction to Algorithms
        library.borrowBook(2, 1); // Alice borrows The Great Gatsby

        System.out.println("\n--- Updated Book List ---");
        library.displayAllBooks();

        System.out.println("\n--- Member Borrowed Books ---");
        library.displayMemberBooks(1);

        System.out.println("\n--- Search by Title ---");
        library.searchByTitle("code");

        System.out.println("\n--- Search by Author ---");
        library.searchByAuthor("Orwell");

        System.out.println("\n--- Return a Book ---");
        library.returnBook(1, 3); // Sricharan returns Clean Code

        System.out.println("\n--- Final Book List ---");
        library.displayAllBooks();

        System.out.println("\n========================================");
        System.out.println("   ✅ Library System Demo Complete!     ");
        System.out.println("========================================");
    }
}
