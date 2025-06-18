select idcli, nomcli, prenomcli, adressecli, codepostal, nommag, numcom, datecom, isbn, titre, qte, prix, prix * qte as totalArticle, sum(prixvente * qte) as total
from CLIENT
natural join COMMANDE
natural join MAGASIN
natural join DETAILCOMMANDE
natural join LIVRE
where month(datecom) = 12
and year(datecom) = 2023
and idcli = 444
group by month(datecom), numcom, isbn