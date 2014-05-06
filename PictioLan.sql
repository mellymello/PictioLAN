-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 9.1.5              
-- * Generator date: Feb 14 2012              
-- * Generation date: Mon May 05 19:07:32 2014 
-- * LUN file: C:\Users\Zak\Documents\2HEIG\2Sem\GEN\PictioLAN\PictioLan.lun 
-- * Schema: BD/SQL1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database pictiolan;
use pictiolan;


-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table Category (
     ID_Category, -- Sequence attribute not implemented -- not null,
     Name varchar(50) not null,
     constraint ID_ID primary key (ID_Category));

create table Players (
     ID_Player, -- Sequence attribute not implemented -- not null,
     Pseudo varchar(50) not null,
     Password varchar(50) not null,
     constraint ID_ID primary key (ID_Player));

create table PlayerStats (
     ID_Player, -- Sequence attribute not implemented -- not null,
     Hav_ID_Player numeric(10) not null,
     ID_Word numeric(10) not null,
     constraint ID_ID primary key (ID_Player));

create table Stats (
     Time char not null,
     Win char not null,
     ID_Player numeric(10) not null);

create table Words (
     ID_Word, -- Sequence attribute not implemented -- not null,
     Word varchar(50) not null,
     ID_Category numeric(10) not null,
     constraint ID_ID primary key (ID_Word));


-- Constraints Section
-- ___________________ 

alter table PlayerStats add constraint ID_CHK
     check(exists(select * from Stats
                  where Stats.ID_Player = ID_Player)); 

alter table PlayerStats add constraint FKHave_FK
     foreign key (Hav_ID_Player)
     references Players;

alter table PlayerStats add constraint FKSave_stats_FK
     foreign key (ID_Word)
     references Words;

alter table Stats add constraint FKUse_FK
     foreign key (ID_Player)
     references PlayerStats;

alter table Words add constraint FKContain_FK
     foreign key (ID_Category)
     references Category;


-- Index Section
-- _____________ 

create unique index ID_IND
     on Category (ID_Category);

create unique index ID_IND
     on Players (ID_Player);

create unique index ID_IND
     on PlayerStats (ID_Player);

create index FKHave_IND
     on PlayerStats (Hav_ID_Player);

create index FKSave_stats_IND
     on PlayerStats (ID_Word);

create index FKUse_IND
     on Stats (ID_Player);

create unique index ID_IND
     on Words (ID_Word);

create index FKContain_IND
     on Words (ID_Category);

