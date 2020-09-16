-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 16, 2020 at 08:18 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `NewsAgency`
--

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

CREATE TABLE `billing` (
  `billid` int(11) NOT NULL,
  `cust_mobile` varchar(12) NOT NULL,
  `noofdays` varchar(3) NOT NULL,
  `date_of_billing` date NOT NULL,
  `amount` float NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billing`
--

INSERT INTO `billing` (`billid`, `cust_mobile`, `noofdays`, `date_of_billing`, `amount`, `status`) VALUES
(1, 'ghjk', '1', '2020-09-09', 3, 1),
(2, 'ghjk', '1', '2020-09-09', 3, 1),
(3, 'dfghjkl', '0', '2020-09-09', 6, 1),
(4, 'dfghjkl', '2', '2020-09-09', 6, 1),
(5, 'ghjk', '0', '2020-09-09', 0, 1),
(6, 'dfghjkl', '0', '2020-09-09', 0, 1),
(7, 'dfghjkl', '-1', '2020-09-09', -3, 1),
(8, 'ghjk', '-1', '2020-09-09', -3, 1),
(9, 'dfghjkl', '0', '2020-09-10', 0, 0),
(10, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(11, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(12, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(13, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(14, 'ghjk', '-1', '2020-09-10', -3, 0),
(15, 'ghjk', '-1', '2020-09-10', -3, 0),
(16, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(17, 'ghjk', '-1', '2020-09-10', -3, 0),
(18, 'ghjk', '-1', '2020-09-10', -3, 0),
(19, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(20, 'ghjk', '-1', '2020-09-10', -3, 0),
(21, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(22, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(23, 'ghjk', '-1', '2020-09-10', -3, 0),
(24, 'ghjk', '-1', '2020-09-10', -3, 0),
(25, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(26, 'dfghjkl', '-1', '2020-09-10', -3, 0),
(27, 'ghjk', '-1', '2020-09-10', -3, 0),
(28, 'dfghjkl', '5', '2020-09-16', 15, 0),
(29, 'ghjk', '5', '2020-09-16', 15, 0),
(30, 'dfghjkl', '-1', '2020-09-16', -3, 0),
(31, 'ghjk', '-1', '2020-09-16', -3, 0),
(32, 'dfghjkl', '-1', '2020-09-16', -3, 0),
(33, 'dfghjkl', '-1', '2020-09-16', -3, 0),
(34, 'ghjk', '-1', '2020-09-16', -3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `name` varchar(20) NOT NULL,
  `mobile` varchar(12) NOT NULL,
  `address` varchar(30) DEFAULT NULL,
  `papers` varchar(30) DEFAULT NULL,
  `hawker` varchar(20) DEFAULT NULL,
  `curdate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`name`, `mobile`, `address`, `papers`, `hawker`, `curdate`) VALUES
('Purva', 'dfghjkl', 'ghjkll', 'a,d,g,', 'chaman', '2020-09-17'),
('Pranav', 'ghjk', 'ghjkll', 'd,e,hindu,', 'chaman', '2020-09-17');

-- --------------------------------------------------------

--
-- Table structure for table `hawkers`
--

CREATE TABLE `hawkers` (
  `name` varchar(20) NOT NULL,
  `mobile` varchar(12) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `areas` varchar(30) DEFAULT NULL,
  `aadharpic` varchar(50) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `doj` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hawkers`
--

INSERT INTO `hawkers` (`name`, `mobile`, `address`, `areas`, `aadharpic`, `salary`, `doj`) VALUES
('chaman', 'x', '234', 'stu,stud,flood,ploug,', NULL, 33, '2020-09-07'),
('panku', '5678', '678', '', NULL, 678, '2020-09-06'),
('Pranav', 'xyz', '89', 'mlo', 'images/Pranav.png', 0, '2020-09-04'),
('Purva', '5678', '678', 'stu,', NULL, 67, '2020-09-06');

-- --------------------------------------------------------

--
-- Table structure for table `papers`
--

CREATE TABLE `papers` (
  `paper` varchar(20) NOT NULL,
  `price` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `papers`
--

INSERT INTO `papers` (`paper`, `price`) VALUES
('a', 1),
('b', 1),
('c', 1),
('d', 1),
('e', 1),
('f', 1),
('g', 1),
('hindu', 1),
('tribune', 22);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billing`
--
ALTER TABLE `billing`
  ADD PRIMARY KEY (`billid`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`mobile`);

--
-- Indexes for table `hawkers`
--
ALTER TABLE `hawkers`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `papers`
--
ALTER TABLE `papers`
  ADD PRIMARY KEY (`paper`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `billing`
--
ALTER TABLE `billing`
  MODIFY `billid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
