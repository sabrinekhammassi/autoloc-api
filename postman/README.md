# Collection Postman AutoLoc ? Atelier 6

## Import

1. Dans Postman, choisis Import.
2. Importe le fichier AutoLoc API - Atelier 6.postman_collection.json.
3. Importe le fichier AutoLoc Local (8080).postman_environment.json.
4. S?lectionne l'environnement AutoLoc Local (8080) en haut ? droite.

## D?marrage

Avec MySQL d?marr? et la configuration locale fournie, lance depuis la racine du projet :

    .\mvnw.cmd -DskipTests spring-boot:run

L'API ?coute sur http://localhost:8080. La configuration phpMyAdmin fournie utilise root, un mot de passe vide et 127.0.0.1.

## Parcours conseill?

1. Commence par les requ?tes GET pour consulter les IDs pr?sents.
2. Cr?e un client avant une r?servation.
3. V?rifie agenceId et vehiculeId avant de cr?er un v?hicule, un employ? ou un ?quipement.
4. Cr?e une r?servation avant son contrat, puis un paiement pour ce contrat.
5. Si un ID diff?re de 1, remplace la variable correspondante dans l'environnement avec l'ID re?u.

Les valeurs {{$timestamp}} rendent uniques les e-mails, num?ros de permis et immatriculations lors des cr?ations r?p?t?es.

## Captures conseill?es

- GET /api/agences ou GET /api/vehicules : r?ponse DTO avec 200 OK.
- Validation - e-mail invalide (400) : erreur d?taill?e dans fieldErrors.
- Agence inexistante (404) : r?ponse du gestionnaire centralis?.
