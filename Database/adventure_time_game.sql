-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 21, 2026 at 09:54 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `adventure_time_game`
--

-- --------------------------------------------------------

--
-- Table structure for table `game_sessions`
--

CREATE TABLE `game_sessions` (
  `id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `session_name` varchar(100) DEFAULT 'My Adventure',
  `skeleton_killed_count` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `item_definitions`
--

CREATE TABLE `item_definitions` (
  `id` int(11) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `TYPE` enum('SWORD','AXE','SHIELD','CONSUMABLE','PICKUP_ONLY','DOOR') NOT NULL,
  `attack_value` int(11) DEFAULT 0,
  `defense_value` int(11) DEFAULT 0,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item_definitions`
--

INSERT INTO `item_definitions` (`id`, `NAME`, `TYPE`, `attack_value`, `defense_value`, `description`) VALUES
(1, 'Finn Sword', 'SWORD', 1, 0, '[Finn Sword]\nA Sentient Sword \ncreated by a paradox'),
(2, 'Iron Axe', 'AXE', 2, 0, '[Axe]\nA bit rusty but still \ncan cut some tree'),
(3, 'Shield', 'SHIELD', 0, 1, '[Shield]\nA strong wooden shield'),
(5, 'Key', 'CONSUMABLE', 0, 0, '[Key]\nKey to Open the Door'),
(6, 'Red Potion', 'CONSUMABLE', 0, 0, '[Red Potion]\nHeals your life by'),
(7, 'Arrow Ammo', 'PICKUP_ONLY', 0, 0, 'Restores 2 arrows'),
(8, 'Heart', 'PICKUP_ONLY', 0, 0, 'Restores 2 health points'),
(9, 'Door', 'DOOR', 0, 0, 'A locked door'),
(10, 'Gold Coin', 'PICKUP_ONLY', 0, 0, 'A shiny gold coin'),
(11, 'Legendary Sword', 'SWORD', 0, 0, 'A Legendary Sword \nFrom BMO');

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` int(11) NOT NULL,
  `player_name` varchar(50) NOT NULL DEFAULT 'Finn',
  `LEVEL` int(11) DEFAULT 1,
  `max_health` int(11) DEFAULT 6,
  `current_health` int(11) DEFAULT 6,
  `max_arrow` int(11) DEFAULT 4,
  `current_arrow` int(11) DEFAULT 4,
  `magic` int(11) DEFAULT 10,
  `strength` int(11) DEFAULT 1,
  `agility` int(11) DEFAULT 1,
  `attack_power` int(11) DEFAULT 1,
  `defense_power` int(11) DEFAULT 1,
  `experience` int(11) DEFAULT 0,
  `next_level_exp` int(11) DEFAULT 4,
  `coin` int(11) DEFAULT 0,
  `world_x` int(11) DEFAULT 0,
  `world_y` int(11) DEFAULT 0,
  `current_map` int(11) DEFAULT 0,
  `equipped_weapon_id` int(11) DEFAULT NULL,
  `equipped_shield_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `player_name`, `LEVEL`, `max_health`, `current_health`, `max_arrow`, `current_arrow`, `magic`, `strength`, `agility`, `attack_power`, `defense_power`, `experience`, `next_level_exp`, `coin`, `world_x`, `world_y`, `current_map`, `equipped_weapon_id`, `equipped_shield_id`) VALUES
(1, 'Finn', 2, 8, 5, 4, 4, 10, 2, 2, 1, 1, 6, 8, 0, 1956, 2044, 1, 11, 3);

-- --------------------------------------------------------

--
-- Table structure for table `player_inventory`
--

CREATE TABLE `player_inventory` (
  `id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `world_items`
--

CREATE TABLE `world_items` (
  `id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `world_x` int(11) NOT NULL,
  `world_y` int(11) NOT NULL,
  `map_id` int(11) NOT NULL DEFAULT 0,
  `is_collected_by_player_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `world_items`
--

INSERT INTO `world_items` (`id`, `item_id`, `world_x`, `world_y`, `map_id`, `is_collected_by_player_id`) VALUES
(1, 7, 1872, 3168, 0, NULL),
(2, 8, 2688, 3552, 0, NULL),
(3, 9, 1728, 2592, 0, NULL),
(4, 9, 3504, 1440, 0, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game_sessions`
--
ALTER TABLE `game_sessions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `player_id` (`player_id`);

--
-- Indexes for table `item_definitions`
--
ALTER TABLE `item_definitions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`),
  ADD KEY `equipped_weapon_id` (`equipped_weapon_id`),
  ADD KEY `equipped_shield_id` (`equipped_shield_id`);

--
-- Indexes for table `player_inventory`
--
ALTER TABLE `player_inventory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `player_id` (`player_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Indexes for table `world_items`
--
ALTER TABLE `world_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `item_id` (`item_id`),
  ADD KEY `is_collected_by_player_id` (`is_collected_by_player_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `game_sessions`
--
ALTER TABLE `game_sessions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `item_definitions`
--
ALTER TABLE `item_definitions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `player_inventory`
--
ALTER TABLE `player_inventory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `world_items`
--
ALTER TABLE `world_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `game_sessions`
--
ALTER TABLE `game_sessions`
  ADD CONSTRAINT `game_sessions_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`);

--
-- Constraints for table `players`
--
ALTER TABLE `players`
  ADD CONSTRAINT `players_ibfk_1` FOREIGN KEY (`equipped_weapon_id`) REFERENCES `item_definitions` (`id`),
  ADD CONSTRAINT `players_ibfk_2` FOREIGN KEY (`equipped_shield_id`) REFERENCES `item_definitions` (`id`);

--
-- Constraints for table `player_inventory`
--
ALTER TABLE `player_inventory`
  ADD CONSTRAINT `player_inventory_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`),
  ADD CONSTRAINT `player_inventory_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item_definitions` (`id`);

--
-- Constraints for table `world_items`
--
ALTER TABLE `world_items`
  ADD CONSTRAINT `world_items_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item_definitions` (`id`),
  ADD CONSTRAINT `world_items_ibfk_2` FOREIGN KEY (`is_collected_by_player_id`) REFERENCES `players` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
