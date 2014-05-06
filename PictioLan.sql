-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Mar 06 Mai 2014 à 08:03
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
-- Structure de la table `players`
--

CREATE TABLE IF NOT EXISTS `players` (
  `ID_Player` int(11) NOT NULL AUTO_INCREMENT,
  `Pseudo` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_Player`),
  UNIQUE KEY `ID_IND` (`ID_Player`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `playerstats`
--

CREATE TABLE IF NOT EXISTS `playerstats` (
  `ID_Player` int(11) NOT NULL AUTO_INCREMENT,
  `Hav_ID_Player` decimal(10,0) NOT NULL,
  `ID_Word` decimal(10,0) NOT NULL,
  PRIMARY KEY (`ID_Player`),
  UNIQUE KEY `ID_IND` (`ID_Player`),
  KEY `FKHave_IND` (`Hav_ID_Player`),
  KEY `FKSave_stats_IND` (`ID_Word`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `stats`
--

CREATE TABLE IF NOT EXISTS `stats` (
  `Time` char(1) NOT NULL,
  `Win` char(1) NOT NULL,
  `ID_Player` decimal(10,0) NOT NULL,
  KEY `FKUse_IND` (`ID_Player`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `words`
--

CREATE TABLE IF NOT EXISTS `words` (
  `ID_Word` int(11) NOT NULL AUTO_INCREMENT,
  `Word` varchar(50) NOT NULL,
  `ID_Category` decimal(10,0) NOT NULL,
  PRIMARY KEY (`ID_Word`),
  UNIQUE KEY `ID_IND` (`ID_Word`),
  KEY `FKContain_IND` (`ID_Category`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=54 ;

--
-- Contenu de la table `words`
--

INSERT INTO `words` (`ID_Word`, `Word`, `ID_Category`) VALUES
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
(53, 'Zèbre', '1');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
