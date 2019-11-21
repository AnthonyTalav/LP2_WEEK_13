-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 21-11-2019 a las 14:06:08
-- Versión del servidor: 5.7.17-log
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `deportes`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo`
--

CREATE TABLE `articulo` (
  `idarticulo` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `descripcion` varchar(120) DEFAULT NULL,
  `precio` float(9,2) DEFAULT NULL,
  `foto` varchar(30) DEFAULT NULL,
  `idcategoria` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `articulo`
--

INSERT INTO `articulo` (`idarticulo`, `nombre`, `descripcion`, `precio`, `foto`, `idcategoria`) VALUES
(1, 'Sandalias', 'Sandalias Calo 5 Mujer', 60.00, NULL, 1),
(2, 'shorts', ' shorts para hombre Tejido de punto liso 100% nylon', 120.00, NULL, 1),
(3, 'Lentes', 'lentes de buceo para entrenamientos', 160.00, NULL, 1),
(4, 'guantes de pesca', 'Guantes de pesca spinning Hart excelentes acabados', 90.00, NULL, 2),
(5, 'polo', 'polo adidas color blanco', 120.00, NULL, 3),
(6, 'zaptillas', 'Zapatillas  adidas color blanco', 230.00, NULL, 3),
(7, 'zaptillas', 'zapatila tigre color ngero y blanco', 180.00, NULL, 3),
(8, 'buso', 'buso nike color negro', 220.00, NULL, 3),
(9, 'polo', 'polo nike cuello v color azul', 98.00, NULL, 3),
(10, 'zapatillas', 'zapatillas mujer voley talla  40', 98.00, NULL, 4),
(11, 'casaca', 'casaca WALON originales', 230.00, NULL, 4),
(12, 'shorts', 'short de voleibol interior partido mujer negro kipsta', 92.00, NULL, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idcategoria` int(11) NOT NULL,
  `categoria` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idcategoria`, `categoria`) VALUES
(1, 'NATACION'),
(2, 'PESCA'),
(3, 'FUTBOL'),
(4, 'VOLEY');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD PRIMARY KEY (`idarticulo`),
  ADD KEY `categoria_articulo` (`idcategoria`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idcategoria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulo`
--
ALTER TABLE `articulo`
  MODIFY `idarticulo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idcategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD CONSTRAINT `categoria_articulo` FOREIGN KEY (`idcategoria`) REFERENCES `categoria` (`idcategoria`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
