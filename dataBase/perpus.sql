-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 08, 2026 at 11:34 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpus`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `ID_Anggota` char(10) NOT NULL,
  `Nama_Anggota` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Telepon` varchar(45) DEFAULT NULL,
  `Jenis_Kelamin` varchar(15) NOT NULL,
  `Tanggal_Bergabung` date DEFAULT NULL,
  `LevelAnggota` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `ID_Buku` char(10) NOT NULL,
  `Judul_Buku` varchar(45) DEFAULT NULL,
  `Pengarang_Buku` varchar(45) DEFAULT NULL,
  `Tahun_Terbit` varchar(45) DEFAULT NULL,
  `ID_Kategori` char(10) NOT NULL,
  `ID_Penerbit` char(10) NOT NULL,
  `Stok` int NOT NULL,
  `Gambar` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `detail_peminjaman`
--

CREATE TABLE `detail_peminjaman` (
  `ID_Peminjaman` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ID_Buku` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Jumlah_Pinjam` int NOT NULL,
  `Status_Peminjaman` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Triggers `detail_peminjaman`
--
DELIMITER $$
CREATE TRIGGER `peminjaman_buku` AFTER INSERT ON `detail_peminjaman` FOR EACH ROW BEGIN
	UPDATE buku SET Stok = stok-new.Jumlah_Pinjam
	WHERE ID_Buku=new.ID_Buku;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `detail_pengembalian`
--

CREATE TABLE `detail_pengembalian` (
  `ID_Pengembalian` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ID_Buku` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Jumlah_Kembali` int NOT NULL,
  `Jumlah_Denda` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Triggers `detail_pengembalian`
--
DELIMITER $$
CREATE TRIGGER `pengembalian_buku` AFTER INSERT ON `detail_pengembalian` FOR EACH ROW BEGIN
	UPDATE buku SET Stok = stok+new.Jumlah_Kembali
	WHERE ID_Buku=new.ID_Buku;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `ID_Kategori` char(10) NOT NULL,
  `Nama_Kategori` varchar(45) DEFAULT NULL,
  `Deskripsi_Kategori` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman`
--

CREATE TABLE `peminjaman` (
  `ID_Peminjaman` char(10) NOT NULL,
  `Tanggal_Peminjaman` date DEFAULT NULL,
  `Tanggal_Pengembalian` date DEFAULT NULL,
  `Total_pinjam` int NOT NULL,
  `ID_Anggota` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ID_User` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `penerbit`
--

CREATE TABLE `penerbit` (
  `ID_Penerbit` char(10) NOT NULL,
  `Nama_Penerbit` varchar(45) DEFAULT NULL,
  `Situs_Penerbit` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pengembalian`
--

CREATE TABLE `pengembalian` (
  `ID_Pengembalian` char(10) NOT NULL,
  `Tanggal_Dikembalikan` date NOT NULL,
  `ID_Peminjaman` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ID_User` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sementara`
--

CREATE TABLE `sementara` (
  `ID_Buku` char(10) NOT NULL,
  `Judul_Buku` varchar(100) NOT NULL,
  `Pengarang_Buku` varchar(45) NOT NULL,
  `Penerbit_Buku` varchar(45) NOT NULL,
  `Jumlah_Pinjam` int NOT NULL,
  `Status_Pinjam` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID_User` char(10) NOT NULL,
  `Nama_User` varchar(45) DEFAULT NULL,
  `Username` varchar(45) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Password` char(100) DEFAULT NULL,
  `Level` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID_User`, `Nama_User`, `Username`, `Email`, `Password`, `Level`) VALUES
('USR2601001', 'admin', 'Admin', 'admin@gmail.com', 'admin', 'Admin'),
('USR2601002', 'petugas', 'Petugas', 'petugas@gmail.com', 'petugas', 'Petugas');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`ID_Anggota`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`ID_Buku`),
  ADD KEY `fk_Buku_Kategori1_idx` (`ID_Kategori`),
  ADD KEY `fk_Buku_Penerbit1_idx` (`ID_Penerbit`);

--
-- Indexes for table `detail_peminjaman`
--
ALTER TABLE `detail_peminjaman`
  ADD KEY `fk_Detail_Peminjaman_Buku1_idx` (`ID_Buku`),
  ADD KEY `fk_Detail_Peminjaman_Peminjaman1_idx` (`ID_Peminjaman`);

--
-- Indexes for table `detail_pengembalian`
--
ALTER TABLE `detail_pengembalian`
  ADD KEY `fk_Detail_Pengembalian_Pengembalian1_idx` (`ID_Pengembalian`),
  ADD KEY `fk_Detail_Pengembalian_Buku1_idx` (`ID_Buku`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`ID_Kategori`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`ID_Peminjaman`),
  ADD KEY `fk_Peminjaman_User_idx` (`ID_User`),
  ADD KEY `fk_Peminjaman_Anggota1_idx` (`ID_Anggota`);

--
-- Indexes for table `penerbit`
--
ALTER TABLE `penerbit`
  ADD PRIMARY KEY (`ID_Penerbit`);

--
-- Indexes for table `pengembalian`
--
ALTER TABLE `pengembalian`
  ADD PRIMARY KEY (`ID_Pengembalian`),
  ADD KEY `fk_Pengembalian_Peminjaman1_idx` (`ID_Peminjaman`),
  ADD KEY `fk_Pengembalian_Anggota1_idx` (`ID_User`);

--
-- Indexes for table `sementara`
--
ALTER TABLE `sementara`
  ADD KEY `ID_Buku` (`ID_Buku`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID_User`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `buku`
--
ALTER TABLE `buku`
  ADD CONSTRAINT `fk_Buku_Kategori1` FOREIGN KEY (`ID_Kategori`) REFERENCES `kategori` (`ID_Kategori`),
  ADD CONSTRAINT `fk_Buku_Penerbit1` FOREIGN KEY (`ID_Penerbit`) REFERENCES `penerbit` (`ID_Penerbit`);

--
-- Constraints for table `detail_peminjaman`
--
ALTER TABLE `detail_peminjaman`
  ADD CONSTRAINT `fk_Detail_Peminjaman_Buku1` FOREIGN KEY (`ID_Buku`) REFERENCES `buku` (`ID_Buku`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Detail_Peminjaman_Peminjaman1` FOREIGN KEY (`ID_Peminjaman`) REFERENCES `peminjaman` (`ID_Peminjaman`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_pengembalian`
--
ALTER TABLE `detail_pengembalian`
  ADD CONSTRAINT `detail_pengembalian_ibfk_3` FOREIGN KEY (`ID_Pengembalian`) REFERENCES `pengembalian` (`ID_Pengembalian`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_pengembalian_ibfk_4` FOREIGN KEY (`ID_Buku`) REFERENCES `buku` (`ID_Buku`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `fk_Peminjaman_Anggota1` FOREIGN KEY (`ID_Anggota`) REFERENCES `anggota` (`ID_Anggota`),
  ADD CONSTRAINT `fk_Peminjaman_User` FOREIGN KEY (`ID_User`) REFERENCES `user` (`ID_User`);

--
-- Constraints for table `pengembalian`
--
ALTER TABLE `pengembalian`
  ADD CONSTRAINT `fk_Pengembalian_Peminjaman1` FOREIGN KEY (`ID_Peminjaman`) REFERENCES `peminjaman` (`ID_Peminjaman`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pengembalian_ibfk_1` FOREIGN KEY (`ID_User`) REFERENCES `user` (`ID_User`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sementara`
--
ALTER TABLE `sementara`
  ADD CONSTRAINT `sementara_ibfk_1` FOREIGN KEY (`ID_Buku`) REFERENCES `buku` (`ID_Buku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
