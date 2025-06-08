-- Devoir 127
-- Nom: BROUSSEAU, Prenom: Raphael
-- Nom: DIAS , Prenom: Alexandre

-- Feuille SAE2.05 Exploitation d'une base de données: Livre Express
-- 
-- Veillez à bien répondre aux emplacements indiqués.
-- Seule la première requête est prise en compte.

-- +-----------------------+--
-- * Question 127156 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Quels sont les livres qui ont été commandés le 1er décembre 2024 ?

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +---------------+--------------------------------------------+---------+-----------+-------+
-- | isbn          | titre                                      | nbpages | datepubli | prix  |
-- +---------------+--------------------------------------------+---------+-----------+-------+
-- | etc...
-- = Reponse question 127156.

\! echo "---requete 1";
select isbn, titre, nbpages, datepubli, prix
from LIVRE natural join DETAILCOMMANDE NATURAL JOIN COMMANDE
WHERE datecom=str_to_date('01/12/2024','%d/%m/%Y');

-- +-----------------------+--
-- * Question 127202 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Quels clients ont commandé des livres de René Goscinny en 2021 ?

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------+---------+-----------+-----------------------------+------------+-------------+
-- | idcli | nomcli  | prenomcli | adressecli                  | codepostal | villecli    |
-- +-------+---------+-----------+-----------------------------+------------+-------------+
-- | etc...
-- = Reponse question 127202.
\! echo "---requete 2";
select distinct idcli, nomcli, prenomcli, adressecli, codepostal, villecli
from CLIENT 
natural join COMMANDE natural join DETAILCOMMANDE natural join LIVRE natural join ECRIRE natural join AUTEUR
where year(datecom) = 2021 and nomauteur = "René Goscinny" ;



-- +-----------------------+--
-- * Question 127235 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Quels sont les livres sans auteur et étant en stock dans au moins un magasin en quantité strictement supérieure à 8 ?

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +---------------+-----------------------------------+-------------------------+-----+
-- | isbn          | titre                             | nommag                  | qte |
-- +---------------+-----------------------------------+-------------------------+-----+
-- | etc...
-- = Reponse question 127235.

\! echo "---requete 3";
select isbn, titre, nommag, qte 
from LIVRE 
natural left join ECRIRE
natural join POSSEDER
natural join MAGASIN 
where idauteur is null and isbn is not null and qte > 8;

-- +-----------------------+--
-- * Question 127279 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Pour chaque magasin, on veut le nombre de clients qui habitent dans la ville de ce magasin (en affichant les 0)

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------+-------------------------+-------+
-- | idmag | nommag                  | nbcli |
-- +-------+-------------------------+-------+
-- | etc...
-- = Reponse question 127279.

\! echo "---requete 4";
select idmag, nommag, ifnull(count(idcli), 0) as nbcli
from CLIENT 
natural join COMMANDE 
natural join MAGASIN 
where villemag = villecli
group by idmag;

-- +-----------------------+--
-- * Question 127291 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Pour chaque magasin, on veut la quantité de livres achetés le 15/09/2022 en affichant les 0.

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------------------------+------+
-- | nommag                  | nbex |
-- +-------------------------+------+
-- | etc...
-- = Reponse question 127291.

\! echo "---requete 5";
select m.nommag, ifnull(sum(qte),0) as nbex
from COMMANDE co
natural join DETAILCOMMANDE 
right join MAGASIN m on m.idmag = co.idmag
and co.datecom = str_to_date("15/09/2022", '%d/%m/%Y')
group by m.nommag;

-- +-----------------------+--
-- * Question 127314 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Instructions d'insertion dans la base de données

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +------------+
-- | insertions |
-- +------------+
-- | etc...
-- = Reponse question 127314.
DELETE FROM POSSEDER WHERE isbn = "9782844273765";
DELETE FROM ECRIRE WHERE isbn = "9782844273765";
DELETE FROM EDITER WHERE isbn = "9782844273765";
DELETE FROM THEMES WHERE isbn = "9782844273765";
DELETE FROM LIVRE WHERE isbn = "9782844273765";
DELETE FROM AUTEUR WHERE idauteur IN ("OL246259A", "OL7670824A");
DELETE FROM EDITEUR WHERE idedit = 240;

