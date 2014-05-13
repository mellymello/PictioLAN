-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Lun 12 Mai 2014 à 16:50
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `pictiolan`
--
CREATE DATABASE IF NOT EXISTS `pictiolan` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `pictiolan`;

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `ID_Category` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY (`ID_Category`),
  UNIQUE KEY `ID_IND` (`ID_Category`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `category`
--

INSERT INTO `category` (`ID_Category`, `Name`) VALUES
(1, 'Animaux'),
(2, 'Fruits'),
(3, 'Légumes'),
(4, 'Sports');

-- --------------------------------------------------------

--
-- Structure de la table `player`
--

CREATE TABLE IF NOT EXISTS `player` (
  `ID_Player` int(11) NOT NULL AUTO_INCREMENT,
  `Pseudo` varchar(50) NOT NULL,
  `Password` char(40) NOT NULL,
  `Email` varchar(255),
  `SubscribeDate` datetime,
  PRIMARY KEY (`ID_Player`),
  UNIQUE KEY `ID_IND` (`ID_Player`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `player`
--

INSERT INTO `player` (`ID_Player`, `Pseudo`, `Password`) VALUES
(1, 'Player1', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8');

-- --------------------------------------------------------

--
-- Structure de la table `stats`
--

CREATE TABLE IF NOT EXISTS `stats` (
  `ID_Stat` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Player` int(11) NOT NULL,
  `ID_Word` decimal(10,0) NOT NULL,
  `Time` time NOT NULL,
  `Win` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID_Stat`),
  UNIQUE KEY `ID_IND` (`ID_Stat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `stats`
--

INSERT INTO `stats` (`ID_Stat`, `ID_Player`, `ID_Word`, `Time`, `Win`) VALUES
(1, 1, '1', '00:01:30', 1);

-- --------------------------------------------------------

--
-- Structure de la table `word`
--

CREATE TABLE IF NOT EXISTS `word` (
  `ID_Word` int(11) NOT NULL AUTO_INCREMENT,
  `Word` varchar(50) NOT NULL,
  `ID_Category` decimal(10,0) NOT NULL,
  PRIMARY KEY (`ID_Word`),
  UNIQUE KEY `ID_IND` (`ID_Word`),
  KEY `FKContain_IND` (`ID_Category`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=114 ;

--
-- Contenu de la table `word`
--

INSERT INTO `word` (`ID_Word`, `Word`, `ID_Category`) VALUES
(1, 'Alpaga', '1'),
(2, 'Anes', '1'),
(3, 'Antilope', '1'),
(4, 'Babouins', '1'),
(5, 'Baleines', '1'),
(6, 'Belettes', '1'),
(7, 'Bisons', '1'),
(8, 'Blaireau', '1'),
(9, 'Boeuf', '1'),
(10, 'Bouquetins', '1'),
(11, 'Buffle', '1'),
(12, 'Cachalots', '1'),
(13, 'Caribou', '1'),
(14, 'Castor', '1'),
(15, 'Cerf', '1'),
(16, 'Chacal', '1'),
(17, 'Chameaux', '1'),
(18, 'Chamois', '1'),
(19, 'Chat', '1'),
(20, 'Cheval', '1'),
(21, 'Chèvre', '1'),
(22, 'Chevreuils', '1'),
(23, 'Chien', '1'),
(24, 'Chimpanzé', '1'),
(25, 'Cochon', '1'),
(26, 'Coyote', '1'),
(27, 'Daim', '1'),
(28, 'Dauphin', '1'),
(29, 'Dromadaire', '1'),
(30, 'Ecureuil', '1'),
(31, 'Elan', '1'),
(32, 'Eléphant', '1'),
(33, 'Fouine', '1'),
(34, 'Girafe', '1'),
(35, 'Guépard', '1'),
(36, 'Hamster', '1'),
(37, 'Hérisson', '1'),
(38, 'Hippopotame', '1'),
(39, 'Lion', '1'),
(40, 'Loup', '1'),
(41, 'Loutre', '1'),
(42, 'Lynx', '1'),
(43, 'Mouton', '1'),
(44, 'Ours', '1'),
(45, 'Rat', '1'),
(46, 'Renard', '1'),
(47, 'Rhinocéros', '1'),
(48, 'Sanglier', '1'),
(49, 'Singe', '1'),
(50, 'Souris', '1'),
(51, 'Taupe', '1'),
(52, 'Vache', '1'),
(53, 'Zèbre', '1'),
(54, 'Abricot', '2'),
(55, 'Ananas', '2'),
(56, 'Banane', '2'),
(57, 'Cerise', '2'),
(58, 'Citron', '2'),
(59, 'Fraise', '2'),
(60, 'Framboise', '2'),
(61, 'Kiwi', '2'),
(62, 'Mangue', '2'),
(63, 'Melon', '2'),
(64, 'Mûre', '2'),
(65, 'Noisette', '2'),
(66, 'Noix de coco', '2'),
(67, 'Orange', '2'),
(68, 'Pastèque', '2'),
(69, 'Poire', '2'),
(70, 'Pomme', '2'),
(71, 'Raisin', '2'),
(72, 'Ail', '3'),
(73, 'Asperge', '3'),
(74, 'Brocoli', '3'),
(75, 'Carotte', '3'),
(76, 'Champignon', '3'),
(77, 'Chou', '3'),
(78, 'Citrouille', '3'),
(79, 'Concombre', '3'),
(80, 'Courgette', '3'),
(81, 'Epinard', '3'),
(82, 'Haricot', '3'),
(83, 'Laitue', '3'),
(84, 'Maïs', '3'),
(85, 'Oignon', '3'),
(86, 'Radis', '3'),
(87, 'Aerobic', '4'),
(88, 'Badminton', '4'),
(89, 'Baseball', '4'),
(90, 'Basketball', '4'),
(91, 'Billard', '4'),
(92, 'Boomerang', '4'),
(93, 'Bowling', '4'),
(94, 'Course', '4'),
(95, 'Cyclisme', '4'),
(96, 'Danse', '4'),
(97, 'Equitation', '4'),
(98, 'Escrime', '4'),
(99, 'Football', '4'),
(100, 'Golf', '4'),
(101, 'Gymnastique', '4'),
(102, 'Handball', '4'),
(103, 'Hockey', '4'),
(104, 'Karaté', '4'),
(105, 'Natation', '4'),
(106, 'Paintball', '4'),
(107, 'Parachutisme', '4'),
(108, 'Pétanque', '4'),
(109, 'Rugby', '4'),
(110, 'Ski', '4'),
(111, 'Snowboard', '4'),
(112, 'Tennis', '4'),
(113, 'Volleyball', '4');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
