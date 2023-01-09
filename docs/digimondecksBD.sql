-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 21-10-2022 a las 17:27:48
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `DigimonDecks`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `card`
--

CREATE TABLE `card` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NULL,
  `color` varchar(255) COLLATE utf8_unicode_ci NULL,
  `stage` varchar(255) COLLATE utf8_unicode_ci NULL,
  `digitype` varchar(255) COLLATE utf8_unicode_ci NULL,
  `attribute` varchar(255) COLLATE utf8_unicode_ci NULL,
  `level` varchar(1) COLLATE utf8_unicode_ci NULL,
  `playCost` varchar(2) COLLATE utf8_unicode_ci NULL,
  `evolutioncost` varchar(1) COLLATE utf8_unicode_ci NULL,
  `dp` varchar (5) COLLATE utf8_unicode_ci NULL,
  `cardnumber` varchar(255) COLLATE utf8_unicode_ci NULL,
  `maineffect` varchar(1000) COLLATE utf8_unicode_ci NULL,
  `sourceeffect` varchar(1000) COLLATE utf8_unicode_ci NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Indices de la tabla `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`id`);
  
--
-- AUTO_INCREMENT de la tabla `card`
--
ALTER TABLE `card`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deck`
--

CREATE TABLE `deck` (
  `id` bigint(20) NOT NULL,
  `idPlayer` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NULL,
  `lastUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `deck`
--

INSERT INTO `deck` (`id`, `idPlayer`, `description`, `lastUpdate`) VALUES
(1, 1, 'Agumon deck', '2022-09-25 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cardDeck`
--

CREATE TABLE `cardDeck` (
  `id` bigint(20) NOT NULL,
  `idCard` bigint(20) NOT NULL,
  `idDeck` bigint(20) NOT NULL,
  `copies` int(1) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `cardDeck`
--

INSERT INTO `cardDeck` (`id`, `idCard`, `idDeck`, `copies`) VALUES
(1, 1, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Player`
--

CREATE TABLE `player` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `idUserType` bigint(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `player`
--

INSERT INTO `player` (`id`, `name`, `email`, `password`, `idUserType`) VALUES
(1, 'alvaro', 'altaro2002@gmail.com', 'admin', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `UserType`
--

CREATE TABLE `usertype` (
  `id` bigint(20) NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usertype`
--

INSERT INTO `usertype` (`id`, `type`) VALUES
(1, 'admin'),
(2, 'user');

-- --------------------------------------------------------

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `deck`
--
ALTER TABLE `deck`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `carddeck`
--
ALTER TABLE `carddeck`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `deck`
--
ALTER TABLE `deck`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de la tabla `carddeck`
--
ALTER TABLE `carddeck`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de la tabla `player`
--
ALTER TABLE `player`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

COMMIT;
