-- ============================================
--   Library Management System Database
--   Author: S P Sricharan
--   Tech: SQL (MySQL)
-- ============================================

-- Create and use database
CREATE DATABASE IF NOT EXISTS LibraryManagementDB;
USE LibraryManagementDB;

-- ============================================
-- TABLE: books
-- ============================================
CREATE TABLE books (
    book_id     INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100) NOT NULL,
    author      VARCHAR(100) NOT NULL,
    genre       VARCHAR(50),
    is_available BOOLEAN DEFAULT TRUE,
    added_on    DATE DEFAULT (CURRENT_DATE)
);

-- ============================================
-- TABLE: members
-- ============================================
CREATE TABLE members (
    member_id   INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    joined_on   DATE DEFAULT (CURRENT_DATE)
);

-- ============================================
-- TABLE: borrow_records
-- ============================================
CREATE TABLE borrow_records (
    record_id       INT PRIMARY KEY AUTO_INCREMENT,
    book_id         INT NOT NULL,
    member_id       INT NOT NULL,
    borrow_date     DATE DEFAULT (CURRENT_DATE),
    return_date     DATE,
    status          ENUM('Borrowed', 'Returned') DEFAULT 'Borrowed',
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);

-- ============================================
-- INSERT: Sample Books
-- ============================================
INSERT INTO books (title, author, genre) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Classic'),
('To Kill a Mockingbird', 'Harper Lee', 'Fiction'),
('Clean Code', 'Robert C. Martin', 'Technology'),
('Introduction to Algorithms', 'Thomas H. Cormen', 'Technology'),
('1984', 'George Orwell', 'Dystopian'),
('The Pragmatic Programmer', 'Andrew Hunt', 'Technology'),
('Atomic Habits', 'James Clear', 'Self-Help');

-- ============================================
-- INSERT: Sample Members
-- ============================================
INSERT INTO members (name, email) VALUES
('S P Sricharan', 'sricharan@email.com'),
('Alice Johnson', 'alice@email.com'),
('Bob Smith', 'bob@email.com');

-- ============================================
-- INSERT: Borrow Records
-- ============================================
INSERT INTO borrow_records (book_id, member_id, borrow_date, status) VALUES
(3, 1, '2025-01-10', 'Borrowed'),
(4, 1, '2025-01-10', 'Borrowed'),
(1, 2, '2025-01-12', 'Borrowed');

INSERT INTO borrow_records (book_id, member_id, borrow_date, return_date, status) VALUES
(5, 3, '2025-01-05', '2025-01-15', 'Returned');

-- Update book availability
UPDATE books SET is_available = FALSE WHERE book_id IN (1, 3, 4);

-- ============================================
-- QUERIES
-- ============================================

-- 1. View all books
SELECT * FROM books;

-- 2. View all available books
SELECT * FROM books WHERE is_available = TRUE;

-- 3. View all borrowed books
SELECT * FROM books WHERE is_available = FALSE;

-- 4. View all members
SELECT * FROM members;

-- 5. View all active borrow records with member and book details
SELECT
    br.record_id,
    m.name AS member_name,
    b.title AS book_title,
    b.author,
    br.borrow_date,
    br.status
FROM borrow_records br
JOIN members m ON br.member_id = m.member_id
JOIN books b ON br.book_id = b.book_id
WHERE br.status = 'Borrowed';

-- 6. Books borrowed by a specific member (Sricharan)
SELECT
    b.title,
    b.author,
    br.borrow_date,
    br.status
FROM borrow_records br
JOIN books b ON br.book_id = b.book_id
JOIN members m ON br.member_id = m.member_id
WHERE m.name = 'S P Sricharan';

-- 7. Search books by genre
SELECT * FROM books WHERE genre = 'Technology';

-- 8. Count books per genre
SELECT genre, COUNT(*) AS total_books
FROM books
GROUP BY genre
ORDER BY total_books DESC;

-- 9. Members who borrowed more than 1 book
SELECT
    m.name,
    COUNT(br.record_id) AS total_borrowed
FROM borrow_records br
JOIN members m ON br.member_id = m.member_id
WHERE br.status = 'Borrowed'
GROUP BY m.name
HAVING COUNT(br.record_id) > 1;

-- 10. Return a book (update record and availability)
UPDATE borrow_records
SET status = 'Returned', return_date = CURRENT_DATE
WHERE book_id = 3 AND member_id = 1 AND status = 'Borrowed';

UPDATE books SET is_available = TRUE WHERE book_id = 3;

-- 11. Full borrow history
SELECT
    br.record_id,
    m.name AS member_name,
    b.title AS book_title,
    br.borrow_date,
    br.return_date,
    br.status
FROM borrow_records br
JOIN members m ON br.member_id = m.member_id
JOIN books b ON br.book_id = b.book_id
ORDER BY br.borrow_date DESC;