insert into EDITEUR(nomedit,idedit) values
    ("First Interactive", 240);
insert into AUTEUR(idauteur, nomauteur,anneenais,anneedeces) values
    ("OL246259A", "Allen G. Taylor", null, null),
    ("OL7670824A", "Reingard Engel", null,null);
insert into LIVRE(isbn, titre,nbpages,datepubli,prix) values
    ("9782844273765", "SQL pour les nuls", 292, 2002, 33.5);
insert into THEMES(isbn,iddewey) values
    ("9782844273765", 000);
insert into EDITER(isbn,idedit) values
    ("9782844273765", 240);
insert into ECRIRE(isbn,idauteur) values
    ("9782844273765", "OL246259A"),
    ("9782844273765", "OL7670824A");
insert into POSSEDER(idmag, isbn, qte) values
    (7, "9782844273765", 3);

SELECT 
    isbn, titre, nbpages, datepubli, prix,
    nomedit,
    idauteur, nomauteur, 
    nomclass,
    nommag, qte
FROM LIVRE l
natural LEFT JOIN EDITER 
natural LEFT JOIN EDITEUR 
natural LEFT JOIN ECRIRE 
natural LEFT JOIN AUTEUR 
natural LEFT JOIN THEMES 
natural LEFT JOIN CLASSIFICATION 
natural LEFT JOIN POSSEDER 
natural LEFT JOIN MAGASIN 
WHERE isbn = "9782844273765";


-- +-----------------------+--
-- * Question 127369 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête Graphique 1 Nombre de livres vendus par magasin et par an

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------------------------+-------+-----+
-- | Magasin                 | Année | qte |
-- +-------------------------+-------+-----+
-- | etc...
-- = Reponse question 127369.

\! echo "---graphique 1";
select nommag, year(datecom) as annee, count(numcom) as nblivre
from COMMANDE natural join MAGASIN
group by nommag, year(datecom);

-- +-----------------------+--
-- * Question 127370 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête Graphique 2  Chiffre d'affaire par thème en 2024

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +--------------------------------------+---------+
-- | Theme                                | Montant |
-- +--------------------------------------+---------+
-- | etc...
-- = Reponse question 127370.

\! echo "---graphique 2";
with CA as (
    select sum(prixvente*qte) CAT from COMMANDE natural join DETAILCOMMANDE 
    where year(datecom) = 2024),
    CAParTheme as (
        select iddewey, nomclass, sum(prixvente*qte) as Montant 
        from COMMANDE
        natural join DETAILCOMMANDE 
        natural join LIVRE 
        natural join THEMES 
        natural join CLASSIFICATION
        where year(datecom) = 2024
        group by floor(iddewey/100))
select iddewey, nomclass, Montant, (Montant / CAT) * 100 as pourcentage
from CAParTheme 
cross join CA
group by iddewey;

-- +-----------------------+--
-- * Question 127381 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête Graphique 3 Evolution chiffre d'affaire par magasin et par mois en 2024

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +------+-------------------------+---------+
-- | mois | Magasin                 | CA      |
-- +------+-------------------------+---------+
-- | etc...
-- = Reponse question 127381.

\! echo "---graphique 3";
with caMag as (
    select nommag as magasin, month(datecom) as mois,sum(prixvente * qte) as CA
    from MAGASIN 
    natural join COMMANDE
    natural join DETAILCOMMANDE
    natural join LIVRE
    where year(datecom)=2024
    group by nommag ,mois)
select * from caMag;

-- +-----------------------+--
-- * Question 127437 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête Graphique 4 Comparaison ventes en ligne et ventes en magasin

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------+------------+---------+
-- | annee | typevente  | montant |
-- +-------+------------+---------+
-- | etc...
-- = Reponse question 127437.
\! echo "---graphique 4 ";

select year(datecom) as annee, enligne as typevente, sum(prixvente * qte) as CAL
from COMMANDE 
natural join DETAILCOMMANDE 
natural join LIVRE
where year(datecom) <> 2025
group by enligne, annee;


