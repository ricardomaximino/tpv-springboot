-- MySQL dump 10.13  Distrib 5.7.36, for Linux (x86_64)
--
-- Host: localhost    Database: springbootdb
-- ------------------------------------------------------
-- Server version	5.7.36

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,_binary '','1234','ADMIN','135584','Rua Martim Alfonso','São Paulo','2º D','1234','Brasil','São Paulo','1964-01-09','2022-01-09',NULL,'1234','Antonio','antoniomataraso@hotmail.com','Freitas','Mataraso'),(2,_binary '','4321','ADMIN','80223657','Av. Princesa Isaura','Salvador','','4321','Brasil','Bahia','1975-05-05','2022-01-09',NULL,'4321','Maria','mmenezez@gmail.com','Menezes','da Silva'),(3,_binary '','6789','ADMIN','774123568','Av 3','Campinas','entre rua 24 e 25','4588','Brasil','São Paulo','1979-08-16','2022-01-09',NULL,'6789','Jose Carlos','josecmiranda_ze@yahoo.com.br','Miranda','Santos'),(4,_binary '','741','ADMIN','03241','Calle Gomes Olivar','Elche','1º A','71','España','Alicante','1996-02-26','2022-01-09',NULL,'741','Mauel','manolosempbahesta@hotmai.com','Sempere','Bayesta'),(5,_binary '','951','ADMIN','03354','Calle Lopez Hernandez','Alicante','planta baja','78','España','Alicante','1987-06-10','2022-01-09',NULL,'951','Maria Clara','marianieves@yahoo.es','Andino','Nieves');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes_contactos`
--

LOCK TABLES `clientes_contactos` WRITE;
/*!40000 ALTER TABLE `clientes_contactos` DISABLE KEYS */;
INSERT INTO `clientes_contactos` VALUES (1,1),(2,2),(3,3),(4,4),(5,5);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactos`
--

LOCK TABLES `contactos` WRITE;
/*!40000 ALTER TABLE `contactos` DISABLE KEYS */;
INSERT INTO `contactos` VALUES (1,'658412320','TELEFONO'),(2,'741258963','TELEFONO'),(3,'841365257','TELEFONO'),(4,'369852147','TELEFONO'),(5,'951357894','TELEFONO'),(6,'634 75 35 62','TELEFONO');
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
  `fecha` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `factura_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK616fkl9vy6g31abajkald8urg` (`factura_id`),
  CONSTRAINT `FK616fkl9vy6g31abajkald8urg` FOREIGN KEY (`factura_id`) REFERENCES `factura` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,_binary '','2022-01-09','2',3.50,1),(2,_binary '','2022-01-09','Antonio',3.50,2);
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
  `fecha` date DEFAULT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdrp2rxy2vals0xhwh7b8u378u` (`cliente_id`),
  KEY `FK5jh2f7cvry6vwq6ats5otr065` (`usuario_id`),
  CONSTRAINT `FK5jh2f7cvry6vwq6ats5otr065` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKdrp2rxy2vals0xhwh7b8u378u` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,_binary '','2022-01-09',3.50,3,1),(2,_binary '','2022-01-09',3.50,3,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo`
--

LOCK TABLES `grupo` WRITE;
/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` VALUES (1,_binary '','bebidas','agua.png','Bebidas'),(2,_binary '','Cafés','solo.png','Cafés');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo_producto`
--

LOCK TABLES `grupo_producto` WRITE;
/*!40000 ALTER TABLE `grupo_producto` DISABLE KEYS */;
INSERT INTO `grupo_producto` VALUES (2,1),(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,_binary '',1000,1.00,'Solo','solo.png',0,0,'Solo',1.00,0.80),(2,_binary '',110,0.70,'coca-cola','coca-cola.png',0,0,'coca-cola',1.50,1.20),(3,_binary '',0,0.70,'Coca-Cola Zero','coca-cola zero.png',0,0,'Coca-Cola Zero',1.50,1.20),(4,_binary '',100,0.50,'Agual M sin gás','agua.png',0,0,'Agual M sin gás',1.00,0.80),(5,_binary '',80,0.80,'Fanta Limon','fanta Limon.png',0,0,'Fanta Limon',1.50,1.20),(6,_binary '',80,0.80,'Fanta Naranja','fanta Naranja.png',0,0,'Fanta Naranja',1.50,1.20),(7,_binary '',80,0.80,'Fanta Uva','fanta Uva.png',0,0,'Fanta Uva',1.50,1.20),(8,_binary '',80,0.60,'Agua con gás','frizz.png',0,0,'Agua con gás',1.50,1.30),(9,_binary '',100,0.50,'Hit Manzana','hitManzana.png',0,0,'Hit Manzana',1.20,1.00),(10,_binary '',100,0.50,'Hit Uva','hitUva.png',0,0,'Hit Uva',1.20,0.80),(11,_binary '',100,0.50,'Hit Piña','hitPina.png',0,0,'Hit Piña',1.20,0.80);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,_binary '','159','ADMIN','032356','Calle Alicante','Alicante','','159','España','Alicante','1982-11-15','2022-01-09',NULL,'159','Ricardo Maximino','ricardomaximino@hotmail.com','Gonçalves','de Moraes');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_contactos`
--

LOCK TABLES `usuarios_contactos` WRITE;
/*!40000 ALTER TABLE `usuarios_contactos` DISABLE KEYS */;
INSERT INTO `usuarios_contactos` VALUES (1,6);
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
  `cliente_id` bigint(20) DEFAULT NULL,
  `cuenta_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpittoyb1d8jt76tv8a17wh1ke` (`cliente_id`),
  KEY `FKseswc41n24heexukpu7n14a5g` (`cuenta_id`),
  KEY `FK1wljq30qekjupdmpom9laqr33` (`producto_id`),
  KEY `FKlostuvs99qor7x91k50ufof16` (`usuario_id`),
  CONSTRAINT `FK1wljq30qekjupdmpom9laqr33` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  CONSTRAINT `FKlostuvs99qor7x91k50ufof16` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKpittoyb1d8jt76tv8a17wh1ke` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  CONSTRAINT `FKseswc41n24heexukpu7n14a5g` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES (1,2,'2022-01-09',2.00,1,1,1,1),(2,1,'2022-01-09',1.50,1,1,8,1),(3,1,'2022-01-09',1.00,NULL,2,1,1),(4,1,'2022-01-09',1.50,NULL,2,2,1),(5,1,'2022-01-09',1.00,NULL,2,4,1);
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

-- Dump completed on 2022-01-09  9:20:33
