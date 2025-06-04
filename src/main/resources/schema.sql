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
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_isbn` (`isbn`)
);

CREATE TABLE IF NOT EXISTS `book_author` (
    `id_book` BIGINT,
    `id_author` BIGINT,
    UNIQUE KEY `unique_book_author` (`id_book`, `id_author`),
    CONSTRAINT `fk_book_author_book_id_relation`
        FOREIGN KEY (`id_book`) REFERENCES `book`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT `fk_book_author_author_id_relation`
        FOREIGN KEY (`id_author`) REFERENCES `author`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `book_genre` (
    `id_book` BIGINT,
    `id_genre` BIGINT,
    UNIQUE KEY `unique_book_genre` (`id_book`, `id_genre`),
    CONSTRAINT `fk_book_genre_book_id_relation`
        FOREIGN KEY (`id_book`) REFERENCES `book`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT `fk_book_genre_genre_id_relation`
        FOREIGN KEY (`id_genre`) REFERENCES `genre`(`id`)
        ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT IGNORE INTO `author` (`id`, `name`) VALUES
(1, 'Robert C. Martin'),
(2, 'Andrew Hunt'),
(3, 'David Thomas'),
(4, 'Erich Gamma'),
(5, 'Richard Helm'),
(6, 'Ralph Johnson'),
(7, 'John Vlissides'),
(8, 'Martin Fowler'),
(9, 'Kent Beck'),
(10, 'Eric Evans'),
(11, 'Michael C. Feathers'),
(12, 'Steve McConnell'),
(13, 'Frederick P. Brooks Jr.'),
(14, 'Thomas H. Cormen'),
(15, 'Douglas Crockford'),
(16, 'Marijn Haverbeke'),
(17, 'Eric Matthes'),
(18, 'Alan Beaulieu'),
(19, 'Jeff Geerling'),
(20, 'Laura Gallego García'),
(21, 'Kiera Cass'),
(22, 'Andrzej Sapkowski'),
(23, 'John Ronald Reuel Tolkien'),
(24, 'Antoine de Saint-Exupéry');

INSERT IGNORE INTO `genre` (`id`, `name`) VALUES
(1, 'Programming'),
(2, 'Software Development'),
(3, 'Computer Science'),
(4, 'Web Development'),
(5, 'Database'),
(6, 'DevOps'),
(7, 'Architecture'),
(8, 'Design Patterns'),
(9, 'Testing'),
(10, 'Agile'),
(11, 'Fantasy'),
(12, 'Young Adult'),
(13, 'Adventure'),
(14, 'Mystery'),
(15, 'Dystopian'),
(16, 'Romance'),
(17, 'Dark Fantasy'),
(18, 'Sword and Sorcery'),
(19, 'High Fantasy'),
(20, 'Epic Fantasy'),
(21, 'Fable'),
(22, 'Philosophical Fiction'),
(23, 'Children''s Literature');

INSERT IGNORE INTO `book` (`id`, `title`, `description`, `isbn`) VALUES
(1, 'Clean Code', 'A handbook of agile software craftsmanship', '978-84-96430-01-2'),
(2, 'The Pragmatic Programmer', 'Guide to software development', '978-84-96430-02-3'),
(3, 'Design Patterns', 'Elements of Reusable Object-Oriented Software', '978-84-96430-03-4'),
(4, 'Refactoring', 'Improving the Design of Existing Code', '978-84-96430-04-5'),
(5, 'Head First Design Patterns', 'A Brain-Friendly Guide', '978-84-96430-05-6'),
(6, 'Clean Architecture', 'A Craftsman''s Guide to Software Structure', '978-84-96430-06-7'),
(7, 'Domain-Driven Design', 'Tackling Complexity in the Heart of Software', '978-84-96430-07-8'),
(8, 'Patterns of Enterprise Application Architecture', 'Enterprise Application Architecture', '978-84-96430-08-9'),
(9, 'Working Effectively with Legacy Code', 'Practical techniques for working with legacy code', '978-84-96430-09-0'),
(10, 'Test-Driven Development', 'By Example', '978-84-96430-10-1'),
(11, 'The Clean Coder', 'A Code of Conduct for Professional Programmers', '978-84-96430-11-2'),
(12, 'Code Complete', 'A Practical Handbook of Software Construction', '978-84-96430-12-3'),
(13, 'The Mythical Man-Month', 'Essays on Software Engineering', '978-84-96430-13-4'),
(14, 'Introduction to Algorithms', 'A Comprehensive Guide', '978-84-96430-14-5'),
(15, 'Memories from Idhún', 'A high-fantasy trilogy that follows the story of Jack, Victoria, and Kirtash as they fight to save the magical world of Idhún from tyranny and destruction.', '978-84-675-0011-1'),
(16, 'The Collector of Extraordinary Clocks', 'When Jonathan''s stepmother falls ill in a mysterious town, he must venture into a museum full of magical and dangerous clocks to find a way to save her.', '978-84-204-6983-6'),
(17, 'The Selection', 'In a dystopian future, 35 girls are selected to compete for the prince''s heart in a televised competition that mixes royalty with reality show drama.', '978-0-06-205994-9'),
(18, 'The Witcher', 'The series follows Geralt of Rivia, a monster hunter with supernatural abilities, as he navigates a morally complex world filled with magic, war, and political intrigue.', '978-0-316-03482-8'),
(19, 'The Lord of the Rings', 'A sweeping epic set in Middle-earth, chronicling the journey to destroy the One Ring and defeat Sauron, the Dark Lord.', '978-0-618-00222-8'),
(20, 'The Little Prince', 'A poetic and philosophical story of a little boy from another planet, exploring love, loneliness, and human folly through encounters on different worlds.', '978-0-15-601219-5');

INSERT IGNORE INTO `book_author` (`id_book`, `id_author`) VALUES
(1, 1), (2, 2), (2, 3), (3, 4), (3, 5), (3, 6), (3, 7),
(4, 8), (5, 4), (6, 1), (7, 10), (8, 8), (9, 11), (10, 9),
(11, 1), (12, 12), (13, 13), (14, 14),
(15, 20), (16, 20), (17, 21), (18, 22), (19, 23), (20, 24);

INSERT IGNORE INTO `book_genre` (`id_book`, `id_genre`) VALUES
(1, 1), (1, 2), (2, 1), (2, 2), (3, 1), (3, 8),
(4, 1), (4, 2), (5, 1), (5, 8), (6, 7), (6, 2),
(7, 7), (7, 2), (8, 7), (8, 8), (9, 1), (9, 2),
(10, 9), (10, 2), (11, 1), (11, 2), (12, 1), (12, 2),
(13, 3), (13, 2), (14, 3), (14, 1),
(15, 11), (15, 12), (15, 13),
(16, 11), (16, 14), (16, 12),
(17, 15), (17, 16), (17, 12),
(18, 11), (18, 17), (18, 18),
(19, 19), (19, 20),
(20, 21), (20, 22), (20, 23);
