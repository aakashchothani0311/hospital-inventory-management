-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 19, 2024 at 10:41 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stock`
--
CREATE DATABASE IF NOT EXISTS `stock` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `stock`;

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `item_code` int(15) NOT NULL,
  `item_name` varchar(50) NOT NULL,
  `qty` int(11) NOT NULL,
  `price` double NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `consumable` tinyint(1) NOT NULL,
  `dept` varchar(40) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`item_code`, `item_name`, `qty`, `price`, `purchase_date`, `expiry_date`, `consumable`, `dept`) VALUES
(1, 'Bandages', 100, 2, '2022-01-01', '2023-01-01', 1, ''),
(2, 'Antiseptic Wipes', 0, 5, '2023-01-08', '2024-06-12', 1, ''),
(3, 'Hospital Beds', 20, 500, '2023-01-15', NULL, 0, 'General Ward'),
(4, 'Hospital Chairs', 50, 50, '2023-10-10', NULL, 0, 'General Ward'),
(5, 'Sterile Dressing ', 100, 3, '2024-03-03', '2024-04-30', 1, ''),
(6, 'X-ray Machine', 0, 50000, '2023-03-05', NULL, 0, 'Radiology'),
(7, 'Defibrillator', 5, 2000, '2023-06-15', NULL, 0, 'Emergency'),
(8, 'Surgical Gowns', 25, 30, '2023-08-05', '2024-05-08', 1, ''),
(9, 'Surgical Tools', 50, 100, '2022-10-11', NULL, 0, 'Surgery'),
(10, 'Oxygen Tanks', 30, 105, '2024-03-04', NULL, 0, 'Respiratory'),
(11, 'Gloves', 300, 5.25, '2024-05-04', '2024-04-05', 1, ''),
(12, 'Antibiotics', 0, 30, '2023-06-15', '2024-06-14', 1, ''),
(13, 'Ultrasound Machine', 1, 30000, '2023-09-09', NULL, 0, 'Radiology'),
(14, 'IV Fluids', 50, 5, '2023-10-07', '2023-11-06', 1, ''),
(15, 'Strechers', 10, 400, '2024-02-28', NULL, 0, 'Emergency'),
(16, 'Face Masks', 1000, 0.5, '2024-01-04', '2025-01-03', 1, ''),
(17, 'Syringes', 200, 1, '2023-03-10', '2024-03-09', 1, ''),
(18, 'Wheelchairs', 15, 300, '2023-11-15', NULL, 0, 'Rehabilitation'),
(19, 'Antipyretics', 0, 10, '2023-09-19', '2024-03-18', 1, ''),
(20, 'ECG Machine', 3, 10000, '2024-03-07', NULL, 0, 'Cardiology');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_code`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `item_code` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
