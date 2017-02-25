-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Фев 25 2017 г., 17:35
-- Версия сервера: 5.5.50-MariaDB
-- Версия PHP: 7.1.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `test`
--

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `age` tinyint(1) UNSIGNED NOT NULL,
  `isAdmin` bit(1) NOT NULL DEFAULT b'0',
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `name`, `age`, `isAdmin`, `createdDate`) VALUES
(1, 'Иванов Иван', 24, b'0', '2017-02-25 08:24:15'),
(2, 'Петров Петр', 35, b'1', '2017-02-25 08:24:28'),
(3, 'Сидоров Илья', 13, b'0', '2017-02-25 08:24:49'),
(4, 'Смирнов Александр', 46, b'0', '2017-02-25 08:25:11'),
(5, 'Васильева Ольга', 17, b'0', '2017-02-25 08:26:43'),
(8, 'Морозов Глеб', 1, b'0', '2017-02-25 08:28:48'),
(9, 'Валова Елена', 31, b'1', '2017-02-25 08:29:25'),
(10, 'Игнатов Павел', 42, b'0', '2017-02-25 08:30:19'),
(11, 'Буров Игорь', 55, b'0', '2017-02-25 08:30:39'),
(12, 'Тяжкова Тамара', 26, b'0', '2017-02-25 08:31:34'),
(13, 'Ладогин Антон', 33, b'0', '2017-02-25 08:31:53');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
