CREATE DATABASE IF NOT EXISTS library;
USE library;

CREATE TABLE IF NOT EXISTS `author` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `genre` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `book` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100),
    `description` VARCHAR(200),
    `isbn` VARCHAR(25),
    `author_id` BIGINT,
    `genre_id` BIGINT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`author_id`) REFERENCES `author`(`id`),
    FOREIGN KEY (`genre_id`) REFERENCES `genre`(`id`)
);

CREATE TABLE IF NOT EXISTS `book_author` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `id_book` BIGINT,
    `id_author` BIGINT,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `book_genre` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `id_book` BIGINT,
    `id_genre` BIGINT,
    PRIMARY KEY (`id`)
);

-- Foreign key constraints
ALTER TABLE `book_genre`
ADD CONSTRAINT `fk_book_genre_id_book` FOREIGN KEY(`id_book`) REFERENCES `book`(`id`)
ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE `book_genre`
ADD CONSTRAINT `fk_book_genre_id_genre` FOREIGN KEY(`id_genre`) REFERENCES `genre`(`id`)
ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE `book_author`
ADD CONSTRAINT `fk_book_author_id_book` FOREIGN KEY(`id_book`) REFERENCES `book`(`id`)
ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE `book_author`
ADD CONSTRAINT `fk_book_author_id_author` FOREIGN KEY(`id_author`) REFERENCES `author`(`id`)
ON UPDATE CASCADE ON DELETE RESTRICT;

-- Insert sample data
INSERT INTO `author` (name) VALUES
('Robert C. Martin'),
('Andrew Hunt'),
('David Thomas'),
('Erich Gamma'),
('Richard Helm'),
('Ralph Johnson'),
('John Vlissides'),
('Martin Fowler'),
('Kent Beck'),
('Eric Evans'),
('Michael C. Feathers'),
('Martin Fowler'),
('Steve McConnell'),
('Frederick P. Brooks Jr.'),
('Thomas H. Cormen'),
('Douglas Crockford'),
('Marijn Haverbeke'),
('Eric Matthes'),
('Alan Beaulieu'),
('Jeff Geerling');

INSERT INTO `genre` (name) VALUES
('Programming'),
('Software Development'),
('Computer Science'),
('Web Development'),
('Database'),
('DevOps'),
('Architecture'),
('Design Patterns'),
('Testing'),
('Agile');

-- Insert books with direct references to authors and genres
INSERT INTO `book` (title, description, isbn, author_id, genre_id) VALUES
('Clean Code', 'A handbook of agile software craftsmanship', '978-84-96430-01-2', 1, 1),
('The Pragmatic Programmer', 'Guide to software development', '978-84-96430-02-3', 2, 2),
('Design Patterns', 'Elements of Reusable Object-Oriented Software', '978-84-96430-03-4', 4, 8),
('Refactoring', 'Improving the Design of Existing Code', '978-84-96430-04-5', 8, 1),
('Head First Design Patterns', 'A Brain-Friendly Guide', '978-84-96430-05-6', 4, 8),
('Clean Architecture', 'A Craftsman''s Guide to Software Structure', '978-84-96430-06-7', 1, 7),
('Domain-Driven Design', 'Tackling Complexity in the Heart of Software', '978-84-96430-07-8', 10, 7),
('Patterns of Enterprise Application Architecture', 'Enterprise Application Architecture', '978-84-96430-08-9', 8, 8),
('Working Effectively with Legacy Code', 'Practical techniques for working with legacy code', '978-84-96430-09-0', 11, 1),
('Test-Driven Development', 'By Example', '978-84-96430-10-1', 9, 9),
('The Clean Coder', 'A Code of Conduct for Professional Programmers', '978-84-96430-11-2', 1, 2),
('Code Complete', 'A Practical Handbook of Software Construction', '978-84-96430-12-3', 13, 1),
('The Mythical Man-Month', 'Essays on Software Engineering', '978-84-96430-13-4', 14, 2),
('Introduction to Algorithms', 'A Comprehensive Guide', '978-84-96430-14-5', 15, 3),
('JavaScript: The Good Parts', 'The Good Parts of JavaScript', '978-84-96430-15-6', 16, 4),
('Eloquent JavaScript', 'A Modern Introduction to Programming', '978-84-96430-16-7', 17, 4),
('Python Crash Course', 'A Hands-On, Project-Based Introduction', '978-84-96430-17-8', 18, 1),
('Learning SQL', 'Master SQL Fundamentals', '978-84-96430-18-9', 19, 5),
('Git for Developers', 'A Complete Guide', '978-84-96430-19-0', 20, 6),
('Docker in Action', 'Containerization Made Easy', '978-84-96430-20-1', 20, 6);

-- Linking books with authors
INSERT INTO `book_author` (id_book, id_author) VALUES
(1, 1), (2, 2), (2, 3), (3, 4), (3, 5), (3, 6), (3, 7),
(4, 8), (5, 4), (6, 1), (7, 10), (8, 8), (9, 11), (10, 9),
(11, 1), (12, 13), (13, 14), (14, 15), (15, 16), (16, 17),
(17, 18), (18, 19), (19, 20), (20, 20);

-- Linking books with genres
INSERT INTO `book_genre` (id_book, id_genre) VALUES
(1, 1), (1, 2), (2, 1), (2, 2), (3, 1), (3, 8),
(4, 1), (4, 2), (5, 1), (5, 8), (6, 7), (6, 2),
(7, 7), (7, 2), (8, 7), (8, 8), (9, 1), (9, 2),
(10, 9), (10, 2), (11, 1), (11, 2), (12, 1), (12, 2),
(13, 3), (13, 2), (14, 3), (14, 1), (15, 4), (15, 1),
(16, 4), (16, 1), (17, 1), (17, 4), (18, 5), (18, 1),
(19, 6), (19, 1), (20, 6), (20, 1);
