# Atelier 3 — couche Repository et CRUD

## Choix d'interface

Les neuf interfaces étendent `JpaRepository<T, Long>`. Ce choix fournit le CRUD, des résultats sous forme de `List`, le tri et la pagination, ainsi que les opérations JPA comme `flush`. La clé primaire de chacune des entités est un `Long`. Spring Data génère les implémentations au démarrage : aucune classe repository ni annotation `@Repository` n'est nécessaire.

| Entité | Interface | Justification |
|---|---|---|
| Agence | `IAgenceRepository` | CRUD, listes, pagination et recherche par nom pour l'initialiseur de développement. |
| Employe | `IEmployeRepository` | CRUD complet avec résultats en listes. |
| Vehicule | `IVehiculeRepository` | CRUD complet et vérification de l'immatriculation pour les données de démonstration. |
| Equipement | `IEquipementRepository` | CRUD complet avec résultats en listes. |
| Client | `IClientRepository` | CRUD complet avec résultats en listes. |
| Reservation | `IReservationRepository` | CRUD complet avec résultats en listes. |
| Contrat | `IContratRepository` | CRUD complet ; la suppression passe par le contexte JPA pour respecter la cascade et `orphanRemoval` des paiements. |
| Paiement | `IPaiementRepository` | CRUD et lecture directe disponibles ; la création et le retrait d'un paiement métier doivent rester pilotés par son contrat. |
| Maintenance | `IMaintenanceRepository` | CRUD complet avec résultats en listes. |

## Services et opérations CRUD

Chaque entité possède une interface `I…Service` et une implémentation `…ServiceImpl`. Les services exposent `create`, `findAll`, `findById`, `update` et `delete`. `findById` lève `NoSuchElementException` si l'identifiant n'existe pas ; `delete` vérifie aussi l'existence avant suppression. Les opérations d'écriture sont transactionnelles et les lectures sont marquées `readOnly`.

Le CRUD REST complet est exposé pour `Vehicule` (`/api/vehicules`) et `Agence` (`/api/agences`) :

| Méthode | Route | Opération |
|---|---|---|
| POST | `/api/vehicules` ou `/api/agences` | Création |
| GET | `/api/vehicules` ou `/api/agences` | Liste |
| GET | `/api/vehicules/{id}` ou `/api/agences/{id}` | Lecture par identifiant |
| PUT | `/api/vehicules/{id}` ou `/api/agences/{id}` | Mise à jour |
| DELETE | `/api/vehicules/{id}` ou `/api/agences/{id}` | Suppression |

`save` insère une entité nouvelle lorsque son identifiant est nul et met à jour une entité existante. `findById` renvoie un `Optional`, traité ici en résultat ou exception explicite. `deleteById` seul est silencieux si l'identifiant manque ; le service le précède donc d'une vérification. Les suppressions en lot de `JpaRepository` ne doivent pas être utilisées pour un contrat, car elles peuvent contourner la cascade et `orphanRemoval`.

## Relecture de qualité

Les corrections suivantes ont été apportées lors de la relecture du projet.

| Anomalie relevée en relecture | Correction |
|---|---|
| Les repositories existants ne respectaient pas la convention `I…Repository`. | Renommage en `IAgenceRepository` et `IVehiculeRepository`, puis mise à jour des injections. |
| Le service de véhicule était une classe concrète sans interface de service. | Remplacement par `IVehiculeService` et `VehiculeServiceImpl`. |
| La suppression par identifiant sans contrôle ne signalait pas l'absence de ligne. | Vérification par `existsById` avant `deleteById`, avec une exception explicite si l'entité est introuvable. |

## Vérification

La compilation a réussi avec Java 21 et Maven (`mvn -DskipTests compile`). Les tests automatisés et le démarrage de l'application avec MySQL n'ont pas été exécutés.
