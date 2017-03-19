CREATE DATABASE  IF NOT EXISTS `springbootdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `springbootdb`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: springbootdb
-- ------------------------------------------------------
-- Server version	5.7.10-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `contraseña` varchar(255) DEFAULT NULL,
  `controle_de_acceso` varchar(255) NOT NULL,
  `cp` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `localidade` varchar(255) DEFAULT NULL,
  `nota` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `fecha_primera_alta` date DEFAULT NULL,
  `fecha_ultima_baja` date DEFAULT NULL,
  `nif` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `nombre_de_usuario` varchar(255) DEFAULT NULL,
  `primer_apellido` varchar(255) DEFAULT NULL,
  `segundo_apellido` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ep7teqj7hbumyua1rquyddfce` (`nif`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'','123456','USER','123456','C/ Java Swing','Alicante','','123','España','Alicante','1988-02-24','2017-02-23',NULL,'486232R','Africa','123456789@123.com','Chacopino','Chacopino'),(2,'','123456','USER','123456','C/ Java Swing','Alicante','','123','España','Alicante','1979-04-02','2017-03-04',NULL,'1478963','Paulo','123456789@123.com','Sandström','');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes_contactos`
--

DROP TABLE IF EXISTS `clientes_contactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes_contactos` (
  `cliente_id` bigint(20) NOT NULL,
  `contactos_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_j318bej5sv2ahcqfacdhn4xnn` (`contactos_id`),
  KEY `FK6wydd9hw9cp3bnhskyqr67bnc` (`cliente_id`),
  CONSTRAINT `FK6wydd9hw9cp3bnhskyqr67bnc` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  CONSTRAINT `FKi91txbugbkrny4swmk8jue5ok` FOREIGN KEY (`contactos_id`) REFERENCES `contactos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes_contactos`
--

LOCK TABLES `clientes_contactos` WRITE;
/*!40000 ALTER TABLE `clientes_contactos` DISABLE KEYS */;
INSERT INTO `clientes_contactos` VALUES (1,2),(2,3);
/*!40000 ALTER TABLE `clientes_contactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactos`
--

DROP TABLE IF EXISTS `contactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contacto` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactos`
--

