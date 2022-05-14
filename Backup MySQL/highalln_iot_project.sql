-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 07, 2022 at 09:12 PM
-- Server version: 10.3.34-MariaDB-log-cll-lve
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `highalln_iot_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_login`
--

CREATE TABLE `admin_login` (
  `id` int(11) NOT NULL,
  `username` text COLLATE utf8_unicode_ci NOT NULL,
  `password` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin_login`
--

INSERT INTO `admin_login` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `control1`
--

CREATE TABLE `control1` (
  `id` int(11) NOT NULL,
  `nhiet_do` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `control1`
--

INSERT INTO `control1` (`id`, `nhiet_do`) VALUES
(1, 'on'),
(2, 'off'),
(3, 'on'),
(4, '526'),
(5, 'on'),
(6, 'off'),
(7, 'ON'),
(8, 'OFF'),
(9, 'ON'),
(10, '526'),
(11, '10'),
(12, 'OFF'),
(13, 'ON'),
(14, ''),
(15, ''),
(16, '52'),
(17, ''),
(18, ''),
(19, 'OFF'),
(20, 'ON'),
(21, '35 96'),
(22, '65'),
(23, '5'),
(24, 'OFF'),
(25, 'ON'),
(26, 'OFF'),
(27, '6'),
(28, 'OFF'),
(29, 'ON'),
(30, 'OFF'),
(31, 'ON'),
(32, '50'),
(33, '5'),
(34, 'OFF'),
(35, 'ON'),
(36, 'ON'),
(37, 'ON'),
(38, 'ON'),
(39, 'OFF'),
(40, 'ON'),
(41, 'OFF'),
(42, 'ON'),
(43, 'OFF'),
(44, 'ON'),
(45, 'OFF'),
(46, 'ON'),
(47, 'OFF'),
(48, 'ON'),
(49, 'OFF'),
(50, 'ON'),
(51, '60'),
(52, 'OFF'),
(53, 'ON'),
(54, 'OFF'),
(55, 'ON'),
(56, 'OFF'),
(57, 'ON'),
(58, 'OFF'),
(59, 'ON'),
(60, '60'),
(61, 'OFF'),
(62, 'ON'),
(63, 'OFF'),
(64, 'ON'),
(65, 'OFF'),
(66, 'ON'),
(67, 'OFF'),
(68, 'ON'),
(69, 'OFF'),
(70, 'ON'),
(71, 'OFF'),
(72, 'ON'),
(73, '31'),
(74, '30'),
(75, '29'),
(76, '50'),
(77, '31'),
(78, '30'),
(79, '15'),
(80, '29'),
(81, '31'),
(82, '29'),
(83, 'OFF'),
(84, 'ON'),
(85, '5'),
(86, 'OFF'),
(87, 'ON'),
(88, 'OFF'),
(89, 'OFF'),
(90, 'ON'),
(91, 'OFF'),
(92, 'ON'),
(93, 'OFF'),
(94, 'ON'),
(95, 'OFF'),
(96, 'ON'),
(97, 'OFF'),
(98, 'ON'),
(99, 'OFF'),
(100, 'ON'),
(101, 'OFF'),
(102, 'ON'),
(103, 'OFF'),
(104, 'ON'),
(105, 'OFF'),
(106, 'ON'),
(107, 'OFF'),
(108, 'ON'),
(109, 'OFF'),
(110, '95'),
(111, '5'),
(112, 'OFF'),
(113, 'ON'),
(114, 'OFF'),
(115, 'ON'),
(116, 'OFF'),
(117, 'ON'),
(118, 'OFF'),
(119, 'ON'),
(120, '32'),
(121, '28'),
(122, '29'),
(123, '30'),
(124, '31'),
(125, '32'),
(126, '32'),
(127, '31'),
(128, '31'),
(129, '32'),
(130, '31'),
(131, '31'),
(132, '30'),
(133, '29.5'),
(134, '29.5'),
(135, '30.5'),
(136, '28'),
(137, 'OFF'),
(138, 'ON'),
(139, '60'),
(140, '29'),
(141, '28'),
(142, '29'),
(143, '30'),
(144, '30.5'),
(145, '31'),
(146, '32'),
(147, 'OFF'),
(148, 'ON'),
(149, '60');

-- --------------------------------------------------------

--
-- Table structure for table `control2`
--

CREATE TABLE `control2` (
  `id` int(11) NOT NULL,
  `nhiet_do` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `control2`
--

INSERT INTO `control2` (`id`, `nhiet_do`) VALUES
(1, 'OFF'),
(2, 'ON'),
(3, '58'),
(4, '586 6'),
(5, '  8'),
(6, 'OFF'),
(7, 'ON'),
(8, 'OFF'),
(9, 'ON'),
(10, 'OFF'),
(11, 'ON'),
(12, 'OFF'),
(13, 'ON');

-- --------------------------------------------------------

--
-- Table structure for table `data1`
--

CREATE TABLE `data1` (
  `id` int(11) NOT NULL,
  `nhiet_do` text COLLATE utf8_unicode_ci NOT NULL,
  `do_am` text COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `data1`
--

INSERT INTO `data1` (`id`, `nhiet_do`, `do_am`, `created_at`) VALUES
(1, '34', '60', '2022-03-22 09:35:09'),
(2, '25', '63', '2022-03-22 09:36:07'),
(3, '40', '67', '2022-03-22 09:36:07'),
(4, '34', '65', '2022-03-29 16:37:53'),
(5, '40', '55', '2022-03-29 16:39:49'),
(6, '36', '65', '2022-03-29 16:39:49'),
(7, '25', '75', '2022-03-29 16:39:49'),
(8, '40', '65', '2022-03-29 16:39:49'),
(9, '10', '63', '2022-03-29 16:39:49'),
(10, '15', '30', '2022-03-29 16:39:49'),
(66, '36.60', '57.00', '2022-04-21 09:15:20'),
(65, '36.90', '57.00', '2022-04-21 09:15:00'),
(64, '36.90', '57.00', '2022-04-21 09:14:40'),
(63, '36.90', '57.00', '2022-04-21 09:14:19'),
(62, '36.90', '57.00', '2022-04-21 09:13:59'),
(61, '37.40', '58.00', '2022-04-21 09:13:39'),
(54, '36.90', '60.00', '2022-04-21 09:11:19'),
(53, '36.90', '60.00', '2022-04-21 09:10:58'),
(52, '36.90', '61.00', '2022-04-21 09:10:40'),
(50, '36.90', '61.00', '2022-04-21 09:09:58'),
(55, '36.90', '60.00', '2022-04-21 09:11:39'),
(56, '36.90', '60.00', '2022-04-21 09:11:59'),
(57, '36.90', '60.00', '2022-04-21 09:12:19'),
(58, '36.90', '62.00', '2022-04-21 09:12:39'),
(59, '37.40', '61.00', '2022-04-21 09:12:59'),
(60, '37.40', '60.00', '2022-04-21 09:13:19'),
(67, '36.30', '54.00', '2022-04-21 09:33:04'),
(68, '36.30', '54.00', '2022-04-21 09:33:24'),
(69, '36.30', '54.00', '2022-04-21 09:33:46'),
(70, '36.30', '54.00', '2022-04-21 09:34:05'),
(71, '36.30', '54.00', '2022-04-21 09:34:25'),
(72, '36.30', '54.00', '2022-04-21 09:34:45'),
(73, '35.60', '54.00', '2022-04-21 09:35:05'),
(75, '36.90', '49.00', '2022-04-23 03:11:09'),
(76, '38.50', '49.00', '2022-04-23 03:11:33'),
(77, '38.50', '47.00', '2022-04-23 03:11:54'),
(78, '39.50', '46.00', '2022-04-23 04:58:47'),
(79, '39.50', '46.00', '2022-04-23 04:59:07'),
(80, '39.50', '46.00', '2022-04-23 04:59:29'),
(81, '39.50', '45.00', '2022-04-23 04:59:48'),
(82, '39.50', '45.00', '2022-04-23 05:00:08'),
(83, '40.10', '44.00', '2022-04-23 05:00:29'),
(84, '40.10', '43.00', '2022-04-23 05:00:48'),
(85, '40.10', '43.00', '2022-04-23 05:01:09'),
(86, '39.50', '44.00', '2022-04-23 05:02:49'),
(87, '39.50', '44.00', '2022-04-23 05:03:11'),
(88, '39.50', '44.00', '2022-04-23 05:03:33'),
(89, '39.50', '44.00', '2022-04-23 05:03:54'),
(90, '39.50', '44.00', '2022-04-23 05:04:14'),
(91, '39.00', '44.00', '2022-04-23 05:04:34'),
(92, '39.00', '44.00', '2022-04-23 05:04:54'),
(93, '39.00', '44.00', '2022-04-23 05:05:16'),
(94, '39.00', '45.00', '2022-04-23 05:05:35'),
(95, '39.00', '45.00', '2022-04-23 05:05:55'),
(96, '39.00', '45.00', '2022-04-23 05:06:15'),
(97, '39.00', '46.00', '2022-04-23 05:06:37'),
(98, '39.00', '46.00', '2022-04-23 05:06:55'),
(99, '39.00', '46.00', '2022-04-23 05:07:16'),
(100, '39.00', '45.00', '2022-04-23 05:07:36'),
(101, '39.00', '45.00', '2022-04-23 05:07:56'),
(102, '39.00', '45.00', '2022-04-23 05:08:17'),
(103, '39.00', '45.00', '2022-04-23 05:08:38'),
(104, '39.00', '45.00', '2022-04-23 05:08:57'),
(105, '39.00', '45.00', '2022-04-23 05:09:19'),
(106, '39.00', '46.00', '2022-04-23 05:09:39'),
(107, '39.00', '46.00', '2022-04-23 05:09:59'),
(108, '39.00', '46.00', '2022-04-23 05:10:19'),
(109, '39.00', '46.00', '2022-04-23 05:10:43'),
(110, '39.00', '46.00', '2022-04-23 05:11:02'),
(111, '39.00', '46.00', '2022-04-23 05:11:20'),
(113, '38.00', '50.00', '2022-04-23 05:27:11'),
(114, '38.00', '50.00', '2022-04-23 05:27:34'),
(115, '36.40', '56.00', '2022-04-24 06:04:25'),
(116, '36.30', '56.00', '2022-04-24 06:04:42'),
(117, '36.30', '56.00', '2022-04-24 06:05:04'),
(118, '36.30', '56.00', '2022-04-24 06:05:25');

-- --------------------------------------------------------

--
-- Table structure for table `data2`
--

CREATE TABLE `data2` (
  `id` int(11) NOT NULL,
  `nhiet_do` text COLLATE utf8_unicode_ci NOT NULL,
  `do_am` text COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `data2`
--

INSERT INTO `data2` (`id`, `nhiet_do`, `do_am`, `created_at`) VALUES
(1, '10', '90', '2022-03-29 16:47:54'),
(2, '10', '90', '2022-03-29 16:49:01'),
(3, '11', '84', '2022-03-29 16:49:01'),
(4, '10', '75', '2022-03-29 16:49:01'),
(5, '8', '90', '2022-03-29 16:49:01'),
(6, '10', '90', '2022-03-29 16:49:01'),
(7, '5', '83', '2022-03-29 16:49:01'),
(8, '6', '85', '2022-03-29 16:49:01'),
(9, '10', '90', '2022-03-29 16:49:01'),
(10, '0', '70', '2022-03-29 16:49:01'),
(11, '10', '80', '2022-03-29 16:49:01');

-- --------------------------------------------------------

--
-- Table structure for table `info1`
--

CREATE TABLE `info1` (
  `id` int(11) NOT NULL,
  `ten_tai_xe` text COLLATE utf8_unicode_ci NOT NULL,
  `dien_thoai` text COLLATE utf8_unicode_ci NOT NULL,
  `xuat_phat` text COLLATE utf8_unicode_ci NOT NULL,
  `dich_den` text COLLATE utf8_unicode_ci NOT NULL,
  `thoi_gian` text COLLATE utf8_unicode_ci NOT NULL,
  `tin_nhan` text COLLATE utf8_unicode_ci NOT NULL,
  `kinh_do_xuat_phat` text COLLATE utf8_unicode_ci NOT NULL,
  `vi_do_xuat_phat` text COLLATE utf8_unicode_ci NOT NULL,
  `kinh_do_dich_den` text COLLATE utf8_unicode_ci NOT NULL,
  `vi_do_dich_den` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `info1`
--

INSERT INTO `info1` (`id`, `ten_tai_xe`, `dien_thoai`, `xuat_phat`, `dich_den`, `thoi_gian`, `tin_nhan`, `kinh_do_xuat_phat`, `vi_do_xuat_phat`, `kinh_do_dich_den`, `vi_do_dich_den`) VALUES
(1, 'Le Thanh Duong', '0912214144', 'TP. Ho Chi Minh', 'Binh Thuan', '7:00 AM', '', '10.771505', '106.645766', '11.190689', '108.580877'),
(2, '', '', 'ca mau', 'lang son', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `info2`
--

CREATE TABLE `info2` (
  `id` int(11) NOT NULL,
  `ten_tai_xe` text COLLATE utf8_unicode_ci NOT NULL,
  `dien_thoai` text COLLATE utf8_unicode_ci NOT NULL,
  `xuat_phat` text COLLATE utf8_unicode_ci NOT NULL,
  `dich_den` text COLLATE utf8_unicode_ci NOT NULL,
  `thoi_gian` text COLLATE utf8_unicode_ci NOT NULL,
  `tin_nhan` text CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `kinh_do_xuat_phat` text COLLATE utf8_unicode_ci NOT NULL,
  `vi_do_xuat_phat` text COLLATE utf8_unicode_ci NOT NULL,
  `kinh_do_dich_den` text COLLATE utf8_unicode_ci NOT NULL,
  `vi_do_dich_den` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `info2`
--

INSERT INTO `info2` (`id`, `ten_tai_xe`, `dien_thoai`, `xuat_phat`, `dich_den`, `thoi_gian`, `tin_nhan`, `kinh_do_xuat_phat`, `vi_do_xuat_phat`, `kinh_do_dich_den`, `vi_do_dich_den`) VALUES
(1, 'Pham Viet Phong', '09412353242', 'Ha Noi', 'Buon Ma Thuoc', '2:00 PM', '', '21.031709', '105.834216', '12.651250', '108.022198');

-- --------------------------------------------------------

--
-- Table structure for table `iot_project1`
--

CREATE TABLE `iot_project1` (
  `id` int(11) NOT NULL,
  `nhiet_do` text COLLATE utf8_unicode_ci NOT NULL,
  `do_am` text COLLATE utf8_unicode_ci NOT NULL,
  `kinh_do` text COLLATE utf8_unicode_ci NOT NULL,
  `vi_do` text COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `iot_project1`
--

INSERT INTO `iot_project1` (`id`, `nhiet_do`, `do_am`, `kinh_do`, `vi_do`, `created_at`) VALUES
(1, '34', '60', '10.772082', '106.650987', '2022-03-22 09:35:09'),
(2, '25', '63', '10.872082', '106.624527', '2022-03-22 09:36:07'),
(3, '40', '67', '10.765082', '106.651347', '2022-03-22 09:36:07'),
(4, '34', '65', '10.773113', '106.652245', '2022-03-29 16:37:53'),
(5, '40', '55', '10.873113', '106.552245', '2022-03-29 16:39:49'),
(6, '36', '65', '10.773113', '106.652245', '2022-03-29 16:39:49'),
(7, '25', '75', '10.773113', '106.652245', '2022-03-29 16:39:49'),
(8, '40', '65', '10.773113', '106.652245', '2022-03-29 16:39:49'),
(9, '10', '63', '10.773113', '106.652245', '2022-03-29 16:39:49'),
(10, '15', '30', '10.773113', '106.652245', '2022-03-29 16:39:49'),
(66, '36.60', '57.00', '', '', '2022-04-21 09:15:20'),
(65, '36.90', '57.00', '', '', '2022-04-21 09:15:00'),
(64, '36.90', '57.00', '', '', '2022-04-21 09:14:40'),
(63, '36.90', '57.00', '', '', '2022-04-21 09:14:19'),
(62, '36.90', '57.00', '', '', '2022-04-21 09:13:59'),
(61, '37.40', '58.00', '', '', '2022-04-21 09:13:39'),
(54, '36.90', '60.00', '', '', '2022-04-21 09:11:19'),
(53, '36.90', '60.00', '', '', '2022-04-21 09:10:58'),
(52, '36.90', '61.00', '', '', '2022-04-21 09:10:40'),
(50, '36.90', '61.00', '', '', '2022-04-21 09:09:58'),
(55, '36.90', '60.00', '', '', '2022-04-21 09:11:39'),
(56, '36.90', '60.00', '', '', '2022-04-21 09:11:59'),
(57, '36.90', '60.00', '', '', '2022-04-21 09:12:19'),
(58, '36.90', '62.00', '', '', '2022-04-21 09:12:39'),
(59, '37.40', '61.00', '', '', '2022-04-21 09:12:59'),
(60, '37.40', '60.00', '', '', '2022-04-21 09:13:19'),
(67, '36.30', '54.00', '', '', '2022-04-21 09:33:04'),
(68, '36.30', '54.00', '', '', '2022-04-21 09:33:24'),
(69, '36.30', '54.00', '', '', '2022-04-21 09:33:46'),
(70, '36.30', '54.00', '', '', '2022-04-21 09:34:05'),
(71, '36.30', '54.00', '', '', '2022-04-21 09:34:25'),
(72, '36.30', '54.00', '', '', '2022-04-21 09:34:45'),
(73, '35.60', '54.00', '', '', '2022-04-21 09:35:05'),
(75, '36.90', '49.00', '', '', '2022-04-23 03:11:09'),
(76, '38.50', '49.00', '', '', '2022-04-23 03:11:33'),
(77, '38.50', '47.00', '', '', '2022-04-23 03:11:54'),
(78, '39.50', '46.00', '', '', '2022-04-23 04:58:47'),
(79, '39.50', '46.00', '', '', '2022-04-23 04:59:07'),
(80, '39.50', '46.00', '', '', '2022-04-23 04:59:29'),
(81, '39.50', '45.00', '', '', '2022-04-23 04:59:48'),
(82, '39.50', '45.00', '', '', '2022-04-23 05:00:08'),
(83, '40.10', '44.00', '', '', '2022-04-23 05:00:29'),
(84, '40.10', '43.00', '', '', '2022-04-23 05:00:48'),
(85, '40.10', '43.00', '', '', '2022-04-23 05:01:09'),
(86, '39.50', '44.00', '', '', '2022-04-23 05:02:49'),
(87, '39.50', '44.00', '', '', '2022-04-23 05:03:11'),
(88, '39.50', '44.00', '', '', '2022-04-23 05:03:33'),
(89, '39.50', '44.00', '', '', '2022-04-23 05:03:54'),
(90, '39.50', '44.00', '', '', '2022-04-23 05:04:14'),
(91, '39.00', '44.00', '', '', '2022-04-23 05:04:34'),
(92, '39.00', '44.00', '', '', '2022-04-23 05:04:54'),
(93, '39.00', '44.00', '', '', '2022-04-23 05:05:16'),
(94, '39.00', '45.00', '', '', '2022-04-23 05:05:35'),
(95, '39.00', '45.00', '', '', '2022-04-23 05:05:55'),
(96, '39.00', '45.00', '', '', '2022-04-23 05:06:15'),
(97, '39.00', '46.00', '', '', '2022-04-23 05:06:37'),
(98, '39.00', '46.00', '', '', '2022-04-23 05:06:55'),
(99, '39.00', '46.00', '', '', '2022-04-23 05:07:16'),
(100, '39.00', '45.00', '', '', '2022-04-23 05:07:36'),
(101, '39.00', '45.00', '', '', '2022-04-23 05:07:56'),
(102, '39.00', '45.00', '', '', '2022-04-23 05:08:17'),
(103, '39.00', '45.00', '', '', '2022-04-23 05:08:38'),
(104, '39.00', '45.00', '', '', '2022-04-23 05:08:57'),
(105, '39.00', '45.00', '', '', '2022-04-23 05:09:19'),
(106, '39.00', '46.00', '', '', '2022-04-23 05:09:39'),
(107, '39.00', '46.00', '', '', '2022-04-23 05:09:59'),
(108, '39.00', '46.00', '', '', '2022-04-23 05:10:19'),
(109, '39.00', '46.00', '', '', '2022-04-23 05:10:43'),
(110, '39.00', '46.00', '', '', '2022-04-23 05:11:02'),
(111, '39.00', '46.00', '', '', '2022-04-23 05:11:20'),
(113, '38.00', '50.00', '10.773776', '106.660751', '2022-04-23 05:27:11'),
(114, '38.00', '50.00', '10.773713', '106.660728', '2022-04-23 05:27:34'),
(115, '36.40', '56.00', '', '', '2022-04-24 06:04:25'),
(116, '36.30', '56.00', '', '', '2022-04-24 06:04:42'),
(117, '36.30', '56.00', '', '', '2022-04-24 06:05:04'),
(118, '36.30', '56.00', '', '', '2022-04-24 06:05:25');

-- --------------------------------------------------------

--
-- Table structure for table `iot_project2`
--

CREATE TABLE `iot_project2` (
  `id` int(11) NOT NULL,
  `nhiet_do` text COLLATE utf8_unicode_ci NOT NULL,
  `do_am` text COLLATE utf8_unicode_ci NOT NULL,
  `kinh_do` text COLLATE utf8_unicode_ci NOT NULL,
  `vi_do` text COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `iot_project2`
--

INSERT INTO `iot_project2` (`id`, `nhiet_do`, `do_am`, `kinh_do`, `vi_do`, `created_at`) VALUES
(1, '10', '90', '20.747656', '105.908840', '2022-03-29 16:47:54'),
(2, '10', '90', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(3, '11', '84', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(4, '10', '75', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(5, '8', '90', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(6, '10', '90', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(7, '5', '83', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(8, '6', '85', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(9, '10', '90', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(10, '0', '70', '20.747656', '105.908840', '2022-03-29 16:49:01'),
(11, '10', '80', '20.747656', '105.908840', '2022-03-29 16:49:01');

-- --------------------------------------------------------

--
-- Table structure for table `iot_project3`
--

CREATE TABLE `iot_project3` (
  `id` int(11) NOT NULL,
  `nhiet_do` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `do_am` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `kinh_do` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `vi_do` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `iot_project3`
--

INSERT INTO `iot_project3` (`id`, `nhiet_do`, `do_am`, `kinh_do`, `vi_do`, `created_at`) VALUES
(146, '36.90', '60.00', '', '', '2022-04-21 08:18:47'),
(144, '36.90', '60.00', '', '', '2022-04-21 08:18:27'),
(145, '36.90', '60.00', '', '', '2022-04-21 08:18:47'),
(142, '36.90', '60.00', '', '', '2022-04-21 08:18:07'),
(143, '36.90', '60.00', '', '', '2022-04-21 08:18:27'),
(141, '36.90', '60.00', '', '', '2022-04-21 08:18:07'),
(139, '36.90', '60.00', '', '', '2022-04-21 08:17:47'),
(140, '36.90', '60.00', '', '', '2022-04-21 08:17:47'),
(138, '36.90', '60.00', '', '', '2022-04-21 08:17:27'),
(137, '36.90', '60.00', '', '', '2022-04-21 08:17:27'),
(136, '36.90', '60.00', '', '', '2022-04-21 08:17:07'),
(135, '36.90', '60.00', '', '', '2022-04-21 08:17:07'),
(134, '36.90', '60.00', '', '', '2022-04-21 08:16:17'),
(133, '36.90', '60.00', '', '', '2022-04-21 08:16:17'),
(132, '36.90', '60.00', '', '', '2022-04-21 08:15:56'),
(131, '36.90', '60.00', '', '', '2022-04-21 08:15:56'),
(130, '36.90', '60.00', '', '', '2022-04-21 08:15:36'),
(129, '36.90', '60.00', '', '', '2022-04-21 08:15:36'),
(128, '36.90', '60.00', '', '', '2022-04-21 08:15:16'),
(127, '36.90', '60.00', '', '', '2022-04-21 08:15:16'),
(126, '36.90', '60.00', '', '', '2022-04-21 08:14:56'),
(125, '36.90', '60.00', '', '', '2022-04-21 08:14:56'),
(124, '36.90', '60.00', '', '', '2022-04-21 08:14:35'),
(123, '36.90', '60.00', '', '', '2022-04-21 08:14:35'),
(122, '36.90', '60.00', '', '', '2022-04-21 08:14:15'),
(121, '36.90', '60.00', '', '', '2022-04-21 08:14:15'),
(120, '36.90', '60.00', '', '', '2022-04-21 08:13:55'),
(119, '36.90', '60.00', '', '', '2022-04-21 08:13:55'),
(118, '36.90', '60.00', '', '', '2022-04-21 08:13:36'),
(117, '36.90', '60.00', '', '', '2022-04-21 08:13:36'),
(116, '36.90', '60.00', '', '', '2022-04-21 08:13:15'),
(115, '36.90', '60.00', '', '', '2022-04-21 08:13:15'),
(114, '36.90', '60.00', '', '', '2022-04-21 08:12:55'),
(113, '36.90', '60.00', '', '', '2022-04-21 08:12:55');

-- --------------------------------------------------------

--
-- Table structure for table `location1`
--

CREATE TABLE `location1` (
  `id` int(11) NOT NULL,
  `kinh_do` text COLLATE utf8_unicode_ci NOT NULL,
  `vi_do` text COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `location1`
--

INSERT INTO `location1` (`id`, `kinh_do`, `vi_do`, `created_at`) VALUES
(1, '10.772082', '106.650987', '2022-03-22 09:35:09'),
(2, '10.872082', '106.624527', '2022-03-22 09:36:07'),
(3, '10.765082', '106.651347', '2022-03-22 09:36:07'),
(4, '10.773113', '106.652245', '2022-03-29 16:37:53'),
(5, '10.873113', '106.552245', '2022-03-29 16:39:49'),
(6, '10.773113', '106.652245', '2022-03-29 16:39:49'),
(7, '10.773113', '106.652245', '2022-03-29 16:39:49'),
(8, '10.773113', '106.652245', '2022-03-29 16:39:49'),
(9, '10.773113', '106.652245', '2022-03-29 16:39:49'),
(10, '10.773113', '106.652245', '2022-03-29 16:39:49'),
(113, '10.773776', '106.660751', '2022-04-23 05:27:11'),
(114, '10.773713', '106.660728', '2022-04-23 05:27:34');

-- --------------------------------------------------------

--
-- Table structure for table `location2`
--

CREATE TABLE `location2` (
  `id` int(11) NOT NULL,
  `kinh_do` text COLLATE utf8_unicode_ci NOT NULL,
  `vi_do` text COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `location2`
--

INSERT INTO `location2` (`id`, `kinh_do`, `vi_do`, `created_at`) VALUES
(1, '20.747656', '105.908840', '2022-03-29 16:47:54'),
(2, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(3, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(4, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(5, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(6, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(7, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(8, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(9, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(10, '20.747656', '105.908840', '2022-03-29 16:49:01'),
(11, '20.747656', '105.908840', '2022-03-29 16:49:01');

-- --------------------------------------------------------

--
-- Table structure for table `login1`
--

CREATE TABLE `login1` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `password` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `login1`
--

INSERT INTO `login1` (`id`, `name`, `password`) VALUES
(1, 'admin1', 'admin1');

-- --------------------------------------------------------

--
-- Table structure for table `login2`
--

CREATE TABLE `login2` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `password` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `login2`
--

INSERT INTO `login2` (`id`, `name`, `password`) VALUES
(1, 'admin2', 'admin2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_login`
--
ALTER TABLE `admin_login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `control1`
--
ALTER TABLE `control1`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `control2`
--
ALTER TABLE `control2`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data1`
--
ALTER TABLE `data1`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data2`
--
ALTER TABLE `data2`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `info1`
--
ALTER TABLE `info1`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `info2`
--
ALTER TABLE `info2`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `iot_project1`
--
ALTER TABLE `iot_project1`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `iot_project2`
--
ALTER TABLE `iot_project2`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `iot_project3`
--
ALTER TABLE `iot_project3`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `location1`
--
ALTER TABLE `location1`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `location2`
--
ALTER TABLE `location2`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login1`
--
ALTER TABLE `login1`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login2`
--
ALTER TABLE `login2`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_login`
--
ALTER TABLE `admin_login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `control1`
--
ALTER TABLE `control1`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=150;

--
-- AUTO_INCREMENT for table `control2`
--
ALTER TABLE `control2`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `data1`
--
ALTER TABLE `data1`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;

--
-- AUTO_INCREMENT for table `data2`
--
ALTER TABLE `data2`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `info1`
--
ALTER TABLE `info1`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `info2`
--
ALTER TABLE `info2`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `iot_project1`
--
ALTER TABLE `iot_project1`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;

--
-- AUTO_INCREMENT for table `iot_project2`
--
ALTER TABLE `iot_project2`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `iot_project3`
--
ALTER TABLE `iot_project3`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=147;

--
-- AUTO_INCREMENT for table `location1`
--
ALTER TABLE `location1`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;

--
-- AUTO_INCREMENT for table `location2`
--
ALTER TABLE `location2`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `login1`
--
ALTER TABLE `login1`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `login2`
--
ALTER TABLE `login2`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