-- +-----------------------+--
-- * Question 127471 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête Graphique 5 

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------------------+-----------+
-- | Editeur           | nbauteurs |
-- +-------------------+-----------+
-- | etc...
-- = Reponse question 127471.

\! echo "---- graphique 5"

select nomedit as Editeur, count(idauteur) as nbauteurs
from EDITEUR 
natural join EDITER
natural join LIVRE
natural join ECRIRE
natural join AUTEUR
group by nomedit
order by count(idauteur) desc 
limit 10;

-- +-----------------------+--
-- * Question 127516 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête Graphique 6 Qté de livres de R. Goscinny achetés en fonction de l'orgine des clients

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------------+-----+
-- | ville       | qte |
-- +-------------+-----+
-- | etc...
-- = Reponse question 127516.

\! echo "graphique 6"

select villecli as ville, count(idcli) as qte
from CLIENT 
natural join COMMANDE 
natural join DETAILCOMMANDE
natural join LIVRE 
natural join ECRIRE
natural join AUTEUR
where nomauteur = "René Goscinny"
group by villecli; 


-- +-----------------------+--
-- * Question 127527 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête Graphique 7 Valeur du stock par magasin
-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------------------------+---------+
-- | Magasin                 | total   |
-- +-------------------------+---------+
-- | etc...
-- = Reponse question 127527.

\! echo "graphique 7"

select nommag as magasin, sum(qte*prix) as valeurStock
from MAGASIN
natural join POSSEDER
natural join LIVRE
group by nommag;

-- +-----------------------+--
-- * Question 127538 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
-- Requête Graphique 8 Statistiques sur l'évolution du chiffre d'affaire total par client 
-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------+---------+---------+---------+
-- | annee | maximum | minimum | moyenne |
-- +-------+---------+---------+---------+
-- | etc...
-- = Reponse question 127538.

\! echo "graphique 8"

with CAPClient as (
    select year(datecom) as annee, idcli, sum(prixvente*qte) as CA
    from COMMANDE
    natural join CLIENT 
    natural join DETAILCOMMANDE
    group by idcli, annee 
)
select annee, min(CA), max(CA), avg(CA) from CAPClient group by annee;

-- +-----------------------+--
-- * Question 127572 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête Palmarès

-- Voici le début de ce que vous devez obtenir.
-- ATTENTION à l'ordre des colonnes et leur nom!
-- +-------+-----------------------+-------+
-- | annee | nomauteur             | total |
-- +-------+-----------------------+-------+
-- | etc...
-- = Reponse question 127572.
\! echo "requete sur les palmares"
with venteParAuteur as (
    select  idauteur,  year(datecom) as annee , sum(qte) as total
    from AUTEUR
    natural join ECRIRE
    natural join LIVRE
    natural join DETAILCOMMANDE
    natural join COMMANDE
    where year(datecom)!=2025
    group by idauteur, annee
    order by total desc)
SELECT annee, nomauteur , max(total) from AUTEUR
natural right join venteParAuteur
group by annee;

-- +-----------------------+--
-- * Question 127574 : 2pts --
-- +-----------------------+--
-- Ecrire une requête qui renvoie les informations suivantes:
--  Requête imprimer les commandes en considérant que l'on veut celles de février 2020
-- = Reponse question 127574


SELECT nommag, numcom, datecom, nomcli, prenomcli, adressecli, codepostal, villecli, numcom ,isbn, titre, qte, prixvente, (prixvente*qte) as total
FROM COMMANDE
NATURAL JOIN DETAILCOMMANDE 
NATURAL JOIN CLIENT
NATURAL JOIN LIVRE
NATURAL JOIN MAGASIN
WHERE year(datecom) = 2020 and month(datecom)= 02
ORDER BY nommag, numcom, isbn;

-- requete poour la partie analyse de données.
/*select sum(prixvente * qte) CA, sum(qte) as NbVente, month(datecom) mois, year(datecom) annee
from MAGASIN 
natural join COMMANDE
natural join DETAILCOMMANDE
natural join LIVRE
group by  annee, mois*/  
