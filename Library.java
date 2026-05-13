import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Member> members;
    private int bookIdCounter = 1;
    private int memberIdCounter = 1;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        seedData();
    }

    private void seedData() {
        addBook("The Great Gatsby", "F. Scott Fitzgerald", "Classic");
        addBook("To Kill a Mockingbird", "Harper Lee", "Fiction");
        addBook("Clean Code", "Robert C. Martin", "Technology");
        addBook("Introduction to Algorithms", "Thomas H. Cormen", "Technology");
        addBook("1984", "George Orwell", "Dystopian");

        addMember("S P Sricharan", "sricharan@email.com");
        addMember("Alice Johnson", "alice@email.com");
    }

    public Book addBook(String title, String author, String genre) {
        Book book = new Book(bookIdCounter++, title, author, genre);
        books.add(book);
        System.out.println("✅ Book added: " + book.getTitle());
        return book;
    }

    public Member addMember(String name, String email) {
        Member member = new Member(memberIdCounter++, name, email);
        members.add(member);
        System.out.println("✅ Member registered: " + member.getName());
        return member;
    }

    public void borrowBook(int memberId, int bookId) {
        Member member = findMember(memberId);
        Book book = findBook(bookId);

        if (member == null) {
            System.out.println("❌ Member not found.");
            return;
        }
        if (book == null) {
            System.out.println("❌ Book not found.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("❌ Book is currently not available.");
            return;
        }

        book.setAvailable(false);
        member.borrowBook(book);
        System.out.println("📖 " + member.getName() + " borrowed: " + book.getTitle());
    }

    public void returnBook(int memberId, int bookId) {
        Member member = findMember(memberId);
        Book book = findBook(bookId);

        if (member == null || book == null) {
            System.out.println("❌ Invalid member or book ID.");
            return;
        }

        book.setAvailable(true);
        member.returnBook(book);
        System.out.println("✅ " + member.getName() + " returned: " + book.getTitle());
    }

    public void searchByTitle(String keyword) {
        System.out.println("\n🔍 Search Results for \"" + keyword + "\":");
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) System.out.println("No books found.");
    }

    public void searchByAuthor(String author) {
        System.out.println("\n🔍 Books by \"" + author + "\":");
        boolean found = false;
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) System.out.println("No books found.");
    }

    public void displayAllBooks() {
        System.out.println("\n📚 All Books in Library:");
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    public void displayAllMembers() {
        System.out.println("\n👥 All Members:");
        if (members.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            members.forEach(System.out::println);
        }
    }

    public void displayMemberBooks(int memberId) {
        Member member = findMember(memberId);
        if (member == null) {
            System.out.println("❌ Member not found.");
            return;
        }
        System.out.println("\n📖 Books borrowed by " + member.getName() + ":");
        if (member.getBorrowedBooks().isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            member.getBorrowedBooks().forEach(System.out::println);
        }
    }

    private Book findBook(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    private Member findMember(int id) {
        return members.stream().filter(m -> m.getMemberId() == id).findFirst().orElse(null);
    }
}
