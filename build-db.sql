/*Dropping the tables to reset the database*/
DROP TABLE IF EXISTS match;
DROP TABLE IF EXISTS characterowns;
DROP TABLE IF EXISTS ability;
DROP TABLE IF EXISTS character;

/*Creating each table, columns in tables, and their primary and forreign keys. aside: I need to work on figuring out date/time */
CREATE TABLE character(
charactername varchar(16) NOT NULL PRIMARY KEY);

CREATE TABLE ability(
abilityid integer NOT NULL PRIMARY KEY,
abilityname varchar(32) NOT NULL,
abilityeffect varchar(128) NOT NULL,
abilitystats varchar(32) NOT NULL);

CREATE TABLE characterowns(
charactername varchar(16) NOT NULL,
abilityid integer NOT NULL,
PRIMARY KEY (charactername, abilityid),
FOREIGN KEY (charactername) REFERENCES character (charactername),
FOREIGN KEY (abilityid) REFERENCES ability (abilityid));

CREATE TABLE match(
gameid integer NOT NULL,
charactername varchar(16) NOT NULL,
dateofmatch date NOT NULL,
map varchar(32) NOT NULL,
gamelength varchar(5) NOT NULL,
othereliminations integer NOT NULL,
finalblows integer NOT NULL,
soloelims integer NOT NULL,
enviornmentalelims integer NOT NULL,
damage integer NOT NULL,
objectivetime varchar(5)NOT NULL,
objectiveelims integer NOT NULL,
healing integer NOT NULL,
deaths integer NOT NULL,
timeonfire varchar(5) NOT NULL,
defenceassists integer NOT NULL,
offenceassists integer NOT NULL,
bronzemedals integer NOT NULL,
silvermedals integer NOT NULL,
goldmedals integer NOT NULL,
winorloss varchar(4) NOT NULL,
weaponaccuracy integer NOT NULL,
PRIMARY KEY (gameid, charactername),
FOREIGN KEY (charactername) REFERENCES character (charactername));

/*Inserting values into each table's comlumns aside:There are only 6 healers in Overwatch */
INSERT INTO character
VALUES
('Mercy'),
('Ana'),
('Lucio'),
('Brigitte'),
('Moria'),
('Zenyatta');

INSERT INTO ability
VALUES
('1', 'Cadeceus Staff', 'Engages one of two beams to ally; heal or damage boost', 'heal :40hp/sec boost :20%'),
('2', 'Cadeceus Blaster', 'Mercy shoots a round from her sidearm', '35 damage/round'),
('3', 'Guardian Angel', 'Mercy flys towards a targeted ally', '50units/sec'),
('4', 'Resurrect', 'Mercy brings a dead ally back into the fight', 'Ressurct at full health'),
('5', 'Angelic Descent', 'Mercy slows the speed of her descent', 'Descent speed reduced by 50%'),
('6', 'Valkerie', 'All of Mercys stats are boosted', 'healing 20% damage 20% speed 20%'),
('7', 'Orb of Destruction', 'Zenyatta projects orbs individually or in a charged rapid shot', '45 damage/orb'),
('8', 'Orb of Harmony', 'Zenyatta grants a healing orb to an ally', '35 healing/sec'),
('9', 'Orb of Discord', 'Zenyatta inflicts a orb to an enemy to amplify damage taken', 'amplify 20%'),
('10', 'Transcendence', 'Immune to damage and grants a strong aura of healing to allys', 'heal 200/sec');

INSERT INTO characterowns
VALUES
('Mercy','1'),
('Mercy','2'),
('Mercy','3'),
('Mercy','4'),
('Mercy','5'),
('Mercy','6'),
('Zenyatta', '7'),
('Zenyatta', '8'),
('Zenyatta', '9'),
('Zenyatta', '10');

/*I didn't have time to actually play 10 matches so I replicated a single match ten times*/
INSERT INTO match
VALUES
('1', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('2', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('3', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('4', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('5', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('6', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('7', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('8', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('9', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
('10', 'Mercy', '2018-11-25', 'Ilios', '8/45', '7', '4', '3', '0', '45', '2/12', '2', '9000', '3', '1/30', '14', '4', '0', '0', '5', 'win', '98'),
