CREATE DATABASE  IF NOT EXISTS `cookbook` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cookbook`;
-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: cookbook
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredients` (
  `recipeId` int NOT NULL,
  `materialName` varchar(80) NOT NULL,
  `unit` varchar(80) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`recipeId`,`materialName`),
  CONSTRAINT `fk_recipeId` FOREIGN KEY (`recipeId`) REFERENCES `recipe` (`recipeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
INSERT INTO `ingredients` VALUES (1,'apple cider vinegar','tbsp.',3),(1,'cilantro','tbsp.',2),(1,'red onion','stück',1),(1,'seitan','lb.',1),(1,'soy sauce','ml',250),(1,'tomato','stück',2),(1,'vegetable oil','tbsp.',2),(2,'black pepper','gram',25),(2,'butter','gram',125),(2,'cloves garlic','stück',2.5),(2,'minced rosemary','gram',12.5),(2,'olive oil','tbsp.',1.25),(2,'pork loin chops','kilogram',1.25),(3,'all-purpse flour','tbsp.',1),(3,'apple','stück',1),(3,'brie','oz.',8),(3,'butter','tbsp.',2),(3,'cinnamon','tsp.',0.25),(3,'sugar','tbsp.',2),(3,'walnut','stück',8),(4,'all-purpose flour','gram',200),(4,'baking soda','tsp.',0.25),(4,'canola oil','cup',0.25),(4,'kosher salt','tsp.',2),(4,'large eggs','stück',2),(4,'packed brown sugar','cup',0.33),(4,'powdered sugar','cup',1.5),(4,'whole milk','cup',0.5),(6,'all-purpose flour','c.',1),(6,'black pepper','gram',20),(6,'breadcrumbs ','c.',2),(6,'chicken breasts ','stück',8),(6,'Kosher salt','gram',30),(6,'large eggs','stück',4),(6,'lemon','stück',2);
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `recipeId` int NOT NULL AUTO_INCREMENT,
  `recipeName` varchar(80) NOT NULL,
  `recipeImage` varchar(80) DEFAULT NULL,
  `prepTime` int NOT NULL,
  `cookTime` int NOT NULL,
  `serve` int NOT NULL,
  `instructions` longtext NOT NULL,
  `score` int DEFAULT NULL,
  `isFavourite` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`recipeId`),
  KEY `fk_Recipes_idx` (`recipeId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (1,'Seitan Lomo Saltado','./src/Pics/Seitan Lomo Saltado.png',10,20,4,'1.In a skillet over high heat, heat oil until it just starts to smoke. Add seitan and sear, stirring occasionally, for 4 to 5 minutes, or it begins to caramelize.\n2.If there is no visible oil in the pan, add about a tablespoon more before adding the red onion and aji amarillo (or fresno chile). Cook, tossing constantly, for 3 minutes, until onions have just started to soften. Add tomatoes and cook for an additional 2 to 3 minutes, tossing them with the rest of the ingredients. Make sure the tomatoes have not cooked down fully and still retain a bit of bite. Add garlic, a good pinch of kosher salt and a couple cracks of black pepper and let cook for another minute.\n3.Reduce heat to medium and add soy sauce and vinegar, stirring until seitan and veggies are fully coated with sauce. Let sauce reduce for 1 to 1 ½ minutes, or until it has thickened slightly.  \n4.Remove skillet from heat and add fries, tossing until they have been fully incorporated. Garnish with cilantro and serve.\n',5,0),(2,'Rosemary Pork Chops','./src/Pics/Rosemary Pork Chops.png',10,20,5,'1. Preheat oven to 375°. Season pork chops generously with salt and pepper.\n2.In a small bowl mix together butter, rosemary, and garlic. Set aside.\n3.In an oven safe skillet over medium-high heat, heat olive oil then add pork chops. Sear until golden, 4 minutes, flip and cook 4 minutes more. Brush pork chops generously with garlic butter.\n4.Place skillet in oven and cook until cooked through (145° for medium), 10-12 minutes. Serve with more garlic butter.',2,0),(3,'Apple Crisp Baked Brie','./src/Pics/Apple Crisp Baked Brie.png',20,55,8,'1. Preheat oven to 350° and place your brie in a small skillet. (Alternatively, line a small baking sheet with parchment paper, so brie can easily be transferred to serving dish.)\n2. Melt 1 tablespoon butter in a small nonstick skillet over medium heat. When butter is melted, add diced apple, 1 tablespoon brown sugar, 1/8 teaspoon cinnamon, and a pinch of salt. Cook, stirring occasionally, until apples are tender but still maintain their shape, 10 to 12 minutes. Spoon apple mixture onto brie in prepared baking dish.\n3. In a medium, microwave-safe bowl, melt remaining 2 tablespoons butter in the microwave. Add oats, walnuts, flour, remaining tablespoon brown sugar, and remaining 1/8 teaspoon cinnamon. Season with a pinch of salt and use your hands or a fork to combine.\n4. Scatter oat mixture over top of brie and bake until cheese is melty and oat topping is crisp, 25 to 30 minutes. \n5. Serve with sliced baguette for dipping.',0,1),(4,'Fruity Pebbles Donuts','./src/Pics/Fruity Pebbles Donuts.png',15,80,1,'1. Preheat oven to 400° and grease two nonstick donut pans with cooking spray. In a medium bowl, whisk to combine flour, baking powder, salt, and baking soda.\n2. In a large bowl, whisk to combine eggs, ½ cup milk, sour cream, sugars, oil, and vanilla until smooth. Pour dry ingredients into wet ingredients and fold until just combined. (Some lumps are OK!) Gently fold in Fruity PEBBLES cereal.\n3. Spoon batter into a piping bag or resealable plastic bag. Cut off the tip and pipe batter into donut pans, filling each well.\n4. Bake for 8 to 10 minutes, or until the tops appear dry and the undersides are golden. Let cool 5 minutes before turning out. Let cool completely.\n5. Whisk powdered sugar with 2 tablespoons whole milk to make a thick but dippable glaze. If necessary, thin glaze with ½ teaspoon more milk at a time. Dip top of each donut in glaze then sprinkle donuts with more Fruity PEBBLES cereal. \n6. Let glaze set, about 20 minutes, before serving.',0,1),(6,'Chicken Schnitzel','./src/Pics/Screen Shot 2020-11-09 at 13.15.59.png',15,30,8,'1. Cut each chicken breast lengthwise until you have two thin halves of chicken breast. Place one breast in a large plastic bag or under plastic wrap, and pound with a tenderizer or heavy bottomed pot until the chicken breast is about ¼ inch thick. Repeat this for each breast until you have 8 cutlets. Season with salt and pepper.\n2. Fill a large saucepan with about ¼-inch of oil and heat to 350º. To set up your dredging station, have one plate full of each of the three breading ingredients: flour, eggs, and breadcrumbs.\n3. Moving quickly, place one cutlet into the flour and flip, covering the cutlet with flour, then move to the egg and do the same. Finally, place the cutlet into the breadcrumbs to coat, but do not press breadcrumbs into the meat—classic schnitzel has very light breading. Repeat this with as many cutlets will fit in your pan without overcrowding. Do not bread all of your cutlets at once, or the breadcrumbs will become oversaturated with egg and keep the cutlet from becoming crispy.\n4. Carefully drop the cutlets into your oil and fry 2 to 3 minutes on both sides or until the cutlet is crispy and golden brown. Move to a paper towel lined plate and immediately season with kosher salt. Repeat with the remainder of the cutlets.\n5. Serve immediately with lemon wedges, roasted potatoes, German potato salad or cucumber salad.',0,1);
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-10 18:40:41