LOCK TABLES `contactos` WRITE;
/*!40000 ALTER TABLE `contactos` DISABLE KEYS */;
INSERT INTO `contactos` VALUES (1,'634753562','TELEFONO'),(2,'123456789','TELEFONO'),(3,'123456789','TELEFONO'),(4,'123456789','TELEFONO'),(5,'123456789','TELEFONO'),(6,'123456789','TELEFONO');
/*!40000 ALTER TABLE `contactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuenta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cobrada` bit(1) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `factura_id` bigint(20) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK616fkl9vy6g31abajkald8urg` (`factura_id`),
  CONSTRAINT `FK616fkl9vy6g31abajkald8urg` FOREIGN KEY (`factura_id`) REFERENCES `factura` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,'','1 - 1488154702676',2.00,1,'2017-02-26'),(2,'','0',2.00,2,'2017-02-26'),(3,'','0',1.00,3,'2017-02-26'),(4,'','Ricardo',2.00,4,'2017-02-26'),(5,'','2',6.00,5,'2017-02-26'),(6,'','0',18.00,6,'2017-02-26'),(7,'','0',2.00,7,'2017-02-26'),(8,'','1',2.00,8,'2017-02-26'),(9,'','0',5.00,9,'2017-02-27'),(10,'','0',5.00,10,'2017-02-27'),(11,'','1',5.00,11,'2017-02-27'),(12,'','2',5.00,12,'2017-02-27'),(13,'','0',2.00,13,'2017-02-27'),(14,'','0',5.00,14,'2017-02-27'),(15,'','0',6.00,15,'2017-02-27'),(16,'','2',4.00,16,'2017-02-27'),(17,'','17 - 1488381801973',7.00,17,'2017-03-01'),(18,'','0',7.00,18,'2017-03-01'),(19,'','19 - 1488381869269',7.00,19,'2017-03-01'),(20,'','20 - 1488377586143',2.00,20,'2017-03-01'),(21,'','0',7.00,21,'2017-03-01'),(22,'','1',2.00,22,'2017-03-01'),(23,'','1',2.00,23,'2017-03-03'),(24,'','6',0.00,24,'2017-03-03'),(25,'','0',13.55,25,'2017-03-03'),(26,'','26 - 1488745629370',23.15,26,'2017-03-04'),(27,'','4',2.00,27,'2017-03-05'),(28,'','28 - 1488738454569',6.05,28,'2017-03-05'),(29,'','3',4.55,29,'2017-03-05'),(30,'','2',6.05,30,'2017-03-05'),(31,'','Cuenta',2.00,31,'2017-03-05');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factura` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cobrada` bit(1) NOT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdrp2rxy2vals0xhwh7b8u378u` (`cliente_id`),
  KEY `FK5jh2f7cvry6vwq6ats5otr065` (`usuario_id`),
  CONSTRAINT `FK5jh2f7cvry6vwq6ats5otr065` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKdrp2rxy2vals0xhwh7b8u378u` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,'',2.00,1,1,'2017-02-26'),(2,'',2.00,1,1,'2017-02-26'),(3,'',1.00,1,1,'2017-02-26'),(4,'',2.00,1,1,'2017-02-26'),(5,'',6.00,1,1,'2017-02-26'),(6,'',18.00,1,1,'2017-02-26'),(7,'',2.00,1,1,'2017-02-26'),(8,'',2.00,1,1,'2017-02-26'),(9,'',5.00,1,1,'2017-02-27'),(10,'',5.00,1,1,'2017-02-27'),(11,'',5.00,1,1,'2017-02-27'),(12,'',5.00,1,1,'2017-02-27'),(13,'',2.00,1,1,'2017-02-27'),(14,'',5.00,1,1,'2017-02-27'),(15,'',6.00,1,1,'2017-02-27'),(16,'',4.00,1,1,'2017-02-27'),(17,'',7.00,1,1,'2017-03-01'),(18,'',7.00,1,1,'2017-03-01'),(19,'',7.00,1,1,'2017-03-01'),(20,'',2.00,1,1,'2017-03-01'),(21,'',7.00,1,1,'2017-03-01'),(22,'',2.00,1,1,'2017-03-01'),(23,'',2.00,1,1,'2017-03-03'),(24,'',0.00,1,1,'2017-03-03'),(25,'',13.55,1,1,'2017-03-03'),(26,'',23.15,1,1,'2017-03-04'),(27,'',2.00,1,1,'2017-03-05'),(28,'',6.05,1,1,'2017-03-05'),(29,'',4.55,1,1,'2017-03-05'),(30,'',6.05,1,1,'2017-03-05'),(31,'',2.00,1,1,'2017-03-05');
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo`
--

DROP TABLE IF EXISTS `grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo`
--

LOCK TABLES `grupo` WRITE;
/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` VALUES (1,'','Refresco',NULL,'Refresco'),(2,'','dfgdf','','Cafes'),(3,'','tortas',NULL,'Torta');
/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo_producto`
--

DROP TABLE IF EXISTS `grupo_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo_producto` (
  `grupo_id` bigint(20) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  KEY `FK84a0hwwtcsp4v5ul56rfjxkqj` (`producto_id`),
  KEY `FK4tgpgarqbqyag569t180xljjo` (`grupo_id`),
  CONSTRAINT `FK4tgpgarqbqyag569t180xljjo` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`),
  CONSTRAINT `FK84a0hwwtcsp4v5ul56rfjxkqj` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo_producto`
--

LOCK TABLES `grupo_producto` WRITE;
/*!40000 ALTER TABLE `grupo_producto` DISABLE KEYS */;
INSERT INTO `grupo_producto` VALUES (1,3),(1,5),(1,6),(1,7),(1,8),(1,1),(1,2),(2,1),(2,4),(3,9),(3,4);
/*!40000 ALTER TABLE `grupo_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `almacen` int(11) NOT NULL,
  `custo` decimal(19,2) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `iva` double NOT NULL,
  `margen` double NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio_mas_iva` decimal(19,2) DEFAULT NULL,
  `precio_sin_iva` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'',220,1.00,'coca cola','coca-cola.png',10,50,'coca-cola',2.00,2.00),(2,'',20,1.00,'Hit Piña super bueno','hitPina.png',10,10,'Hit Piña',2.00,1.10),(3,'',200,2.00,'HIT DE UVA','hitUva.png',20,0.02,'HIT UVA',2.55,2.00),(4,'',10,1.00,'','solo.png',10,10,'SOLO            ',1.00,1.00),(5,'',200,1.00,'COCA ZERO','coca-cola zero.png',10,10,'COCA-COLA ZERO',1.50,1.00),(6,'',100,1.00,'FANTA DE NARANJA','fanta Naranja.png',10,10,'FANTA NARANJA',1.50,1.00),(7,'',50,1.00,'FANTA DE LIMON','fanta Limon.png',0.01,0.01,'FANTA LIMON',1.65,1.00),(8,'',100,1.00,'FANTA DE UVA','fanta Uva.png',10,10,'FANTA UVA',1.35,1.00),(9,'',10,1.00,'dfsdfs','ciel.png',10,0,'torta',1.00,1.00);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `contraseña` varchar(255) DEFAULT NULL,
  `controle_de_acceso` varchar(255) NOT NULL,
  `cp` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `localidade` varchar(255) DEFAULT NULL,
  `nota` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `fecha_primera_alta` date DEFAULT NULL,
  `fecha_ultima_baja` date DEFAULT NULL,
  `nif` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `nombre_de_usuario` varchar(255) DEFAULT NULL,
  `primer_apellido` varchar(255) DEFAULT NULL,
  `segundo_apellido` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_igfhcb3km5lfdcc9v0v3mdbbg` (`nif`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'','123456','ADMIN','12345','C/ Java Swing','Santa Pola','Yo','123','España','Alicante','1982-11-15','2017-02-23',NULL,'22661004831','Ricardo Maximino','ricardomaximino@hotmail.com','Gonçalves','De Moraes'),(2,'','123456','ADMIN','12345','C/Java Swing','Alicante','','123','España','Alicante','2017-03-05','2017-03-05',NULL,'1478963','Jose','123456789@123.com','Garcia','Lopez'),(3,'','123456','ADMIN','123456','C/ Java Swing','Alicante','','123','España','Alicante','1972-03-05','2017-03-05',NULL,'3698741','Lucia','123456789@123.com','Fraga','Campello'),(4,'','123456','USER','123456','C/ Java Swing','Alicante','','123','España','Alicante','1996-08-05','2017-03-05',NULL,'1598753','Carlos','','Ribera','Callosa');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_contactos`
--

DROP TABLE IF EXISTS `usuarios_contactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios_contactos` (
  `usuario_id` bigint(20) NOT NULL,
  `contactos_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_o028maratv71w9ge9oyp0j7b0` (`contactos_id`),
  KEY `FKov1wykm2bmpyqfk37ygylg6jk` (`usuario_id`),
  CONSTRAINT `FK5qopi3j6v47o78x6bxrr1jq4p` FOREIGN KEY (`contactos_id`) REFERENCES `contactos` (`id`),
  CONSTRAINT `FKov1wykm2bmpyqfk37ygylg6jk` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_contactos`
--

LOCK TABLES `usuarios_contactos` WRITE;
/*!40000 ALTER TABLE `usuarios_contactos` DISABLE KEYS */;
INSERT INTO `usuarios_contactos` VALUES (1,1),(2,4),(3,5),(4,6);
/*!40000 ALTER TABLE `usuarios_contactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `cuenta_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKseswc41n24heexukpu7n14a5g` (`cuenta_id`),
  KEY `FK1wljq30qekjupdmpom9laqr33` (`producto_id`),
  KEY `FKlostuvs99qor7x91k50ufof16` (`usuario_id`),
  KEY `FKpittoyb1d8jt76tv8a17wh1ke` (`cliente_id`),
  CONSTRAINT `FK1wljq30qekjupdmpom9laqr33` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  CONSTRAINT `FKlostuvs99qor7x91k50ufof16` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKpittoyb1d8jt76tv8a17wh1ke` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  CONSTRAINT `FKseswc41n24heexukpu7n14a5g` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES (2,2,'2017-02-26',2.00,1,4,1,1),(3,1,'2017-02-26',2.00,2,2,1,1),(4,1,'2017-02-26',1.00,3,4,1,1),(5,1,'2017-02-26',2.00,4,2,1,1),(6,1,'2017-02-26',2.00,5,1,1,1),(7,1,'2017-02-26',2.00,5,2,1,1),(8,2,'2017-02-26',2.00,5,4,1,1),(9,3,'2017-02-26',6.00,6,1,1,1),(10,6,'2017-02-26',12.00,6,2,1,1),(11,1,'2017-02-26',2.00,7,2,1,1),(12,1,'2017-02-26',2.00,8,2,1,1),(13,1,'2017-02-27',2.00,9,1,1,1),(14,1,'2017-02-27',2.00,9,2,1,1),(15,1,'2017-02-27',1.00,9,4,1,1),(16,1,'2017-02-27',2.00,10,1,1,1),(17,1,'2017-02-27',2.00,10,2,1,1),(18,1,'2017-02-27',1.00,10,4,1,1),(19,1,'2017-02-27',2.00,11,1,1,1),(20,1,'2017-02-27',2.00,11,2,1,1),(21,1,'2017-02-27',1.00,11,4,1,1),(22,1,'2017-02-27',2.00,12,1,1,1),(23,1,'2017-02-27',2.00,12,2,1,1),(24,1,'2017-02-27',1.00,12,4,1,1),(25,1,'2017-02-27',2.00,13,1,1,1),(26,1,'2017-02-27',2.00,14,1,1,1),(27,1,'2017-02-27',2.00,14,2,1,1),(28,1,'2017-02-27',1.00,14,4,1,1),(29,1,'2017-02-27',2.00,15,1,1,1),(30,4,'2017-02-27',4.00,15,4,1,1),(31,2,'2017-02-27',4.00,16,1,1,1),(32,1,'2017-03-01',2.00,17,1,NULL,NULL),(33,1,'2017-03-01',2.00,17,2,NULL,NULL),(34,1,'2017-03-01',2.00,17,3,NULL,NULL),(35,1,'2017-03-01',1.00,17,4,NULL,NULL),(36,1,'2017-03-01',2.00,18,1,1,1),(37,1,'2017-03-01',2.00,18,2,1,1),(38,1,'2017-03-01',2.00,18,3,1,1),(39,1,'2017-03-01',1.00,18,4,1,1),(40,1,'2017-03-01',2.00,19,1,NULL,NULL),(41,1,'2017-03-01',2.00,19,2,NULL,NULL),(42,1,'2017-03-01',2.00,19,3,NULL,NULL),(43,1,'2017-03-01',1.00,19,4,NULL,NULL),(44,1,'2017-03-01',2.00,20,3,NULL,NULL),(45,1,'2017-03-01',2.00,21,1,NULL,NULL),(46,1,'2017-03-01',2.00,21,2,NULL,NULL),(47,1,'2017-03-01',2.00,21,3,NULL,NULL),(48,1,'2017-03-01',1.00,21,4,NULL,NULL),(49,1,'2017-03-01',2.00,22,1,1,1),(50,1,'2017-03-03',2.00,23,1,1,1),(51,1,'2017-03-03',2.00,25,2,NULL,NULL),(52,1,'2017-03-03',2.55,25,3,NULL,NULL),(53,1,'2017-03-03',1.50,25,5,NULL,NULL),(54,1,'2017-03-03',1.50,25,6,NULL,NULL),(55,1,'2017-03-03',1.65,25,7,NULL,NULL),(56,1,'2017-03-03',1.35,25,8,NULL,NULL),(57,1,'2017-03-03',2.00,25,1,NULL,NULL),(58,1,'2017-03-03',1.00,25,4,NULL,NULL),(59,4,'2017-03-04',8.00,26,2,NULL,NULL),(60,1,'2017-03-04',2.55,26,3,NULL,NULL),(61,3,'2017-03-04',4.50,26,6,NULL,NULL),(62,6,'2017-03-04',8.10,26,8,NULL,NULL),(63,1,'2017-03-05',2.00,27,1,1,1),(64,1,'2017-03-05',2.00,28,2,1,1),(65,1,'2017-03-05',2.55,28,3,1,1),(66,1,'2017-03-05',1.50,28,5,1,1),(67,1,'2017-03-05',2.00,29,2,1,1),(68,1,'2017-03-05',2.55,29,3,1,1),(69,1,'2017-03-05',2.00,30,2,1,1),(70,1,'2017-03-05',2.55,30,3,1,1),(71,1,'2017-03-05',1.50,30,5,1,1),(72,1,'2017-03-05',1.00,31,9,1,1),(73,1,'2017-03-05',1.00,31,4,1,1);
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-05 23:07:24
